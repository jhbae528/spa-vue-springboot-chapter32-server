package org.hdcd.repository;

import org.hdcd.domain.CodeDetail;
import org.hdcd.domain.CodeDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CodeDetailRepository extends JpaRepository<CodeDetail, CodeDetailId> {

    @Query("SELECT max(cd.sortSeq) FROM CodeDetail cd WHERE cd.groupCode = ?1")
    public List<Object[]> getMaxSortSeq(String groupCode);

    // 지정된 그룹코드의 상세 목록 조회
    @Query("SELECT cd FROM CodeDetail cd WHERE cd.groupCode = ?1")
    public List<CodeDetail> getCodeList(String groupCode);

}
