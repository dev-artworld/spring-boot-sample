package com.equinix.appops.dart.portal.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.equinix.appops.dart.portal.common.DFRSyncResponse;
import com.equinix.appops.dart.portal.model.FileUp;

/**
 * The persistent class for the DFR_FILE database table.
 * 
 */
@Entity
@Table(name="DFR_FILE", schema="EQX_DART")
@NamedQuery(name="DFRFile.findAll", query="SELECT d FROM DFRFile d")

public class DFRFile implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="DFR_FILE_ID",updatable=false,nullable=false)
	private Long dfrFileId;

	
	

	@Column(name="DFR_ID")
	private String dfrId;

	@Column(name="DFR_FILE")
	private byte[] dfrFile;

	@Column(name="FILE_NAME")
	private String fileName;

	@Column(name="FILE_TYPE")
	private String fileType;
	
	

	public Long getDfrFileId() {
		return dfrFileId;
	}

	public void setDfrFileId(Long dfrFileId) {
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
