package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import util.HtmlUtil;
import util.MessageConstructor;
import util.MessageConstructor.Msg;
import util.TcpUtil.SocketInfo;
import util.NetworkUtil;
import util.TcpUtil;
import util.UdpUtil;
import view.*;

public class Backstage implements BackstageInterface {
	private boolean isServerMode;
	private static Backstage backstage;
	private WindowInterface window;
	private UdpUtil udpUtil;
	private TcpUtil tcpUtil;
	private String serverAddress;
	private int serverPort;
	private String nickname;
	
	public static void main(String[] args) {
		backstage = new Backstage();
		Choose._main(backstage);
	}
	private Backstage() {
		try {
			serverAddress = NetworkUtil.getLocalHostLANAddress().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		serverPort = 7747;//随机服务器端口这个功能搁一下
		/* 初始化socket(udp)部分 */
		udpUtil = new UdpUtil(this);
	}

	/**
	 * Choose 窗口调用
	 * @param whichWindow 选择启用的窗口
	 */
	@Override
	public void returnChoose(int whichWindow) {
		// TODO Auto-generated method stub
		if(whichWindow == Choose.CLIENT) {
			System.out.println("CLIENT");
			isServerMode = false;
			LoginWindow._main(this);
		}
		if(whichWindow == Choose.SERVER) {
			System.out.println("SERVER");
			isServerMode = true;
			tcpUtil = TcpUtil.getTcpUtilOfServer(serverPort, this);
			ServerWindow._main(this);
		}
	}
	/**
	 * LoginWindow 窗口调用
	 * @param nickname 登录的昵称
	 * @return 是否允许登录。
	 */
	@Override
	public void loginRequest(String nickname) {
		// TODO Auto-generated method stub
		this.nickname = nickname;
		if(tcpUtil == null) {
			tcpUtil = TcpUtil.getTcpUtilOfClient(serverAddress, serverPort, this);
		}
		sendTcpMessage(MessageConstructor.constructMessage(MessageConstructor.Code.TCP.LOGIN_REQUEST, nickname));
	}
	/**
	 * LoginWindow 窗口调用
	 * @return 返回扫描到的服务器地址:端口。
	 */
	@Override
	public boolean scanServer() {
		// TODO Auto-generated method stub
//		serverAddressAndPort = null;
		serverPort = -1;
		sendUdpMessage(MessageConstructor.constructMessage(MessageConstructor.Code.UDP.REQUEST_SERVER_ADDRESS_AND_PORT, ""));
		/**
		 * 超时控制代码，我自己写的，网上的都是垃圾
		 */
		for(int i=0;i<10;i++) {
			try {
				Thread.sleep(300);
				if(serverPort != -1) {
					return true;
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	@Override
	public void loadChatWindow() {
		// TODO Auto-generated method stub
		ChatWindow._main(this);
	}
	@Override
	public void udpCallBack(String receiveString) {
		// TODO Auto-generated method stub
		/* 其实udp接收的信息只有两种，
		 * 分别是 '服务器接收客户端的获取请求' 和 '客户端接收服务器的反馈' ，
		 * 所以只需要一个很简单的文本分割or正则匹配就OK。 */
		Msg msg = MessageConstructor.parseMessage(receiveString);
		System.out.println(receiveString);
		if(isServerMode) {
			if(msg.getCode() == MessageConstructor.Code.UDP.REQUEST_SERVER_ADDRESS_AND_PORT) {
				String newMsg = serverAddress + ":" + serverPort;
				sendUdpMessage(MessageConstructor.constructMessage(MessageConstructor.Code.UDP.SERVER_ADDRESS_AND_PORT_FEEDBACK, newMsg));
			}
		}
		else {
			if(msg.getCode() == MessageConstructor.Code.UDP.SERVER_ADDRESS_AND_PORT_FEEDBACK) {
				Msg tmp = MessageConstructor.parseIPAP(msg.getMessage());
				serverAddress = tmp.getMessage();
				serverPort = tmp.getCode();
			}
		}
	}

	@Override
	public void sendUdpMessage(String message) {
		// TODO Auto-generated method stub
		udpUtil.sendUdpPacket(message);
	}
	@Override
	public void sendTcpMessage(String message) {
		// TODO Auto-generated method stub
		tcpUtil.sendMessage(message, null);
	}
	@Override
	public void sendTcpMessagePrivate(String message, String nickname) {
		// TODO Auto-generated method stub
		tcpUtil.sendMessage(message, nickname);
	}
	private void sendTcpMessagePrivate(String message, SocketInfo userInfo) {
		// TODO Auto-generated method stub
		tcpUtil.sendMessage2(message, userInfo);
	}
	@Override
	public String getNickname() {
		// TODO Auto-generated method stub
		return nickname;
	}
	@Override
	public void setEchoMessageInterface(WindowInterface wif) {
		// TODO Auto-generated method stub
		window = wif;
	}
	@Override
	public void tcpCallBack(String receiveString, SocketInfo userInfo) {
		// TODO Auto-generated method stub
		Msg msg = MessageConstructor.parseMessage(receiveString);
		System.out.println(receiveString);
		if(isServerMode){
			if(msg.getCode() == MessageConstructor.Code.TCP.MESSAGE_FROM_CLIENT_TO_SERVER) {
				String newMessage = HtmlUtil.addUserInfo(userInfo.toString(), msg.getMessage());
				window.echoMessage(newMessage, null);
				sendTcpMessage(MessageConstructor.constructMessage(MessageConstructor.Code.TCP.MESSAGE_FROM_SERVER_TO_CLIENT, newMessage));
			}
			if(msg.getCode() == MessageConstructor.Code.TCP.LOGIN_REQUEST) {
				String nickname = msg.getMessage();
				boolean loginStatus = !tcpUtil.isContain(nickname) && !nickname.contains("-") && !nickname.equals("所有人");//ban '-'/"所有人" ,防止影响数据传输
				if(loginStatus) {
					/* 允许登录 */
					userInfo.setNickname(nickname);
					add2ServerList(userInfo);
					someEnterOrLeave(nickname, true);
				}
				sendTcpMessagePrivate(MessageConstructor.constructMessage(MessageConstructor.Code.TCP.LOGIN_FEEDBACK, String.valueOf(loginStatus)), userInfo);
			}
			if(msg.getCode() == MessageConstructor.Code.TCP.MESSAGE_PRIVATE) {
				String ori = msg.getMessage();
				String[] arr = MessageConstructor.parsePrivateMessage(ori);
				String newMsg = HtmlUtil.addUserInfo(userInfo.toString(), arr[1]);
				String umsg = MessageConstructor.constructMessage(MessageConstructor.Code.TCP.MESSAGE_PRIVATE, 
						MessageConstructor.constructPrivateMessage(newMsg, userInfo.getNickname()));
				String umsg2 = MessageConstructor.constructMessage(MessageConstructor.Code.TCP.MESSAGE_PRIVATE, 
						MessageConstructor.constructPrivateMessage(newMsg, arr[0]));
				sendTcpMessagePrivate(umsg, arr[0]); /* 投递私聊信息至目的地 */
				sendTcpMessagePrivate(umsg2, userInfo); /* 回显私聊信息至发送端 */
			}
			if(msg.getCode() == MessageConstructor.Code.TCP.CURRENT_USER_REQUEST) {
				tcpUtil.userBroadcast(userInfo);
			}
		}
		else {
			SocketInfo fakeBooleanTure = new SocketInfo("");
			SocketInfo fakeBooleanFalse = null;
			if(msg.getCode() == MessageConstructor.Code.TCP.MESSAGE_FROM_SERVER_TO_CLIENT) {
				window.echoMessage(msg.getMessage(), null);
			}
			if(msg.getCode() == MessageConstructor.Code.TCP.LOGIN_FEEDBACK) {
				boolean status = Boolean.parseBoolean(msg.getMessage());
				window.otherFunc(status);
				if(status == true) {
					//获取当前用户请求
					sendTcpMessage(MessageConstructor.constructMessage(MessageConstructor.Code.TCP.CURRENT_USER_REQUEST, ""));
				}
			}
			if(msg.getCode() == MessageConstructor.Code.TCP.MESSAGE_PRIVATE) {
				String ori = msg.getMessage();
				String[] arr = MessageConstructor.parsePrivateMessage(ori);
				window.echoMessage(arr[1], arr[0]);
			}
			if(msg.getCode() == MessageConstructor.Code.TCP.USER_ONLINE) {
				window.addOrDeleteListItem(fakeBooleanTure, msg.getMessage());
			}
			if(msg.getCode() == MessageConstructor.Code.TCP.USER_OFFLINE) {
				window.addOrDeleteListItem(fakeBooleanFalse, msg.getMessage());
			}
			if(msg.getCode() == MessageConstructor.Code.TCP.CURRRENT_USER_FEEDBACK) {
				List<String> nicknameSet = MessageConstructor.parseCurrentUserMsg(msg.getMessage());
				for(String s : nicknameSet) {
					window.addOrDeleteListItem(fakeBooleanTure, s);
				}
			}
		} 
	}
	@Override
	public void someEnterOrLeave(String nickname, boolean eol) {
		// TODO Auto-generated method stub
		if(isServerMode) {
			String s;
			String msg;
			/* 用户进入 */
			if(eol) {
				msg = MessageConstructor.constructMessage(MessageConstructor.Code.TCP.USER_ONLINE, nickname);
				s = HtmlUtil.welcome(nickname);
			}
			/* 用户离开 */
			else {
				msg = MessageConstructor.constructMessage(MessageConstructor.Code.TCP.USER_OFFLINE, nickname);
				s = HtmlUtil.leave(nickname);
			}
			window.echoMessage(s, null);
			tcpUtil.sendMessageNoSelf(msg, nickname);
			sendTcpMessage(MessageConstructor.constructMessage(MessageConstructor.Code.TCP.MESSAGE_FROM_SERVER_TO_CLIENT, s));
		}
		else if(!eol) {
			/* 服务器下线 */
			window.echoMessage(HtmlUtil.serverShutdown(), null);
			window.otherFunc(false);
		}
	}
	@Override
	public void add2ServerList(SocketInfo socketinfo) {
		// TODO Auto-generated method stub
		if(isServerMode) {
			window.addOrDeleteListItem(socketinfo, null);
		}
	}
	@Override
	public void deleteFromServerList(String nickname) {
		// TODO Auto-generated method stub
		if(isServerMode) {
			window.addOrDeleteListItem(null, nickname);
		}
	}
}
