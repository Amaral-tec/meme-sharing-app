package br.com.amaral.controller;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.amaral.ExceptionProject;
import br.com.amaral.model.User;
import br.com.amaral.repository.IUserRepository;

@RestController
public class UserController {
	
	@Autowired
	private IUserRepository userRepository;
	
	@ResponseBody
	@PostMapping(value = "**/create-user")
	public ResponseEntity<User> createUser(@RequestBody @Valid User user) throws ExceptionProject {

		boolean result = userRepository.isUserRegistered(user.getLogin());
		if (result) {
			throw new ExceptionProject(
					"Operation not carried out: Already registered with the login " + user.getLogin());
		}
	
		String encryptedPassword = new BCryptPasswordEncoder().encode(user.getLogin());
		user.setPassword(encryptedPassword);
		user.setPasswordCreatedAt(new Date());
		userRepository.save(user);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/get-user/{id}")
	public ResponseEntity<User> getUser(@PathVariable("id") Long id) throws ExceptionProject {

		User user = userRepository.findById(id).orElse(null);
		if (user == null) {
			throw new ExceptionProject("Operation not performed: Not included in the ID database " + id);
		}

		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping(value = "**/get-user-by-login/{login}")
	public ResponseEntity<User> getUserByLogin(@PathVariable("login") String login) {

		User user = userRepository.getUserByLogin(login);

		return new ResponseEntity<>(user, HttpStatus.OK);
	}

}
