package view;

import java.awt.EventQueue;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.UIManager;

import control.BackstageInterface;
import control.WindowInterface;
import util.TcpUtil.SocketInfo;

import javax.swing.SpringLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class ServerWindow implements WindowInterface {

	private JFrame frmServer;
	private JTable table;
	private JScrollPane scrollPane_1;
	private JTextField textField;
	private JButton btnSend;
	private JTextArea textArea;
	private BackstageInterface backstageInterface;
	private Vector<Vector<String>> dataVec;

	/**
	 * Launch the application.
	 */
	public static void _main(final BackstageInterface bif) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerWindow window = new ServerWindow(bif);
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
	public ServerWindow(BackstageInterface bif) {
		backstageInterface = bif;
		backstageInterface.setEchoMessageInterface(this);
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
		
		Vector<String> colHeader = new Vector<String>(); 
        colHeader.add("nickname"); 
        colHeader.add("ip address"); 
        colHeader.add("port"); 
        dataVec = new Vector<Vector<String>>(); 
		table = new JTable(dataVec, colHeader);
		scrollPane.setViewportView(table);
		/* test */
//		addUser2Table(new User("shinrai", "0.0.0.0", 81, "xxxxxx"));
//		addUser2Table(new User("shinrai2", "0.0.0.0", 81, "xxxxxx"));
//		deleteUserFromTable("shinrai");
		
		scrollPane_1 = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane_1, 6, SpringLayout.SOUTH, scrollPane);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane_1, 10, SpringLayout.WEST, frmServer.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, scrollPane_1, 0, SpringLayout.EAST, scrollPane);
		frmServer.getContentPane().add(scrollPane_1);
		
		textField = new JTextField();
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane_1, -6, SpringLayout.NORTH, textField);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
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

	@Override
	public void echoMessage(String message) {
		// TODO Auto-generated method stub
		textArea.append(message + "\n");
		textArea.setCaretPosition(textArea.getDocument().getLength());
	}
	private void addUser2Table(SocketInfo socketInfo) {
		Vector<String> newLine = new Vector<String>(); 
		newLine.add(socketInfo.getNickname());
		newLine.add(socketInfo.getAddress());
		newLine.add(String.valueOf(socketInfo.getPort()));
		dataVec.add(newLine);
	}
	private void deleteUserFromTable(String nickname) {
		for (int i = 0;i < dataVec.size(); i++) {
			Vector<String> data = dataVec.get(i);
			if(data.get(0).equals(nickname)) {
				dataVec.remove(data);
			}
		}
	}

	@Override
	public void otherFunc(boolean b) {
		// TODO Auto-generated method stub
		
	}
}
