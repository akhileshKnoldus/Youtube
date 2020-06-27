package com.techmind.controller;

import com.techmind.model.DatabaseConnector;
import com.techmind.utility.JdbcUtility;

public class FetchData {

	private static DatabaseConnector dbConnector;
	private static FetchData fetchData = null;

	public static FetchData getInstance() throws Exception {
		if (fetchData == null) {
			fetchData = new FetchData();
			initializeConnection();
		}

		return fetchData;
	}

	public static void initializeConnection() throws Exception {
		dbConnector = new DatabaseConnector(JdbcUtility.getDBDriverURL(JdbcUtility.DATA_BASE_TYPE));
		dbConnector.getConnection(JdbcUtility.getDBConnectionString(JdbcUtility.DATA_BASE_TYPE, JdbcUtility.SERVER_NAME,
				JdbcUtility.PORT_NO, JdbcUtility.DATA_BASE_NAME), JdbcUtility.USER_NAME, JdbcUtility.PASSWORD);
	}

	
}
