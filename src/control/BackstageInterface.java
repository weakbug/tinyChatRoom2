package control;

import util.TcpUtil.SocketInfo;

/**
 * ��̨�ӿ���
 * @author Shinrai
 * @since 2017-6-26 16:43:06
 */
public interface BackstageInterface {
	public void returnChoose(int whichWindow);
	/* ��¼������÷��� */
	public void loginRequest(String nickname);
	public void loadChatWindow();
	/* ɨ����������÷��� */
	public boolean scanServer();
	public void sendUdpMessage(String message);
	public void sendTcpMessage(String message);
	public void sendTcpMessagePrivate(String message, String nickname);
	public void udpCallBack(String receiveString);
	public void tcpCallBack(String receiveString, SocketInfo userInfo);
	public String getNickname();
	public void setEchoMessageInterface(WindowInterface wif);
}
