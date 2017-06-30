package util;

import java.awt.Color;

public class HtmlUtil {
	/**
	 * ��ԭʼ�ı���ʽ��ΪHTML�ı���
	 * @param info �ǳơ�ip�Ͷ˿ںŵ��ı���
	 * @param origin ԭʼ�ı���
	 * @return ������HTML�ı���
	 */
	public static String formatText2HTML(String origin, boolean isProtect) {
		String escaped = isProtect?protect(origin):origin;
		String[] res = escaped.split("\n");
		StringBuilder stringBuilder = new StringBuilder();
		for(String s : res) {
			stringBuilder.append("<li>");
			stringBuilder.append(s);
			stringBuilder.append("</li>");
		}
		return stringBuilder.toString();
	}
	public static String addUserInfo(String info, String s) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<ul>");
		stringBuilder.append("<font color=\"#008800\">");
		stringBuilder.append(info);
		stringBuilder.append(":</font>");
		stringBuilder.append(s);
		stringBuilder.append("</ul>");
		return stringBuilder.toString();
	}
	public static String addSystemInfo(String info, String s) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<ul>");
		stringBuilder.append("<font color=\"#088880\"><strong>");
		stringBuilder.append(info);
		stringBuilder.append("</strong>:</font>");
		stringBuilder.append(s);
		stringBuilder.append("</ul>");
		return stringBuilder.toString();
	}
	/**
	 * ���µ�����׷�ӵ�ԭHTML�С�
	 * @param newContent
	 * @return ׷�����µ����ݵ�HTML�ı���
	 */
	public static String append(String originHTML, String newContent) {
		String newEnd = newContent + "</body></html>";
		String newHTML = originHTML.replaceFirst("<\\/body>[\\s\\S]*<\\/html>", newEnd);
		return newHTML;
	}
	public static String getBase() {
		return "<html><style type=\"text/css\"> ul{ margin: 4px; list-style-type:none; } div{ text-align:center; }</style><body></body></html>";
	}
	public static String welcome(String nickname) {
		String s = "<div><font color=\"#880000\">�û� " + protect(nickname) + " �����������ҡ�</font></div>";
		return s;
	}
	public static String leave(String nickname) {
		String s = "<div><font color=\"#000088\">�û� " + protect(nickname) + " �뿪�������ҡ�</font></div>";
		return s;
	}
	public static String serverShutdown() {
		String s = "<div><font color=\"#000088\"><strong>ע�⣺�������ѹرա�</strong></font></div>";
		return s;
	}
	/**
	 * ��������������ˡ�
	 * ��ֱֹ������html���뵽���촰�ڻ��ǳ��ϵ��µı�ǩע�롣
	 * @author Shinrai
	 * @param origin ���ܴ��ڱ�ǩע����ַ�����
	 * @return
	 */
	private static String protect(String origin) {
		String r;
		r = origin.replaceAll("&", "&amp;");
		r = r.replaceAll(" ", "&nbsp;");
		r = r.replaceAll("\"", "&quot;");
		r = r.replaceAll("<", "&lt;");
		r = r.replaceAll(">", "&gt;");
		return r;
	}
}
