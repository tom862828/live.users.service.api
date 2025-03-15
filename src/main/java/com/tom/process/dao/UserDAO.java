package com.tom.process.dao;

import java.util.List;

import com.tom.process.vo.UserVO;
import com.tom.process.config.ResultFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserDAO {
	
	public int insertUser(UserVO userVO) throws Exception{
		ResultFactory resultFactory = new ResultFactory();
		int result = resultFactory.insertObject("insertUser", userVO);
		log.debug("result -> " + result);
		return result;
	}
	
	public List<UserVO> getUser(UserVO userVO) throws Exception{
		ResultFactory resultFactory = new ResultFactory();
		@SuppressWarnings("unchecked")
		List<UserVO> result = (List<UserVO>) resultFactory.querylist("queryUser", userVO);
		return result;
	}
	
	public int updateUser(UserVO userVO) throws Exception{
		ResultFactory resultFactory = new ResultFactory();
		int result = resultFactory.updateObject("updateUser", userVO);
		return result;
	}
	
	public int deleteUser(UserVO userVO) throws Exception{
		ResultFactory resultFactory = new ResultFactory();
		int result = resultFactory.deleteObject("deleteUser", userVO);
		return result;
	}
}
