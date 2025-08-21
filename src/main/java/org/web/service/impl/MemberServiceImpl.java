package org.web.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.web.dao.MemberDao;
import org.web.dto.MemberDto;
import org.web.entity.MemberEntity;
import org.web.service.MemberService;

import com.sun.nio.sctp.IllegalReceiveException;

public class MemberServiceImpl implements MemberService {

    private final MemberDao dao = MemberDao.getInstance();

    // -------------------- 회원 등록 --------------------
    @Override
    public int save(MemberDto memberDto) {
        // 이미 등록된 이메일 확인
        MemberEntity existing = dao.findByUserEmail(memberDto.getUserEmail());
        if (existing != null) {
            System.out.println("등록된 이메일이 있습니다!");
            throw new IllegalReceiveException("이미 이메일이 존재합니다");
        }

        // DTO -> Entity 변환 후 저장
        MemberEntity memberEntity = MemberEntity.toInsertMemberEntity(memberDto);
        return dao.save(memberEntity);
    }

    // -------------------- 회원 수정 --------------------
    @Override
    public int updateSave(MemberDto memberDto) {
        // 기존 회원 조회
        MemberEntity existing = dao.findById(memberDto.getMemberId());
        if (existing == null) {
            System.out.println("수정할 회원이 없습니다!");
            return 0;
        }

        // DTO -> Entity 변환 후 DAO update 호출
        MemberEntity updatedEntity = MemberEntity.toUpdateMemberEntity(memberDto);
        return dao.update(updatedEntity);
    }

    // -------------------- 회원 삭제 --------------------
    @Override
    public int deleteById(Long memberId) {
        return dao.deleteById(memberId);
    }

    // -------------------- 이메일로 회원 조회 --------------------
    @Override
    public MemberDto findByEmail(String email) {
        MemberEntity memberEntity = dao.findByUserEmail(email);
        if (memberEntity == null) return null;
        return MemberDto.toMemberDto(memberEntity);
    }

    // -------------------- 전체 회원 조회 --------------------
    @Override
    public List<MemberDto> findAll() {
        List<MemberEntity> entityList = dao.findAll();
        return entityList.stream()
                .map(MemberDto::toMemberDto)
                .collect(Collectors.toList());
    }

    // -------------------- ID로 회원 조회 --------------------
    @Override
    public MemberDto findById(Long memberId) {
        MemberEntity memberEntity = dao.findById(memberId);
        if (memberEntity == null) return null;
        return MemberDto.toMemberDto(memberEntity);
    }

    // -------------------- 회원 검색 ------------------------
    @Override
    public List<MemberDto> findByMemberIdAndAmount() {
        MemberDao dao = MemberDao.getInstance();
        List<MemberEntity> memberEntities = dao.findByMemberIdAndAmount();
        
        return memberEntities.stream()
                             .map(MemberDto::toMemberDto) // Entity → Dto 변환
                             .collect(Collectors.toList());
    }
		
}
