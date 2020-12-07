package com.cytxcn.netty;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cytxcn.netty.server.ProtocolServer;

@SpringBootApplication
public class Netty2Application implements CommandLineRunner {

	@Autowired
	private ProtocolServer nettyServer; 

	
	public static void main(String[] args) {
		SpringApplication.run(Netty2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		nettyServer.run();
	}
	
	
	
	

}
