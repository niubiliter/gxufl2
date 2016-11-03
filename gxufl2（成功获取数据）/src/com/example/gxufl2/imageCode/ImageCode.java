package com.example.gxufl2.imageCode;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import com.example.gxufl2.ScheduleActivity;
import com.example.gxufl2.MD5.md5;
/**
 * 获取网站的验证码
 * @author ImagesCode
 *
 */
public class ImageCode {
	HttpClient client = new DefaultHttpClient();
	private static List<Cookie> cook ;
	private static String cookie1="";
	private static String cookie2="";
	private static String COOKIE="";
	 public Bitmap getcode() throws Exception {
			HttpGet get = new HttpGet(
					"http://jw.gxufl.com/sys/ValidateCode.aspx"); // 
			get.setHeader("Cookie", COOKIE); 
			HttpResponse httpResponse = client.execute(get);
			cook = ((AbstractHttpClient) client).getCookieStore().getCookies();
			int i=cook.size();
			//cookie1=cook.get(0).getValue();
			//cookie2=cook.get(1).getValue();
			//cookie = ((AbstractHttpClient) client).getCookieStore().getCookies().get(1).getValue();
			//Log.e("黄柳淞cookie1",cookie1);
			//Log.e("黄柳淞cookie2",cookie2);
			//Log.e("黄柳淞cookie_Size",i+"个");
			byte[] bytes = EntityUtils.toByteArray(httpResponse.getEntity());
			String str=new String(bytes);
			Log.e("柳", str);
			Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
			return bitmap;
		}
	 
	 
	 /**
	  * 通过Post验证登录
	  * @param url
	  * @param map
	  * @return
	  */
	 public String getWebWithPost(String loginURL,Map<String,String> map) {
		 HttpPost httpRequest = new HttpPost(loginURL);
		 String name=map.get("txt_asmcdefsddsd");//获取用户名
		 String pass=map.get("txt_pewerwedsdfsdff");//获取密码
		 String code=map.get("txt_sdertfgsadscxcadsads");//获取验证码
		 
		 md5 m=new md5();
		 String codeMD5=m.getMD5(m.getMD5(code.toUpperCase()).substring(0, 30)+"13830").substring(0, 30);
		 String usepassMD5=m.getMD5(name.concat(m.getMD5(pass).substring(0, 30)+"13830")).substring(0,30);
		 Log.e("MD5+验证码", codeMD5);
		 Log.e("MD5+用户名密码", usepassMD5);
		 List<NameValuePair> params = new ArrayList<NameValuePair>();
		 params.add(new BasicNameValuePair("txt_asmcdefsddsd",name));//设置用户名
		 params.add(new BasicNameValuePair("txt_pewerwedsdfsdff",pass));//设置密码
		 params.add(new BasicNameValuePair("txt_sdertfgsadscxcadsads",code));//设置验证码
		 params.add(new BasicNameValuePair("fgfggfdgtyuuyyuuckjg",codeMD5));//设置验证码
		 params.add(new BasicNameValuePair("dsdsdsdsdxcxdfgfg",usepassMD5));//设置

		 params.add(new BasicNameValuePair("Sel_Type","STU"));//设置
		 //params.add(new BasicNameValuePair("typeName","STU000"));//设置
		 params.add(new BasicNameValuePair("pcInfo","Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.3; WOW64; Trident/7.0; .NET4.0E; .NET4.0C; .NET CLR 3.5.30729; .NET CLR 2.0.50727; .NET CLR 3.0.30729; Tablet PC 2.0)x860 SN:NULL"));//设置
		 
		/* for(String key : map.keySet()) {
			params.add(new BasicNameValuePair(key, map.get(key))); //添加参数
		 }*/
		 /*Log.e("用户名",name);
		 Log.e("密码",pass);
		 Log.e("验证码",code);
		 Log.e("黄柳淞cookie11",cookie1);
		 Log.e("黄柳淞cookie22",cookie2);
		 Log.e("黄柳淞","name=value; __jsluid="+cookie2+"; ASP.NET_SessionId="+cookie1);*/
		httpRequest.setHeader("Cookie", 
				"name=value; Hm_lvt_2d57a0f88eed9744a82604dcfa102e49=1437955437,1437955450; __jsluid="+cookie2+"; ASP.NET_SessionId="+cookie1);//加入Cookie
		//设置一系列的请求头，否则403
		httpRequest.setHeader("Accept", "text/html, application/xhtml+xml, */*");
		httpRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
		httpRequest.setHeader("DNT", "1");
		httpRequest.setHeader("Host", "jw.gxufl.com");
		httpRequest.setHeader("Proxy-Connection", "Keep-Alive");
		httpRequest.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko");
		httpRequest.setHeader("Referer", "http://jw.gxufl.com/_data/home_login.aspx");
		try {
			//设置请求参数
			httpRequest.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			//发送post请求
			HttpResponse httpResponse = client.execute(httpRequest); 	
			int state=httpResponse.getStatusLine().getStatusCode(); 
			//登录-状态-判断
			if (state== 200) { 	//状态码
				Log.e("登录成功-----", "Yes。。。");
				
				return  readFromStream(httpResponse.getEntity().getContent());
				
			}else if(state==403){
				Log.e("登录失败----", "资源不可用。服务器理解客户的请求，但'拒绝'处理它");
			}
			else {
				Log.e("登录失败----", "ERROR");
				return "false";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	 /**
	  * 获取整个网页的源代码
	  * @param inputStream
	  * @return
	  * @throws Exception
	  */
	 public static String readFromStream(InputStream inputStream)
				throws Exception {
		StringBuffer sb = new StringBuffer();
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "GBK"));
		String data = "";
		while ((data = br.readLine()) != null) {
			sb.append(data);
		}		
		return sb.toString(); 
	}	 	
	 
	 public  Bitmap getcookie() throws Exception
	 {
		 HttpGet get = new HttpGet(
					"http://jw.gxufl.com/_data/home_login.aspx"); // 
			get.setHeader("Cookie", ""); 
			HttpResponse httpResponse = client.execute(get);
			cook = ((AbstractHttpClient) client).getCookieStore().getCookies();
			int i=cook.size();
			cookie1=cook.get(0).getValue();
			cookie2=cook.get(1).getValue();
			client = new DefaultHttpClient();	
			COOKIE="name=value; __jsluid="+cookie2+"; ASP.NET_SessionId="+cookie1;
			 Log.e("COOKIE12111111111111", COOKIE);
			 Bitmap bitmap=getcode();
			 return bitmap;
	 }
	
	 
	
	 public String getSco(){
		 Log.e("我的成绩输出3","输出我成绩是多少呢");
			HttpGet get = new HttpGet(
					"http://jw.gxufl.com/xscj/Stu_MyScore.aspx");
			get.setHeader("Cookie","name=value; Hm_lvt_2d57a0f88eed9744a82604dcfa102e49=1437955437,1437955450; __jsluid="+cookie2+"; ASP.NET_SessionId="+cookie1);
			try {
				HttpResponse httpResponse = client.execute(get);
				String scon=readFromStream(httpResponse.getEntity().getContent());
				return scon;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
}
