package com.example.gxufl2;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gxufl2.imageCode.ImageCode;
import com.example.gxufl2.jsouphtml.HtmlJsoup;

public class MainActivity extends Activity {
	private EditText usename;				//用户名输入框
	private EditText password;				//密码输入框
	private EditText authcode;				//验证码输入框
	private ImageView image;				//显示验证码
	private ImageButton login;				//登录
	private Bitmap bitmap;					//一张位图
	private String loginURL="http://jw.gxufl.com/_data/home_login.aspx";//请求网址
	private int getcode_success=1;			//获取验证码成功代号为:1
	private int getcode_error=2;			//获取验证码失败
	private int login_success=3;			//登录成功
	private int login_error=4;				//登录失败
	private static String html="";
	private static String htmls="";
	/**
	 * Handler机制
	 * getcode_success 获取验证码成功代号为:1
	 * getcode_error=2;//获取验证码失败
	 * login_success=3;//登录成功
	 * login_error=4;//登录失败
	 */
	final Handler handler=new Handler(){
		public void handleMessage(Message msg){
			
			if(msg.what==1){
				//getcode_success //获取验证码成功代号为:1
				image.setImageBitmap(bitmap);
			}
			else if(msg.what==2)
			{
				//getcode_error=2;//获取验证码失败
			}
			else if(msg.what==3)
			{
				//login_success=3;//登录成功
				Toast.makeText(getApplicationContext(), "登录成功！", 1).show();				
				Schedule();
			}
			else if(msg.what==4)
			{
				Toast.makeText(getApplicationContext(), "登录失败！", 1).show();
				//login_error=4;//登录失败
				authcode.setText("");
			}
		}
		
	};

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Init();
		new GetCode().start();
	}

	
	
	public void Init()
	{
		usename=(EditText)this.findViewById(R.id.usename);
		password=(EditText)this.findViewById(R.id.password);
		authcode=(EditText)this.findViewById(R.id.authcode);
		image=(ImageView)this.findViewById(R.id.image);
		login=(ImageButton)this.findViewById(R.id.login);
		login.setOnClickListener(new OnClickListener(){		
			public void onClick(View v) {
				String u_name=usename.getText().toString().trim();
				String p_word=password.getText().toString().trim();
				String code=authcode.getText().toString().trim();
				Map<String,String> map=new HashMap<String,String>();
				map.put("txt_asmcdefsddsd", u_name);
				map.put("txt_pewerwedsdfsdff", p_word);
				map.put("txt_sdertfgsadscxcadsads", code);
				Login(loginURL,map);
				
			}		
		});
	}
	
	/**
	 * 登录方法
	 * @param loginURL 要登录的网址
	 * @param map		要方式的参数：用户名，密码，验证码  的封装
	 */
	private void Login(final String loginURL, final Map<String, String> map) {
		new Thread(new Runnable(){
			public void run() {
				Message msg=new Message();
				try{
					ImageCode imagecode=new ImageCode();
				    html=imagecode.getWebWithPost(loginURL, map);
				    HtmlJsoup HJ=new HtmlJsoup();
				   htmls= HJ.htmlAnalysis(html);
				   if(htmls.equals("验证码错误！ 登录失败！"))
				   {
					   msg.what=login_error;
					   Log.e("黄柳淞", htmls);
					   new GetCode().start();
				   }
				   else if(htmls.equals("正在加载权限数据..."))
				   {
					   Log.e("黄柳淞", htmls);
					   msg.what=login_success;
				   }
				}
				catch(Exception e){
					e.printStackTrace();
				}
				handler.sendMessage(msg);
			}
			
		}).start();
	}
	

	
	/**
	 * 获取验证码
	 * @author Administrator
	 *
	 */
	private class GetCode extends Thread{
		public void run(){
			/*super.start();*/
			Message msg=new Message();
			try{
				msg.what=getcode_success;
				ImageCode imagecode=new ImageCode();
				bitmap=imagecode.getcookie();
			}
			catch(Exception e){
				e.printStackTrace();
			}
			handler.sendMessage(msg);
		}
	}
	
	 private void Schedule(){
		   Intent intent = new Intent();
		   intent.setClass(MainActivity.this, ScheduleActivity.class);
		   Bundle bundle = new Bundle();
		   bundle.putString("html", htmls);
		   intent.putExtras(bundle);
		   startActivity(intent);	   
	   }
}
