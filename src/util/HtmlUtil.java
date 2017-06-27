package util;

import java.awt.Color;

public class HtmlUtil {
	/**
	 * 把原始文本格式化为HTML文本。
	 * @param info 昵称、ip和端口号的文本。
	 * @param origin 原始文本。
	 * @return 处理后的HTML文本。
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
	 * 把新的内容追加到原HTML中。
	 * @param newContent
	 * @return 追加了新的内容的HTML文本。
	 */
	public static String append(String originHTML, String newContent) {
		
		return null;
	}

}
