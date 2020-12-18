package com.equinix.appops.dart.portal.model.search.product;

import java.util.List;
import java.util.Map;

import com.equinix.appops.dart.portal.entity.AssetNewVal;
import com.equinix.appops.dart.portal.entity.SnapshotClxAssetDa;
import com.equinix.appops.dart.portal.entity.SnapshotSiebelAssetDa;
import com.equinix.appops.dart.portal.entity.SnapshotSvAssetDa;

public class SnapshotProductFilterResult {
	
	private ProductFilter productFilter;
	private List<SnapshotSiebelAssetDa> sblList;
	private Map<String,AssetNewVal>  assetNewValMap;
	private List<SnapshotClxAssetDa> clxList;
	private List<SnapshotSvAssetDa> svList;
	
	public ProductFilter getProductFilter() {
		return productFilter;
	}
	public void setProductFilter(ProductFilter productFilter) {
		this.productFilter = productFilter;
	}
	public List<SnapshotSiebelAssetDa> getSblList() {
		return sblList;
	}
	public void setSblList(List<SnapshotSiebelAssetDa> sblList) {
		this.sblList = sblList;
	}
	public List<SnapshotClxAssetDa> getClxList() {
		return clxList;
	}
	public void setClxList(List<SnapshotClxAssetDa> clxList) {
		this.clxList = clxList;
	}
	public List<SnapshotSvAssetDa> getSvList() {
		return svList;
	}
	public void setSvList(List<SnapshotSvAssetDa> svList) {
		this.svList = svList;
	}
	public Map<String, AssetNewVal> getAssetNewValMap() {
		return assetNewValMap;
	}
	public void setAssetNewValMap(Map<String, AssetNewVal> assetNewValMap) {
		this.assetNewValMap = assetNewValMap;
	}
	
	
	

}
