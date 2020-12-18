package com.equinix.appops.dart.portal.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

public class DFRFileModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String dfrFileId;

	private String dfrId;

	private byte[] dfrFile;
	
	private String fileName;

	private String fileType;
	
	public DFRFileModel(FileUp fileUp,byte[] dfrFile)
	{
		this.dfrId=fileUp.getDfrId();
		this.fileName=fileUp.getFileName();
		this.fileType=fileUp.getFileType();
		this.dfrFile=dfrFile;
	}

	public String getDfrFileId() {
		return dfrFileId;
	}

	public void setDfrFileId(String dfrFileId) {
		this.dfrFileId = dfrFileId;
	}

	public String getDfrId() {
		return dfrId;
	}

	public void setDfrId(String dfrId) {
		this.dfrId = dfrId;
	}

	public byte[] getDfrFile() {
		return dfrFile;
	}

	public void setDfrFile(byte[] dfrFile) {
		this.dfrFile = dfrFile;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	

}
