package com.example.comptermangersystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

public class GetPostUtil {
	/**
	 * ��ָ��URL����GET����������
	 * 
	 * @param url
	 *            ���������URL
	 * @param params
	 *            ����������������Ӧ����name1=value1&name2=value2����ʽ��
	 * @return URL������Զ����Դ����Ӧ
	 */
	public static String sendGet(String url, String params) {
		int success = 1;
		StringBuilder result = new StringBuilder();
		BufferedReader in = null;
		try {

			String urlName = url;
			if (params != null) {
				urlName += "?" + params;
			}
			// Log.e("requestURL",urlName);
			URL realUrl = new URL(urlName);
			// �򿪺�URL֮�������
			URLConnection conn = realUrl.openConnection();
			// ����ͨ�õ���������
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			// ����ʵ�ʵ�����

			conn.connect(); // ��
			// ��ȡ������Ӧͷ�ֶ�
			/*
			 * Map<String, List<String>> map = conn.getHeaderFields(); //
			 * �������е���Ӧͷ�ֶ� for (String key : map.keySet()) {
			 * System.out.println(key + "--->" + map.get(key)); }
			 */
			// ����BufferedReader����������ȡURL����Ӧ

			in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				// System.out.println(line);
				result.append(line);
			}
		} catch (Exception e) {
			System.out.println("����GET��������쳣��" + e);
			e.printStackTrace();
			result.append(e.toString());
			success = 0;
		}
		// ʹ��finally�����ر�������
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		if (success == 0)
			return "";

		return result.toString();
	}

	/**
	 * ��ָ��URL����POST����������
	 * 
	 * @param url
	 *            ���������URL
	 * @param params
	 *            ����������������Ӧ����name1=value1&name2=value2����ʽ��
	 * @return URL������Զ����Դ����Ӧ
	 */
	public static String sendPost(String url, String params) {
		PrintWriter out = null;
		BufferedReader in = null;
		StringBuilder result = new StringBuilder();
		try {
			URL realUrl = new URL(url);
			// �򿪺�URL֮�������
			URLConnection conn = realUrl.openConnection();
			// ����ͨ�õ���������
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			conn.setConnectTimeout(4000);
			conn.setReadTimeout(4000);
			// ����POST�������������������
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// ��ȡURLConnection�����Ӧ�������
			out = new PrintWriter(conn.getOutputStream());
			// �����������
			out.print(params); // ��
			// flush������Ļ���
			out.flush();
			// ����BufferedReader����������ȡURL����Ӧ
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
		} catch (Exception e) {
			System.out.println("����POST��������쳣��" + e);
			e.printStackTrace();
		}
		// ʹ��finally�����ر��������������
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result.toString();
	}
}
