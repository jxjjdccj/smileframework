package org.smileframework.common.jdk;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 根据日期取星座
 * @author duo_ning
 *
 */
public class ConstellationUtils {
	
	public static String getConstellationNameByDateString(String date){
		SimpleDateFormat sdf =  new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",Locale.US);  
		String cname = "";
		try {
			cname = getConstellationNameByDate(sdf.parse(date));
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return cname;
	}
	
	/*public static void main(String[] args) {
		System.out.println(getConstellationNameByDateString("Sun Sep 07 00:00:00 CDT 1986"));
	}*/

	public static String getConstellationNameByDate(Date date){
		if(date==null){
			return null;
		}
		String cname="";
		SimpleDateFormat dformat=new SimpleDateFormat("Mdd");
		int md = Integer.valueOf(dformat.format(date));
		if(md>=120 && md<=218){
			cname="水瓶座";
		}else if(md>=219 && md<=320){
			cname="双鱼座";
		}else if(md>=321 && md<=420){
			cname="白羊座";
		}else if(md>=421 && md<=520){
			cname="金牛座";
		}else if(md>=521 && md<=621){
			cname="双子座";
		}else if(md>=622 && md<=722){
			cname="巨蟹座";
		}else if(md>=723 && md<=822){
			cname="狮子座";
		}else if(md>=823 && md<=922){
			cname="处女座";
		}else if(md>=923 && md<=1023){
			cname="天秤座";
		}else if(md>=1024 && md<=1122){
			cname="天蝎座";
		}else if(md>=1123 && md<=1221){
			cname="射手座";
		}else if(md>=101 && md<=119){
			cname="摩羯座";
		}else if(md>=1222 && md<=1231){
			cname="摩羯座";
		}
		return cname;
	}
	
}
