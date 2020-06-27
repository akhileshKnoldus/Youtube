package com.techmind.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
	
	private static Connection connection ;
	
	public DatabaseConnector(String driverURL) throws Exception{
		Class.forName(driverURL);
	}

	public Connection getConnection (String connectionURL , String userName , String password) throws SQLException {
		connection = null;
		connection = DriverManager.getConnection(connectionURL, userName, password);
		return connection;
	}
	
	public static Connection getConnection(){
		return connection;
	}
}
