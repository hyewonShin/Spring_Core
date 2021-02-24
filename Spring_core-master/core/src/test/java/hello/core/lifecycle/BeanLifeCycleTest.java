package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest(){
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        // 빈 조회
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();
    }

    @Configuration
    static class LifeCycleConfig {
        @Bean
        public NetworkClient networkClient(){
            NetworkClient networkClient = new NetworkClient(); //생성자호출됌
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }
    }
}


// 초기화 : 의존관계주입이 다 완료된 다음, 외부랑 연결시키는 것
// 의존관계주입이 완료되면 스프링은 스피링빈에게 콜백 메서드를 통통서 초기화 시점을 알려준다.

// 스프링빈의 이벤트 라이프사이클
//스프링 컨테이너 생성 -> 스프링빈 생성 -> 의존관계 주입 -> 초기화 콜백 -> 사용 ->소멸 전 콜백 -> 스프링 종료

// 초기화 콜백 : 빈이 생성되고, 빈의 의존관계 주입이 완료된 후 호출
// 소멸전 콜백 : 빈이 소멸되기 직전에 호출

// 객체의 생성과 초기화를 분리하자