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
	
	public static float byteToFloat(byte[] b, int index) {
		int l;
		l = b[index + 0];
		l &= 0xff;
		l |= ((long) b[index + 1] << 8);
		l &= 0xffff;
		l |= ((long) b[index + 2] << 16);
		l &= 0xffffff;
		l |= ((long) b[index + 3] << 24);
		return Float.intBitsToFloat(l);
	}
	
	public static int byteToInt(byte[] b) {
		return (((int) b[0]) << 24) + (((int) b[1]) << 16) + (((int) b[2]) << 8) + b[3];
	}
	
}
