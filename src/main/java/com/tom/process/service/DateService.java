package com.tom.process.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
/*
 * @author tomli
 * G  	Era designator 	Text  	AD
 * y 	Year 	Year 	1996; 96
 * M 	Month in year 	Month 	July; Jul; 07
 * w 	Week in year 	Number 	27
 * W 	Week in month 	Number 	2
 * D 	Day in year 	Number 	189
 * d 	Day in month 	Number 	10
 * F 	Day of week in month 	Number 	2
 * E 	Day in week 	Text 	Tuesday; Tue
 * a 	Am/pm marker 	Text 	PM
 * H 	Hour in day (0-23) 	Number 	0
 * k 	Hour in day (1-24) 	Number 	24
 * K 	Hour in am/pm (0-11) 	Number 	0
 * h 	Hour in am/pm (1-12) 	Number 	12
 * m 	Minute in hour 	Number 	30
 * s 	Second in minute 	Number 	55
 * S 	Millisecond 	Number 	978
 * z 	Time zone 	General time zone 	Pacific Standard Time; PST; GMT-08:00
 * Z 	Time zone 	RFC 822 time zone 	-0800
 */
public class DateService {
	private static DateService ds;
	private DateService() {
		// TODO Auto-generated constructor stub
	}
    public static synchronized DateService getInstance(){
        if(ds == null)
            ds = new DateService();
        return ds;
    }	
    public static String getToday(String formatPattern){
    	/* yyyy/MM/dd,yyyyMMdd 
    	 * yyyy/MM/dd HH:mm:ss z
    	 * */
        SimpleDateFormat dfx = new SimpleDateFormat(formatPattern);
        Calendar c = Calendar.getInstance();        
        return dfx.format(c.getTime());
    }
    public static String getLongToday(){
    	String formatPattern = "yyyy/MM/dd HH:mm:ss z";
    	TimeZone tz = TimeZone.getTimeZone("GMT+08:00");    	
    	Calendar cl = Calendar.getInstance(tz);
    	cl.setTimeZone(TimeZone.getTimeZone("GMT"));
    	SimpleDateFormat dfx = new SimpleDateFormat(formatPattern,Locale.TAIWAN);
        return dfx.format(cl.getTime());
    }    
    public static String getyyyyMMddHHmmss(){
    	String formatPattern = "yyyyMMddHHmmss";
    	TimeZone tz = TimeZone.getTimeZone("GMT+08:00");    	
    	Calendar cl = Calendar.getInstance(tz);
    	cl.setTimeZone(TimeZone.getTimeZone("GMT"));
    	SimpleDateFormat dfx = new SimpleDateFormat(formatPattern,Locale.TAIWAN);
        return dfx.format(cl.getTime());
    }    
    public static String getShortToday(){
    	String formatPattern = "yyyy/MM/dd";
    	TimeZone tz = TimeZone.getTimeZone("GMT+08:00");    	
    	Calendar cl = Calendar.getInstance(tz);
    	cl.setTimeZone(TimeZone.getTimeZone("GMT"));
    	SimpleDateFormat dfx = new SimpleDateFormat(formatPattern,Locale.TAIWAN);
        return dfx.format(cl.getTime());
    }
    public static String getNow(){
    	String formatPattern = "HH:mm:ss";
    	TimeZone tz = TimeZone.getTimeZone("GMT+08:00");
    	
    	Calendar cl = Calendar.getInstance(tz);
    	cl.setTimeZone(TimeZone.getTimeZone("GMT"));
    	SimpleDateFormat dfx = new SimpleDateFormat(formatPattern,Locale.TAIWAN);
        return dfx.format(cl.getTime());
    }   
    public static String getThisWeek(){
     	String formatPattern = "w";
    	TimeZone tz = TimeZone.getTimeZone("GMT+08:00");
    	
    	Calendar cl = Calendar.getInstance(tz);
    	cl.setTimeZone(TimeZone.getTimeZone("GMT"));
    	SimpleDateFormat dfx = new SimpleDateFormat(formatPattern,Locale.TAIWAN);
        return dfx.format(cl.getTime());
    }   
    public static String getDayInYear(){
    	String formatPattern = "D";
    	TimeZone tz = TimeZone.getTimeZone("GMT+08:00");
    	
    	Calendar cl = Calendar.getInstance(tz);
    	cl.setTimeZone(TimeZone.getTimeZone("GMT"));
    	SimpleDateFormat dfx = new SimpleDateFormat(formatPattern,Locale.TAIWAN);
        return dfx.format(cl.getTime());
    }  
    public static String getWeekInMonth(){
    	String formatPattern = "W";
    	TimeZone tz = TimeZone.getTimeZone("GMT+08:00");    	
    	Calendar cl = Calendar.getInstance(tz);
    	cl.setTimeZone(TimeZone.getTimeZone("GMT"));
    	SimpleDateFormat dfx = new SimpleDateFormat(formatPattern,Locale.TAIWAN);
        return dfx.format(cl.getTime());
    } 
    public static String getDayInWeek(){
    	String formatPattern = "E";
    	TimeZone tz = TimeZone.getTimeZone("GMT+08:00");    	
    	Calendar cl = Calendar.getInstance(tz);
    	cl.setTimeZone(TimeZone.getTimeZone("GMT"));
    	SimpleDateFormat dfx = new SimpleDateFormat(formatPattern,Locale.TAIWAN);
        return dfx.format(cl.getTime());
    }    
    public static String DateToLongString(Date date){
     	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(date);
    }  
    public static String DateToShortString(Date date){
     	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return dateFormat.format(date);
    }
    public static String DateToyyyyMMdd(Date date){
     	DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(date);
    }
    public static String DateToTimeString(Date date){
     	DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        return dateFormat.format(date);
    }     
    public static Date StringToShortDate(String stringDate) throws ParseException{
     	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return dateFormat.parse(stringDate);
    } 
    public static Date StringToLongDate(String stringDate) throws ParseException{
     	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.parse(stringDate);
    }
    public static Date StringToShortDateYYYYMMDD(String stringDate) throws ParseException{
     	DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.parse(stringDate);
    }  
}
