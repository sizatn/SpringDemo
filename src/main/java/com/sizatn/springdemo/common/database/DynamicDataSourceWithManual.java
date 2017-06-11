package com.sizatn.springdemo.common.database;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 
 * @desc 
 * @author sizatn
 * @date Jun 10, 2017
 */
public class DynamicDataSourceWithManual extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return DataBaseSwitcher.getDbType();
	}

}
