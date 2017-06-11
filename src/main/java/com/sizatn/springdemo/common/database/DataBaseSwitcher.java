package com.sizatn.springdemo.common.database;

/**
 * 
 * @desc manual switching database
 * @author sizatn
 * @date Apr 13, 2015
 */
public class DataBaseSwitcher {
	private static final ThreadLocal<String> dbSwitcher = new ThreadLocal<String>();
	
	public static void setDbType(String dbType) {
		dbSwitcher.set(dbType);
	}
	
	public static String getDbType() {
		return dbSwitcher.get();
	}
	
	public static void clearDbType() {
		dbSwitcher.remove();
	}

}
