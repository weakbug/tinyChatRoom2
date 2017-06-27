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
import util.HtmlUtil;

import javax.swing.SpringLayout;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
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
		
		btnSend = new JButton("Send");
		btnSend.setEnabled(false);
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String inputText = inputTextArea.getText();
//				dialogueTextArea.append("我：" + inputText + "\n");
//				dialogueTextPane.setText(dialogueTextPane.getText() + "我：" + inputText + "<br />");
//				"<html><body><font color=\"#ff0000\">我：你好</font><br /><font color=\"#ff0000\">a：大家好！</font></body></html>"
				dialogueTextPane.setText(HtmlUtil.addNewDialogue(dialogueTextPane.getText(), inputText, false));
				System.out.println(dialogueTextPane.getText());
				dialogueTextPane.setCaretPosition(dialogueTextPane.getDocument().getLength());
				inputTextArea.setText(null);
				inputTextArea.grabFocus();
				backstageInterface.sendMessage(inputText);
			}
		});
		springLayout.putConstraint(SpringLayout.EAST, scrollPane_2, -6, SpringLayout.WEST, btnSend);
		
		inputTextArea = new JTextArea();
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
		
		JList list = new JList();
		scrollPane.setViewportView(list);
		springLayout.putConstraint(SpringLayout.EAST, btnSend, 0, SpringLayout.EAST, scrollPane_1);
		
		dialogueTextPane = new JTextPane();
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
		dialogueTextPane.setText("<html><style type=\"text/css\">ul{ margin-left: 14px;}</style><body><ul><font color=\"#ff0000\">shinrai(0.0.0.0:1234):</font><li>关于计网的实验报告和课设</li><li style=\"list-style-type:none\">1、要交的有：①实验报告；②打印版课设任务书和课设报告、电子版任务书和课设报告、程序源码</li><li style=\"list-style-type:none\">2、7月5日23：00前交给我（老学委)_）</li><li style=\"list-style-type:none\">3、消息群里有具体要求和模板（实验和课设的都有）</li><li style=\"list-style-type:none\">4、另外，请需要申请答辩的同学在7月5日前跟我报名</li><li>内容2</li></ul></body></html>");
		frmChatroom.getContentPane().add(btnSend);
	}

	@Override
	public void echoMessage(String message) {
		// TODO Auto-generated method stub
		
	}

}
