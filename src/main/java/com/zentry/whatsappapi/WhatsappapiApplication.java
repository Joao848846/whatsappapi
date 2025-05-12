package com.zentry.whatsappapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocket;


@EnableScheduling
@SpringBootApplication
@EnableWebSocket
public class WhatsappapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WhatsappapiApplication.class, args);
	}

}
