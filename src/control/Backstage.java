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
import util.NetworkUtil;
import util.TcpUtil;
import util.UdpUtil;
import view.*;

public class Backstage implements BackstageInterface {
	
	private static Backstage backstage;
	private WindowInterface serverWindow;
	private WindowInterface chatWindow;
	private User self;
	private UdpUtil udpUtil;
	private TcpUtil tcpUtil;
	private String serverAddressAndPort = null;
	
	public static void main(String[] args) {
		backstage = new Backstage();
		Choose._main(backstage);
	}
	private Backstage() {
		udpUtil = new UdpUtil(this);
//		TcpUtil = new TcpUtil(this);
	}

	/**
	 * Choose ���ڵ���
	 * @param whichWindow ѡ�����õĴ���
	 */
	@Override
	public void returnChoose(int whichWindow) {
		// TODO Auto-generated method stub
		if(whichWindow == Choose.CLIENT) {
			LoginWindow._main(this);
		}
		if(whichWindow == Choose.SERVER) {
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
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * LoginWindow ���ڵ���
	 * @return ����ɨ�赽�ķ�������ַ:�˿ڡ�
	 */
	@Override
	public String scanServer() {
		// TODO Auto-generated method stub
		serverAddressAndPort = null;
		sendUdpMessage("unfinished");
		final ExecutorService exec = Executors.newFixedThreadPool(1);
		Callable<String> call = new Callable<String>() {
			@Override
			public String call() throws Exception {
				// TODO Auto-generated method stub
				while(true) {
					if(serverAddressAndPort != null) {
						Thread.sleep(200);
						return serverAddressAndPort;
					}
				}
			}
		};
		try {
			Future<String> future = exec.submit(call);
			serverAddressAndPort = future.get(1000 * 3, TimeUnit.MILLISECONDS);
		} catch (TimeoutException ex) {
			serverAddressAndPort = null;
            System.out.println("����ʱ��....");  
//            ex.printStackTrace();  
        } catch (Exception e) {
        	serverAddressAndPort = null;
            System.out.println("����ʧ��.");  
//            e.printStackTrace();  
        }
		return serverAddressAndPort;
	}
	@Override
	public void loadChatWindow(String nickname) {
		// TODO Auto-generated method stub
		String ipAddress = null;
		try {
			ipAddress = NetworkUtil.getLocalHostLANAddress().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		self = new User(nickname, ipAddress, 1234, null);
		chatWindow = ChatWindow._main(this);
	}
	@Override
	public User getSelf() {
		// TODO Auto-generated method stub
		return self;
	}
	@Override
	public void udpCallBack(String receiveString) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void tcpCallBack(String receiveString) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void sendUdpMessage(String message) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void sendTcpMessage(String message) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void sendTdpMessagePrivate(String message, String nickname) {
		// TODO Auto-generated method stub
		
	}
}
