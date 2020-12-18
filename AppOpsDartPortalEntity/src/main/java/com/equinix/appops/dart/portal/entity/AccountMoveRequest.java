package com.equinix.appops.dart.portal.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.equinix.appops.dart.portal.mapper.accountmove.dto.AccountMoveAssetRequest;
import com.equinix.appops.dart.portal.mapper.accountmove.dto.Asset;


/**
 * The persistent class for the DFR_MASTER database table.
 * 
 */
@Entity
@Table(name="DART_ACC_MOVE" , schema="EQX_DART")
@NamedQuery(name="AccountMoveRequest.findAll", query="SELECT d FROM AccountMoveRequest d")
public class AccountMoveRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="DFR_ID")
	private String dfrId;

	@Lob
    @Column(name = "SEARCH_FILTER")
    private byte[] searchFilter;

	@Column(name = "PORT_ROWIds")
    private String portRowIds;
	
	public AccountMoveRequest(){
		
	}
	public AccountMoveRequest(AccountMoveAssetRequest request) {
		this.dfrId=request.getDfrId();
		this.searchFilter= request.getSearchCriteria().getBytes();
		this.portRowIds = request.getPortRowIds();
	}

	private String prepareCommaSeperateList(List<Asset> ports) {
		StringBuffer rowIds = new StringBuffer();
		for (Asset asset : ports) {
			if(rowIds.length()==0) {
				rowIds.append(asset.getRowId()).append("#").append(asset.getUcmID()).append("#").append(asset.getProduct());
			} else {
				rowIds.append(",").append(asset.getRowId()).append("#").append(asset.getUcmID()).append("#").append(asset.getProduct());
			}
		}
		return rowIds.toString();
	}
	public String getDfrId() {
		return dfrId;
	}

	public void setDfrId(String dfrId) {
		this.dfrId = dfrId;
	}

	public String getSearchFilter() {
		return new String(searchFilter);
	}

	public void setSearchFilter(byte[] searchFilter) {
		this.searchFilter = searchFilter;
	}
	public String getPortRowIds() {
		return portRowIds;
	}
	public void setPortRowIds(String portRowIds) {
		this.portRowIds = portRowIds;
	}
	
}