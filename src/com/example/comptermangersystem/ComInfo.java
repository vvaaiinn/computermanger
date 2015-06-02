package com.example.comptermangersystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ComInfo extends Activity {
	private TextView tv1, tv2, tv3, tv4;
	private ListView lv;
	private List<Map<String, String>> list;
	private String[] from = { "tv1", "tv2", "tv3", "tv4" };
	private int[] to = { R.id.item1, R.id.item2, R.id.item3, R.id.item4 };
	private SharedPreferences sp;
	private String phone = "";
	public static final int CheckInfo = 1;
	public String res = "";
	private myHandler handler;
	private int index_page = 0;
	SimpleAdapter adapter;
	private int lastitem;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cominfo);
		initMainView();
		load_info();
		// 滚动Listener
		lv.setOnScrollListener(new OnScrollListener() {
			boolean isLastRow = false;

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO 自动生成的方法存根
				if (isLastRow
						&& scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
					index_page++;
					load_info();
					adapter.notifyDataSetChanged();
					isLastRow = false;
					lv.setSelection(lastitem - 1);
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO 自动生成的方法存根
				if (firstVisibleItem + visibleItemCount == totalItemCount
						&& totalItemCount > 0) {
					isLastRow = true;
					lastitem = firstVisibleItem + visibleItemCount;
				}
			}
		});
	}

	public void load_info() {
		new Thread() {
			public void run() {
				try {
					StringBuilder params = new StringBuilder();
					params.append("phone=" + phone);
					params.append("&index_page=" + index_page);
					res = "";
					res = GetPostUtil.sendGet(
							Login.URL + "getComputerInfo.php",
							params.toString());
					handler.sendEmptyMessage(CheckInfo);

				} catch (Exception e) {
					// Log.e("msg", "err:" + e.getMessage());
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
		handler = new myHandler();

		adapter = new SimpleAdapter(ComInfo.this, list, R.layout.infoitem,
				from, to);
	}

	public class myHandler extends Handler {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CheckInfo:
				if (res != null) {

					String begin = String.valueOf(index_page * 30);
					try {
						JSONObject result = new JSONObject(res);
						String status = result.getString("status");
						if (status.equals("0")) {
							for (int i = 0; i < 30; i++) {
								JSONObject obj = result.getJSONObject(begin);
								HashMap<String, String> map = new HashMap<String, String>();
								map.put("tv1", obj.getString("business"));
								map.put("tv2", obj.getString("IP"));
								map.put("tv3", obj.getString("online"));
								map.put("tv4", obj.getString("manager"));
								list.add(map);
								begin = String
										.valueOf(Integer.parseInt(begin) + 1);
							}
							lv.setAdapter(adapter);
							lv.setSelection(lastitem - 1);
						}
					} catch (JSONException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}
				break;
			}
		}
	}

}
