package com.equinix.appops.dart.portal.model.search.product;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.equinix.appops.dart.portal.entity.ClxAssetDa;
import com.equinix.appops.dart.portal.entity.SiebelAssetDa;
import com.equinix.appops.dart.portal.entity.SvAssetDa;

public class ProductFilterResult implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4629466434754077860L;
	private ProductFilter productFilter;
	private List<SiebelAssetDa> sblList;
	private List<ClxAssetDa> clxList;
	private List<SvAssetDa> svList;
	private Set<String> sblRowIds;
	private Set<String> clxCageRowIds;
	private Set<String> clxCabninetRowIds;
	private Set<String> clxCabninetDpRowIds;
	private long totalRows;
	
	
	
	public Set<String> getClxCabninetDpRowIds() {
		return clxCabninetDpRowIds;
	}
	public void setClxCabninetDpRowIds(Set<String> clxCabninetDpRowIds) {
		this.clxCabninetDpRowIds = clxCabninetDpRowIds;
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
	public ProductFilter getProductFilter() {
		return productFilter;
	}
	public void setProductFilter(ProductFilter productFilter) {
		this.productFilter = productFilter;
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
	public long getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(long totalRows) {
		this.totalRows = totalRows;
	}
	
	
	

}
