package com.ezace.board;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DatabaseTestController {

    @Autowired private DataSource dataSource;

    @GetMapping("/db-test")
    public Map<String, Object> testDatabaseConnection() {
        Map<String, Object> result = new HashMap<>();

        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();

            result.put("status", "success");
            result.put("message", "데이터베이스 연결 성공");
            result.put("databaseProductName", metaData.getDatabaseProductName());
            result.put("databaseProductVersion", metaData.getDatabaseProductVersion());
            result.put("driverName", metaData.getDriverName());
            result.put("driverVersion", metaData.getDriverVersion());
            result.put("url", metaData.getURL());
            result.put("username", metaData.getUserName());
            result.put("isReadOnly", connection.isReadOnly());
            result.put("catalog", connection.getCatalog());

        } catch (SQLException e) {
            result.put("status", "error");
            result.put("message", "데이터베이스 연결 실패");
            result.put("error", e.getMessage());
            result.put("errorClass", e.getClass().getName());
        }

        return result;
    }
}
