package org.hdcd.repository;

import org.hdcd.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // JPA 쿼리메서드를 사용하여 사용자 정보 조회
    public List<Member> findByUserId(String userId);

    @Query("SELECT m.userNo, m.userId, m.userPw, m.userName, cd.codeName, m.coin, m.regDate "
            + "FROM Member m "
            + "INNER JOIN CodeDetail cd ON cd.codeValue = m.job "
            + "INNER JOIN CodeGroup cg ON cg.groupCode = cd.groupCode "
            + "WHERE cg.groupCode = 'A01' "
            + "ORDER BY m.regDate DESC")
    public List<Object[]> listAllMember();
}
