package com.equinix.appops.dart.portal.mapper.accountmove;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.equinix.appops.dart.portal.mapper.accountmove.dto.AssetVaildationData;

public class AssetVaildationDataMapper implements RowMapper<AssetVaildationData> {

	String product;
	String validationType;

	public AssetVaildationDataMapper (String product, String validationTtype){
		this.product=product;
		this.validationType=validationTtype;
	}
	@Override
	public AssetVaildationData mapRow(ResultSet rs, int rowNum) throws SQLException {
		AssetVaildationData asv = new AssetVaildationData();
		if("BILLING".equalsIgnoreCase(validationType)) {
			asv.setAssetNum(rs.getString("ASSET_NUM"));
			asv.setAccountNum(rs.getString("OU_NUM"));
		} else if("OPENORDER".equalsIgnoreCase(validationType)) {
			asv.setAssetNum(rs.getString("ASSET_NUM"));
			asv.setOrderNum(rs.getString("ORDER_NUM"));
		}  else if("OPENQUOTE".equalsIgnoreCase(validationType)) {
			asv.setAssetNum(rs.getString("ASSET_NUM"));
			asv.setQuoteNum(rs.getString("QUOTE_NUM"));
		}
		return asv;
	}

}
