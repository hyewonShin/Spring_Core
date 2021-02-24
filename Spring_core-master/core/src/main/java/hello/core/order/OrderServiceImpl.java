package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor  // final이 붙은 생성자 생성 ( 많이 사용함 )
public class OrderServiceImpl implements OrderService{

    private  MemberRepository memberRepository;
    private  DiscountPolicy discountPolicy;

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    //테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }


}

// 최근에는 생선자를 딱 1개 두고, @Autowired 를 생략하는 방법을 주로 사용한다.
// 여기에 Lombok 라이브러리의 @RequiredArgsConstructor 함께 사용하면 기능은
// 다 제공하면서, 코드는 깔끔하게 사용할 수 있다.

// 빈 이름이 중복될 때
//1. @Autowired 는 타입 매칭을 시도하고, 이때 여러 빈이 있으면 필드 이름(파라미터 이름)으로
// 빈 이름을 추가 매칭한다.

//2. @Primary 는 우선순위를 정하는 방법이다. @Autowired 시에 여러 빈 매칭되면
// @Primary 가 우선권을 가진다.
