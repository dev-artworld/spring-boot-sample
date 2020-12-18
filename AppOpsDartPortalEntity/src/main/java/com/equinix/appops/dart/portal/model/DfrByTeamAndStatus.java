package com.equinix.appops.dart.portal.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.equinix.appops.dart.portal.mapper.dto.pro.DfrMasterHomeVO;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"team",
    "status",
    "dfrs"
})
public class DfrByTeamAndStatus {
	
	@JsonProperty("team")
    private String team;
	
	@JsonProperty("status")
    private String status;
	
    @JsonProperty("dfrs")
    private List<DfrMasterHomeVO> dfrs = new ArrayList<>();

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<DfrMasterHomeVO> getDfrs() {
		return dfrs;
	}

	public void setDfrs(List<DfrMasterHomeVO> dfrs) {
		this.dfrs = dfrs;
	}
    
    

}
