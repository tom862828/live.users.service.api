package com.tom.process.controller;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tom.process.vo.UserVO;
import com.tom.process.dao.UserDAO;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user/service/api/1.0")
@Slf4j
public class InsertController {
	
	@PostMapping(value="/{user_name}/{password}/{email}/{id_number}/{department}/insertUser", produces = "application/json")
	public ResponseEntity<UserVO> insertUser(@PathVariable("user_name") String user_name,@PathVariable("password") String password,@PathVariable("email") String email,@PathVariable("id_number") String id_number,@PathVariable("department") String department) {
		
		UserVO userVO = new UserVO();
		UserDAO userDAO = new UserDAO();
		
		try {
			userVO.setUser_name(user_name);
			userVO.setUser_password(BCrypt.hashpw(password, BCrypt.gensalt()));
			userVO.setUser_email(email);
			userVO.setId_number(id_number);
			userVO.setDepartment(department);
			userDAO.insertUser(userVO);
			userVO.setStatus("200");
			return ResponseEntity.status(HttpStatus.OK).body(userVO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.debug(e.toString());
			userVO.setStatus("400");
			userVO.setErrorMessage(e.getCause().toString());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userVO);
		}
	}
}
