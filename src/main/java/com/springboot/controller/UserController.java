package com.springboot.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.net.MediaType;
import com.springboot.dao.IUserDAO;
import com.springboot.model.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RequestMapping("/users")
@Api(value="Users", description="User API Call")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved list"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
}
)

@RestController
public class UserController {
	
		@Autowired
		private IUserDAO userDAO; 
		
		@GetMapping(path="/", produces = "application/json")
		public String root() {
			return "index";
		}
		
		@GetMapping(path="/home", produces = "application/json")
		public String home() {
			return "home";
		}
		
		@GetMapping(path="/getAllUser")
		public List<User> getAllUser() {
			return userDAO.findAll();
		}
		
		@GetMapping(path="/findByID/{id}")
		public ResponseEntity<User> findByID(@PathVariable("id") Integer id) {
			Optional<User> op =  userDAO.findById(id);
			if (op.isPresent()) {
				return new ResponseEntity<User>(op.get(), HttpStatus.OK);
			} else {
				throw new NoSuchElementException("Invalid user id : " + id);
			}
		}

}
