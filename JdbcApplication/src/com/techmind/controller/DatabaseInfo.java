package com.techmind.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;
import com.techmind.model.DatabaseConnector;
import com.techmind.utility.JdbcUtility;
import com.techmind.utility.TechMindConstant;
import com.techmind.view.UserScreen;

public class DatabaseInfo {

	/*
	 * Show particular database name
	 * 
	 */
	public static void showTables(Connection connection, String databaseName) throws SQLException {
		ResultSet rs = connection.getMetaData().getTables(databaseName, null, "%", new String[] { "TABLE" });
		printTable(rs);
	}

	public static List<String> getTableList(Connection connection, String databaseName) throws SQLException {
		ResultSet rs = connection.getMetaData().getTables(databaseName, null, "%", new String[] { "TABLE" });
		ArrayList<String> list = new ArrayList<>();
		while (rs.next()) {
			String table = rs.getString(3);
			list.add(table);
		}
		return list;
	}

	/**
	 * Show All tables in Catalog
	 * 
	 * @throws SQLException
	 */
	public static void showTables(Connection connection) throws SQLException {
		ResultSet rs = connection.getMetaData().getTables(null, null, "%", new String[] { "TABLE" });
		printTable(rs);
	}

	public static void printTable(ResultSet rs) throws SQLException {
		System.out.println("Tables List are::\nS.No\tTable Name");
		int count = 0;
		while (rs.next()) {
			String table = rs.getString(3);
			count++;
			System.out.println(count + "\t" + table);
		}
	}

	public static List<String> getColumnList(Connection conn, String dataBaseName, String tableName)
			throws SQLException {
		ArrayList<String> list = new ArrayList<>();
		String query = "select * from " + tableName;
		ResultSet rs = conn.createStatement().executeQuery(query);

		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		for (int i = 1; i <= columnCount; i++)
			list.add(rsmd.getColumnName(i));
		return list;
	}

	public static void showTablesData() throws SQLException {
		showTables(DatabaseConnector.getConnection(), JdbcUtility.DATA_BASE_NAME);
		System.out.println(TechMindConstant.SELECT_TABLE);
		int index = UserScreen.sc.nextInt();
		List<String> tablesList = getTableList(DatabaseConnector.getConnection(), JdbcUtility.DATA_BASE_NAME);
		if (index <= tablesList.size()) {
			String tableName = tablesList.get(index - 1);
			showTablesData(DatabaseConnector.getConnection(), tableName,
					getColumnList(DatabaseConnector.getConnection(), JdbcUtility.DATA_BASE_NAME, tableName));
		}
	}

	public static void showTablesData(Connection conn, String tableName, List<String> columns) throws SQLException {
		if (!JdbcUtility.isEmptyOrNull(tableName))
			try (Statement statement = (Statement) conn.createStatement()) {
				String query = "select * from " + tableName;
				ResultSet rs = statement.executeQuery(query);
				ArrayList<String> data = new ArrayList<>();
				for (int i = 0; i < columns.size(); i++) {
					System.out.print(columns.get(i) + "\t");
				}
				System.out.println();
				while (rs.next()) {
					for (int i = 0; i < columns.size(); i++) {
						String columnName = columns.get(i);
						System.out.print(rs.getString(columnName) + "\t");
					}
					System.out.println();
				}
			}
	}
}
