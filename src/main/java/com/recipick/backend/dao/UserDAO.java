package com.recipick.backend.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class UserDAO {
    private final Connection conn;

    public UserDAO() {
        conn = DBconnect.getConnection(); // DB 연결 유틸
    }

    // 사용자 전체 조회
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT id, email, name, picture_url, created_at, last_login FROM users";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setEmail(rs.getString("email"));
                user.setName(rs.getString("name"));
                user.setPictureUrl(rs.getString("picture_url"));

                Timestamp created = rs.getTimestamp("created_at");
                if (created != null) user.setCreatedAt(created.toLocalDateTime());

                Timestamp last = rs.getTimestamp("last_login");
                if (last != null) user.setLastLogin(last.toLocalDateTime());

                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // 사용자 삽입
    public void insertUser(User user) {
        String sql = "INSERT INTO users (email, name, picture_url, created_at, last_login) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getPictureUrl());
            stmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now())); // createdAt
            stmt.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now())); // lastLogin
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 마지막 로그인 시간 갱신
    public void updateLastLogin(String email) {
        String sql = "UPDATE users SET last_login = ? WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setString(2, email);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 사용자 삭제
    public void deleteUser(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 이메일로 사용자 찾기
    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getLong("id"));
                    user.setEmail(rs.getString("email"));
                    user.setName(rs.getString("name"));
                    user.setPictureUrl(rs.getString("picture_url"));
                    user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    user.setLastLogin(rs.getTimestamp("last_login").toLocalDateTime());
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
