package com.bridgelabz.fundoo_note_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.fundoo_note_api.dto.Register;
import com.bridgelabz.fundoo_note_api.dto.UserLogin;
import com.bridgelabz.fundoo_note_api.entity.User;
import com.bridgelabz.fundoo_note_api.response.Response;
import com.bridgelabz.fundoo_note_api.service.UserService;
import com.bridgelabz.fundoo_note_api.utility.JwtGenerator;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtGenerator generator;

	/* API for user login */

	
	@PostMapping(value = "/user/login")
	public ResponseEntity<Response> loginUser(@RequestBody UserLogin user) {
		User result = userService.login(user);
		if (result != null) {
			   generator.jwtToken(result.getUid());
			return ResponseEntity.status(HttpStatus.ACCEPTED)
			              .body(new Response("You have Loggined in Successfully"));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response("Loggined in Failed"));

	}
	
	/* API for user register */

	@PostMapping(value = "/user/add-user")
	public ResponseEntity<Response> register(@RequestBody Register userRecord) {
		User user = userService.register(userRecord);
		if (user != null) {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new Response("Registration Successfully"));
		} else {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
					.body(new Response("Already existing user"));
		}
	}

	@PostMapping(value = "/user/{emailId}")
	public ResponseEntity<Response> emailVerify(@PathVariable String emailId) {

		String result = userService.emailVerify(emailId);
		if (result != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new Response("email sent Successfully"));
		}
		return null; 
	}

	/* API for user Forgot Passsword */

	@PutMapping(value = "/user/{newPassword}/{token}")
	public ResponseEntity<Response> forgetPassword(@PathVariable String newPassword,@PathVariable String token) {

		User result = userService.forgotPassword(newPassword,token);
		if (result != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new Response("Password Updated Successfully"));
		}
		return null; 
	}

	/* API for verifying the token generated for the email */

	@GetMapping(value="/verify/{token}")
	public ResponseEntity<Response> verify(@PathVariable("token") String token) throws Exception {
		boolean verification = userService.verify(token);
		if (verification) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("verified"));
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("not verified"));
	}
	
	@GetMapping(value="/user/{token}") 
	public User getUser(@PathVariable String token) 
	{
		return userService.getUser(token); 
	}

	/* API for Getting the all users */

	/*
	 * @GetMapping(value="/user") public List<User> getAllUsers() { return
	 * userService.getUsers(); }
	 * 
	 * /* API for Deleting the user
	 * 
	 * @DeleteMapping(value = "/user/delete/{id}") public void
	 * deleteUser(@PathVariable String id) { User result =
	 * userService.removeUser(id); if(result!=null){ System.out.println("@@@"); } }
	 */
}
