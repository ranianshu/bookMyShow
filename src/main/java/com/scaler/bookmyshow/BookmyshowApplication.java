package com.scaler.bookmyshow;

import com.scaler.bookmyshow.controllers.UserController;
import com.scaler.bookmyshow.dtos.LoginRequestDTO;
import com.scaler.bookmyshow.dtos.LoginResponseDTO;
import com.scaler.bookmyshow.dtos.SignUpRequestDTO;
import com.scaler.bookmyshow.models.BaseModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BookmyshowApplication implements CommandLineRunner{

	@Autowired
	private UserController userController;

	public static void main(String[] args) {
		SpringApplication.run(BookmyshowApplication.class, args);
		System.out.println("BookMyShow");
	}

	@Override
	public void run(String... args) throws Exception {
		/*
		 * SignUpRequestDTO signUpRequestDTO = new SignUpRequestDTO();
		 * signUpRequestDTO.setEmail("ujjwal@email.com");
		 * signUpRequestDTO.setPassword("ujjwal!123");
		 * signUpRequestDTO.setName("Ujjwal"); userController.signUp(signUpRequestDTO);
		 */
		
		LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
		loginRequestDTO.setEmail("ujjwal@email.com");
		loginRequestDTO.setPassword("ujjwal!123");
		userController.login(loginRequestDTO);
		
	}

}
