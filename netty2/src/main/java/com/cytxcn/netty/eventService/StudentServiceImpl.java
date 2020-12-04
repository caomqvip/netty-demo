package com.cytxcn.netty.eventService;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.cytxcn.netty.event.StudentEvent;

@Component
public class StudentServiceImpl  {

	
	
	@EventListener(classes = {ApplicationEvent.class})
	public void handleStudentEvent(StudentEvent event) {
		System.err.println(event.toString());
	}


}	
