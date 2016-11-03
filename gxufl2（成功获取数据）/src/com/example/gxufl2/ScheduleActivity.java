package com.example.gxufl2;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gxufl2.imageCode.ImageCode;

public class ScheduleActivity extends Activity {
	private TextView shows;
	private String sco="";
	private Button look;
	final Handler handler=new Handler(){
		public void handleMessage(Message msg){
			
			if(msg.what==11){
				
				shows.setText(sco);
			}
			
		}
		
	};
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedule);
		shows=(TextView)this.findViewById(R.id.shows);
		shows.setMovementMethod(ScrollingMovementMethod.getInstance());
		Init();
		Bundle bundle=this.getIntent().getExtras();
		if(bundle!=null){
			String str=bundle.getString("html");
			shows.setText(str);
		}	
	}
	
	
	
	
	
	public  void  Init()
	{
		look=(Button)this.findViewById(R.id.look);
		look.setOnClickListener(new OnClickListener(){	
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "检索成绩", 0).show();
				new GetCode().start();		
			}
			
		});
	}
	private class GetCode extends Thread{
		public void run(){
			/*super.start();*/
			Message msg=new Message();
			try{
				msg.what=11;
				ImageCode scon=new ImageCode();
				sco=scon.getSco();
				Log.e("我的成绩输出2","输出我成绩是多少呢");
			}
			catch(Exception e){
				e.printStackTrace();
			}
			handler.sendMessage(msg);
		}
	}

	
	
	
	
	
}
