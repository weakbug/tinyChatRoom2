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
	 * Choose 窗口调用
	 * @param whichWindow 选择启用的窗口
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
	 * LoginWindow 窗口调用
	 * @param nickname 登录的昵称
	 * @return 是否允许登录。
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
	 * LoginWindow 窗口调用
	 * @return 返回扫描到的服务器地址。null为服务器响应。
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
