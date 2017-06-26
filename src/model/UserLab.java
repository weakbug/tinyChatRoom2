package model;

import java.util.ArrayList;
import java.util.List;

/**
 * 单例模式，用户操作类
 * @author Shinrai
 * @since 2017-6-26 14:21:23
 */
public class UserLab {
	private static UserLab userLab;
	private List<User> userList;
	
	private UserLab() {
		userList = new ArrayList<User>();
	}
	public static synchronized UserLab getUserLab() {
		if(userLab == null) {
			userLab = new UserLab();
		}
		return userLab;
	}
	
}
