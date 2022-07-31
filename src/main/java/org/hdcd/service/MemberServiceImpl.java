package org.hdcd.service;

import lombok.RequiredArgsConstructor;
import org.hdcd.domain.Member;
import org.hdcd.domain.MemberAuth;
import org.hdcd.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository repository;

    @Override
    public List<Member> list() throws Exception {
        List<Object[]> list = repository.listAllMember();

        List<Member> response = new ArrayList<Member>();
        for(Object[] valueArray : list){
            Member member = new Member();
            member.setUserNo((Long)valueArray[0]);
            member.setUserId((String)valueArray[1]);
            member.setUserPw((String)valueArray[2]);
            member.setUserName((String)valueArray[3]);
            member.setJob((String)valueArray[4]);
            member.setCoin((int)valueArray[5]);
            member.setRegDate((LocalDateTime) valueArray[6]);

            response.add(member);
        }
        return response;
    }

    @Override
    public Member read(Long userNo) throws Exception {
        Member response = repository.findById(userNo).get();
        return response;
    }

    @Override
    public void register(Member member) throws Exception {
        Member memberEntity = new Member();
        memberEntity.setUserId(member.getUserId());
        memberEntity.setUserPw(member.getUserPw());
        memberEntity.setUserName(member.getUserName());
        memberEntity.setJob(member.getJob());

        MemberAuth memberAuth = new MemberAuth();
        memberAuth.setAuth("ROLE_MEMBER");

        memberEntity.addAuth(memberAuth);
        repository.save(memberEntity);

        member.setUserNo(memberEntity.getUserNo());
    }

    @Override
    public void modify(Member member) throws Exception {
        Member memberEntity = repository.findById(member.getUserNo()).get();
        memberEntity.setUserName(member.getUserName());
        memberEntity.setJob(member.getJob());

        List<MemberAuth> authEntityList = memberEntity.getAuthList();
        List<MemberAuth> authList = member.getAuthList();

        for(int i = 0; i < authList.size(); i++){
            MemberAuth auth = authList.get(i);
            if(i < authEntityList.size()){
                MemberAuth authEntity = authEntityList.get(i);
                authEntity.setAuth(auth.getAuth());
            }
        }
        repository.save(member);
    }

    @Override
    public void remove(Long userNo) throws Exception {
        repository.deleteById(userNo);
    }

    @Override
    public void setupAdmin(Member member) throws Exception {
        Member memberEntity = new Member();
        memberEntity.setUserId(member.getUserId());
        memberEntity.setUserPw(member.getUserPw());
        memberEntity.setUserName(member.getUserName());
        memberEntity.setJob(member.getJob());

        MemberAuth memberAuth = new MemberAuth();
        memberAuth.setAuth("ROLE_ADMIN");
        repository.save(memberEntity);
    }

    @Override
    public long countAll() throws Exception {
        return repository.count();
    }
}
