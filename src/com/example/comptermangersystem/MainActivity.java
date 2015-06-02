package com.example.comptermangersystem;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class MainActivity extends ActivityGroup {
	private LinearLayout one, two, three, four, five;
	private LinearLayout bodyView;
	private int flag = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initMainView(); // ��ʼ������
		bindListener();// ������
	}

	// ��ʼ������
	public void initMainView() {
		bodyView = (LinearLayout) findViewById(R.id.body);
		one = (LinearLayout) findViewById(R.id.one);
		two = (LinearLayout) findViewById(R.id.two);
		three = (LinearLayout) findViewById(R.id.three);
		four = (LinearLayout) findViewById(R.id.four);
		five = (LinearLayout) findViewById(R.id.five);
	}

	// ������
	public void bindListener() {
		one.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				flag = 1;
				showView(flag);

			}
		});
		two.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				flag = 2;

			}
		});
		three.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				flag = 3;

			}
		});
		four.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				flag = 4;

			}
		});
		five.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������s
				flag = 5;
			}
		});
	}

	// ��ʾ�ӻ
	public void showView(int flag) {
		switch (flag) {
		case 1:
			bodyView.removeAllViews();
			Intent intent = new Intent(MainActivity.this, ComInfo.class);
			bodyView.addView(getLocalActivityManager().startActivity("one",
					intent).getDecorView());
			break;

		default:
			break;
		}

	}
}
