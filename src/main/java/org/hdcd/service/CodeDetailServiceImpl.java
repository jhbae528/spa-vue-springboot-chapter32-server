package org.hdcd.service;

import java.util.List;

import org.hdcd.domain.CodeDetail;
import org.hdcd.domain.CodeDetailId;
import org.hdcd.repository.CodeDetailRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CodeDetailServiceImpl implements CodeDetailService {

	private final CodeDetailRepository repository;

	@Override
	public List<CodeDetail> list() throws Exception {
		return repository.findAll(Sort.by(Direction.ASC, "groupCode", "codeValue"));
	}

	@Override
	public CodeDetail read(CodeDetail codeDetail) throws Exception {
		CodeDetailId codeDetailId = new CodeDetailId();
		codeDetailId.setGroupCode(codeDetail.getGroupCode());
		codeDetailId.setCodeValue(codeDetail.getCodeValue());
		return repository.getOne(codeDetailId);
	}

	@Override
	public void register(CodeDetail codeDetail) throws Exception {
		String groupCode = codeDetail.getGroupCode();
		List<Object[]> rsList = repository.getMaxSortSeq(groupCode);
		
		Integer maxSortSeq = 0;
		if(rsList.size() > 0) {
			Object[] maxvalues = rsList.get(0);
			System.out.println(maxvalues);
			if(maxvalues != null && maxvalues.length > 0) {
				maxSortSeq = (Integer)maxvalues[0];
			}
		}
		
		codeDetail.setSortSeq(maxSortSeq + 1);
		
		repository.save(codeDetail);
	}

	@Override
	public void modify(CodeDetail codeDetail) throws Exception {
		CodeDetailId codeDetailId = new CodeDetailId();
		codeDetailId.setGroupCode(codeDetail.getGroupCode());
		codeDetailId.setCodeValue(codeDetail.getCodeValue());
		
		CodeDetail codeDetailEntity = repository.getOne(codeDetailId);
		
		codeDetailEntity.setCodeValue(codeDetail.getCodeValue());
		codeDetailEntity.setCodeName(codeDetail.getCodeName());
		
		repository.save(codeDetailEntity);		
	}

	@Override
	public void remove(CodeDetail codeDetail) throws Exception {
		CodeDetailId codeDetailId = new CodeDetailId();
		codeDetailId.setGroupCode(codeDetail.getGroupCode());
		codeDetailId.setCodeValue(codeDetail.getCodeValue());
		
		repository.deleteById(codeDetailId);
	}
}
