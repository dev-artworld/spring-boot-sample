package com.equinix.appops.dart.portal.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.equinix.appops.dart.portal.model.dfr.DfrNotesInput;

@Entity
@Table(name = "DFR_NOTES", schema = "EQX_DART")
@NamedQuery(name = "DfrNotes.findAll", query = "SELECT d FROM DfrNotes d")
public class DfrNotes {

	@Id
	@Column(name = "ROW_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGenerator")
	@SequenceGenerator(name = "seqGenerator", sequenceName = "DFR_NOTES_PK", schema = "EQX_DART", allocationSize = 1)
	private long rowId;

	@Column(name = "DFR_ID")
	private String dfrId;

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATED_DT")
	private Date createdDt;

	@Column(name = "NOTES")
	private String notes;

	@Column(name = "USER_NAME")
	private String userName;

	@Column(name = "TEAM")
	private String team;

	@Column(name = "MECHANISM")
	private String mechanism;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	public DfrNotes(DfrNotesInput dfrNotesInput) {
		super();
		this.dfrId = dfrNotesInput.getDfrId();
		this.notes = dfrNotesInput.getNotes();
		this.team = dfrNotesInput.getTeam();
		this.mechanism = dfrNotesInput.getMechanism();
	}
	
	public DfrNotes() {
		// TODO Auto-generated constructor stub
	}

	public long getRowId() {
		return rowId;
	}

	public void setRowId(long rowId) {
		this.rowId = rowId;
	}

	public String getDfrId() {
		return dfrId;
	}

	public void setDfrId(String dfrId) {
		this.dfrId = dfrId;
	}

	public Date getCreatedDt() {
		return createdDt;
	}

	public void setCreatedDt(Date createdDt) {
		this.createdDt = createdDt;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getMechanism() {
		return mechanism;
	}

	public void setMechanism(String mechanism) {
		this.mechanism = mechanism;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
