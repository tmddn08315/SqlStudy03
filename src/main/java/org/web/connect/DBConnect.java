package org.web.connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
	public static Connection getConnection() {        
        // DB 연동 객체
        Connection conn = null;        
        
        // MySQL
        String driver = "com.mysql.cj.jdbc.Driver";  // 1. MySQL 드라이버
        String url = "jdbc:mysql://127.0.0.1:3306/springdb9";  // DB URL
        String user = "root";                 // 계정 아이디
        String password = "1234";             // 계정 비밀번호
        
        try {
            // 1. 드라이버 로드
            Class.forName(driver);
            System.out.println("Driver OK!");
            
            // 2. DB 연결
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("DB연동 OK!");
            
        } catch (ClassNotFoundException e) {
            System.out.println("Driver Fail!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("DB연동 Fail!");
            e.printStackTrace();
        }
        
        return conn;
    }
}

