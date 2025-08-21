package org.web.service;

import java.util.List;

import org.web.dto.BoardDto;

public interface BoardService {

	// 게시글 아이디가 없으면 추가  -> Spring JPA .save()
		int save(BoardDto boardDto);

		// 게시글 아이디가 있으면 수정  -> Spring JPA .save()
		int updateSave(BoardDto boardDto);

		int deleteById(Long boardId);

		List<BoardDto> findAll();

		BoardDto findById(Long boardId);
		
		// 회원번호가 1 글을 작성한 한 사람의
		// 게시글 목록 조회 하시오 inner join이용
		List<BoardDto> findByMemberId(Long memberId);
}
