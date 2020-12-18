package com.equinix.appops.dart.portal.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.equinix.appops.dart.portal.constant.DartConstants;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class DartUtil {
	
	static Logger logger = LoggerFactory.getLogger(DartUtil.class);
	
	public static String dateToString(Date date){
		if(date!=null)
			return DartConstants.DART_SDF.format(date);
		else
			return DartConstants.NA;
	}
	
	public static Integer getIntegerValue(String headerPosition){
		return Integer.valueOf(headerPosition.replace(DartConstants.HEADER, DartConstants.BLANK));
	}
	
	public static Integer getIntegerAttrValue(String headerPosition){
		return Integer.valueOf(headerPosition.replace(DartConstants.ATTR_UNDERSCORE, DartConstants.BLANK));
	}
	
	public static  String sanitizeInput(String val) {
		return val.replaceAll("~", ",");
	
	}
	public static  String sanitizeOutput(String val) {
		return val.replaceAll(",", "~");
	
	}
	
	public static List<String> senitizeOutputList(List<String> list){
		List<String> senitizedResultList = new ArrayList<>();
		for(String val : list){
			senitizedResultList.add(sanitizeOutput(val));
		}
		return senitizedResultList;
	}
	public static String executeShellWithSUDO( String host, String user, String password, String []command) throws Exception {
		StringBuffer response = new StringBuffer();
		JSch jsch = new JSch();
		Session session;

		session = jsch.getSession(user, host, 22);
		Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig("PreferredAuthentications",
				"publickey,keyboard-interactive,password");
		session.setConfig(config);

		
		session.setPassword(password);
		session.setConfig(config);
		session.connect();
		logger.info("Connected to " + host);
		Channel channel = session.openChannel("exec");
		((ChannelExec) channel).setCommand("sh "+ command[0]);
		channel.setInputStream(null);
		OutputStream out = channel.getOutputStream();
		((ChannelExec) channel).setErrStream(System.err);
		InputStream in = channel.getInputStream();
		((ChannelExec) channel).setPty(true);
		channel.connect();
		out.write((password + "\n").getBytes());
		out.flush();
		byte[] tmp = new byte[1024];
		while (true) {
			while (in.available() > 0) {
				int i = in.read(tmp, 0, 1024);
				if (i < 0) 
					break;
				response.append(new String(tmp, 0, i));
			}
			if (channel.isClosed()) {
				logger.info("Exit status: " + channel.getExitStatus());
				break;
			}
		}
		channel.disconnect();
		session.disconnect();
		logger.info("DONE");
		return response.toString();
	}
	
	
	
	public static List<String> validateSearchKeywordForAssetNum(String keyword,String key){
		List<String> assetNumListAll = new ArrayList<String>();
		if(StringUtils.isNotEmpty(key)&& key.equalsIgnoreCase("header2")
				&& keyword.contains(",")){
			String assetNumArray[] = keyword.split(",");
			List<String> assetNumList = new ArrayList<String>(Arrays.asList(assetNumArray));
			if(CollectionUtils.isNotEmpty(assetNumList)){
				for(String assetNum : assetNumList ){
						if(StringUtils.isNotEmpty(assetNum))
							assetNumListAll.add(assetNum.trim());
					}
			}
			return assetNumListAll;
		}
		return null;
	}

	public static List<String> deserializedSystemName(List<String> listOfValues) {
		List<String> systemNameList = new ArrayList<>();
		for (String systemName : listOfValues) {
			if (systemName.contains("##")) {
				systemNameList.add(systemName.replace("##", ","));
			} else {
				systemNameList.add(systemName);
			}
		}
		return systemNameList;
	}
}
