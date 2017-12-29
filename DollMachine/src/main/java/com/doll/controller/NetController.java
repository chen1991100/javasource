package com.doll.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/net")
public class NetController {
	
	public static String client_ip = "0.0.0.0";
	public static int server_port= 0;
	@RequestMapping("/udp")
	public void startUdp(){
		System.out.println("server is on，waiting for client to send data......");
		DatagramSocket ds = null;
		int serverport = 2222;
		boolean flag=true;
		while(flag){
			try {
				ds = new DatagramSocket(serverport);
				server_port = serverport;
				flag=false;
			} catch (SocketException e) {
				serverport++;
			}
		}
		byte[] buf = new byte[1024];  
		DatagramPacket dp_receive = new DatagramPacket(buf, buf.length);
		boolean f = true;
		while(f){
			//服务器端接收来自客户端的数据  
            try {
				ds.receive(dp_receive);  
				System.out.println("server received data from client：");  
				String str_receive = new String(dp_receive.getData(),0,dp_receive.getLength()) +   
				        " from " + dp_receive.getAddress().getHostAddress() + ":" + dp_receive.getPort();  
				String str_send = dp_receive.getAddress().getHostAddress();
				client_ip =str_send;
				DatagramPacket dp_send= new DatagramPacket(str_send.getBytes(),str_send.length(),dp_receive.getAddress(),dp_receive.getPort());  
				ds.send(dp_send);  
				dp_receive.setLength(1024);
			} catch (IOException e) {
				ds.close();
				e.printStackTrace();
			}  
		}
		ds.close();
	}
	
	@RequestMapping("/ip")
	public void getinfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		PrintWriter out = response.getWriter();
		out.print(client_ip);
		out.flush();
		out.close();
	}
	
	@RequestMapping("/port")
	public void port(HttpServletRequest request,HttpServletResponse response) throws IOException{
		PrintWriter out = response.getWriter();
		out.print(server_port);
		out.flush();
		out.close();
	}
}
