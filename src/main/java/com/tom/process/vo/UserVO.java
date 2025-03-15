package com.tom.process.vo;

import lombok.Data;

@Data
public class UserVO {
	private long key_no;
	private String User_name;
	private String User_password;
	private String User_email;
	private String Id_number;
	private String Department;
	private String Active;
	private String ErrorMessage;
	private String Description;
	private String Status;
}
