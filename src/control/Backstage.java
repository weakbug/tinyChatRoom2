package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;

import model.User;
import util.NetworkUtil;
import view.*;

public class Backstage implements BackstageInterface {
	
	private static Backstage backstage;
	private WindowInterface serverWindow;
	private WindowInterface chatWindow;
	private User self;
	
	public static void main(String[] args) {
		backstage = new Backstage();
		Choose._main(backstage);
	}
	private Backstage() {}

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
//			System.out.println("banNickname : " + nickname);
			return false;
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	/**
	 * LoginWindow ���ڵ���
	 * @return ����ɨ�赽�ķ�������ַ��nullΪ��������Ӧ��
	 */
	@Override
	public String scanServer() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "0.0.0.0";
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
	public void sendMessage(String message) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public User getSelf() {
		// TODO Auto-generated method stub
		return self;
	}
}
