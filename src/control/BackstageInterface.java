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
	public boolean scanServer();
	public void sendUdpMessage(String message);
	public void sendTcpMessage(String message);
	public void sendTcpMessagePrivate(String message, String nickname);
	public void udpCallBack(String receiveString);
	public void tcpCallBack(String receiveString);
	public String getNickname();
}
