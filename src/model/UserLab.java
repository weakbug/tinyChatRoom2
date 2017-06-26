package model;

/**
 * 单例模式，用户操作类
 * @author Shinrai
 * @since 2017-6-26 14:21:23
 */
public class UserLab {
	private static UserLab userLab;
	
	private UserLab() {
	}
	public static synchronized UserLab getUserLab() {
		if(userLab == null) {
			userLab = new UserLab();
		}
		return userLab;
	}
}
