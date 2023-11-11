package br.com.amaral.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.amaral.ExceptionProject;
import br.com.amaral.model.User;
import br.com.amaral.repository.IUserRepository;
import io.micrometer.core.annotation.Timed;

@RestController
public class UserController {
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
    private LogController<User> logController;
	
	@Timed(value = "createUser", description = "Time taken to create a user")
	@ResponseBody
	@PostMapping(value = "**/create-user")
	public ResponseEntity<User> createUser(@RequestBody @Valid User user) throws ExceptionProject {

		boolean result = userRepository.isUserRegistered(user.getLogin());
		if (result) {
			throw new ExceptionProject(
					"Operation not carried out: Already registered with the login " + user.getLogin());
		}
	
		String encryptedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
		user.setPassword(encryptedPassword);
		user.setPasswordCreatedAt(new Date());
		userRepository.save(user);
		
		logController.logEntity(user);
		
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@Timed(value = "deleteUser", description = "Time taken to delete a user")
	@ResponseBody
	@PostMapping(value = "**/delete-user")
	public ResponseEntity<String> deleteUser(@RequestBody User user) {

		if (!userRepository.findById(user.getId()).isPresent()) {
			return new ResponseEntity<>("Operation not performed: Not included in the ID database: " + user.getId(), HttpStatus.NOT_FOUND);
		}
		
		user.setIsDeleted(true);
		userRepository.save(user);
		
		logController.logEntity(user);
		
		return new ResponseEntity<>("OK: Deletion completed successfully.", HttpStatus.OK);
	}
	
	@Timed(value = "deleteUserById", description = "Time taken to delete a user by ID")
	@ResponseBody
	@DeleteMapping(value = "**/delete-user-by-id/{id}")
	public ResponseEntity<String> deleteUserById(@PathVariable("id") Long id) throws ExceptionProject {
		
		User user = userRepository.findById(id).orElse(null);
		if (user == null) {
			throw new ExceptionProject("Operation not performed: Not included in the ID database: " + id);
		}
		
		user.setIsDeleted(true);
		userRepository.save(user);
		
		logController.logEntity(user);
		
		return new ResponseEntity<>("OK: Deletion completed successfully.", HttpStatus.OK);
	}
	
	@Timed(value = "getUser", description = "Time taken to get a specific user")
	@ResponseBody
	@GetMapping(value = "**/get-user/{id}")
	public ResponseEntity<User> getUser(@PathVariable("id") Long id) throws ExceptionProject {

		User user = userRepository.findById(id).orElse(null);
		if (user == null) {
			throw new ExceptionProject("Operation not performed: Not included in the ID database " + id);
		}

		logController.logEntity(user);
		
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@Timed(value = "findAllUsers", description = "Time taken to find all users")
	@ResponseBody
	@GetMapping(value = "**/find-all-users")
	public ResponseEntity<List<User>> findAllUsers() {

		List<User> list = userRepository.findAll();

		logController.logEntityList(list);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@Timed(value = "getUserByLogin", description = "Time taken to find users by login")
	@ResponseBody
	@GetMapping(value = "**/get-user-by-login/{login}")
	public ResponseEntity<User> getUserByLogin(@PathVariable("login") String login) {

		User user = userRepository.getUserByLogin(login);

		logController.logEntity(user);
		
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

}
