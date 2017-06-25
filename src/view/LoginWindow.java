package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class LoginWindow {

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindow window = new LoginWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 280, 120);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		
		JLabel lblUser = new JLabel("Nickname");
		springLayout.putConstraint(SpringLayout.NORTH, lblUser, 16, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblUser, 10, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(lblUser);
		
		textField = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, textField, 13, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, textField, 6, SpringLayout.EAST, lblUser);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblServerScaning = new JLabel("Server scaning..");
		springLayout.putConstraint(SpringLayout.WEST, lblServerScaning, 10, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(lblServerScaning);
		
		JButton btnNewButton = new JButton("Scan");
		springLayout.putConstraint(SpringLayout.NORTH, lblServerScaning, 4, SpringLayout.NORTH, btnNewButton);
		springLayout.putConstraint(SpringLayout.SOUTH, btnNewButton, -10, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnNewButton, 0, SpringLayout.EAST, textField);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnLogin = new JButton("LOGIN");
		springLayout.putConstraint(SpringLayout.EAST, textField, -6, SpringLayout.WEST, btnLogin);
		springLayout.putConstraint(SpringLayout.SOUTH, btnLogin, 0, SpringLayout.SOUTH, btnNewButton);
		springLayout.putConstraint(SpringLayout.NORTH, btnLogin, 0, SpringLayout.NORTH, textField);
		springLayout.putConstraint(SpringLayout.EAST, btnLogin, -10, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(btnLogin);
	}
}
