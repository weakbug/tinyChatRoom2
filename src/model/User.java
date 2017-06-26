package model;
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
			"gm"
	};
	private String user;
	private String ipAddress;
	private int port;
	private String publicKey;
}
