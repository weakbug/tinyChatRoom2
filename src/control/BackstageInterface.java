package control;

import model.User;

/**
 * 后台接口类
 * @author Shinrai
 * @since 2017-6-26 16:43:06
 */
public interface BackstageInterface {
	public void returnChoose(int whichWindow);
	public boolean loginRequest(String nickname);
	public void loadChatWindow(String nickname);
	public String scanServer();
	public void sendMessage(String message);
	public User getSelf();
}
