package org.web.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.web.connect.DBConnect;
import org.web.constraint.Role;
import org.web.dto.MemberDto;
import org.web.entity.MemberEntity;

public class MemberDao {

    // ---------------- 싱글톤 ----------------
    private MemberDao() {
        System.out.println("싱글톤 -> MemberDao");
    }

    private static class SingleTon {
        private static final MemberDao INSTANCE = new MemberDao();
    }

    public static MemberDao getInstance() {
        return SingleTon.INSTANCE;
    }

    // ---------------- 저장 ----------------
    public int save(MemberEntity memberEntity) {
        String query = "INSERT INTO member_tb2(user_email, user_name, age, role, createTime) VALUES(?,?,?,?,?)";
        int result = 0;

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstm = conn.prepareStatement(query)) {

            pstm.setString(1, memberEntity.getUserEmail());
            pstm.setString(2, memberEntity.getUserName());
            pstm.setInt(3, memberEntity.getAge());
            pstm.setString(4, memberEntity.getRole().name());
            pstm.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));

            result = pstm.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    // ---------------- 이메일 조회 ----------------
    public MemberEntity findByUserEmail(String email) {
        MemberEntity memberEntity = null;
        String query = "SELECT * FROM member_tb2 WHERE user_email=?";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstm = conn.prepareStatement(query)) {

            pstm.setString(1, email);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    memberEntity = new MemberEntity(
                            rs.getLong("member_id"),
                            rs.getString("user_email"),
                            rs.getString("user_name"),
                            rs.getInt("age"),
                            Role.valueOf(rs.getString("role")),
                            rs.getTimestamp("createTime").toLocalDateTime(),
                            rs.getTimestamp("updateTime") != null ? rs.getTimestamp("updateTime").toLocalDateTime() : null
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return memberEntity;
    }

    // ---------------- 전체 조회 ----------------
    public List<MemberEntity> findAll() {
        List<MemberEntity> memberList = new ArrayList<>();
        String query = "SELECT * FROM member_tb2";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstm = conn.prepareStatement(query);
             ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                memberList.add(new MemberEntity(
                        rs.getLong("member_id"),
                        rs.getString("user_email"),
                        rs.getString("user_name"),
                        rs.getInt("age"),
                        Role.valueOf(rs.getString("role")),
                        rs.getTimestamp("createTime").toLocalDateTime(),
                        rs.getTimestamp("updateTime") != null ? rs.getTimestamp("updateTime").toLocalDateTime() : null
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return memberList;
    }

    // ---------------- ID 조회 ----------------
    public MemberEntity findById(Long memberId) {
        MemberEntity memberEntity = null;
        String query = "SELECT * FROM member_tb2 WHERE member_id=?";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstm = conn.prepareStatement(query)) {

            pstm.setLong(1, memberId);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    memberEntity = new MemberEntity(
                            rs.getLong("member_id"),
                            rs.getString("user_email"),
                            rs.getString("user_name"),
                            rs.getInt("age"),
                            Role.valueOf(rs.getString("role")),
                            rs.getTimestamp("createTime").toLocalDateTime(),
                            rs.getTimestamp("updateTime") != null ? rs.getTimestamp("updateTime").toLocalDateTime() : null
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return memberEntity;
    }

 // ---------------- 회원 수정 ----------------
    public int update(MemberEntity memberEntity) {
        String query = "UPDATE member_tb2 SET user_email=?, user_name=?, age=?, role=?, updateTime=? WHERE member_id=?";
        int result = 0;

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstm = conn.prepareStatement(query)) {

            pstm.setString(1, memberEntity.getUserEmail());
            pstm.setString(2, memberEntity.getUserName());
            pstm.setInt(3, memberEntity.getAge());

            // role null 안전 처리
            if (memberEntity.getRole() != null) {
                pstm.setString(4, memberEntity.getRole().name());
            } else {
                pstm.setString(4, "USER"); // 기본값 지정
            }

            pstm.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            pstm.setLong(6, memberEntity.getMemberId());

            result = pstm.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    // ---------------- 회원 삭제 ----------------
    public int deleteById(Long memberId) {
        String query = "DELETE FROM member_tb2 WHERE member_id=?";
        int result = 0;

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstm = conn.prepareStatement(query)) {

            pstm.setLong(1, memberId);
            result = pstm.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    // ---------------- 회원 검색 (조건: amount >= 3000) ----------------
    public List<MemberEntity> findByMemberIdAndAmount() {
        List<MemberEntity> memberList = new ArrayList<>();
        String query = "SELECT DISTINCT m.* FROM member_tb2 m INNER JOIN project_tb1 p ON m.member_id = p.member_id WHERE p.amount >= 3000";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstm = conn.prepareStatement(query);
             ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                memberList.add(new MemberEntity(
                        rs.getLong("member_id"),
                        rs.getString("user_email"),
                        rs.getString("user_name"),
                        rs.getInt("age"),
                        Role.valueOf(rs.getString("role")),
                        rs.getTimestamp("createTime").toLocalDateTime(),
                        rs.getTimestamp("updateTime") != null ? rs.getTimestamp("updateTime").toLocalDateTime() : null
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return memberList;
    }
}