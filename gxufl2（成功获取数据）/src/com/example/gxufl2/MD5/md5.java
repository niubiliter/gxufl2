package com.example.gxufl2.MD5;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具类
 * @author Sylthas
 *
 */
public class md5 {
	private static final char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',  
	    'A', 'B', 'C', 'D', 'E', 'F' };
	
	/**
	 * 得到参数加密后的MD5值
	 * @param inStr
	 * @return 32byte MD5 Value
	 */
	public static String getMD5(String inStr){
		byte[] inStrBytes = inStr.getBytes();
		try {
			MessageDigest MD = MessageDigest.getInstance("MD5");
			MD.update(inStrBytes);
			byte[] mdByte = MD.digest();
			char[] str = new char[mdByte.length * 2];
			int k = 0;
			for(int i=0;i<mdByte.length;i++) {
				byte temp = mdByte[i];
				str[k++] = hexDigits[temp >>> 4 & 0xf];
				str[k++] = hexDigits[temp & 0xf];
			}
			return new String(str);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
