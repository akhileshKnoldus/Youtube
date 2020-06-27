package com.techmind.view;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import com.techmind.controller.DatabaseInfo;
import com.techmind.controller.FetchData;
import com.techmind.model.DatabaseConnector;
import com.techmind.utility.JdbcUtility;
import com.techmind.utility.TechMindConstant;

public class UserScreen {

	public static  Scanner sc = new Scanner(System.in);
	
	public static String  welcomeScreen (String name) {
		if(!JdbcUtility.isEmptyOrNull(name))
			return "\n**********************  "+name+"  *******************\n";
		else 
			return "\n**********************  "+TechMindConstant.COMPANY_NAME+"  *******************\n";
	}
	
	public static void userOption() throws Exception {
		FetchData fetchData = new FetchData().getInstance();
		optionList();

	}
	
	public static void optionList() throws SQLException {
		System.out.println(welcomeScreen(null));
		System.out.println(TechMindConstant.OPTION_LIST);
		int option = sc.nextInt();
		if(option==1) {
			DatabaseInfo.showTables(DatabaseConnector.getConnection(), JdbcUtility.DATA_BASE_NAME);
		}else if(option==2) {
			DatabaseInfo.showTablesData();
		}else {
			System.out.println(TechMindConstant.FEATURES_UPCOMING);
		}
	}
}
