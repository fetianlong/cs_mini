package com.dearho.cs.device.action;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;




public class DBUtil {
	private static Connection conn;
	static{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://182.92.165.121:3306/carsharing", "root", "dell123");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static int insertOrUpdateSql(String sql) throws SQLException{
		PreparedStatement ps = conn.prepareStatement(sql);
		return ps.executeUpdate();
	}
	
	public static ResultSet querySql(String sql) throws SQLException{
		PreparedStatement ps = conn.prepareStatement(sql);
		return ps.executeQuery();
	}

}
