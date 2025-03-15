package com.tom.process.service;

public class StringService {
	
	public static String checkNull(String dataValue) {
		String returnValue = "";
		if (dataValue == null) {
			returnValue = "";
		}
		else {
			returnValue = dataValue.trim();
		}
		return returnValue;
	}
	
	public static String checkNullNoTrim(String dataValue) {
		String returnValue = "";
		if (dataValue == null) {
			returnValue = "";
		}else {
			returnValue = dataValue;
		}
		return returnValue;
	}	
}