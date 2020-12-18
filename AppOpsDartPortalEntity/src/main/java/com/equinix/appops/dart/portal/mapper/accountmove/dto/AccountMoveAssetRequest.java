package com.equinix.appops.dart.portal.mapper.accountmove.dto;

import java.util.List;
import java.util.Set;

import com.equinix.appops.dart.portal.entity.ClxAssetDa;
import com.equinix.appops.dart.portal.entity.SiebelAssetDa;
import com.equinix.appops.dart.portal.entity.SvAssetDa;

public class AccountMoveAssetRequest {

	String dfrId;
	
	AccountMoveSearchFilter sourceCriteria;
	
	AccountMoveSearchFilter targetCriteria;
	
	List<Asset> targetAsset;
	
	List<Asset> srcAssets;
	
	String portRowIds;
	
	Boolean isNCCChildValidation=false;
	/**
	 * skip billing validation
	 */
	String xATTR22;
	
	
	private List<SiebelAssetDa> sblList;
	private List<ClxAssetDa> clxList;
	private List<SvAssetDa> svList;
	private Set<String> sblRowIds;
	private Set<String> clxCageRowIds;
	private Set<String> clxCabninetRowIds;
	private Set<String> clxCabninetDpRowIds;
	private long totalRows;
	private List<Asset> ports;
	
	private String searchCriteria;
	
	public String getDfrId() {
		return dfrId;
	}
	public void setDfrId(String dfrId) {
		this.dfrId = dfrId;
	}
	public AccountMoveSearchFilter getSourceCriteria() {
		return sourceCriteria;
	}
	public void setSourceCriteria(AccountMoveSearchFilter sourceCriteria) {
		this.sourceCriteria = sourceCriteria;
	}
	public AccountMoveSearchFilter getTargetCriteria() {
		return targetCriteria;
	}
	public void setTargetCriteria(AccountMoveSearchFilter targetCriteria) {
		this.targetCriteria = targetCriteria;
	}
	public List<Asset> getTargetAsset() {
		return targetAsset;
	}
	public void setTargetAsset(List<Asset> targetAsset) {
		this.targetAsset = targetAsset;
	}
	public List<Asset> getSrcAssets() {
		return srcAssets;
	}
	public void setSrcAssets(List<Asset> srcAssets) {
		this.srcAssets = srcAssets;
	}
	public List<SiebelAssetDa> getSblList() {
		return sblList;
	}
	public void setSblList(List<SiebelAssetDa> sblList) {
		this.sblList = sblList;
	}
	public List<ClxAssetDa> getClxList() {
		return clxList;
	}
	public void setClxList(List<ClxAssetDa> clxList) {
		this.clxList = clxList;
	}
	public List<SvAssetDa> getSvList() {
		return svList;
	}
	public void setSvList(List<SvAssetDa> svList) {
		this.svList = svList;
	}
	public Set<String> getSblRowIds() {
		return sblRowIds;
	}
	public void setSblRowIds(Set<String> sblRowIds) {
		this.sblRowIds = sblRowIds;
	}
	public Set<String> getClxCageRowIds() {
		return clxCageRowIds;
	}
	public void setClxCageRowIds(Set<String> clxCageRowIds) {
		this.clxCageRowIds = clxCageRowIds;
	}
	public Set<String> getClxCabninetRowIds() {
		return clxCabninetRowIds;
	}
	public void setClxCabninetRowIds(Set<String> clxCabninetRowIds) {
		this.clxCabninetRowIds = clxCabninetRowIds;
	}
	public Set<String> getClxCabninetDpRowIds() {
		return clxCabninetDpRowIds;
	}
	public void setClxCabninetDpRowIds(Set<String> clxCabninetDpRowIds) {
		this.clxCabninetDpRowIds = clxCabninetDpRowIds;
	}
	public long getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(long totalRows) {
		this.totalRows = totalRows;
	}
	public String getSearchCriteria() {
		return searchCriteria;
	}
	public void setSearchCriteria(String searchCriteria) {
		this.searchCriteria = searchCriteria;
	}
	public List<Asset> getPorts() {
		return ports;
	}
	public void setPorts(List<Asset> ports) {
		this.ports = ports;
	}
	public String getPortRowIds() {
		return portRowIds;
	}
	public void setPortRowIds(String portRowIds) {
		this.portRowIds = portRowIds;
	}
	public Boolean getIsNCCChildValidation() {
		return isNCCChildValidation;
	}
	public void setIsNCCChildValidation(Boolean isNCCChildValidation) {
		this.isNCCChildValidation = isNCCChildValidation;
	}
	
	public void setxATTR22(String xATTR22) {
		this.xATTR22 = xATTR22;
	}
	
	public String getxATTR22() {
		return xATTR22;
	}
	
}
