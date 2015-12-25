package com.amani.tools;

import java.sql.*;

public class JDBCTool {
	public Connection getConnection() {
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:jtds:sqlserver://10.0.0.9:1433;databaseName=MasterDatabase2014","sa","RUz4mRt?LuEy=@uG");
			return con;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 释放连接
	public boolean closeConnection(Connection con) {
		try {
			if (con == null)
				return true;
			con.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean executeSql(String strSql) {
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		try {
			boolean bret = false;

			preparedStatement = connection.prepareStatement(strSql);
			bret = preparedStatement.execute();
			if (bret) {
				connection.commit();
			}
			return bret;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public ResultSet loadResultSet(String strSql) {
		Connection connection = getConnection();
		PreparedStatement preparedStatement = null;
		try {

			preparedStatement = connection.prepareStatement(strSql);
			return preparedStatement.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
}
