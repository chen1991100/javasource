package com.doll.projecttimer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
public class TimerTask {
	@Scheduled(cron="0 * * * * ?") //无返回值
	public void say(){
		System.out.println("say hello say hello say hello");
	}
	
	@Scheduled(cron="0 * * * * ?") //无返回值
	public void saysay(){
		System.out.println("say hello say hello say hello");
	}
}
