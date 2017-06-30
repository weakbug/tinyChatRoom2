package control;

import util.TcpUtil.SocketInfo;

public interface WindowInterface {
	public void echoMessage(String message, String nickname);
	/* 这个方法有好几个用法，说到底是为了偷懒减少接口而拼接出来的 */
	public void otherFunc(boolean b);
	public void addOrDeleteServerListItem(SocketInfo socketInfo, String nickname);
}
