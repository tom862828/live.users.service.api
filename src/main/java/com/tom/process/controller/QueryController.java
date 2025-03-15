package com.tom.process.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.tom.process.vo.UserVO;
import com.tom.process.dao.UserDAO;
import com.tom.process.service.DateService;
import com.tom.process.service.EncryService;
import com.tom.process.service.MailService;
import com.tom.process.service.StringService;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

@RestController
@RequestMapping("/user/service/api/1.0")
@Slf4j
public class QueryController {
	
	@Value("${SECRET_KEY}")
	private String SECRET_KEY;
	
	@Value("${EXPIRE_TIME}")
	private int EXPIRE_TIME;
	
	@Autowired
	private MailService mailService;
	
	@GetMapping(value="/{user_name}/sendVerfiyMailtoUser", produces = "application/json")
	public ResponseEntity<List<UserVO>> sendVerfiyMailtoUser(@PathVariable("user_name") String user_name) {
		
		UserVO userVO = new UserVO();
		UserDAO userDAO = new UserDAO();
		List<UserVO> resultList = new ArrayList<UserVO>();
		Jedis jedis = new Jedis("127.0.0.1",6379);
		jedis.auth("tom862828");
		String userIdUUIDKey = UUID.randomUUID().toString();
		String dateKey = DateService.getyyyyMMddHHmmss();
		String secret = dateKey + "*TOM*";
		String encodedUserId = "";
		String mailSubject = "";
		String mailBody = "";
		ArrayList<String> receivers = new ArrayList<>();
		String api_url ="https://127.0.0.1/user/service/api/1.0/verify?";
		
		try {
			
			userVO.setUser_name(user_name);
			userVO.setActive("N");
			resultList = userDAO.getUser(userVO);
			
			if(resultList.isEmpty()) {
				userVO.setStatus("200");
				userVO.setActive("null");
				userVO.setDescription("User verified or user invalid.");
				resultList.add(userVO);
				jedis.close();
				return ResponseEntity.status(HttpStatus.OK).body(resultList);
			}
			
			resultList.forEach(result -> result.setStatus("200"));
			
			encodedUserId = EncryService.encrypt(secret + resultList.get(0).getKey_no() + ":" + "Y", SECRET_KEY);
			encodedUserId = encodedUserId.replace("/", "_");
			encodedUserId = encodedUserId.replace("+", "*");
			SetParams setParams = new SetParams();
			setParams.ex(EXPIRE_TIME);
			jedis.set(userIdUUIDKey, encodedUserId, setParams);
			jedis.close();
			
			mailSubject ="[User verification mail] - User name #" + resultList.get(0).getUser_name().toUpperCase();
			mailBody =
					"<div style=\"font-size:14px;font-family:Verdana\">Dear "+ resultList.get(0).getUser_name().toUpperCase() +",</div>    "
				   +"<br>																									"
				   +"<div style=\"font-size:12px;font-family:Verdana\">Kindly click the below url to verify your accout in 30 minutes.<br>"
				   +"<table style=\"font-size:12px;font-family:Verdana\">													"
				   +"	<tr>																								"
				   +"		<td style=\"text-align:left\">													"
				   +"			<a href="+api_url+"uuId="+userIdUUIDKey+"&userId="+encodedUserId+">							"
				   +					api_url + " uuId="+ userIdUUIDKey + "&userId=" + encodedUserId				
				   +"			</a>																						"
				   +"		</td>																							"
				   +"	</tr>																								"
				   +"</table>																								";
			
			receivers.add(resultList.get(0).getUser_email());
			
			this.mailService.setConfig(mailSubject, mailBody, receivers, null, null);
			this.mailService.sendMessage();
			
			return ResponseEntity.status(HttpStatus.OK).body(resultList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.debug(e.toString());
			userVO.setStatus("400");
			resultList.add(userVO);
			jedis.close();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultList);
		}
	}
	
	@GetMapping(value = "/verify", produces = "text/html")
	public ResponseEntity<String> clickVerfiyMailtoUser(@RequestParam("uuId") String uuId, @RequestParam("userId") String userId) {
		
		UserVO userVO = new UserVO();
		UserDAO userDAO = new UserDAO();
		
		uuId = StringService.checkNull(uuId);
		userId = StringService.checkNull(userId);
		
		Jedis jedis = new Jedis("127.0.0.1",6379);
		jedis.auth("tom862828");
		String redisUserId = StringService.checkNull(jedis.get(uuId));
		jedis.close();
		
		try {
			if(redisUserId.equalsIgnoreCase(userId)) {
				
				userId = userId.replace("_", "/");
				userId = userId.replace("*", "+");
				userId = EncryService.decrypt(userId, SECRET_KEY);
				
				if(userId.indexOf(":") > -1) {
					String[] temp = userId.split(":");
					String tempData = temp[0];
					String[] userIdData = tempData.split("\\*");
					
					userVO.setKey_no(Long.parseLong(userIdData[2]));
					userVO.setActive("Y");
					userDAO.updateUser(userVO);
					userVO.setDescription("This user has been verfied.");
				}
			} else {
				userVO.setDescription("The service has been closed. Please re-trigger the verification mail.");
			}
		}catch (Exception e) {
			// TODO: handle exception
			log.debug(e.toString());
			userVO.setStatus("500");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.toString() + "<br>Status: " + userVO.getStatus());
		}
		return ResponseEntity.status(HttpStatus.OK).body(userVO.getDescription());
	}
}
