package control;

import util.TcpUtil.SocketInfo;

/**
 * 后台接口类
 * @author Shinrai
 * @since 2017-6-26 16:43:06
 */
public interface BackstageInterface {
	public void returnChoose(int whichWindow);
	/* 登录请求调用方法 */
	public void loginRequest(String nickname);
	public void loadChatWindow();
	/* 扫描服务器调用方法 */
	public boolean scanServer();
	public void sendUdpMessage(String message);
	public void sendTcpMessage(String message);
	public void sendTcpMessagePrivate(String message, String nickname);
	public void udpCallBack(String receiveString);
	public void tcpCallBack(String receiveString, SocketInfo userInfo);
	public String getNickname();
	public void setEchoMessageInterface(WindowInterface wif);
}
