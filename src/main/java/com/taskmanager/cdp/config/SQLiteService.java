package com.taskmanager.cdp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SQLiteService {

    @Value("${sqlite.url}")
    private String DB_URL;

    public Integer[] getIds(String orgName, String projectName) {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            Integer orgId = getIdByField(connection, "organizations", "name", orgName);
            Integer projectId = getIdByField(connection, "projects", "name", projectName);
            return new Integer[]{orgId, projectId};
        } catch (SQLException e) {
            throw new RuntimeException("Database error", e);
        }
    }

    private Integer getIdByField(Connection connection, String tableName, String fieldName, String fieldValue) throws SQLException {
        String query = String.format("SELECT id FROM %s WHERE %s = ?", tableName, fieldName);
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, fieldValue);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        return null;
    }
}
