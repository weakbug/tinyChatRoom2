package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.SpringLayout;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JList;

public class ChatWindow {

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
					ChatWindow window = new ChatWindow();
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
	public ChatWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 778, 502);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		
		JScrollPane scrollPane = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -10, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, 142, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(scrollPane);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane_1, 0, SpringLayout.NORTH, scrollPane);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane_1, 6, SpringLayout.EAST, scrollPane);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane_1, 367, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, scrollPane_1, -10, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(scrollPane_1);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane_2, 6, SpringLayout.SOUTH, scrollPane_1);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane_2, 6, SpringLayout.EAST, scrollPane);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane_2, 0, SpringLayout.SOUTH, scrollPane);
		frame.getContentPane().add(scrollPane_2);
		
		JButton btnSend = new JButton("Send");
		springLayout.putConstraint(SpringLayout.EAST, scrollPane_2, -6, SpringLayout.WEST, btnSend);
		
		JTextArea inputTextArea = new JTextArea();
		scrollPane_2.setViewportView(inputTextArea);
		springLayout.putConstraint(SpringLayout.SOUTH, btnSend, 0, SpringLayout.SOUTH, scrollPane);
		
		JList list = new JList();
		scrollPane.setViewportView(list);
		springLayout.putConstraint(SpringLayout.EAST, btnSend, 0, SpringLayout.EAST, scrollPane_1);
		
		JTextArea dialogueTextArea = new JTextArea();
		scrollPane_1.setViewportView(dialogueTextArea);
		frame.getContentPane().add(btnSend);
	}

}
