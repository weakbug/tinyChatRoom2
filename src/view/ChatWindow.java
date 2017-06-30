package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Element;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.InlineView;
import javax.swing.text.html.ParagraphView;

import control.BackstageInterface;
import control.WindowInterface;
import util.HtmlUtil;
import util.MessageConstructor;
import util.TcpUtil.SocketInfo;

import javax.swing.SpringLayout;
import javax.swing.JScrollPane;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SizeRequirements;
import javax.swing.JList;
import javax.swing.JPanel;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;

public class ChatWindow implements WindowInterface {

	private JFrame frmChatroom;
	private BackstageInterface backstageInterface;
	private JTextArea inputTextArea;
	private JTextPane dialogueTextPane;
	private JButton btnSend;
	private String publicChatRecord;
	private DefaultListModel listModel;
	private JList list;
	private Render render;
	
	class Render extends JLabel implements ListCellRenderer{
		Color color;
		int row = -1;
		public Render(){
			
		}
		public Render(Color color, int i) {
			// TODO Auto-generated constructor stub
			this.color = color;
			this.row = i;
		}

		@Override
		 public Component getListCellRendererComponent(JList list, Object value,  
		            int index, boolean isSelected, boolean cellHasFocus) {   
			setText(value.toString());
			System.out.println(value.toString());
			 if(this.row != -1){
				 if(index == this.row){
						setForeground(color);
						System.out.println("red");
					}else{
						setBackground(list.getBackground());  
			              setForeground(list.getForeground()); 
			              setFont(list.getFont());  
					}
			 }else{
				 setBackground(list.getBackground());  
	              setForeground(list.getForeground()); 
	              setFont(list.getFont());
			 }
			 if (isSelected) {  
				 setBackground(Color.GRAY);
				 setForeground(Color.WHITE);
				 }
			if(this.row!= -1 && index == this.row && isSelected){
				row= -1;
			}
		          setEnabled(list.isEnabled());  	          
		        setOpaque(true);  
		  
		        return this;  
		    }  
	}

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
					ChatWindow window = new ChatWindow(bif);
					window.frmChatroom.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ChatWindow(BackstageInterface bif) {
		backstageInterface = bif;
		backstageInterface.setEchoMessageInterface(this);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmChatroom = new JFrame();
		frmChatroom.setTitle("Chatroom");
		frmChatroom.setBounds(100, 100, 778, 502);
		frmChatroom.setResizable(false);
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
		
		btnSend = new JButton("Send");
		btnSend.setEnabled(false);
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String diaText = HtmlUtil.formatText2HTML(inputTextArea.getText(), true);
				String appendText = HtmlUtil.addUserInfo(backstageInterface.getNickname(), diaText);
				inputTextArea.setText(null);
				inputTextArea.grabFocus();
				String msg = MessageConstructor.constructMessage(MessageConstructor.Code.TCP.MESSAGE_FROM_CLIENT_TO_SERVER, diaText);
				backstageInterface.sendTcpMessage(msg);
			}
		});
		springLayout.putConstraint(SpringLayout.EAST, scrollPane_2, -6, SpringLayout.WEST, btnSend);
		
		inputTextArea = new JTextArea();
		inputTextArea.setWrapStyleWord(true);
		inputTextArea.setLineWrap(true);
		scrollPane_2.setViewportView(inputTextArea);
		inputTextArea.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_ENTER) {
					inputTextArea.append("\n");
				}
				else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					e.consume();
					btnSend.doClick();
				}
			}
		});
		inputTextArea.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				if(inputTextArea.getText().equals("")) {
					btnSend.setEnabled(false);
				}
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				btnSend.setEnabled(true);
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		springLayout.putConstraint(SpringLayout.SOUTH, btnSend, 0, SpringLayout.SOUTH, scrollPane);
		
		
		listModel = new DefaultListModel();
		listModel.addElement("所有人");
		addToList("fuck");
		addToList("happy");
		addToList("H");
		list = new JList(listModel);
		list.setCellRenderer(new Render());
		list.setCellRenderer(new Render(Color.RED,3));
		list.setCellRenderer(new Render(Color.RED,1));
//		list.setCellRenderer((ListCellRenderer) render.getListCellRenderComponent(list, "", 1, true , true));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(list);
//		changeColor("所有人");
		
//		changeColor("H");
//		changeColor("happy");
//		backChangeColor("H");
		
		springLayout.putConstraint(SpringLayout.EAST, btnSend, 0, SpringLayout.EAST, scrollPane_1);
		
		dialogueTextPane = new JTextPane();
		dialogueTextPane.setEditable(false);
		/*
		 * JTextPane HTML word wrap. get source code from:
		 * https://stackoverflow.com/questions/7811666/enabling-word-wrap-in-a-jtextpane-with-htmldocument
		 * http://java-sl.com/tip_html_letter_wrap.html
		 */
		dialogueTextPane.setEditorKit(new HTMLEditorKit(){ 
	           @Override 
	           public ViewFactory getViewFactory(){ 
	 
	               return new HTMLFactory(){ 
	                   public View create(Element e){ 
	                      View v = super.create(e); 
	                      if(v instanceof InlineView){ 
	                          return new InlineView(e){ 
	                              public int getBreakWeight(int axis, float pos, float len) { 
	                                  return GoodBreakWeight; 
	                              } 
	                              public View breakView(int axis, int p0, float pos, float len) { 
	                                  if(axis == View.X_AXIS) { 
	                                      checkPainter(); 
	                                      int p1 = getGlyphPainter().getBoundedPosition(this, p0, pos, len); 
	                                      if(p0 == getStartOffset() && p1 == getEndOffset()) { 
	                                          return this; 
	                                      } 
	                                      return createFragment(p0, p1); 
	                                  } 
	                                  return this; 
	                                } 
	                            }; 
	                      } 
	                      else if (v instanceof ParagraphView) { 
	                          return new ParagraphView(e) { 
	                              protected SizeRequirements calculateMinorAxisRequirements(int axis, SizeRequirements r) { 
	                                  if (r == null) { 
	                                        r = new SizeRequirements(); 
	                                  } 
	                                  float pref = layoutPool.getPreferredSpan(axis); 
	                                  float min = layoutPool.getMinimumSpan(axis); 
	                                  // Don't include insets, Box.getXXXSpan will include them. 
	                                    r.minimum = (int)min; 
	                                    r.preferred = Math.max(r.minimum, (int) pref); 
	                                    r.maximum = Integer.MAX_VALUE; 
	                                    r.alignment = 0.5f; 
	                                  return r; 
	                                } 
	 
	                            }; 
	                        } 
	                      return v; 
	                    } 
	                }; 
	            } 
	        }); 
		dialogueTextPane.setContentType("text/html");
		scrollPane_1.setViewportView(dialogueTextPane);
		String html = HtmlUtil.getBase();
		dialogueTextPane.setText(html);
		setChatRecord(html);
		frmChatroom.getContentPane().add(btnSend);
	}
	private void appendNewDia(String appendText) {
		String allText = HtmlUtil.append(dialogueTextPane.getText(), appendText);
		dialogueTextPane.setText(allText);
		setChatRecord(allText);
		dialogueTextPane.setCaretPosition(dialogueTextPane.getDocument().getLength());
	}
	@Override
	public void echoMessage(String message, String nickname) {
		// TODO Auto-generated method stub
		System.out.println("echoMessage");
		appendNewDia(message);
	}

	private String getChatRecord() {
		return publicChatRecord;
	}
	private void setChatRecord(String chatRecord) {
		publicChatRecord = chatRecord;
	}
	private void addToList(String str){
		listModel.addElement(str);
	}
	private void deleteFromList(String str){
		listModel.removeElement(str);
	}

	@Override
	public void otherFunc(boolean b) {
		// TODO Auto-generated method stub
		inputTextArea.setText("");
		inputTextArea.setEnabled(b);
	}

	@Override
	public void addOrDeleteServerListItem(SocketInfo socketInfo, String nickname) {
		// TODO Auto-generated method stub
		
	}
	private int search(String str){
		int index;
		index = listModel.indexOf(str);
		return index;
	}
}
