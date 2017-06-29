package view;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
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
import model.UserLab;
import util.HtmlUtil;

import javax.swing.SpringLayout;
import javax.swing.JScrollPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.SizeRequirements;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;

public class ChatWindow implements WindowInterface {

	private JFrame frmChatroom;
	private static ChatWindow window;
	private BackstageInterface backstageInterface;
	private JTextArea inputTextArea;
	private JTextPane dialogueTextPane;
	private JButton btnSend;
	private String publicChatRecord;
	private DefaultListModel listModel;

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
				String diaText = HtmlUtil.formatText2HTML(inputTextArea.getText());
				String appendText = HtmlUtil.addUserInfo(null, diaText);
				String allText = HtmlUtil.append(dialogueTextPane.getText(), appendText);
				dialogueTextPane.setText(allText);
				setChatRecord(allText);
				dialogueTextPane.setCaretPosition(dialogueTextPane.getDocument().getLength());
				inputTextArea.setText(null);
				inputTextArea.grabFocus();
				backstageInterface.sendTcpMessage(diaText);
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
		JList list = new JList(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(list);
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
		String html = HtmlUtil.welcome(HtmlUtil.getBase() , backstageInterface.getNickname());
		dialogueTextPane.setText(html);
		setChatRecord(html);
		frmChatroom.getContentPane().add(btnSend);
	}

	@Override
	public void echoMessage(String message) {
		// TODO Auto-generated method stub
		
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
		if(listModel.removeElement(str)){
			return;
		}else{
			System.out.println("错误：没有"+str);
		}
	}
}
