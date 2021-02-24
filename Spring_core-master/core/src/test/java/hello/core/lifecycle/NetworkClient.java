package hello.core.lifecycle;

public class NetworkClient {

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
}
