package com.shop.service;

import com.shop.dto.MemberFormDto;
import com.shop.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
//@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;
    
    @PersistenceContext
    EntityManager em;

    public Member createMember(){
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("test2@email.com");
        memberFormDto.setName("홍길동");
        memberFormDto.setAddress("서울시 마포구 합정동");
        memberFormDto.setPassword("1234");
        return Member.createMember(memberFormDto, passwordEncoder);
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void saveMemberTest(){
        Member member = createMember();
        // 회원 가입 후 회원 정보가 디비에 반영 되는 부분이 포함 된 상태.
        System.out.println("savedMember.getEmail() 호출 전의 정보 member: " + member.getEmail());
        Member savedMember = memberService.saveMember(member);
        
        System.out.println("savedMember.getEmail() 호출 후의 정보 " + savedMember.getEmail());
//        em.flush();
//        assertEquals(member.getEmail(), savedMember.getEmail());
//        assertEquals(member.getName(), savedMember.getName());
//        assertEquals(member.getAddress(), savedMember.getAddress());
//        assertEquals(member.getPassword(), savedMember.getPassword());
//        assertEquals(member.getRole(), savedMember.getRole());
    }

    @Test
    @DisplayName("중복 회원 가입 테스트")
    public void saveDuplicateMemberTest(){
        Member member1 = createMember();
        Member member2 = createMember();
        memberService.saveMember(member1);
        Throwable e = assertThrows(IllegalStateException.class, () -> {
            memberService.saveMember(member2);});
        assertEquals("이미 가입된 회원입니다.", e.getMessage());
    }
}