package com.example.comptermangersystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Total extends Activity {
	public static final int show = 1;
	private ListView lv;
	public String res = "";
	private List<Map<String, String>> list;
	private String[] from = { "tv1", "tv2", "tv3", "tv4", "tv5", "tv6" };
	private int[] to = { R.id.item1, R.id.item2, R.id.item3, R.id.item4,
			R.id.item5, R.id.item6 };
	private String[] title = { "PC搜索", "无线搜索", "联盟", "公共", "品牌", "总计" };
	private int[] num = { 0, 0, 0, 0, 0 };
	SimpleAdapter adapter;
	private myHandler handler;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.total);
		initMainView(); // 初始化界面
		load_info();
	}

	public void initMainView() {
		lv = (ListView) findViewById(R.id.listview);
		list = new ArrayList<Map<String, String>>();
		handler = new myHandler();
		adapter = new SimpleAdapter(Total.this, list, R.layout.totalitem, from,
				to);
	}

	public void load_info() {
		new Thread() {
			public void run() {
				try {
					res = "";
					res = GetPostUtil.sendGet(Login.URL + "getTotal.php", null);
					handler.sendEmptyMessage(show);

				} catch (Exception e) {

				}
			}
		}.start();
	}

	public class myHandler extends Handler {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case show:
				if (res != null) {
					// System.out.println(res);
					try {
						JSONObject result = new JSONObject(res);
						String status = result.getString("status");
						if (status.equals("0")) {
							for (int i = 0; i < 5; i++) {
								JSONObject obj = result.getJSONObject(String
										.valueOf(i));
								HashMap<String, String> map = new HashMap<String, String>();
								map.put("tv1", title[i]);
								map.put("tv2", obj.getString("1"));
								map.put("tv3", obj.getString("0"));
								map.put("tv4", obj.getString("2"));
								map.put("tv5", obj.getString("3"));
								map.put("tv6", obj.getString("4"));
								num[0] += Integer.parseInt(obj.getString("1"));
								num[1] += Integer.parseInt(obj.getString("0"));
								num[2] += Integer.parseInt(obj.getString("2"));
								num[3] += Integer.parseInt(obj.getString("3"));
								num[4] += Integer.parseInt(obj.getString("4"));
								list.add(map);
							}
							HashMap<String, String> map = new HashMap<String, String>();
							map.put("tv1", title[5]);
							map.put("tv2", String.valueOf(num[0]));
							map.put("tv3", String.valueOf(num[1]));
							map.put("tv4", String.valueOf(num[2]));
							map.put("tv5", String.valueOf(num[3]));
							map.put("tv6", String.valueOf(num[4]));
							list.add(map);
							lv.setAdapter(adapter);
						}
					} catch (JSONException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}
			}
		}
	}
}
