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
    public boolean login(LoginReq req) {
         return members.stream()
                .anyMatch(m -> m.getUsername().equals(req.getUsername()) && m.getPassword().equals(req.getPassword()));
    }
}
