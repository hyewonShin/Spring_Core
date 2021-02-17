package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberReopsitory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

//설정정보
@Configuration
// @Component 어노테이션 붙은 클래스를 찾아서 자동으로 스프링 빈을 등록해준다.
@ComponentScan(
        basePackages = "hello.core.member",
        basePackageClasses = AutoAppConfig.class,
        // @Conponent를 찾는 시작 위치 지정. 이 패키지(클래스)를 포함해서 하위 패키지를 모두 탐색한다(시간단축위해 사용).
        // 만약 지정하지 않으면 @ComponentScan 이 붙은 설정 정보 클래스의 패키지가 시작 위치가 된다.
        excludeFilters =
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
        // excludeFilters : 스프링빈을 등록하는데 제외할 것을 지정해주는 것.
        // Configuration으로 수동으로 스프링빈 설정정보를 이미 등록해주었기 때문에
        // 자동으로 등록해주는 ComponentScan  과 충돌하여 제외해준다.
        // 보통 실무에서는 따로 제외하지는 않지만, 예제를 유지하기위해 사용했음.
)
public class AutoAppConfig {

        @Bean(name = "memoryMemberReopsitory")
        MemberRepository memberRepository(){
                return new MemoryMemberReopsitory();
        }
}



//컴포넌트 스캔을 사용하려면 먼저 @ComponentScan을 설정 정보에 붙여주면 된다.
//기존의 AppConfig와는 다르게 @Bean 으로 등록한 클래스가 하나도 없다.
//컴포넌트 스캔은 이름 그대로 @Conponent 에너테이션이 붙은 클래스를 스캔해서 스프링 빈으로 등록한다.