package com.equinix.appops.dart.portal.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"Cage",
	"Cabinet",
	"Network Cable Connection",
	"AC Circuit",
	"DC Circuit",
	"Patch Panel",
	"Demarcation Point",
	"Private Patch Panel (Equinix)",
	"Private Patch Panel (Customer)",
	"SBL",
	"CLX",
	"SV"
})
public class UserPrefModel implements Serializable {

	private static final long serialVersionUID = 8803551475330535838L;
	
	@JsonProperty("Cage")
	private List<String> cage = null;
	
	@JsonProperty("Cabinet")
	private List<String> cabinet = null;
	
	@JsonProperty("Network Cable Connection")
	private List<String> networkCableConnection = null;
	
	@JsonProperty("AC Circuit")
	private List<String> acCircuit = null;
	
	@JsonProperty("DC Circuit")
	private List<String> dcCircuit = null;
	
	@JsonProperty("Patch Panel")
	private List<String> patchPanel = null;
	
	@JsonProperty("Demarcation Point")
	private List<String> demarcationPoint = null;
	
	@JsonProperty("Private Patch Panel (Equinix)")
	private List<String> privatePatchPanelEquinix = null;
	
	@JsonProperty("Private Patch Panel (Customer)")
	private List<String> privatePatchPanelCustomer = null;
	
	@JsonProperty("SBL")
	private String sbl;
	
	@JsonProperty("CLX")
	private String clx;
	
	@JsonProperty("SV")
	private String sv;

	public List<String> getCage() {
		return cage;
	}

	public void setCage(List<String> cage) {
		this.cage = cage;
	}

	public List<String> getCabinet() {
		return cabinet;
	}

	public void setCabinet(List<String> cabinet) {
		this.cabinet = cabinet;
	}

	public List<String> getNetworkCableConnection() {
		return networkCableConnection;
	}

	public void setNetworkCableConnection(List<String> networkCableConnection) {
		this.networkCableConnection = networkCableConnection;
	}

	public List<String> getAcCircuit() {
		return acCircuit;
	}

	public void setAcCircuit(List<String> acCircuit) {
		this.acCircuit = acCircuit;
	}

	public List<String> getDcCircuit() {
		return dcCircuit;
	}

	public void setDcCircuit(List<String> dcCircuit) {
		this.dcCircuit = dcCircuit;
	}

	public List<String> getPatchPanel() {
		return patchPanel;
	}

	public void setPatchPanel(List<String> patchPanel) {
		this.patchPanel = patchPanel;
	}

	public List<String> getDemarcationPoint() {
		return demarcationPoint;
	}

	public void setDemarcationPoint(List<String> demarcationPoint) {
		this.demarcationPoint = demarcationPoint;
	}
	
	public List<String> getPrivatePatchPanelEquinix() {
		return privatePatchPanelEquinix;
	}

	public void setPrivatePatchPanelEquinix(List<String> privatePatchPanelEquinix) {
		this.privatePatchPanelEquinix = privatePatchPanelEquinix;
	}

	public List<String> getPrivatePatchPanelCustomer() {
		return privatePatchPanelCustomer;
	}

	public void setPrivatePatchPanelCustomer(List<String> privatePatchPanelCustomer) {
		this.privatePatchPanelCustomer = privatePatchPanelCustomer;
	}

	public String getSbl() {
		return sbl;
	}

	public void setSbl(String sbl) {
		this.sbl = sbl;
	}

	public String getClx() {
		return clx;
	}

	public void setClx(String clx) {
		this.clx = clx;
	}

	public String getSv() {
		return sv;
	}

	public void setSv(String sv) {
		this.sv = sv;
	}
}
