package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.*;

public class Backstage implements BackstageInterface {
	
	private static Backstage backstage;
	private WindowInterface serverWindow;
	private WindowInterface chatWindow;
	
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
		try {
			Thread.sleep(3000);
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
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	@Override
	public void loadChatWindow() {
		// TODO Auto-generated method stub
		chatWindow = ChatWindow._main(this);
	}
	@Override
	public void sendMessage(String message) {
		// TODO Auto-generated method stub
		
	}
}
