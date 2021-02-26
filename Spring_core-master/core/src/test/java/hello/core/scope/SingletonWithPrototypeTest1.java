package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.Assertions.*;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);
    }

    @Test
    void singletonClientUsePrototype(){
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean1.logic();
        assertThat(count2).isEqualTo(1);

        ClientBean clientBean3 = ac.getBean(ClientBean.class);
        int count3 = clientBean1.logic();
        assertThat(count3).isEqualTo(1);

    }

    @Scope("singleton")
    static class ClientBean {

        @Autowired
        private Provider<PrototypeBean> prototypeBeanProvider;
        //Provider : javax.inject 임폴트 (미리 그레들에 설정해놓기)
        // - 자바 표준인 JSR.330.Provide 이다.

        //ObjectProvider : 지정한 빈을 컨테이너에서 대신 찾아주는 DL 서비스를 제공하는 것
        // - 스프링 외에 별도의 의존관계 추가가 필요 없기 때문에 편리하다.

        public int logic(){
            PrototypeBean prototypeBean = prototypeBeanProvider.get();
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

    @Scope("prototype")
    static class PrototypeBean {

        private int count = 0;

        public void addCount() {
            count++;
        }

        // 카운트 조회
        public int getCount(){
            return count; // return은 메서드 외부에서 값을 사용하기 위해 사용.
        }

        @PostConstruct
        public void init(){
            System.out.println("PrototypeBean.init " + this);  //this: 현재 나의 참조값
        }

        @PreDestroy
        public void destroy(){
            System.out.println("PrototypeBean.destroy");
        }
    }
}

// 실문에선 대부분 싱클톤 자체로 문제해결이 가능하기 때문에, 싱글톤과 프로토타입을
// 함께 쓰는 경우는 거의 없다.

// # 웹 스코프 종류
// 1. request ★
// HTTP 요청 하나가 들어오고 나갈 때 까지 유지되는 스코프, 각각의 HTTP 요청마다 별도의
// 빈 인스턴스가 생성되고, 관리된다.
// 2. session
// HTTP Session 과 동일한 생명주기를 가지는 스코프
// 3. application
// 서블릿 컨텍스트와 동일한 생명주기를 가지는 스코프
// 4. websocket
// 웹 소켓과 동일한 생명주기를 가지는 스코프