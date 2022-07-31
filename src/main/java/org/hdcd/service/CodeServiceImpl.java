package org.hdcd.service;

import java.util.ArrayList;
import java.util.List;

import org.hdcd.domain.CodeDetail;
import org.hdcd.domain.CodeGroup;
import org.hdcd.dto.CodeLabelValue;
import org.hdcd.repository.CodeDetailRepository;
import org.hdcd.repository.CodeGroupRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CodeServiceImpl implements CodeService {

	private final CodeGroupRepository codeGroupRepository;

	private final CodeDetailRepository codeDetailRepository;
	
	@Override
	public List<CodeLabelValue> getCodeGroupList() throws Exception {
		List<CodeGroup> codeGroups = codeGroupRepository.findAll(Sort.by(Direction.ASC, "groupCode"));
		
		List<CodeLabelValue> codeGroupList = new ArrayList<CodeLabelValue>();
		
		for (CodeGroup codeGroup : codeGroups) {
			CodeLabelValue codeLabelValue = new CodeLabelValue(codeGroup.getGroupCode(), codeGroup.getGroupName());
			codeGroupList.add(codeLabelValue);
		}
		return codeGroupList;
	}

	// 지정된 코드그룹의 상세 코드 목록 조회
	@Override
	public List<CodeLabelValue> getCodeList(String groupCode) throws Exception {
		List<CodeDetail> codeDetails = codeDetailRepository.getCodeList(groupCode);

		List<CodeLabelValue> codeList = new ArrayList<CodeLabelValue>();

		for (CodeDetail codeDetail : codeDetails){
			CodeLabelValue codeLabelValue = new CodeLabelValue(codeDetail.getCodeValue(), codeDetail.getCodeName());
			codeList.add(codeLabelValue);
		}
		return codeList;
	}

}
