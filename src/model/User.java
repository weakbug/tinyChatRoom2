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
			"notification"
	};
	private String nickname;
	private String ipAddress;
	private int port;
	private String publicKey;
	private Color color;
	
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
	
	public String getNickname() {
		return nickname;
	}
}
