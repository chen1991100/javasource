package com.doll.projectsockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
	private static int port = 2222;
	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(port);
//			while(true){
				Socket socket = serverSocket.accept();
				socket.setKeepAlive(true);
				SocketRunnable socketRunnable = new SocketRunnable(socket);
				new Thread(socketRunnable).start();
//			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
