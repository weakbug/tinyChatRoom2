package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class TcpUtil {
	private static TcpUtil tcpUtil;
//	private List<Socket> socketlist=new ArrayList<Socket>();
	private List<SocketInfo> socketinfolist = new ArrayList<SocketInfo>();
	/**
	 * 服务端用的构造器
	 */
	private TcpUtil(int port) {
		try {
			final ServerSocket server = new ServerSocket(port);
			for(int i = 0;i<10;i++){
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						while(true) {
							Socket connect;
							try {
								connect = server.accept();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								connect = null;
								e.printStackTrace();
							}
							if(connect != null) {
//								socketlist.add(connect);
								socketinfolist.add(new SocketInfo(connect.getInetAddress().toString(),connect.getPort(), connect));
								exec(connect);
							}
						}
					}
				}).start();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 客户端用的构造器
	 * @param serverAddress 服务端地址
	 * @param port 服务端端口
	 */
	private TcpUtil(String serverAddress, int port) {
		try {
			Socket socket = new Socket(serverAddress, port);
			socketinfolist.add(new SocketInfo(serverAddress,port,socket));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static TcpUtil getTcpUtilOfServer(int port) {
		if(tcpUtil == null) {
			return new TcpUtil(port);
		}
		return tcpUtil;
	}
	public static TcpUtil getTcpUtilOfClient(String serverAddress, int port) {
		if(tcpUtil == null) {
			return new TcpUtil(serverAddress, port);
		}
		return tcpUtil;
	}
	/**
	 * 
	 * @param msg 需要发送的消息
	 */
	public void sendMessage(final String msg) {		
		new Thread(new Runnable() {
			@Override
			public void run() {
				PrintWriter writer = null;
				// TODO Auto-generated method stub
				for(SocketInfo si : socketinfolist) {
					Socket s = si.getSocket();
					try {
						writer = new PrintWriter(s.getOutputStream());
						writer.println(msg);
						writer.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	private void exec(final Socket socket) {//接收信息，回调
		new Thread(new Runnable(){
			@Override
			public void run(){
				try{
				BufferedReader reader = null;
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				while(true){
					String message = reader.readLine();
			    	System.out.println("text received : "+message);
				}    	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			}
		}).start();	
	}
		
	
	public Socket searchSocket(String ipAddress,Integer port){
		int index;
		SocketInfo temp = new SocketInfo(ipAddress,port);
		index = socketinfolist.indexOf(temp);
		if(index != -1) {
			return socketinfolist.get(index).getSocket();
		}
		return null;
	}
	class SocketInfo{
		private String ipAddress;
		private Integer port;
		private Socket socket;
		SocketInfo(String a,Integer b){
			ipAddress = a;
			port = b;
		}
		SocketInfo(String a, Integer b, Socket socket) {
			this(a, b);
			this.socket = socket;
		}
		public Socket getSocket() {
			return socket;
		}
		@Override 
		public boolean equals(Object anObject) {
			if(this == anObject) {
				return true;
			}
			if(anObject instanceof SocketInfo) {
				SocketInfo si = (SocketInfo)anObject;
				if(this.ipAddress.equals(si.ipAddress) && this.port == si.port) {
					return true;
				}
			}
			return false;
		}
	}
	
//	private static void execute(Socket client){
//		try {
//			OutputStream out = client.getOutputStream();
//			BufferedReader buf = new BufferedReader(new InputStreamReader(client.getInputStream()));
//			boolean flag = true;
//			while(flag){
//				String str = buf.readLine();
//				if(str==null || "".equals(str)){
//					flag = false;
//				}else{
//					if("over".equals(str)){
//						flag = false;
//					}else{
//						out.write("Message has been received".getBytes());
//					}
//				}
//			}
//			for(Socket a:socketlist){
//				if(a.equals(client)){
//					a=null;
//					break;
//				}
//			}
//			out.close();
//			buf.close();
//			client.close();
//		
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
//	
//	public static ServerSocket getServer() throws IOException{
//		final ServerSocket server = new ServerSocket(20086);
//		for(int i = 0;i<100;i++){
//			Thread thread = new Thread(){
//				public void run(){
//					while(true){
//						try{
//							Socket client = server.accept();
//							for(int i=0;i<100;i++){
//								if(socketlist[i]==null){
//									socketlist[i] = client;
//									break;
//								}
//							}
//							execute(client);
//						}catch(IOException e){
//							e.printStackTrace();
//						}
//					}
//				}
//			};
//			thread.start();
//		}
//		return server;
//	}
//	
//    public static Socket getClient() throws UnknownHostException, IOException{
//    	Socket socket = new Socket("224.0.0.1",20086);
//    	OutputStream out=socket.getOutputStream();
//    	out.write("请求连接".getBytes());
//    	out.close();
//    	return socket;
//    }
//    
//    public void ClientSendMessge(Socket client,String str) throws IOException{
//    	OutputStream out=client.getOutputStream();
//    	out.write(str.getBytes());
//    }
//    
//    public void ServerSendMessage(Socket[] socketlist,String str) throws IOException{
//    	for(Socket a:socketlist){
//    		OutputStream out = a.getOutputStream();
//    	    out.write(str.getBytes());
//    	}
//    }
//    
//    public void receiveMessage(Socket client) throws IOException{
//    	InputStream in=client.getInputStream();
//    	byte[] buf = new byte[1024];
//    	int len = in.read(buf);
//    	String text = new String(buf,0,len);
//    }
    
   
}
