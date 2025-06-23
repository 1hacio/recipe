package com.recipick.backend.dao;

import java.sql.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class PantryDAO {
    private final Connection conn;

    public PantryDAO() {
        conn = DBconnect.getConnection();
    }

    public List<PantryItem> getAllItems() {
        List<PantryItem> items = new ArrayList<>();
        String sql = "SELECT p.id, i.name, p.quantity, p.unit, p.expiry_date " +
                     "FROM pantry p JOIN ingredient i ON p.ingredient_id = i.id";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                PantryItem item = new PantryItem();
                item.setId(rs.getLong("id"));
                item.setIngredientName(rs.getString("name"));
                item.setQuantity(rs.getDouble("quantity"));
                item.setUnit(rs.getString("unit"));
                item.setExpiryDate(rs.getDate("expiry_date"));
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public void insertItem(PantryItem item) {
        try {
            long ingredientId = getOrCreateIngredientId(item.getIngredientName());

            String sql = "INSERT INTO pantry (user_id, ingredient_id, quantity, unit, expiry_date) " +
                         "VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setLong(1, 1); // 임시 user_id = 1
                pstmt.setLong(2, ingredientId);
                pstmt.setDouble(3, item.getQuantity());
                pstmt.setString(4, item.getUnit());
                pstmt.setDate(5, new java.sql.Date(item.getExpiryDate().getTime()));
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateItem(PantryItem item) {
        try {
            long ingredientId = getOrCreateIngredientId(item.getIngredientName());

            String sql = "UPDATE pantry SET ingredient_id = ?, quantity = ?, unit = ?, expiry_date = ? WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setLong(1, ingredientId);
                pstmt.setDouble(2, item.getQuantity());
                pstmt.setString(3, item.getUnit());
                pstmt.setDate(4, new java.sql.Date(item.getExpiryDate().getTime()));
                pstmt.setLong(5, item.getId());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteItem(long id) {
        String sql = "DELETE FROM pantry WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private long getOrCreateIngredientId(String name) throws SQLException {
        String selectSql = "SELECT id FROM ingredient WHERE name = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(selectSql)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getLong("id");
            }
        }

        String insertSql = "INSERT INTO ingredient (name, default_unit) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, name);
            pstmt.setString(2, "g"); // 기본 단위: g
            pstmt.executeUpdate();

            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) {
                return keys.getLong(1);
            }
        }

        throw new SQLException("재료 ID를 생성하거나 조회할 수 없습니다.");
    }
}
