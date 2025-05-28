package com.AutoTrack;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.Key;

@SpringBootApplication
public class ProjectAutoTrackApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectAutoTrackApplication.class, args);
	}

	public static void GenerateKey()
	{
		Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		String secret = Encoders.BASE64.encode(key.getEncoded());
		System.out.println("Secure key: " + secret);
	}
}
