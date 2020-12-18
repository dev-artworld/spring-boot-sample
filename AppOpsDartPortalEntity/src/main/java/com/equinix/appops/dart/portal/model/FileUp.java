package com.equinix.appops.dart.portal.model;

import java.io.File;
import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
    "dfr_id",
    "fileName",
    "fileType",
    "filePath"
})

public class FileUp implements Serializable {
	
	 @JsonProperty("dfr_id")
	    private String dfrId;
	 
	 @JsonProperty("fileName")
	    private String fileName;
	 
	 @JsonProperty("fileType")
	    private String fileType;
	 
	 @JsonProperty("filePath")
	    private File filePath;

	 @JsonProperty("dfr_id")
	 public String getDfrId() {
		return dfrId;
	}
	 
	 @JsonProperty("dfr_id")
	public void setDfrId(String dfrId) {
		this.dfrId = dfrId;
	}
    
	 @JsonProperty("fileName")
	public String getFileName() {
		return fileName;
	}
	 @JsonProperty("fileName")
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
    
	 @JsonProperty("fileType")
	public String getFileType() {
		return fileType;
	}

	 @JsonProperty("fileType")
	 public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@JsonProperty("filePath")
	public File getFilePath() {
		return filePath;
	}
    
	@JsonProperty("filePath")
	public void setFilePath(File filePath) {
		this.filePath = filePath;
	}

}
