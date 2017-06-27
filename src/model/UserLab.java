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
	public User getUser(String nickname) {
		int index = userList.indexOf(User.getTempUser(nickname));
		if(index != -1) {
			return userList.get(index);
		}
		return new User("error", "0.0.0.0", 88, null);//出错保护
	}
}
