package com.doll.controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doll.pagebean.DataTableBean;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@RequestMapping("/list")
	@ResponseBody
	public DataTableBean getUserInfo(HttpServletRequest request,HttpServletResponse response){
	    return null;
	}
	
	@RequestMapping("/login")
	public void login(HttpServletRequest request,HttpServletResponse response){

	}
	
	public static void main(String[] args) {
		DatagramSocket ds = null;
		try {
			String str_send = "Hello UDPclient";  
			byte[] buf = new byte[1024];  
			ds = new DatagramSocket(2222);
			//接收从客户端发送过来的数据  
	        DatagramPacket dp_receive = new DatagramPacket(buf, buf.length);  
	        System.out.println("server is on，waiting for client to send data......");
	        boolean f = true;  
	        while(f){  
	            //服务器端接收来自客户端的数据  
	            ds.receive(dp_receive);  
	            System.out.println("server received data from client：");  
	            String str_receive = new String(dp_receive.getData(),0,dp_receive.getLength()) +   
	                    " from " + dp_receive.getAddress().getHostAddress() + ":" + dp_receive.getPort();  
	            System.out.println(str_receive);  
	            //数据发动到客户端的3000端口  
	            DatagramPacket dp_send= new DatagramPacket(str_send.getBytes(),str_send.length(),dp_receive.getAddress(),9000);  
	            ds.send(dp_send);  
	            dp_receive.setLength(1024);  
	        }  
	        ds.close(); 
		} catch (SocketException e) {
			ds.close();
			e.printStackTrace();
		} catch (IOException e) {
			ds.close();
			e.printStackTrace();
		}  
	}
}
