package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.JButton;

public class Choose {

	private JFrame frame;

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
					Choose window = new Choose();
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
	public Choose() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 220, 82);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		
		JButton btnServer = new JButton("Server");
		springLayout.putConstraint(SpringLayout.WEST, btnServer, 10, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(btnServer);
		
		JButton btnClient = new JButton("Client");
		springLayout.putConstraint(SpringLayout.NORTH, btnClient, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, btnServer, 0, SpringLayout.NORTH, btnClient);
		springLayout.putConstraint(SpringLayout.EAST, btnClient, -10, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(btnClient);
	}
}
