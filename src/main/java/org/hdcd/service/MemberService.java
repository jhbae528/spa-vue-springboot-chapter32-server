package org.hdcd.service;

import org.hdcd.domain.Member;

import java.util.List;

public interface MemberService {

    public List<Member> list() throws Exception;

    public Member read(Long userNo) throws Exception;

    public void register(Member user) throws Exception;

    public void modify(Member user) throws Exception;

    public void remove(Long userNo) throws Exception;

    public void setupAdmin(Member member) throws Exception;

    public long countAll() throws Exception;
}
