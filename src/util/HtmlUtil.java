package util;

import java.awt.Color;

public class HtmlUtil {
	/**
	 * ��ԭʼ�ı���ʽ��ΪHTML�ı���
	 * @param info �ǳơ�ip�Ͷ˿ںŵ��ı���
	 * @param origin ԭʼ�ı���
	 * @return ������HTML�ı���
	 */
	public static String formatText2HTML(String info, String origin) {
		String[] res = origin.split("\n");
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("<ul>");
		stringBuilder.append("<font color=\"#008800\">");
		stringBuilder.append(info);
		stringBuilder.append(":</font>");
		for(String s : res) {
			stringBuilder.append("<li>");
			stringBuilder.append(s);
			stringBuilder.append("</li>");
		}
		return stringBuilder.toString();
	}
	/**
	 * ���µ�����׷�ӵ�ԭHTML�С�
	 * @param newContent
	 * @return ׷�����µ����ݵ�HTML�ı���
	 */
	public static String append(String originHTML, String newContent) {
		
		return null;
	}

}
