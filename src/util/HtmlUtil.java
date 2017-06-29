package util;

import java.awt.Color;

public class HtmlUtil {
	/**
	 * ��ԭʼ�ı���ʽ��ΪHTML�ı���
	 * @param info �ǳơ�ip�Ͷ˿ںŵ��ı���
	 * @param origin ԭʼ�ı���
	 * @return ������HTML�ı���
	 */
	public static String formatText2HTML(String origin) {
		String escaped = origin.replaceAll(" ", "&nbsp;");
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
		stringBuilder.append((info==null)?"�Լ�":info);
		stringBuilder.append(":</font>");
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
	public static String welcome(String originHTML, String nickname) {
		String s = "<div><font color=\"#880000\">��ӭ " + nickname + " ���뱾�����ҡ�</font></div>";
		return append(originHTML, s);
	}
}
