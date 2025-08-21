package org.web.entity;

import java.time.LocalDateTime;

import org.web.dto.BoardDto;

public class BoardEntity {

	private Long boardId;
	private String title;
	private String content;
	private Long memberId;
	private LocalDateTime updateTime;
	private LocalDateTime createTime;

	public BoardEntity() {
		// TODO Auto-generated constructor stub
	}

	public BoardEntity(Long boardId, String title, String content, Long memberId, LocalDateTime updateTime,
			LocalDateTime createTime) {
		super();
		this.boardId = boardId;
		this.title = title;
		this.content = content;
		this.memberId = memberId;
		this.updateTime = updateTime;
		this.createTime = createTime;
	}

	public Long getBoardId() {
		return boardId;
	}

	public void setBoardId(Long boardId) {
		this.boardId = boardId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	// 게시글 작성
	// 회원가입
	public static BoardEntity toInsertBoardEntity(BoardDto boardDto) {

		BoardEntity boardEntity = new BoardEntity();

//		boardEntity.setBoardId(boardDto.getBoardId());
		boardEntity.setTitle(boardDto.getTitle());
		boardEntity.setContent(boardDto.getContent());
		boardEntity.setMemberId(boardDto.getMemberId());
		// + 시간

		return boardEntity;
	}

	// 게시글 수정
	public static BoardEntity toUpdateBoardEntity(BoardDto boardDto) {

		BoardEntity boardEntity = new BoardEntity();

		boardEntity.setBoardId(boardDto.getBoardId());
		boardEntity.setTitle(boardDto.getTitle());
		boardEntity.setContent(boardDto.getContent());
		boardEntity.setMemberId(boardDto.getMemberId());
		// + 시간

		return boardEntity;
	}

	@Override
	public String toString() {
		return "BoardEntity [boardId=" + boardId + ", title=" + title + ", content=" + content + ", memberId="
				+ memberId + ", updateTime=" + updateTime + ", createTime=" + createTime + "]";
	}

}
