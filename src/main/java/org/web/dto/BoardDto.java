package org.web.dto;

import java.time.LocalDateTime;

import org.web.entity.BoardEntity;

public class BoardDto {
	
	private Long boardId;
	private String	title;
	private String	content;
	private Long memberId;
	private LocalDateTime updateTime;
	private LocalDateTime createTime;
	
	public BoardDto() {
		// TODO Auto-generated constructor stub
	}
	
	public BoardDto(Long boardId, String title, String content, Long memberId, LocalDateTime updateTime,
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
	
	//Entity -> Dto
		public static BoardDto toBoardDto(BoardEntity boardEntity) {

			BoardDto boardDto = new BoardDto();

			boardDto.setBoardId(boardEntity.getBoardId());
			boardDto.setTitle(boardEntity.getTitle());
			boardDto.setContent(boardEntity.getContent());
			boardDto.setMemberId(boardEntity.getMemberId());
			// + 시간

			return boardDto;
		}
		
	@Override
	public String toString() {
		return "BoardDto [boardId=" + boardId + ", title=" + title + ", content=" + content + ", memberId=" + memberId
				+ ", updateTime=" + updateTime + ", createTime=" + createTime + "]";
	}
	
	

}
