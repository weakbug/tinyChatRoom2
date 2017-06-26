package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;

import control.BackstageInterface;
import control.WindowInterface;

import javax.swing.SpringLayout;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChatWindow implements WindowInterface {

	private JFrame frmChatroom;
	private static ChatWindow window;
	private BackstageInterface backstageInterface;
	private JTextArea inputTextArea;
	private JTextArea dialogueTextArea;

	/**
	 * Launch the application.
	 */
	public static WindowInterface _main(final BackstageInterface bif) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new ChatWindow(bif);
					window.frmChatroom.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		return window;
	}

	/**
	 * Create the application.
	 */
	public ChatWindow(BackstageInterface bif) {
		backstageInterface = bif;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmChatroom = new JFrame();
		frmChatroom.setTitle("Chatroom");
		frmChatroom.setBounds(100, 100, 778, 502);
		frmChatroom.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frmChatroom.getContentPane().setLayout(springLayout);
		
		JScrollPane scrollPane = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.NORTH, frmChatroom.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, frmChatroom.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -10, SpringLayout.SOUTH, frmChatroom.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, 142, SpringLayout.WEST, frmChatroom.getContentPane());
		frmChatroom.getContentPane().add(scrollPane);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane_1, 0, SpringLayout.NORTH, scrollPane);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane_1, 6, SpringLayout.EAST, scrollPane);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane_1, 367, SpringLayout.NORTH, frmChatroom.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, scrollPane_1, -10, SpringLayout.EAST, frmChatroom.getContentPane());
		frmChatroom.getContentPane().add(scrollPane_1);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane_2, 6, SpringLayout.SOUTH, scrollPane_1);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane_2, 6, SpringLayout.EAST, scrollPane);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane_2, 0, SpringLayout.SOUTH, scrollPane);
		frmChatroom.getContentPane().add(scrollPane_2);
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String inputText = inputTextArea.getText();
				dialogueTextArea.append("Œ“£∫" + inputText + "\n");
				inputTextArea.setText(null);
				backstageInterface.sendMessage(inputText);
			}
		});
		springLayout.putConstraint(SpringLayout.EAST, scrollPane_2, -6, SpringLayout.WEST, btnSend);
		
		inputTextArea = new JTextArea();
		inputTextArea.setLineWrap(true);
		scrollPane_2.setViewportView(inputTextArea);
		springLayout.putConstraint(SpringLayout.SOUTH, btnSend, 0, SpringLayout.SOUTH, scrollPane);
		
		JList list = new JList();
		scrollPane.setViewportView(list);
		springLayout.putConstraint(SpringLayout.EAST, btnSend, 0, SpringLayout.EAST, scrollPane_1);
		
		dialogueTextArea = new JTextArea();
		dialogueTextArea.setLineWrap(true);
		scrollPane_1.setViewportView(dialogueTextArea);
		frmChatroom.getContentPane().add(btnSend);
	}

	@Override
	public void echoMessage(String message) {
		// TODO Auto-generated method stub
		
	}

}
