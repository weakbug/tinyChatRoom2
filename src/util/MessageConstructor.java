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
	public static Msg parseMessage(String s) {
		Pattern pattern = Pattern.compile("(\\d+)-(.*)");
		Matcher matcher = pattern.matcher(s);
		if(matcher.find()) {
			return new Msg(Integer.parseInt(matcher.group(1)), matcher.group(2));
		}
		return null;
	}
	public static Msg parseIPAP(String s) {
		Pattern pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+)");
		Matcher matcher = pattern.matcher(s);
		if(matcher.find()) {
			return new Msg(Integer.parseInt(matcher.group(2)), matcher.group(1));
		}
		return null;
	}
}
