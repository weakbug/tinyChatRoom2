package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.SpringLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class ServerWindow {

	private JFrame frmServer;
	private JTable table;
	private JScrollPane scrollPane_1;
	private JTextField textField;
	private JButton btnSend;
	private JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void _main() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerWindow window = new ServerWindow();
					window.frmServer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ServerWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmServer = new JFrame();
		frmServer.setTitle("Server");
		frmServer.setBounds(100, 100, 777, 479);
		frmServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frmServer.getContentPane().setLayout(springLayout);
		
		JScrollPane scrollPane = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.NORTH, frmServer.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, frmServer.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, 252, SpringLayout.NORTH, frmServer.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, frmServer.getContentPane());
		frmServer.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		scrollPane_1 = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane_1, 6, SpringLayout.SOUTH, scrollPane);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane_1, 10, SpringLayout.WEST, frmServer.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, scrollPane_1, 0, SpringLayout.EAST, scrollPane);
		frmServer.getContentPane().add(scrollPane_1);
		
		textField = new JTextField();
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane_1, -6, SpringLayout.NORTH, textField);
		
		textArea = new JTextArea();
		scrollPane_1.setViewportView(textArea);
		springLayout.putConstraint(SpringLayout.WEST, textField, 10, SpringLayout.WEST, frmServer.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, textField, -10, SpringLayout.SOUTH, frmServer.getContentPane());
		frmServer.getContentPane().add(textField);
		textField.setColumns(10);
		
		btnSend = new JButton("Send");
		springLayout.putConstraint(SpringLayout.SOUTH, btnSend, -10, SpringLayout.SOUTH, frmServer.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, textField, -6, SpringLayout.WEST, btnSend);
		springLayout.putConstraint(SpringLayout.EAST, btnSend, 0, SpringLayout.EAST, scrollPane);
		frmServer.getContentPane().add(btnSend);
	}
}
