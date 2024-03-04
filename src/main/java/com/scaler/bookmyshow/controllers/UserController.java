package com.scaler.bookmyshow.controllers;

import com.scaler.bookmyshow.dtos.LoginRequestDTO;
import com.scaler.bookmyshow.dtos.LoginResponseDTO;
import com.scaler.bookmyshow.dtos.ResponseStatus;
import com.scaler.bookmyshow.dtos.SignUpRequestDTO;
import com.scaler.bookmyshow.dtos.SignUpResponseDTO;
import com.scaler.bookmyshow.models.User;
import com.scaler.bookmyshow.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    public SignUpResponseDTO signUp(SignUpRequestDTO request){
        SignUpResponseDTO signUpResponseDTO = new SignUpResponseDTO();
        User user;
        try{
            user = userService.userSignup(request.getEmail(), request.getPassword(), request.getName());
            signUpResponseDTO.setResponseStatus(ResponseStatus.SUCCESS);
            signUpResponseDTO.setUserId(user.getId());
        }catch (Exception exception){
            signUpResponseDTO.setResponseStatus(ResponseStatus.FAILURE);
        }
        return null;
    }
    
    public LoginResponseDTO login(LoginRequestDTO request) {
    	LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
    	User user;
    	try {
    		user = userService.login(request.getEmail(), request.getPassword());
    		loginResponseDTO.setResponseStatus(ResponseStatus.SUCCESS);
    		loginResponseDTO.setUserId(user.getId());
    		System.out.println("Logged In successfully by : " + user.getEmail());
    	}catch(Exception exception){
    		loginResponseDTO.setResponseStatus(ResponseStatus.FAILURE);
    		System.out.println("Loggin not successful");
    	}
    	return null;
    }
}
