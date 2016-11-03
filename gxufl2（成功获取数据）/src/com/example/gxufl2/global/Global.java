package com.example.gxufl2.global;
/**
 * 保存整个程序都要用到的变量
 * @author cookie1 保存cookie1
 * @author cookie2 保存cookie2
 */
public class Global {
	private static String cookie1="";
	private static String cookie2="";
	public void setcookie1(String cookie){
		cookie1=cookie;
	}
	public void setcookie2(String cookie){
		cookie2=cookie;
	}
	public String getcookie1(){
		return cookie1;
	}
	public String getcookie2(){
		return cookie2;
	}
}
