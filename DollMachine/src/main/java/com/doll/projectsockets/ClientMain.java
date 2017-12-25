package com.doll.projectsockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientMain {
	private static String ip="127.0.0.1";
	private static int port=2222;
	static Scanner scanner=new Scanner(System.in);
	public static void main(String[] args) {
		try {
			Socket socket = new Socket(ip,port);
			PrintWriter pw=new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				/*System.out.println("Client端请输入：");
				String str = scanner.next();*/
			while(true){
				pw.println("55555");
				pw.flush();
				String str =br.readLine();
				System.out.println(str);
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
