package com.sizatn.springdemo.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * 
 * 
 */
public class ByteUtils {

	public static byte[] getBytes(InputStream is) throws Exception{
		ByteArrayOutputStream bos = null;
		byte[] result = null;
		try{		
			bos = new ByteArrayOutputStream();
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
			bos.flush();
			result = bos.toByteArray();
		}finally{
			if(bos!=null){
				bos.close();
			}
			if(is!=null){
				is.close();
			}
		}	
		return result;
	}	
}
