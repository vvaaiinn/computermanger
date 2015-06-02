package com.example.comptermangersystem;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {
	private EditText name, password;
	private Button submit;
	private NetUtil netUtil;
	private myHandler handler;
	private SharedPreferences sp;
	private CheckBox remember;
	public static final int CheckInfo = 1;
	public static final int CHECK = 2;
	public static final String URL = "http://10.129.149.23/consume/"; // 本地脚本位置
	public String res = "";
	private Boolean isconnected = false;
	private String phone = "";
	private String pwd = "";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		initMainView(); // 初始化界面

		netUtil = new NetUtil(getApplicationContext());
		// 判断网络情况
		if (!netUtil.isNetworkConnected()) {
			Toast.makeText(getApplicationContext(), "网络异常，请检查网络",
					Toast.LENGTH_SHORT).show();
		} else {
			isconnected = true;
		}
		submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				phone = name.getText().toString();
				pwd = password.getText().toString();
				Editor editor = sp.edit();
				editor.putString("phone", phone);
				editor.putString("pwd", pwd);

				if (remember.isChecked()) {
					editor.putBoolean("remember", true);
				} else {
					editor.putBoolean("remember", false);
				}
				editor.commit();

				if (isconnected)
					// handler.sendEmptyMessage(CheckInfo);
					new Thread() {
						public void run() {
							try {
								StringBuilder params = new StringBuilder();
								params.append("phone=" + phone);
								params.append("&pwd=" + pwd);
								String url = URL + "login2.php?" + params;
								res = "";
								res = GetPostUtil.sendGet(URL + "login2.php",
										params.toString());
								handler.sendEmptyMessage(CheckInfo);

							} catch (Exception e) {
								Log.e("msg", "err:" + e.getMessage());
							}
						}
					}.start();

			}

		});

	}

	// 初始化界面
	public void initMainView() {
		name = (EditText) findViewById(R.id.name);
		password = (EditText) findViewById(R.id.password);
		submit = (Button) findViewById(R.id.submit);
		handler = new myHandler();
		remember = (CheckBox) findViewById(R.id.remember);
		sp = getSharedPreferences("test", 0);
		String nStr = sp.getString("phone", "");
		String pStr = sp.getString("pwd", "");
		Boolean flag = sp.getBoolean("remember", false);
		if (flag) {
			name.setText(nStr);
			password.setText(pStr);
			remember.setChecked(true);
		}
	}

	public class myHandler extends Handler {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CheckInfo:
				if (res.equals("success")) {
					Intent intent = new Intent(Login.this, MainActivity.class);
					startActivity(intent);
				}
				break;
			case CHECK:

				break;

			}
		}
	}

	class myThread extends Thread {
		public void run() {
			Message msg = new Message();
			msg.what = CHECK;
			handler.sendMessage(msg);

		}
	}

}
