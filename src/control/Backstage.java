package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.*;

public class Backstage implements BackstageInterface {
	private static Backstage backstage;
	public static void main(String[] args) {
		backstage = new Backstage();
		Choose._main(backstage);
	}
	private Backstage() {}

	@Override
	public void returnChoose(int whichWindow) {
		// TODO Auto-generated method stub
		if(whichWindow == Choose.CLIENT) {
			LoginWindow._main();
		}
		if(whichWindow == Choose.SERVER) {
			ServerWindow._main();
		}
	}
	
}
