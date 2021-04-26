package com.af.arabimotors.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static String getDateForamtTimeStamp(String timestampDate) {
		
		String newDate = "";
		
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(timestampDate);
			newDate = new SimpleDateFormat("yyyy,MM dd").format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return newDate;

	}
	
}
