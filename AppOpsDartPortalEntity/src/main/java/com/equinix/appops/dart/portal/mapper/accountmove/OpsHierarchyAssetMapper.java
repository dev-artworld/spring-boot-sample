package com.equinix.appops.dart.portal.mapper.accountmove;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.RowMapper;

import com.equinix.appops.dart.portal.mapper.accountmove.dto.Asset;
import com.equinix.appops.dart.portal.mapper.accountmove.dto.OpsHierarchyAsset;

public class OpsHierarchyAssetMapper implements RowMapper<Asset> {
	OpsHierarchyAsset opsHierarchyAssets;
	public OpsHierarchyAssetMapper(OpsHierarchyAsset opsHierarchyAssets) {
		this.opsHierarchyAssets=opsHierarchyAssets;
	}

	public OpsHierarchyAssetMapper() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Asset mapRow(ResultSet rs, int rowNum) throws SQLException {
		Asset asset = prepareAsset(rs);		
		return asset;
	}

	private Asset prepareAsset(ResultSet rs) throws SQLException {
		Asset asset = new Asset();
		asset.setRowId(rs.getString("ROW_ID"));
		asset.setAssetNum(rs.getString("ASSET_NUM"));
		asset.setSerialNum(rs.getString("SERIAL_NUM"));
		asset.setAssetUCMID(rs.getString("ASSET_UCM_ID"));
		asset.setUcmID(rs.getString("UCM_ID"));
		asset.setAccount(rs.getString("ACCOUNT"));
		asset.setAccountName(rs.getString("ACCOUNTNAME"));
		asset.setIbxName(rs.getString("IBX"));
		asset.setxUniqueSpaceId(rs.getString("X_UNIQUE_SPACE_ID"));
		asset.setCageUSID(rs.getString("cage_USID_VAL"));
		asset.setCabinetUSId(rs.getString("cabinet_usid"));
		asset.setCabUSIdVal(rs.getString("cabusidVAL"));
		asset.setCabinetNumber(rs.getString("CABINETNUMBER"));
		asset.setSystemName(rs.getString("SYSNAME"));
		asset.setPoeName(rs.getString("POENAME"));
		asset.setParAssetId(rs.getString("PARASST_ID"));
		asset.setRootAssetId(rs.getString("ROOT_AST_ID"));
		asset.setPofAssetNum(rs.getString("POF_AST_NUM"));
		asset.setPofName(rs.getString("POF_NAME"));
		asset.setRelatedAccountNumber(rs.getString("REL_ACCT_ID"));
		asset.setCableId(rs.getString("CABLE_ID"));
		asset.setOwnerAccoutId(rs.getString("owner_accoutid"));
		asset.setPpId(rs.getString("PP_ID"));
		asset.setParentAssetId(rs.getString("X_OPS_PAR_ASSET_NUM"));
		asset.setParentAsset(rs.getString("PARENT_ASSET#"));
		asset.setHieararcyLevel(rs.getInt("HIEARARCY_LEVEL"));
		asset.setProduct(rs.getString("POENAME"));	
		asset.setModified(false);		
		asset.setxATTR22(rs.getString("xATTR22"));
		return asset;
	}

	
	public static void main(String[] args) {		
		String path = "C:\\Users\\mm\\Desktop\\assets.csv";
		List<Asset> l = new OpsHierarchyAssetMapper().processInputFile(path);
		Asset cage = null;
		for (Asset asset : l) {
			System.out.println(asset.getProduct()+" - "+asset.getAssetNum());
			
		}
		cage = prepareOpsHierarchy(l, cage);
		String str = "";//g.toJson(cage);
		System.out.println(str);
		System.out.println("===DONE====");
	}
	 
	
	private static Asset prepareOpsHierarchy(List<Asset> assets, Asset cage) {
		
		if(cage == null) {
			for (Asset asset : assets) {
				System.out.print(","+asset.getProduct());
				if(asset.getProduct().equalsIgnoreCase("Cage")) {
					System.err.println("Cage Found..");
					cage=asset;
					prepareOpsHierarchy(assets, cage);
				}
			}
		} else {
			List<Asset> childAsset=new ArrayList<>(0);
			findChildren(assets,cage,childAsset);
			if(childAsset.size()==0) {
				return null;
			} else {
				cage.getChildAssetList().addAll(childAsset);
				for (Asset c : childAsset) {
					prepareOpsHierarchy(assets, c);
				}
			}
		}
		
		System.out.println(cage);
		return cage;
	}

	private static List<Asset> findChildren(List<Asset> assets, Asset parentAsset, List<Asset> children) {
		for (Asset asset : assets) {
			if(asset.getParentAsset().equalsIgnoreCase(parentAsset.getAssetNum())) {
				children.add(asset);
			}
		}
		return children;
	}

	private List<Asset> processInputFile(String inputFilePath) {
	    List<Asset> inputList = new ArrayList<Asset>();
	    try{
	      File inputF = new File(inputFilePath);
	      InputStream inputFS = new FileInputStream(inputF);
	      BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));
	      // skip the header of the csv
	      inputList = br.lines()
	    		  .skip(1)
	    		  .map(mapToItem).collect(Collectors.toList());
	      br.close();
	    } catch (Exception e) {
	      e.printStackTrace(); 
	    }
	    return inputList ;
	}
	
	private Function<String, Asset> mapToItem = (line) -> {
		  String[] rs = line.split(";");// a CSV has comma separated lines
		  //"ASSETID","IBX","ROW_ID","ASSET#","PRODUCT","PARENT_ASSET#","ACCOUNT#","ACCOUNT",
		  //"CAGE","EQX_SYS_NAME","CABINET","CABINET_USID","PP_ID","CABLE_ID","HIEARARCY_LEVEL",
		  //"UCM_ID","X_UNIQUE_SPACE_ID"
		  //"ASSETID","IBX","ROW_ID","ASSET#","PRODUCT","PARENT_ASSET#","ACCOUNT#","ACCOUNT","CAGE","EQX_SYS_NAME","CABINET","CABINET_USID","PP_ID","CABLE_ID","HIEARARCY_LEVEL","UCM_ID","X_UNIQUE_SPACE_ID"
		  
		  int index=0;
		  Asset asset = new Asset();
		    //asset.setId(rs[index++]);
		    asset.setIbxName(rs[index++]);
			asset.setRowId(rs[index++]);
			asset.setAssetNum(rs[index++]);
			asset.setProduct(rs[index++]);
			//asset.setParrentAssetNum(rs[index++]);
			//asset.setAccountNumber(rs[index++]);
			asset.setAccount(rs[index++]);
			asset.setCageUSID(rs[index++]);
			asset.setSystemName(rs[index++]);
			//asset.setCabinetId(rs[index++]);
			asset.setCabinetUSId(rs[index++]);
			asset.setPpId(rs[index++]);
			asset.setCableId(rs[index++]);	
			asset.setHieararcyLevel(Integer.parseInt(rs[index++]));
			asset.setUcmID(rs[index++]);
			asset.setxUniqueSpaceId(rs[index++]);
			asset.setModified(false);			
			return asset;
	};	
}
