package com.myJwtTest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class TestRepository {

    private final List<Member> members = new ArrayList<>();
    private static int sequence = 0;

    // 유저 생성
    public TestRepository() {
        members.add(new Member(++sequence, "kim", "1234"));
    }

    // 유저 확인
    // 포스트 맨에서 입력한 유저, 패스워드 가 LoginReq 에 담겨짐. 
    // members 리스트는 하나의 멤버를 가지고 있습니다. kim/1234
    public boolean login(LoginReq req) {
    	// m : mebers에서 하나의 멤버를 꺼내어서 : 입력된 계정: kim/1234
    	// req :포스트 맨에서 입력한 유저, 패스워드 가 LoginReq 에 담겨짐. 
         return members.stream()
                .anyMatch(m -> m.getUsername().equals(req.getUsername()) && m.getPassword().equals(req.getPassword()));
    }
}
