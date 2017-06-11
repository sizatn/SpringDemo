package com.sizatn.springdemo.common.utils;

import java.util.HashMap;
import java.util.Map;

public class MapUtils {
	
	/**
	 * 
	 * @param parameters id=1&name=zhenxiang
	 * @return
	 */
	public static Map getParameterMap(String parameters) {
		Map paramsMap = new HashMap();
		if (StringUtils.isEmpty(parameters)) {
			return paramsMap;
		}
		while (parameters.startsWith("?")) {
			parameters = parameters.substring(1);
		}
		String[] paramPair = StringUtils.splitByTokenizer(parameters, "&");

		int len = paramPair.length;
		for (int i = 0; i < len; i++) {
			String strParamPair = paramPair[i].trim();
			if (strParamPair.equals("")) {
				continue;
			}
			String[] paramNameVal = StringUtils.splitByTokenizer(strParamPair, "=");
			int len2 = paramNameVal.length;
			if (len2 == 2) {
				paramsMap.put(paramNameVal[0].trim(), new String[] { paramNameVal[1].trim() });
			}
		}
		return paramsMap;
	}

}
