package model;

import java.awt.Color;

/**
 * @author Shinrai
 * @since 2017-6-26 14:16:09
 */
public class User {
	public static final String[] banNickname = {
			"admin",
			"administrator", 
			"guest", 
			"user", 
			"gm", 
			"message", 
			"notification", 
			"error"
	};
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
		if(anObject instanceof String) {
			String s = (String)anObject;
			if(this.nickname.equals(s)) {
				return true;
			}
			return false;
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
}
