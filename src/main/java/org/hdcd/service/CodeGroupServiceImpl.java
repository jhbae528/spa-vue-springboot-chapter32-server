package org.hdcd.service;

import java.util.List;

import org.hdcd.domain.CodeGroup;
import org.hdcd.repository.CodeGroupRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CodeGroupServiceImpl implements CodeGroupService {

	private final CodeGroupRepository repository;

	@Override
	public List<CodeGroup> list() throws Exception {
		return repository.findAll(Sort.by(Direction.DESC, "groupCode"));
	}

	@Override
	public CodeGroup read(String groupCode) throws Exception {
		return repository.getOne(groupCode);
	}

	@Override
	public void register(CodeGroup codeGroup) throws Exception {
		repository.save(codeGroup);
	}

	@Override
	public void modify(CodeGroup codeGroup) throws Exception {
		CodeGroup codeGroupEntity = repository.getOne(codeGroup.getGroupCode());
		
		codeGroupEntity.setGroupName(codeGroup.getGroupName());
		
		repository.save(codeGroupEntity);		
	}

	@Override
	public void remove(String groupCode) throws Exception {
		repository.deleteById(groupCode);
	}
}
