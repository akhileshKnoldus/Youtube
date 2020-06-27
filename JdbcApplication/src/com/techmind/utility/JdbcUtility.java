package com.techmind.utility;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class JdbcUtility {

	public static final int MSSQL_SERVER = 2;

	public static final int ORACLE_SERVER = 1;

	public static final int MYSQL_SERVER = 0;

	public static String DATA_BASE_NAME = null;

	public static String PORT_NO = null;

	public static String USER_NAME = null;

	public static String PASSWORD = null;

	public static int DATA_BASE_TYPE = 0;

	public static String SERVER_NAME = null;

	private static Properties properties = null;

	static {
		properties = new Properties();
		try {
			properties.load(new FileInputStream("src/database.properties"));
			DATA_BASE_NAME = properties.getProperty("dataBaseName"); 
			USER_NAME= properties.getProperty("userName");
			PASSWORD=properties.getProperty("password");
			PORT_NO= properties.getProperty("portNo");
			SERVER_NAME= properties.getProperty("serverName");
			DATA_BASE_TYPE=Integer.parseInt((properties.getProperty("databaseType")));
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}

	public static boolean isEmptyOrNull(String str) {
		return (str == null || str.isEmpty());
	}

	public static Connection getConnection(String driverName, String userName, String password, String url)
			throws Exception {
		Class.forName(driverName);
		return DriverManager.getConnection(url, userName, password);
	}

	/**
	 * Return database connection string
	 * 
	 * @param databaseType
	 * @param databaseServerName
	 * @param portName
	 * @param databaseName
	 * @return
	 */

	public static String getDBConnectionString(int databaseType, String databaseServerName, String portName,
			String databaseName) {
		String connectionString = null;
		switch (databaseType) {
		case MSSQL_SERVER:
			connectionString = "jdbc:sqlserver://" + databaseServerName + ":" + portName + ";databaseName="
					+ databaseName;
			break;
		case ORACLE_SERVER:
			connectionString = "jdbc:oracle:thin:@" + databaseServerName + ":" + portName + ":" + databaseName;
			break;
		case MYSQL_SERVER:
			connectionString = "jdbc:mysql://" + databaseServerName + ":" + portName + "/" + databaseName;
			break;
		}
		return connectionString+"?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&serverTimezone=UTC";

	}

	/**
	 * Return database driver URL
	 * 
	 * @param databaseType
	 * @return
	 */
	public static String getDBDriverURL(int databaseType) {
		String driverURL = null;
		switch (databaseType) {
		case MSSQL_SERVER:
			driverURL = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			break;
		case ORACLE_SERVER:
			driverURL = "oracle.jdbc.driver.OracleDriver";
			break;
		case MYSQL_SERVER:
			driverURL = "com.mysql.jdbc.Driver";
			break;
		}
		return driverURL;
	}

	/**
	 * Return database driver URL
	 * 
	 * @param databaseType
	 * @param databaseName
	 * @param driverClass
	 * @return
	 */
	public static String getDBDriverURL(int databaseType, String databaseName, String driverClass) {
		if (!JdbcUtility.isEmptyOrNull(driverClass))
			return driverClass;
		else
			return getDBDriverURL(databaseType);
	}

}
