package org.web.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.web.connect.DBConnect;
import org.web.entity.BoardEntity;

public class BoardDao {

    private BoardDao() {
        System.out.println("싱글톤 -> BoardDao");
    }

    private static class SingleTon {
        private static final BoardDao INSTANCE = new BoardDao();
    }

    public static BoardDao getInstance() {
        return SingleTon.INSTANCE;
    }

    // 게시글 작성
    public int save(BoardEntity boardEntity) {
        int rs = 0;

        String query = "INSERT INTO board_tb1(title, content, member_id, createTime) VALUES(?,?,?,?)";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstm = conn.prepareStatement(query)) {

            pstm.setString(1, boardEntity.getTitle());
            pstm.setString(2, boardEntity.getContent());
            pstm.setLong(3, boardEntity.getMemberId());

            if (boardEntity.getCreateTime() != null) {
                pstm.setTimestamp(4, Timestamp.valueOf(boardEntity.getCreateTime()));
            } else {
                pstm.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            }

            rs = pstm.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rs;
    }

    // 게시글 수정
    public int update(BoardEntity boardEntity) {
        Connection conn = null;
        PreparedStatement pstm = null;
        int rs = 0;

        try {
            conn = DBConnect.getConnection();
            String query = "UPDATE board_tb1 SET title=?, content=?, updateTime=? WHERE board_Id=?";
            pstm = conn.prepareStatement(query);
            pstm.setString(1, boardEntity.getTitle());
            pstm.setString(2, boardEntity.getContent());
            pstm.setTimestamp(3, Timestamp.valueOf(boardEntity.getUpdateTime()));
            pstm.setLong(4, boardEntity.getBoardId());

            rs = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstm != null) pstm.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rs;
    }

    // 게시글 삭제
    public int deleteById(Long boardId) {
        Connection conn = null;
        PreparedStatement pstm = null;
        int rs = 0;

        try {
            conn = DBConnect.getConnection();
            String query = "DELETE FROM board_tb1 WHERE board_Id=?";
            pstm = conn.prepareStatement(query);
            pstm.setLong(1, boardId);

            rs = pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstm != null) pstm.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rs;
    }

    // ID로 게시글 조회
    public BoardEntity findById(Long boardId) {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        BoardEntity entity = null;

        try {
            conn = DBConnect.getConnection();
            String query = "SELECT board_Id, title, content, member_Id, updateTime, createTime FROM board_tb1 WHERE board_Id=?";
            pstm = conn.prepareStatement(query);
            pstm.setLong(1, boardId);
            rs = pstm.executeQuery();

            if (rs.next()) {
                entity = new BoardEntity(
                        rs.getLong("board_Id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getLong("member_Id"),
                        rs.getTimestamp("updateTime") != null ? rs.getTimestamp("updateTime").toLocalDateTime() : null,
                        rs.getTimestamp("createTime") != null ? rs.getTimestamp("createTime").toLocalDateTime() : null
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return entity;
    }

    // 전체 게시글 조회
    public List<BoardEntity> findAll() {
        List<BoardEntity> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            conn = DBConnect.getConnection();
            String query = "SELECT board_Id, title, content, member_Id, updateTime, createTime FROM board_tb1 ORDER BY board_Id DESC";
            pstm = conn.prepareStatement(query);
            rs = pstm.executeQuery();

            while (rs.next()) {
                list.add(new BoardEntity(
                        rs.getLong("board_Id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getLong("member_Id"),
                        rs.getTimestamp("updateTime") != null ? rs.getTimestamp("updateTime").toLocalDateTime() : null,
                        rs.getTimestamp("createTime") != null ? rs.getTimestamp("createTime").toLocalDateTime() : null
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    // 특정 회원 게시글 조회
    public List<BoardEntity> findByMemberId(Long memberId) {
        List<BoardEntity> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;

        try {
            conn = DBConnect.getConnection();
            String query = "SELECT b.board_id, b.title, b.content, b.member_id, b.updateTime, b.createTime " +
                    "FROM member_tb1 m " +
                    "INNER JOIN board_tb1 b ON m.member_id = b.member_id " +
                    "WHERE b.member_id = ?";
            pstm = conn.prepareStatement(query);
            pstm.setLong(1, memberId);
            rs = pstm.executeQuery();

            while (rs.next()) {
                list.add(new BoardEntity(
                        rs.getLong("board_Id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getLong("member_Id"),
                        rs.getTimestamp("updateTime") != null ? rs.getTimestamp("updateTime").toLocalDateTime() : null,
                        rs.getTimestamp("createTime") != null ? rs.getTimestamp("createTime").toLocalDateTime() : null
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return list;
    }
}