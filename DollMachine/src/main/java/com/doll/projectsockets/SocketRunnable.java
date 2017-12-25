package com.doll.projectsockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketRunnable implements Runnable {
	private Socket socket;
	
	public SocketRunnable(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			System.out.println("新连接:" + socket.getInetAddress() + ":"
					+ socket.getPort());
			PrintWriter pw=new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String str = null;
			while(true){
				String string=br.readLine();
				System.out.println("Server读到："+string);
				pw.println(str);
	            pw.flush();
			}
//                String string=br.readLine();
//                System.out.println("Server读到："+string);
/*                System.out.println("Server端请输入：");
                String str=ClientMain.scanner.next();
                pw.println(str);
                pw.flush();
                if(str.equals("exit")){
                    break;
                }*/
		} catch (Exception e) {
			e.printStackTrace();
		} /*finally {
			try {
				System.out.println("关闭连接:" + socket.getInetAddress() + ":"
						+ socket.getPort());
				if (socket != null)
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
	}

}
