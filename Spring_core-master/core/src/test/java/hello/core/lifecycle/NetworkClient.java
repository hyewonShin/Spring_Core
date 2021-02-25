package hello.core.lifecycle;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NetworkClient  {

    private String url;

    // 디폴트 생성자
    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
        connect();
        call("초기화 연결 메세지");
    }

    // 외부에서 url 넣을 수 있도록 setter 생성
    public void setUrl(String url) {
        this.url = url;
    }

    // 서비스 시작시 호출
    public void connect(){
        System.out.println("connect: " + url);
    }

    // 연결한 서버에 메세지 전달
    public void call(String messsage) {
        System.out.println("call: " + url + " message = " + messsage);
    }

    // 서비스 종료시 호출
    public void disconnect(){
        System.out.println("close " + url);
    }


    @PostConstruct  // 초기화 완료 알려주는 애노테이션
    public void init() {
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메세지");
    }

    @PreDestroy // 빈이 소멸되기 전 알려준느 애노테이션
    public void close() {
        System.out.println("NetworkClient.close");
        disconnect();
    }
}

// implements InitializingBean, DisposableBean 는
// 초창기의 방법들이고, 지금은 잘(거의) 사용하지 않는다.

// 현재는 @PostConstuct, @PreDestory 애노테니션을 주로 사용한다.
// 최신 스프링에서 가장 권장하는 방법이다.
// 애노테이션 하나만 붙이면 되므로 매우 편리하다.
// 컴포넌트 스캔과 잘 어울린다.
// 유일한 단점은 외부 라이브러리에는 적응하지 못한다는 것이다. 외부 라이브러리를 초기화, 종료 해야하면
// @Bean의 기능을 사용하자.


// 빈 스코프란?
// 스프링 빈이 스프링 컨테이너의 시작과 함께 시작되어서 스프링 컨테이너가 종료될 때까지 유지되는 것.
// 이것은 스프링 빈이 기본적으로 싱글톤 스코프로 생성되기 때문이다.
// 스코프는 번역 그대로 빈이 존재할 수 있는 범위를 뜻한다.

// 프로토타입: 스프링 컨테이너는 프로토타입 빈의 생성과 의존관계 주입, 초기화까지만 관여하고 더는
// 관여하지 않는 매우 짧은 범위의 스코프이다.
// 그래서 @PreDestory 같은 종료 메스드가 호출되지 않는다.

// 웹 관련 스코프
// request : 웹 요청이 들어오고 나갈때까지 유지되는 스코프이다.

// 프로
