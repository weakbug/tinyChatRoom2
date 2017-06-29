package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import model.User;
import util.MessageConstructor;
import util.MessageConstructor.Msg;
import util.NetworkUtil;
import util.TcpUtil;
import util.UdpUtil;
import view.*;

public class Backstage implements BackstageInterface {
	private boolean isServerMode;
	private static Backstage backstage;
	private WindowInterface serverWindow;
	private WindowInterface chatWindow;
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
		serverPort = 7747;
		//��ʼ��socket(udp)����
		udpUtil = new UdpUtil(this);
	}

	/**
	 * Choose ���ڵ���
	 * @param whichWindow ѡ�����õĴ���
	 */
	@Override
	public void returnChoose(int whichWindow) {
		// TODO Auto-generated method stub
		if(whichWindow == Choose.CLIENT) {
			isServerMode = false;
			LoginWindow._main(this);
		}
		if(whichWindow == Choose.SERVER) {
			isServerMode = true;
			tcpUtil = TcpUtil.getTcpUtilOfServer(serverPort, this);
			serverWindow = ServerWindow._main(this);
			
		}
	}
	/**
	 * LoginWindow ���ڵ���
	 * @param nickname ��¼���ǳ�
	 * @return �Ƿ������¼��
	 */
	@Override
	public boolean loginRequest(String nickname) {
		// TODO Auto-generated method stub
		if(nickname.matches(User.banNickname)) {
			return false;
		}
		this.nickname = nickname;
		tcpUtil = TcpUtil.getTcpUtilOfClient(serverAddress, serverPort, this);
		tcpUtil.sendMessage("test");
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return true;
	}
	/**
	 * LoginWindow ���ڵ���
	 * @return ����ɨ�赽�ķ�������ַ:�˿ڡ�
	 */
	@Override
	public boolean scanServer() {
		// TODO Auto-generated method stub
//		serverAddressAndPort = null;
		serverPort = -1;
		sendUdpMessage(MessageConstructor.constructMessage(MessageConstructor.Code.UDP.REQUEST_SERVER_ADDRESS_AND_PORT, ""));
		/**
		 * ��ʱ���ƴ��룬ȡ��������վ
		 * http://blog.csdn.net/xmlrequest/article/details/8992029
		 */
		final ExecutorService exec = Executors.newFixedThreadPool(1);
		Callable<String> call = new Callable<String>() {
			@Override
			public String call() throws Exception {
				// TODO Auto-generated method stub
				while(true) {
					if(serverPort != -1) {
						Thread.sleep(300);//����ѭ��Ƶ��
						return null;
					}
				}
			}
		};
		try {
			Future<String> future = exec.submit(call);
			future.get(1000 * 4, TimeUnit.MILLISECONDS);
			return true;
		} catch (TimeoutException ex) {
            System.out.println("��ȡ��������ַ�Ͷ˿ںų�ʱ��....");
            return false;
        } catch (Exception e) {
            System.out.println("��ȡʧ��.(δ֪ԭ��)");
            return false;
        }
	}
	@Override
	public void loadChatWindow(String nickname) {
		// TODO Auto-generated method stub
		chatWindow = ChatWindow._main(this);
	}
	@Override
	public void udpCallBack(String receiveString) {
		// TODO Auto-generated method stub
		/* ��ʵudp���յ���Ϣֻ�����֣�
		 * �ֱ��� '���������տͻ��˵Ļ�ȡ����' �� '�ͻ��˽��շ������ķ���' ��
		 * ����ֻ��Ҫһ���ܼ򵥵��ı��ָ�or����ƥ���OK�� */
		Msg msg = MessageConstructor.parseMessage(receiveString);
		if(isServerMode) {
			if(msg.getCode() == MessageConstructor.Code.UDP.REQUEST_SERVER_ADDRESS_AND_PORT) {
				String newMsg = serverAddress + ":" + serverPort;
				sendUdpMessage(MessageConstructor.constructMessage(MessageConstructor.Code.UDP.SERVER_ADDRESS_AND_PORT_FEEDBACK, newMsg));
			}
			if(msg.getCode() == MessageConstructor.Code.UDP.SERVER_ADDRESS_AND_PORT_FEEDBACK) {
				Msg tmp = MessageConstructor.parseIPAP(msg.getMessage());
				serverAddress = tmp.getMessage();
				serverPort = tmp.getCode();
			}
		}
	}
	@Override
	public void tcpCallBack(String receiveString) {
		// TODO Auto-generated method stub
		System.out.println(receiveString);
	}
	@Override
	public void sendUdpMessage(String message) {
		// TODO Auto-generated method stub
		udpUtil.sendUdpPacket(message);
	}
	@Override
	public void sendTcpMessage(String message) {
		// TODO Auto-generated method stub
		tcpUtil.sendMessage(message);
	}
	@Override
	public void sendTdpMessagePrivate(String message, String nickname) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getNickname() {
		// TODO Auto-generated method stub
		return null;
	}
}
