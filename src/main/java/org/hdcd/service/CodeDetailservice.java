package org.hdcd.service;

import java.util.List;

import org.hdcd.domain.CodeDetail;

public interface CodeDetailservice {
	
	public List<CodeDetail> list() throws Exception;
	
	public void register(CodeDetail codeDetail) throws Exception;
	
	public CodeDetail read(CodeDetail codeDetail) throws Exception;
	
	public void modify(CodeDetail codeDetail) throws Exception;
	
	public void remove(CodeDetail codeDetail) throws Exception;

}
