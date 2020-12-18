package com.equinix.appops.dart.portal.mapper.accountmove.dto;

import java.util.HashMap;
import java.util.Map;

import com.equinix.appops.dart.portal.mapper.accountmove.AccountMoveConstants;

public class OpsHierarchyAsset {
	Map<String, Asset> cages;
	Map<String, Asset> cabinets;
	Map<String, Asset> patchPanels;
	Map<String, Asset> networkCabels;
	Map<String, Asset> circuits;
	Map<String, Asset> ports;
	public OpsHierarchyAsset() {
		cages = new HashMap<String, Asset>(0);
		cabinets = new HashMap<String, Asset>(0);
		patchPanels = new HashMap<String, Asset>(0);
		networkCabels = new HashMap<String, Asset>(0);
		circuits = new HashMap<String, Asset>(0);
		ports = new HashMap<String, Asset>(0);
	}

	public Map<String, Asset> getCages() {
		return cages;
	}

	public void setCages(Map<String, Asset> cages) {
		this.cages = cages;
	}

	public Map<String, Asset> getCabinets() {
		return cabinets;
	}

	public void setCabinets(Map<String, Asset> cabinets) {
		this.cabinets = cabinets;
	}

	public Map<String, Asset> getPatchPanels() {
		return patchPanels;
	}

	public void setPatchPanels(Map<String, Asset> patchPanels) {
		this.patchPanels = patchPanels;
	}

	public Map<String, Asset> getNetworkCabels() {
		return networkCabels;
	}

	public void setNetworkCabels(Map<String, Asset> networkCabels) {
		this.networkCabels = networkCabels;
	}

	public Map<String, Asset> getCircuits() {
		return circuits;
	}

	public void setCircuits(Map<String, Asset> circuits) {
		this.circuits = circuits;
	}

	public Map<String, Asset> getPorts() {
		return ports;
	}

	public void setPorts(Map<String, Asset> ports) {
		this.ports = ports;
	}

	public Cage getOpsHierarchy() {
		/*
		 * 
		 * Cage cage = new Cage();
		 * 
		 * for (Entry<String, Asset> cabinet : cabinets.entrySet()) {
		 * cage.getCabinets().add(new Cabinet(cabinet.getValue())); }
		 * 
		 * for (Cabinet cabinet : cage.getCabinets()) { List<> for (Entry<String, Asset>
		 * circuitEntrySet : circuits.entrySet()) {
		 * 
		 * } } Map<String,NetworkCabelConnection> nccMap= new
		 * HashMap<String,NetworkCabelConnection>(); Map<String,PatchPanel>
		 * patchPanelMap= new HashMap<String,PatchPanel>();
		 * 
		 * for (Entry<String, Asset> port : ports.entrySet()) { NetworkCabelConnection
		 * ncc= nccMap.get(port.getValue().getParrentAssetNum()); if(null != ncc) {
		 * updatePort(port.getValue(),ncc); } else { ncc= new NetworkCabelConnection();
		 * updatePort(port.getValue(),ncc); } }
		 * 
		 * for (Entry<String, Asset> patchPanelEntrySet : patchPanels.entrySet()) {
		 * Asset patchPanelAsset = patchPanelEntrySet.getValue(); PatchPanel patchPanel
		 * = patchPanelMap.get(patchPanelAsset.getAssetNum()); if(null != patchPanel ) {
		 * updatePatchPanel(patchPanel,nccMap.get(patchPanelAsset.getAssetNum())); }
		 * else { patchPanel= new PatchPanel(); updatePatchPanel(patchPanel,nccMap); } }
		 * return new Cage();
		 */
		return new Cage(new Asset());	
	}

	private void updatePatchPanel(PatchPanel patchPanel, Map<String, NetworkCabelConnection> nccMap) {
		//patchPanel.getNetworkCabelConnection().add(nccMap.get(key));
		
	}

	private void updatePort(Asset port, NetworkCabelConnection ncc) {
		String productName=port.getProduct();
		if(productName.equalsIgnoreCase(AccountMoveConstants.EQUINIX_CONNECT_PORT)) {
			ncc.getEquinxConnectPort().add(port);			
		} else if(productName.equalsIgnoreCase(AccountMoveConstants.METRO_CONNECT_PORT)) {
			ncc.getMetroConnectPort().add(port);	
		} else if(productName.equalsIgnoreCase(AccountMoveConstants.EQUINIX_CONNECT_ADDITIONAL_IP_ALLOCATION)) {
			ncc.getEquinixConnectAddIPAllocation().add(port);	
		} else if(productName.equalsIgnoreCase(AccountMoveConstants.EQUINIX_CONNECT_MINIMUM_BANDWIDTH_COMMIT)) {
			ncc.getEquinixConnectMinBandWidthCommit().add(port);	
		}
		
	}
}
