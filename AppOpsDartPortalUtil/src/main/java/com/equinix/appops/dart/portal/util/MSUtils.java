package com.equinix.appops.dart.portal.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MSUtils {
	
	public static String setDtlCapxTimestamp(){
		
		SimpleDateFormat dFormat  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dtltime = dFormat.format(new Date());
			return dtltime;
		}
		
	}
