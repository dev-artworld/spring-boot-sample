package com.equinix.appops.dart.portal.model.search.product.ms;

import java.util.List;
import java.util.Map;

import com.equinix.appops.dart.portal.entity.mservices.MsAssetNewVal;
import com.equinix.appops.dart.portal.entity.mservices.MsCiDaNewVal;
import com.equinix.appops.dart.portal.entity.mservices.MsCiXaNewVal;
import com.equinix.appops.dart.portal.entity.mservices.MsSnapshotSiebelAssetDa;
import com.equinix.appops.dart.portal.entity.mservices.SnapshotSnowAssetDa;
import com.equinix.appops.dart.portal.entity.mservices.SnapshotSnowConfigItemDa;
import com.equinix.appops.dart.portal.entity.mservices.SnapshotSnowConfigItemXa;
import com.equinix.appops.dart.portal.entity.mservices.SnowConfigItemDa;
import com.equinix.appops.dart.portal.entity.mservices.SnowConfigItemXa;
import com.equinix.appops.dart.portal.model.search.product.ProductFilter;

public class MsSnapshotProductFilterResult {
	
	private ProductFilter productFilter;
	private List<MsSnapshotSiebelAssetDa> sblList;
	private Map<String,MsAssetNewVal>  msAssetNewValMap;
	private List<SnapshotSnowAssetDa> snowList;
	private List<SnapshotSnowConfigItemDa> snowConfigItemDaList;
	private List<SnapshotSnowConfigItemXa> snowConfigItemXaList;
	private Map<String,MsCiDaNewVal>  msCiDaNewValMap;
	private Map<String,MsCiXaNewVal>  msCiXaNewValMap;
	
	public ProductFilter getProductFilter() {
		return productFilter;
	}
	public void setProductFilter(ProductFilter productFilter) {
		this.productFilter = productFilter;
	}
	public List<MsSnapshotSiebelAssetDa> getSblList() {
		return sblList;
	}
	public void setSblList(List<MsSnapshotSiebelAssetDa> sblList) {
		this.sblList = sblList;
	}
	public Map<String, MsAssetNewVal> getMsAssetNewValMap() {
		return msAssetNewValMap;
	}
	public void setMsAssetNewValMap(Map<String, MsAssetNewVal> msAssetNewValMap) {
		this.msAssetNewValMap = msAssetNewValMap;
	}
	public List<SnapshotSnowAssetDa> getSnowList() {
		return snowList;
	}
	public void setSnowList(List<SnapshotSnowAssetDa> snowList) {
		this.snowList = snowList;
	}
	public List<SnapshotSnowConfigItemDa> getSnowConfigItemDaList() {
		return snowConfigItemDaList;
	}
	public void setSnowConfigItemDaList(List<SnapshotSnowConfigItemDa> snowConfigItemDaList) {
		this.snowConfigItemDaList = snowConfigItemDaList;
	}
	public List<SnapshotSnowConfigItemXa> getSnowConfigItemXaList() {
		return snowConfigItemXaList;
	}
	public void setSnowConfigItemXaList(List<SnapshotSnowConfigItemXa> snowConfigItemXaList) {
		this.snowConfigItemXaList = snowConfigItemXaList;
	}
	public Map<String, MsCiDaNewVal> getMsCiDaNewValMap() {
		return msCiDaNewValMap;
	}
	public void setMsCiDaNewValMap(Map<String, MsCiDaNewVal> msCiDaNewValMap) {
		this.msCiDaNewValMap = msCiDaNewValMap;
	}
	public Map<String, MsCiXaNewVal> getMsCiXaNewValMap() {
		return msCiXaNewValMap;
	}
	public void setMsCiXaNewValMap(Map<String, MsCiXaNewVal> msCiXaNewValMap) {
		this.msCiXaNewValMap = msCiXaNewValMap;
	}
	
}
