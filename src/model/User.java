package model;

import java.awt.Color;

/**
 * @author Shinrai
 * @since 2017-6-26 14:16:09
 */
public class User {
	public static final String banNickname = "^(?i)(?:admin|administrator|guest|user|gm|error)$";
	private String nickname;
	private String ipAddress;
	private int port;
	private String publicKey;
	
	public User(String nickname, String ipAddress, int port, String publicKey) {
		this.nickname = nickname;
		this.ipAddress = ipAddress;
		this.port = port;
		this.publicKey = publicKey;
	}
	@Override
	public boolean equals(Object anObject) {
		if(this == anObject) {
			return true;
		}
		if(anObject instanceof User) {
			User user = (User)anObject;
			if(this.getNickname().equals(user.getNickname())) {
				return true;
			}
			return false;
		}
		return false;
	}
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(nickname);
		stringBuilder.append('(');
		stringBuilder.append(ipAddress);
		stringBuilder.append(':');
		stringBuilder.append(String.valueOf(port));
		stringBuilder.append(')');
		return stringBuilder.toString();
	}
	
	public String getNickname() {
		return nickname;
	}
	/**
	 * 用作比较
	 * @return 临时用户。
	 */
	public static User getTempUser(String nickname) {
		return new User(nickname, null, -1, null);
	}
}
