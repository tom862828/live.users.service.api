package com.tom.process.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.tom.process.vo.UserVO;
import com.tom.process.dao.UserDAO;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user/service/api/1.0")
@Slf4j
public class deleteController {

    @DeleteMapping(value = "/{user_name}" , produces = "application/json")
    public ResponseEntity<String> deleteUser(@PathVariable("user_name") String user_name) {

        log.info("Delete user id: {}", user_name);
        UserVO userVO = new UserVO();
        UserDAO userDAO = new UserDAO();

        try {
            userVO.setUser_name(user_name);
            userDAO.deleteUser(userVO);
            userVO.setStatus("200");
            return ResponseEntity.status(HttpStatus.OK).body("Deleted successfully");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.debug(e.toString());
            userVO.setStatus("400");
            userVO.setErrorMessage(e.getCause().toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
        }
    }
}
