package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageConstructor {
	public static class Code {
		public static class UDP {
			public static final int REQUEST_SERVER_ADDRESS_AND_PORT = 0;
			public static final int SERVER_ADDRESS_AND_PORT_FEEDBACK = 1;
		}
		public static class TCP {
			public static final int MESSAGE_FROM_CLIENT_TO_SERVER = 0;
			public static final int MESSAGE_FROM_SERVER_TO_CLIENT = 1;
			public static final int LOGIN_REQUEST = 3;
			public static final int LOGIN_FEEDBACK = 4;
			public static final int MESSAGE_PRIVATE = 5;
			public static final int USER_ONLINE = 6;
			public static final int USER_OFFLINE = 7;
		}
	}
	public static class Msg {
		private int code;
		private String message;
		public Msg(int code, String additionMessage) {
			this.code = code;
			message = additionMessage;
		}
		public int getCode() {
			return code;
		}
		public String getMessage() {
			return message;
		}
	}
	
	public static String constructMessage(int code, String additionMessage) {
		return String.valueOf(code) + "-" + additionMessage;
	}
	/**
	 * 解析消息
	 * @param s
	 * @return
	 */
	public static Msg parseMessage(String s) {
		Pattern pattern = Pattern.compile("(\\d+)-(.*)");
		Matcher matcher = pattern.matcher(s);
		if(matcher.find()) {
			return new Msg(Integer.parseInt(matcher.group(1)), matcher.group(2));
		}
		return null;
	}
	/**
	 * 解析地址和端口
	 * @param s
	 * @return
	 */
	public static Msg parseIPAP(String s) {
		Pattern pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)");
		Matcher matcher = pattern.matcher(s);
		if(matcher.find()) {
			return new Msg(Integer.parseInt(matcher.group(2)), matcher.group(1));
		}
		return null;
	}
	public static String constructPrivateMessage(String message, String nickname) {
		return nickname + "-" + message;
	}
	public static String[] parsePrivateMessage(String s) {
		Pattern pattern = Pattern.compile("(.*)-(.*)");
		Matcher matcher = pattern.matcher(s);
		if(matcher.find()) {
			String nickname = matcher.group(1);
			String message = matcher.group(2);
			String[] arr = new String[]{nickname, message};
			return arr;
		}
		return null;
	}
}
