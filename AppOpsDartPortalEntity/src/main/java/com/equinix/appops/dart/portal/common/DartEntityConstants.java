package com.equinix.appops.dart.portal.common;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import org.springframework.util.StringUtils;

public class DartEntityConstants {
	public static HashMap<String, String> STATUS_MAP;
	static {
		STATUS_MAP = new HashMap<>();
		STATUS_MAP.put("in_progress", "In-progress");
		STATUS_MAP.put("physical_audit_initiated", "Physical Audit Initiated");
		STATUS_MAP.put("pending", "Pending");
		STATUS_MAP.put("open", "Open");
		STATUS_MAP.put("new", "New");
		STATUS_MAP.put("sync_in_progress", "Sync In Progress");
		STATUS_MAP.put("ops_review", "OPS Review");
		STATUS_MAP.put("global_review", "Global Review");
		STATUS_MAP.put("clx_review", "CLX Review");
		STATUS_MAP.put("cancelled", "Cancelled");
		STATUS_MAP.put("completed", "Completed");

	}

	public static final SimpleDateFormat DART_DATE_FORMAT = new SimpleDateFormat("dd-MMM-yyy");

	public static String dbStatus(String status) {

		String dbStatus = null;
		String[] statusArray = status.split("_");

		StringBuilder buffer = new StringBuilder();
		for (String state : statusArray) {
			buffer.append(StringUtils.capitalize(state) + " ");
		}
		dbStatus = buffer.toString();
		return dbStatus.trim();
	}
	
	public static void main(String[] args) {
		
		String status = "new";
		String ss = dbStatus(status);
		System.out.println(ss);
	}

}
