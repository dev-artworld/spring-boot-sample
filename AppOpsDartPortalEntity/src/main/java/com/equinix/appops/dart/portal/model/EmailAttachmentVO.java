package com.equinix.appops.dart.portal.model;

import java.io.Serializable;
import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;

public class EmailAttachmentVO  implements Serializable{
	
private static final long serialVersionUID = 1L;
    
    private byte[] fileContent; 
	
	private String contentType;
	
	private String fileName;

	public byte[] getFileContent() {
		return fileContent.clone();
	}

	public void setFileContent(byte[] fileContents) {
		this.fileContent = Arrays.copyOf(fileContents, fileContents.length);
			this.fileContent = Arrays.copyOf(fileContents, fileContents.length);
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String toXML() {
		// TODO Auto-generated method stub
		 byte[] encoded = Base64.encodeBase64(this.getFileContent());
		   String encodedString = new String(encoded);
	       
			StringBuilder emailString = new StringBuilder();
			
			emailString.append("<ns1:Attachment>");
			emailString.append("<ns1:FileContent>");
			emailString.append(encodedString);
			emailString.append("</ns1:FileContent>");
			
			emailString.append("<ns1:FileName>");
			emailString.append(fileName);
			emailString.append("</ns1:FileName>");
						
			emailString.append("<ns1:ContentType>");
			emailString.append(contentType);
			emailString.append("</ns1:ContentType>");
			
			emailString.append("</ns1:Attachment>");
			
			return emailString.toString();
	}
	
	
}
