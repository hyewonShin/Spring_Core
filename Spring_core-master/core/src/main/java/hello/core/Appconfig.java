package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberReopsitory;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration   //싱클톤 보장해준다

public class Appconfig {

    //@Bean memberService -> new MemoryMemberReopsitory()
    //@Bean orderService -> new MemoryMemberReopsitory()

    @Bean
    public MemberService memberService(){
        System.out.println("call Appconfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        System.out.println("call Appconfig.memberRepository");
        return new MemoryMemberReopsitory();
    }

    @Bean
    public OrderService orderService(){
        System.out.println("call Appconfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());

    }

    @Bean
    public DiscountPolicy discountPolicy(){
        return new FixDiscountPolicy();

    }

}
