package com.cytxcn.netty;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

//@SpringBootTest
public class SecurityApplicationTests implements ApplicationContextAware {

	private ApplicationContext context = null;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		 this.context = applicationContext;
	}

	
	@Test
	public void listener() {
		
	}
}
