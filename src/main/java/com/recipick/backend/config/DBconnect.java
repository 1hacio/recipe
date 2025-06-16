package com.recipick.backend.config;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class DBconnect {
	
	public static final String dbDriver = "com.mysql.cj.jdbc.Driver";
	public static final String dbUrl = "jdbc:mysql://localhost:3306/pos?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF8";
	public static final String dbUser = "root";
	public static final String dbPwd = "1234";
	public static Connection conn = null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		connect();
		close();
	}
	
	public static Connection connect() {
		try {	
			Class.forName(dbDriver);
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
			if(conn!=null)
				System.out.println("Connection Succeed");
			else
				System.out.println("Connection Failed");			
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "데이터베이스가 연결되지 않았습니다", "경고", JOptionPane.WARNING_MESSAGE);
		}
		return conn;
	}
	
	public static void close() {
		try {
			if(conn!=null) {
				System.out.println("Connection Close");
				conn.close();
			}
		}catch(Exception e) {
			System.out.println("Connection Closing Failed : "+e.getMessage());
			e.printStackTrace();
		}
	}

}
