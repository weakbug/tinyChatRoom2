package control;

import util.TcpUtil.SocketInfo;

public interface WindowInterface {
	public void echoMessage(String message, String nickname);
	/* ��������кü����÷���˵������Ϊ��͵�����ٽӿڶ�ƴ�ӳ����� */
	public void otherFunc(boolean b);
	public void addOrDeleteListItem(SocketInfo socketInfo, String nickname);
}
