package org.web.service;

import java.util.List;

import org.web.dto.MemberDto;

public interface MemberService {
	
int save(MemberDto memberDto);
	
	int updateSave(MemberDto memberDto);

	int deleteById(Long memberId);

	MemberDto findByEmail(String email);
	
	List<MemberDto> findAll();
	
	MemberDto findById(Long memberId);
	
	// 상품의 재고량이 3000개 이상 남은 성품을 등록한 사람의 이메일을 조회
	List<MemberDto> findByMemberIdAndAmount();

}
