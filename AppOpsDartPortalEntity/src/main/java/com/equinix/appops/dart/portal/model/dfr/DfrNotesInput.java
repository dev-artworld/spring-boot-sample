package com.equinix.appops.dart.portal.model.dfr;

import javax.persistence.Column;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "dfrId",
    "team",
	"notes",
    "mechanism"
})
public class DfrNotesInput {

	@JsonProperty("dfrId")
	private String dfrId;
	
	@JsonProperty("team")
	private String team;

	@Column(name="notes")
	private String notes;
	
	@Column(name="mechanism")
	private String mechanism;

	@JsonProperty("dfrId")
	public String getDfrId() {
		return dfrId;
	}

	@JsonProperty("dfrId")
	public void setDfrId(String dfrId) {
		this.dfrId = dfrId;
	}

	@Column(name="notes")
	public String getNotes() {
		return notes;
	}

	@Column(name="notes")
	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Column(name="mechanism")
	public String getMechanism() {
		return mechanism;
	}

	@Column(name="mechanism")
	public void setMechanism(String mechanism) {
		this.mechanism = mechanism;
	}

	@Column(name="team")
	public String getTeam() {
		return team;
	}

	@Column(name="team")
	public void setTeam(String team) {
		this.team = team;
	}
	
}
