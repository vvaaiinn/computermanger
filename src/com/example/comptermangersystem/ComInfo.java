package com.example.comptermangersystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ComInfo extends Activity {
	private TextView tv1, tv2, tv3, tv4;
	private ListView lv;
	private List<Map<String, String>> list;
	private String[] from = { "tv1", "tv2", "tv3", "tv4" };
	private int[] to = { R.id.item1, R.id.item2, R.id.item3, R.id.item4 };
	SimpleAdapter adapter;
	private SharedPreferences sp;
	private String phone = "";
	public static final int CheckInfo = 1;
	public String res = "";
	private myHandler handler;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cominfo);
		initMainView();
		new Thread() {
			public void run() {
				try {
					StringBuilder params = new StringBuilder();
					params.append("phone=" + phone);
					res = "";
					res = GetPostUtil.sendGet(
							Login.URL + "getComputerInfo.php",
							params.toString());
					handler.sendEmptyMessage(CheckInfo);
				} catch (Exception e) {
					Log.e("msg", "err:" + e.getMessage());
				}
			}
		}.start();
	}

	public void initMainView() {
		tv1 = (TextView) findViewById(R.id.tv1);
		tv2 = (TextView) findViewById(R.id.tv2);
		tv3 = (TextView) findViewById(R.id.tv3);
		tv4 = (TextView) findViewById(R.id.tv4);
		lv = (ListView) findViewById(R.id.listview);
		list = new ArrayList<Map<String, String>>();
		sp = getSharedPreferences("test", 0);
		phone = sp.getString("phone", "");
		System.out.println(phone);
		// adapter = new SimpleAdapter(this, list, R.layout.infoitem, from, to);
	}

	public class myHandler extends Handler {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CheckInfo:
				if (res != null) {
					System.out.println(res);
				}
				break;

			}
		}
	}
}
