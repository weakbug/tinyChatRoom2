package control;

import util.TcpUtil.SocketInfo;

public interface WindowInterface {
	public void echoMessage(String message);
	public void otherFunc(boolean b);
	public void addOrDeleteServerListItem(SocketInfo socketInfo, String nickname);
}
