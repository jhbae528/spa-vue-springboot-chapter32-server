package org.hdcd.common.security;

import lombok.extern.slf4j.Slf4j;
import org.hdcd.common.security.domain.CustomUser;
import org.hdcd.domain.Member;
import org.hdcd.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("userName : " + username);

        List<Member> memberList = repository.findByUserId(username);
        Member member = memberList.get(0);

        log.info("member : " + member);

        UserDetails userDetails = null;
        if(member != null){
            userDetails = new CustomUser(member);
        }
        return userDetails;
    }
}
