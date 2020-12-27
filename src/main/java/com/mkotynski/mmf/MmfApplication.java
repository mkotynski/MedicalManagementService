package com.mkotynski.mmf;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.security.Security;

@SpringBootApplication
public class MmfApplication {

	public static void main(String[] args) {
		SpringApplication.run(MmfApplication.class, args);
	}
}
