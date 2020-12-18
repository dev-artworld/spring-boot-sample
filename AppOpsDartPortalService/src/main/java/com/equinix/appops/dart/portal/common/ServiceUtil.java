package com.equinix.appops.dart.portal.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.equinix.appops.dart.portal.constant.DartConstants;
import com.equinix.appops.dart.portal.entity.AttributeConfig;
import com.equinix.appops.dart.portal.entity.ClxAssetDa;
import com.equinix.appops.dart.portal.entity.SiebelAssetDa;
import com.equinix.appops.dart.portal.entity.SnapshotClxAssetDa;
import com.equinix.appops.dart.portal.entity.SnapshotSiebelAssetDa;
import com.equinix.appops.dart.portal.entity.SnapshotSvAssetDa;
import com.equinix.appops.dart.portal.entity.SvAssetDa;
import com.equinix.appops.dart.portal.model.search.product.PFilter;
import com.equinix.appops.dart.portal.model.search.product.ProductFilter;
import com.equinix.appops.dart.portal.model.search.product.SearchDrop;
import com.equinix.appops.dart.portal.util.DartUtil;

public class ServiceUtil {
	public static final List<String> productListWhichHaveCommonAttribute = Arrays.asList(new String[]{"Network Cable Connection","AC Circuit"});
 	
	/// snapshot error and snapshot sv with dfrid 
	public static SvAssetDa getSv(SiebelAssetDa sbl, List<SvAssetDa> svList) {
		 for(SvAssetDa sv : svList){
			 if(sbl.getHeader2()!= null 
					 &&  sbl.getHeader2().equalsIgnoreCase(sv.getHeader2())
					 && sbl.getHeader20().equalsIgnoreCase(sv.getHeader20())){
				 return sv;
			 }
		 }
		 return null;
	}
	
	/*public static ClxAssetDa getClx(SiebelAssetDa sbl,List<ClxAssetDa> clxList) {
		if(sbl.getHeader20().equalsIgnoreCase("Cage")){
			for(ClxAssetDa clx : clxList){
				if( sbl.getHeader10()!=null 
					&& sbl.getHeader6()!=null 
					&& sbl.getHeader10().equalsIgnoreCase(clx.getHeader10()) 
					&& sbl.getHeader6().equalsIgnoreCase(clx.getHeader6())
					&& sbl.getHeader20().equalsIgnoreCase(clx.getHeader20())){
					return clx;
				}  	
			}
		}
		
		if(sbl.getHeader20().equalsIgnoreCase("Cabinet")){
			for(ClxAssetDa clx : clxList){
				if(sbl.getHeader20().equalsIgnoreCase(clx.getHeader20()) 
						&& sbl.getHeader12()!=null 
						&& sbl.getHeader12().equalsIgnoreCase(clx.getHeader12())){
					return clx;
				}  	
			}
		}
		return null;
	}*/
	
	public static ClxAssetDa getClx(SiebelAssetDa sbl,List<ClxAssetDa> clxList) {
		 	/*if(sbl.getHeader2().equalsIgnoreCase("14_0002242701"))
		 		System.out.println("check");*/
			for(ClxAssetDa clx : clxList){
				/*if(clx.getHeader10().equalsIgnoreCase("MA3:02:00Z2C2"))
					System.out.println("check2");*/
				if(sbl.getHeader20().equalsIgnoreCase("Cage") 
					&& sbl.getHeader10()!=null 
							&& sbl.getHeader6()!=null 
							&& sbl.getHeader10().equalsIgnoreCase(clx.getHeader10()) 
							//&& sbl.getHeader6().equalsIgnoreCase(clx.getHeader6())
							&& sbl.getHeader20().equalsIgnoreCase(clx.getHeader20())){
				
					return clx;
				} 
				if(sbl.getHeader20().equalsIgnoreCase("Cabinet")
						&&sbl.getHeader20().equalsIgnoreCase(clx.getHeader20()) 
						&& sbl.getHeader12()!=null 
						&& sbl.getHeader12().equalsIgnoreCase(clx.getHeader12())){
					
					return clx;
					
				}
				
				if(sbl.getHeader20().equalsIgnoreCase("Demarcation Point")
						&&sbl.getHeader20().equalsIgnoreCase(clx.getHeader20()) 
						&& sbl.getHeader12()!=null 
						&& sbl.getHeader12().equalsIgnoreCase(clx.getHeader12())){
					
					return clx;
					
				}
			}
		return null;
	}
	
	
	public static SnapshotSvAssetDa getSv(SnapshotSiebelAssetDa sbl, List<SnapshotSvAssetDa> svList) {
		 for(SnapshotSvAssetDa sv : svList){
			 if(sbl.getHeader2()!= null 
					 &&  sbl.getHeader2().equalsIgnoreCase(sv.getHeader2())
					 && sbl.getHeader20().equalsIgnoreCase(sv.getHeader20())){
				 return sv;
			 }
		 }
		 return null;
	}
	
	public static SnapshotClxAssetDa getClx(SnapshotSiebelAssetDa sbl,List<SnapshotClxAssetDa> clxList) {
		/*if(sbl.getHeader2().equalsIgnoreCase("14_0002242701"))
 		System.out.println("check");*/
		for(SnapshotClxAssetDa clx : clxList){
			/*if(clx.getHeader10().equalsIgnoreCase("MA3:02:00Z2C2"))
			System.out.println("check2");*/
			if(sbl.getHeader20().equalsIgnoreCase("Cage") 
					&& sbl.getHeader10()!=null 
					&& sbl.getHeader6()!=null 
					&& sbl.getHeader10().equalsIgnoreCase(clx.getHeader10()) 
					//&& sbl.getHeader6().equalsIgnoreCase(clx.getHeader6())
					&& sbl.getHeader20().equalsIgnoreCase(clx.getHeader20())){

				return clx;
			} 
			if(sbl.getHeader20().equalsIgnoreCase("Cabinet")
					&&sbl.getHeader20().equalsIgnoreCase(clx.getHeader20()) 
					&& sbl.getHeader12()!=null 
					&& sbl.getHeader12().equalsIgnoreCase(clx.getHeader12())){

				return clx;

			}
			
			if(sbl.getHeader20().equalsIgnoreCase("Demarcation Point")
					&&sbl.getHeader20().equalsIgnoreCase(clx.getHeader20()) 
					&& sbl.getHeader12()!=null 
					&& sbl.getHeader12().equalsIgnoreCase(clx.getHeader12())){
				
				return clx;
				
			}
		}
		return null;
}
	public static String getSvHeaderValue(int dataRowIndex,SnapshotSvAssetDa da){
		String result = DartConstants.NA; 
		
		switch(dataRowIndex){
		case 1 : return da.getHeader1(); 
		case 2 : return da.getHeader2();
		case 3 : return da.getHeader3();
		case 4 : return da.getHeader4();
		case 5 : return da.getHeader5();
		case 6 : return da.getHeader6();
		case 7 : return da.getHeader7();
		case 8 : return da.getHeader8();
		case 9 : return da.getHeader9();
		case 10: return da.getHeader10();
		case 11: return da.getHeader11();
		case 12: return da.getHeader12();
		case 13: return da.getHeader13();
		case 14: return da.getHeader14();
		case 15: return da.getHeader15();
		case 16: return da.getHeader16();
		case 17: return DartUtil.dateToString(da.getHeader17());
		case 18: return da.getHeader18();
		case 19: return da.getHeader19();
		case 20: return da.getHeader20();
		case 21: return da.getHeader21();
		case 22: return da.getHeader22();
		case 23: return da.getHeader23();
		case 24: return da.getHeader24();
		case 25: return da.getHeader25();
		case 26: return da.getHeader26();
		case 27: return da.getHeader27();
		case 28: return da.getHeader28();
		case 29: return da.getHeader29();
		case 30: return da.getHeader30();
		case 31: return da.getHeader31();
		case 32: return da.getHeader32();
		case 33: return da.getHeader33();
		case 34: return DartUtil.dateToString(da.getHeader34());
		case 35: return da.getHeader35();
		case 36: return DartUtil.dateToString(da.getHeader36());
		case 37: return da.getHeader37();
		case 38: return da.getHeader38();
		case 39: return da.getHeader39();
		case 40: return da.getHeader40();
		case 41: return da.getHeader41();
		case 42: return da.getHeader42();
		case 43: return da.getHeader43();
		case 44: return da.getHeader44();
		case 45: return da.getHeader45();
		case 46: return da.getHeader46();
		case 47: return da.getHeader47();
		case 48: return da.getHeader48();
		case 49: return da.getHeader49();
		case 50: return da.getHeader50();
		case 51: return da.getHeader51();
		case 52: return da.getHeader52();
		case 53: return da.getHeader53();
		case 54: return da.getHeader54();
		case 55: return DartUtil.dateToString(da.getHeader55());
		case 56: return da.getHeader56();
		case 57: return da.getHeader57();
		case 58: return da.getHeader58();
		case 59: return da.getHeader59();
		case 60: return da.getHeader60();
		case 61: return da.getHeader61();
		case 62: return da.getHeader62();
		case 63: return da.getHeader63();
		case 64: return da.getHeader64();
		case 65: return da.getHeader65();
		case 66: return da.getHeader66();
		
		case 67: return da.getAttr325();
		case 68: return da.getAttr326();
		case 69: return da.getAttr327();
		case 70: return da.getAttr328();
		case 71: return da.getAttr329();
		case 72: return da.getAttr330();
		case 73: return da.getAttr331();
		case 74: return da.getAttr332();
		case 75: return da.getAttr333();
		case 76: return da.getAttr334();
		case 77: return da.getAttr335();
		case 78: return da.getAttr336();
		case 79: return da.getAttr337();
		case 80: return da.getAttr338();
		case 81: return da.getAttr339();
		case 82: return da.getAttr340();
		case 83: return da.getAttr341();
		case 84: return da.getAttr342();
		case 85: return da.getAttr343();
		case 86: return da.getAttr344();
		case 87: return da.getAttr345();
		case 88: return da.getAttr346();
		case 89: return da.getAttr347();
		case 90: return da.getAttr348();
		case 91: return da.getAttr349();
		case 92: return da.getAttr350();
		default :
				return result;
		}
	}
	
	public static String getSvHeaderValue(int dataRowIndex,SvAssetDa da){
		String result = DartConstants.NA; 
		
		switch(dataRowIndex){
		case 1 : return da.getHeader1(); 
		case 2 : return da.getHeader2();
		case 3 : return da.getHeader3();
		case 4 : return da.getHeader4();
		case 5 : return da.getHeader5();
		case 6 : return da.getHeader6();
		case 7 : return da.getHeader7();
		case 8 : return da.getHeader8();
		case 9 : return da.getHeader9();
		case 10: return da.getHeader10();
		case 11: return da.getHeader11();
		case 12: return da.getHeader12();
		case 13: return da.getHeader13();
		case 14: return da.getHeader14();
		case 15: return da.getHeader15();
		case 16: return da.getHeader16();
		case 17: return DartUtil.dateToString(da.getHeader17());
		case 18: return da.getHeader18();
		case 19: return da.getHeader19();
		case 20: return da.getHeader20();
		case 21: return da.getHeader21();
		case 22: return da.getHeader22();
		case 23: return da.getHeader23();
		case 24: return da.getHeader24();
		case 25: return da.getHeader25();
		case 26: return da.getHeader26();
		case 27: return da.getHeader27();
		case 28: return da.getHeader28();
		case 29: return da.getHeader29();
		case 30: return da.getHeader30();
		case 31: return da.getHeader31();
		case 32: return da.getHeader32();
		case 33: return da.getHeader33();
		case 34: return DartUtil.dateToString(da.getHeader34());
		case 35: return da.getHeader35();
		case 36: return DartUtil.dateToString(da.getHeader36());
		case 37: return da.getHeader37();
		case 38: return da.getHeader38();
		case 39: return da.getHeader39();
		case 40: return da.getHeader40();
		case 41: return da.getHeader41();
		case 42: return da.getHeader42();
		case 43: return da.getHeader43();
		case 44: return da.getHeader44();
		case 45: return da.getHeader45();
		case 46: return da.getHeader46();
		case 47: return da.getHeader47();
		case 48: return da.getHeader48();
		case 49: return da.getHeader49();
		case 50: return da.getHeader50();
		case 51: return da.getHeader51();
		case 52: return da.getHeader52();
		case 53: return da.getHeader53();
		case 54: return da.getHeader54();
		case 55: return DartUtil.dateToString(da.getHeader55());
		case 56: return da.getHeader56();
		case 57: return da.getHeader57();
		case 58: return da.getHeader58();
		case 59: return da.getHeader59();
		case 60: return da.getHeader60();
		case 61: return da.getHeader61();
		case 62: return da.getHeader62();
		case 63: return da.getHeader63();
		case 64: return da.getHeader64();
		case 65: return da.getHeader65();
		case 66: return da.getHeader66();
		
		case 67: return da.getAttr325();
		case 68: return da.getAttr326();
		case 69: return da.getAttr327();
		case 70: return da.getAttr328();
		case 71: return da.getAttr329();
		case 72: return da.getAttr330();
		case 73: return da.getAttr331();
		case 74: return da.getAttr332();
		case 75: return da.getAttr333();
		case 76: return da.getAttr334();
		case 77: return da.getAttr335();
		case 78: return da.getAttr336();
		case 79: return da.getAttr337();
		case 80: return da.getAttr338();
		case 81: return da.getAttr339();
		case 82: return da.getAttr340();
		case 83: return da.getAttr341();
		case 84: return da.getAttr342();
		case 85: return da.getAttr343();
		case 86: return da.getAttr344();
		case 87: return da.getAttr345();
		case 88: return da.getAttr346();
		case 89: return da.getAttr347();
		case 90: return da.getAttr348();
		case 91: return da.getAttr349();
		case 92: return da.getAttr350();
		default :
				return result;
		}
	}
	
	public static String getClxHeaderValue(int dataRowIndex,SnapshotClxAssetDa da){
		String result = "NA"; 
		
		switch(dataRowIndex){
		case 1 : return da.getHeader1();
		case 2 : return da.getHeader2();
		case 3 : return da.getHeader3();
		case 4 : return da.getHeader4();
		case 5 : return da.getHeader5();
		case 6 : return da.getHeader6();
		case 7 : return da.getHeader7();
		case 8 : return da.getHeader8();
		case 9 : return da.getHeader9();
		case 10: return da.getHeader10();
		case 11: return da.getHeader11();
		case 12: return da.getHeader12();
		case 13: return da.getHeader13();
		case 14: return da.getHeader14();
		case 15: return da.getHeader15();
		case 16: return da.getHeader16();
		case 17: return DartUtil.dateToString(da.getHeader17());
		case 18: return da.getHeader18();
		case 19: return da.getHeader19();
		case 20: return da.getHeader20();
		case 21: return da.getHeader21();
		case 22: return da.getHeader22();
		case 23: return da.getHeader23();
		case 24: return da.getHeader24();
		case 25: return da.getHeader25();
		case 26: return da.getHeader26();
		case 27: return da.getHeader27();
		case 28: return da.getHeader28();
		case 29: return da.getHeader29();
		case 30: return da.getHeader30();
		case 31: return da.getHeader31();
		case 32: return da.getHeader32();
		case 33: return da.getHeader33();
		case 34: return DartUtil.dateToString(da.getHeader34());
		case 35: return da.getHeader35();
		case 36: return DartUtil.dateToString(da.getHeader36());
		case 37: return da.getHeader37();
		case 38: return da.getHeader38();
		case 39: return da.getHeader39();
		case 40: return da.getHeader40();
		case 41: return da.getHeader41();
		case 42: return da.getHeader42();
		case 43: return da.getHeader43();
		case 44: return da.getHeader44();
		case 45: return da.getHeader45();
		case 46: return da.getHeader46();
		case 47: return da.getHeader47();
		case 48: return da.getHeader48();
		case 49: return da.getHeader49();
		case 50: return da.getHeader50();
		case 51: return da.getHeader51();
		case 52: return da.getHeader52();
		case 53: return da.getHeader53();
		case 54: return da.getHeader54();
		case 55: return DartUtil.dateToString(da.getHeader55());
		case 56: return da.getHeader56();
		case 57: return da.getHeader57();
		case 58: return da.getHeader58();
		case 59: return da.getHeader59();
		case 60: return da.getHeader60();
		case 61: return da.getHeader61();
		case 62: return da.getHeader62();
		case 63: return da.getHeader63();
		case 64: return da.getHeader64();
		case 65: return da.getHeader65();
		case 66: return da.getHeader66();
		
		case 67: return da.getAttr325();
		case 68: return da.getAttr326();
		case 69: return da.getAttr327();
		case 70: return da.getAttr328();
		case 71: return da.getAttr329();
		case 72: return da.getAttr330();
		case 73: return da.getAttr331();
		case 74: return da.getAttr332();
		case 75: return da.getAttr333();
		case 76: return da.getAttr334();
		case 77: return da.getAttr335();
		case 78: return da.getAttr336();
		case 79: return da.getAttr337();
		case 80: return da.getAttr338();
		case 81: return da.getAttr339();
		case 82: return da.getAttr340();
		case 83: return da.getAttr341();
		case 84: return da.getAttr342();
		case 85: return da.getAttr343();
		case 86: return da.getAttr344();
		case 87: return da.getAttr345();
		case 88: return da.getAttr346();
		case 89: return da.getAttr347();
		case 90: return da.getAttr348();
		case 91: return da.getAttr349();
		case 92: return da.getAttr350();
		default :
				return result;
		}
	}
	
	public static String getClxHeaderValue(int dataRowIndex,ClxAssetDa da){
		String result = "NA"; 
		
		switch(dataRowIndex){
		case 1 : return da.getHeader1();
		case 2 : return da.getHeader2();
		case 3 : return da.getHeader3();
		case 4 : return da.getHeader4();
		case 5 : return da.getHeader5();
		case 6 : return da.getHeader6();
		case 7 : return da.getHeader7();
		case 8 : return da.getHeader8();
		case 9 : return da.getHeader9();
		case 10: return da.getHeader10();
		case 11: return da.getHeader11();
		case 12: return da.getHeader12();
		case 13: return da.getHeader13();
		case 14: return da.getHeader14();
		case 15: return da.getHeader15();
		case 16: return da.getHeader16();
		case 17: return DartUtil.dateToString(da.getHeader17());
		case 18: return da.getHeader18();
		case 19: return da.getHeader19();
		case 20: return da.getHeader20();
		case 21: return da.getHeader21();
		case 22: return da.getHeader22();
		case 23: return da.getHeader23();
		case 24: return da.getHeader24();
		case 25: return da.getHeader25();
		case 26: return da.getHeader26();
		case 27: return da.getHeader27();
		case 28: return da.getHeader28();
		case 29: return da.getHeader29();
		case 30: return da.getHeader30();
		case 31: return da.getHeader31();
		case 32: return da.getHeader32();
		case 33: return da.getHeader33();
		case 34: return DartUtil.dateToString(da.getHeader34());
		case 35: return da.getHeader35();
		case 36: return DartUtil.dateToString(da.getHeader36());
		case 37: return da.getHeader37();
		case 38: return da.getHeader38();
		case 39: return da.getHeader39();
		case 40: return da.getHeader40();
		case 41: return da.getHeader41();
		case 42: return da.getHeader42();
		case 43: return da.getHeader43();
		case 44: return da.getHeader44();
		case 45: return da.getHeader45();
		case 46: return da.getHeader46();
		case 47: return da.getHeader47();
		case 48: return da.getHeader48();
		case 49: return da.getHeader49();
		case 50: return da.getHeader50();
		case 51: return da.getHeader51();
		case 52: return da.getHeader52();
		case 53: return da.getHeader53();
		case 54: return da.getHeader54();
		case 55: return DartUtil.dateToString(da.getHeader55());
		case 56: return da.getHeader56();
		case 57: return da.getHeader57();
		case 58: return da.getHeader58();
		case 59: return da.getHeader59();
		case 60: return da.getHeader60();
		case 61: return da.getHeader61();
		case 62: return da.getHeader62();
		case 63: return da.getHeader63();
		case 64: return da.getHeader64();
		case 65: return da.getHeader65();
		case 66: return da.getHeader66();
		
		case 67: return da.getAttr325();
		case 68: return da.getAttr326();
		case 69: return da.getAttr327();
		case 70: return da.getAttr328();
		case 71: return da.getAttr329();
		case 72: return da.getAttr330();
		case 73: return da.getAttr331();
		case 74: return da.getAttr332();
		case 75: return da.getAttr333();
		case 76: return da.getAttr334();
		case 77: return da.getAttr335();
		case 78: return da.getAttr336();
		case 79: return da.getAttr337();
		case 80: return da.getAttr338();
		case 81: return da.getAttr339();
		case 82: return da.getAttr340();
		case 83: return da.getAttr341();
		case 84: return da.getAttr342();
		case 85: return da.getAttr343();
		case 86: return da.getAttr344();
		case 87: return da.getAttr345();
		case 88: return da.getAttr346();
		case 89: return da.getAttr347();
		case 90: return da.getAttr348();
		case 91: return da.getAttr349();
		case 92: return da.getAttr350();
		default :
				return result;
		}
	}
	
	
	
	public static String getSblHeaderValue(int dataRowIndex,SiebelAssetDa da){
		String result = "NA"; 
		
		switch(dataRowIndex){
		case 1 : return da.getHeader1();
		case 2 : return da.getHeader2();
		case 3 : return da.getHeader3();
		case 4 : return da.getHeader4();
		case 5 : return da.getHeader5();
		case 6 : return da.getHeader6();
		case 7 : return da.getHeader7();
		case 8 : return da.getHeader8();
		case 9 : return da.getHeader9();
		case 10: return da.getHeader10();
		case 11: return da.getHeader11();
		case 12: return da.getHeader12();
		case 13: return da.getHeader13();
		case 14: return da.getHeader14();
		case 15: return da.getHeader15();
		case 16: return da.getHeader16();
		case 17: return DartUtil.dateToString(da.getHeader17());
		case 18: return da.getHeader18();
		case 19: return da.getHeader19();
		case 20: return da.getHeader20();
		case 21: return da.getHeader21();
		case 22: return da.getHeader22();
		case 23: return da.getHeader23();
		case 24: return da.getHeader24();
		case 25: return da.getHeader25();
		case 26: return da.getHeader26();
		case 27: return da.getHeader27();
		case 28: return da.getHeader28();
		case 29: return da.getHeader29();
		case 30: return da.getHeader30();
		case 31: return da.getHeader31();
		case 32: return da.getHeader32();
		case 33: return da.getHeader33();
		case 34: return DartUtil.dateToString(da.getHeader34());
		case 35: return da.getHeader35();
		case 36: return DartUtil.dateToString(da.getHeader36());
		case 37: return da.getHeader37();
		case 38: return da.getHeader38();
		case 39: return da.getHeader39();
		case 40: return da.getHeader40();
		case 41: return da.getHeader41();
		case 42: return da.getHeader42();
		case 43: return da.getHeader43();
		case 44: return da.getHeader44();
		case 45: return da.getHeader45();
		case 46: return da.getHeader46();
		case 47: return da.getHeader47();
		case 48: return da.getHeader48();
		case 49: return da.getHeader49();
		case 50: return da.getHeader50();
		case 51: return da.getHeader51();
		case 52: return da.getHeader52();
		case 53: return da.getHeader53();
		case 54: return da.getHeader54();
		case 55: return DartUtil.dateToString(da.getHeader55());
		case 56: return da.getHeader56();
		case 57: return da.getHeader57();
		case 58: return da.getHeader58();
		case 59: return da.getHeader59();
		case 60: return da.getHeader60();
		case 61: return da.getHeader61();
		case 62: return da.getHeader62();
		case 63: return da.getHeader63();
		case 64: return da.getHeader64();
		case 65: return da.getHeader65();
		case 66: return da.getHeader66();
		
		case 67: return da.getAttr325();
		case 68: return da.getAttr326();
		case 69: return da.getAttr327();
		case 70: return da.getAttr328();
		case 71: return da.getAttr329();
		case 72: return da.getAttr330();
		case 73: return da.getAttr331();
		case 74: return da.getAttr332();
		case 75: return da.getAttr333();
		case 76: return da.getAttr334();
		case 77: return da.getAttr335();
		case 78: return da.getAttr336();
		case 79: return da.getAttr337();
		case 80: return da.getAttr338();
		case 81: return da.getAttr339();
		case 82: return da.getAttr340();
		case 83: return da.getAttr341();
		case 84: return da.getAttr342();
		case 85: return da.getAttr343();
		case 86: return da.getAttr344();
		case 87: return da.getAttr345();
		case 88: return da.getAttr346();
		case 89: return da.getAttr347();
		case 90: return da.getAttr348();
		case 91: return da.getAttr349();
		case 92: return da.getAttr350();
		default :
				return result;
		}
	}
	
	public static String getSblHeaderValue(int dataRowIndex,SnapshotSiebelAssetDa da){
		String result = "NA"; 
		
		switch(dataRowIndex){
		case 1 : return da.getHeader1();
		case 2 : return da.getHeader2();
		case 3 : return da.getHeader3();
		case 4 : return da.getHeader4();
		case 5 : return da.getHeader5();
		case 6 : return da.getHeader6();
		case 7 : return da.getHeader7();
		case 8 : return da.getHeader8();
		case 9 : return da.getHeader9();
		case 10: return da.getHeader10();
		case 11: return da.getHeader11();
		case 12: return da.getHeader12();
		case 13: return da.getHeader13();
		case 14: return da.getHeader14();
		case 15: return da.getHeader15();
		case 16: return da.getHeader16();
		case 17: return DartUtil.dateToString(da.getHeader17());
		case 18: return da.getHeader18();
		case 19: return da.getHeader19();
		case 20: return da.getHeader20();
		case 21: return da.getHeader21();
		case 22: return da.getHeader22();
		case 23: return da.getHeader23();
		case 24: return da.getHeader24();
		case 25: return da.getHeader25();
		case 26: return da.getHeader26();
		case 27: return da.getHeader27();
		case 28: return da.getHeader28();
		case 29: return da.getHeader29();
		case 30: return da.getHeader30();
		case 31: return da.getHeader31();
		case 32: return da.getHeader32();
		case 33: return da.getHeader33();
		case 34: return DartUtil.dateToString(da.getHeader34());
		case 35: return da.getHeader35();
		case 36: return DartUtil.dateToString(da.getHeader36());
		case 37: return da.getHeader37();
		case 38: return da.getHeader38();
		case 39: return da.getHeader39();
		case 40: return da.getHeader40();
		case 41: return da.getHeader41();
		case 42: return da.getHeader42();
		case 43: return da.getHeader43();
		case 44: return da.getHeader44();
		case 45: return da.getHeader45();
		case 46: return da.getHeader46();
		case 47: return da.getHeader47();
		case 48: return da.getHeader48();
		case 49: return da.getHeader49();
		case 50: return da.getHeader50();
		case 51: return da.getHeader51();
		case 52: return da.getHeader52();
		case 53: return da.getHeader53();
		case 54: return da.getHeader54();
		case 55: return DartUtil.dateToString(da.getHeader55());
		case 56: return da.getHeader56();
		case 57: return da.getHeader57();
		case 58: return da.getHeader58();
		case 59: return da.getHeader59();
		case 60: return da.getHeader60();
		case 61: return da.getHeader61();
		case 62: return da.getHeader62();
		case 63: return da.getHeader63();
		case 64: return da.getHeader64();
		case 65: return da.getHeader65();
		case 66: return da.getHeader66();
		
		case 67: return da.getAttr325();
		case 68: return da.getAttr326();
		case 69: return da.getAttr327();
		case 70: return da.getAttr328();
		case 71: return da.getAttr329();
		case 72: return da.getAttr330();
		case 73: return da.getAttr331();
		case 74: return da.getAttr332();
		case 75: return da.getAttr333();
		case 76: return da.getAttr334();
		case 77: return da.getAttr335();
		case 78: return da.getAttr336();
		case 79: return da.getAttr337();
		case 80: return da.getAttr338();
		case 81: return da.getAttr339();
		case 82: return da.getAttr340();
		case 83: return da.getAttr341();
		case 84: return da.getAttr342();
		case 85: return da.getAttr343();
		case 86: return da.getAttr344();
		case 87: return da.getAttr345();
		case 88: return da.getAttr346();
		case 89: return da.getAttr347();
		case 90: return da.getAttr348();
		case 91: return da.getAttr349();
		case 92: return da.getAttr350();
		default :
				return result;
		}
	}
	
	public static String getSblAttrValue(int dataRowIndex,SiebelAssetDa da){
		String result = "NA"; 

		switch(dataRowIndex){
		case 1  : return da.getAttr1();
		case 2  : return da.getAttr2();
		case 3  : return da.getAttr3();
		case 4  : return da.getAttr4();
		case 5  : return da.getAttr5();
		case 6  : return da.getAttr6();
		case 7  : return da.getAttr7();
		case 8  : return da.getAttr8();
		case 9  : return da.getAttr9();
		case 10 : return da.getAttr10();
		case 11 : return da.getAttr11();
		case 12 : return da.getAttr12();
		case 13 : return da.getAttr13();
		case 14 : return da.getAttr14();
		case 15 : return da.getAttr15();
		case 16 : return da.getAttr16();
		case 17 : return da.getAttr17();
		case 18 : return da.getAttr18();
		case 19 : return da.getAttr19();
		case 20 : return da.getAttr20();
		case 21 : return da.getAttr21();
		case 22 : return da.getAttr22();
		case 23 : return da.getAttr23();
		case 24 : return da.getAttr24();
		case 25 : return da.getAttr25();
		case 26 : return da.getAttr26();
		case 27 : return da.getAttr27();
		case 28 : return da.getAttr28();
		case 29 : return da.getAttr29();
		case 30 : return da.getAttr30();
		case 31 : return da.getAttr31();
		case 32 : return da.getAttr32();
		case 33 : return da.getAttr33();
		case 34 : return da.getAttr34();
		case 35 : return da.getAttr35();
		case 36 : return da.getAttr36();
		case 37 : return da.getAttr37();
		case 38 : return da.getAttr38();
		case 39 : return da.getAttr39();
		case 40 : return da.getAttr40();
		case 41 : return da.getAttr41();
		case 42 : return da.getAttr42();
		case 43 : return da.getAttr43();
		case 44 : return da.getAttr44();
		case 45 : return da.getAttr45();
		case 46 : return da.getAttr46();
		case 47 : return da.getAttr47();
		case 48 : return da.getAttr48();
		case 49 : return da.getAttr49();
		case 50 : return da.getAttr50();
		case 51 : return da.getAttr51();
		case 52 : return da.getAttr52();
		case 53 : return da.getAttr53();
		case 54 : return da.getAttr54();
		case 55 : return da.getAttr55();
		case 56 : return da.getAttr56();
		case 57 : return da.getAttr57();
		case 58 : return da.getAttr58();
		case 59 : return da.getAttr59();
		case 60 : return da.getAttr60();
		case 61 : return da.getAttr61();
		case 62 : return da.getAttr62();
		case 63 : return da.getAttr63();
		case 64 : return da.getAttr64();
		case 65 : return da.getAttr65();
		case 66 : return da.getAttr66();
		case 67 : return da.getAttr67();
		case 68 : return da.getAttr68();
		case 69 : return da.getAttr69();
		case 70 : return da.getAttr70();
		case 71 : return da.getAttr71();
		case 72 : return da.getAttr72();
		case 73 : return da.getAttr73();
		case 74 : return da.getAttr74();
		case 75 : return da.getAttr75();
		case 76 : return da.getAttr76();
		case 77 : return da.getAttr77();
		case 78 : return da.getAttr78();
		case 79 : return da.getAttr79();
		case 80 : return da.getAttr80();
		case 81 : return da.getAttr81();
		case 82 : return da.getAttr82();
		case 83 : return da.getAttr83();
		case 84 : return da.getAttr84();
		case 85 : return da.getAttr85();
		case 86 : return da.getAttr86();
		case 87 : return da.getAttr87();
		case 88 : return da.getAttr88();
		case 89 : return da.getAttr89();
		case 90 : return da.getAttr90();
		case 91 : return da.getAttr91();
		case 92 : return da.getAttr92();
		case 93 : return da.getAttr93();
		case 94 : return da.getAttr94();
		case 95 : return da.getAttr95();
		case 96 : return da.getAttr96();
		case 97 : return da.getAttr97();
		case 98 : return da.getAttr98();
		case 99 : return da.getAttr99();
		case 100: return da.getAttr100();
		case 101: return da.getAttr101();
		case 102: return da.getAttr102();
		case 103: return da.getAttr103();
		case 104: return da.getAttr104();
		case 105: return da.getAttr105();
		case 106: return da.getAttr106();
		case 107: return da.getAttr107();
		case 108: return da.getAttr108();
		case 109: return da.getAttr109();
		case 110: return da.getAttr110();
		case 111: return da.getAttr111();
		case 112: return da.getAttr112();
		case 113: return da.getAttr113();
		case 114: return da.getAttr114();
		case 115: return da.getAttr115();
		case 116: return da.getAttr116();
		case 117: return da.getAttr117();
		case 118: return da.getAttr118();
		case 119: return da.getAttr119();
		case 120: return da.getAttr120();
		case 121: return da.getAttr121();
		case 122: return da.getAttr122();
		case 123: return da.getAttr123();
		case 124: return da.getAttr124();
		case 125: return da.getAttr125();
		case 126: return da.getAttr126();
		case 127: return da.getAttr127();
		case 128: return da.getAttr128();
		case 129: return da.getAttr129();
		case 130: return da.getAttr130();
		case 131: return da.getAttr131();
		case 132: return da.getAttr132();
		case 133: return da.getAttr133();
		case 134: return da.getAttr134();
		case 135: return da.getAttr135();
		case 136: return da.getAttr136();
		case 137: return da.getAttr137();
		case 138: return da.getAttr138();
		case 139: return da.getAttr139();
		case 140: return da.getAttr140();
		case 141: return da.getAttr141();
		case 142: return da.getAttr142();
		case 143: return da.getAttr143();
		case 144: return da.getAttr144();
		case 145: return da.getAttr145();
		case 146: return da.getAttr146();
		case 147: return da.getAttr147();
		case 148: return da.getAttr148();
		case 149: return da.getAttr149();
		case 150: return da.getAttr150();
		case 151: return da.getAttr151();
		case 152: return da.getAttr152();
		case 153: return da.getAttr153();
		case 154: return da.getAttr154();
		case 155: return da.getAttr155();
		case 156: return da.getAttr156();
		case 157: return da.getAttr157();
		case 158: return da.getAttr158();
		case 159: return da.getAttr159();
		case 160: return da.getAttr160();
		case 161: return da.getAttr161();
		case 162: return da.getAttr162();
		case 163: return da.getAttr163();
		case 164: return da.getAttr164();
		case 165: return da.getAttr165();
		case 166: return da.getAttr166();
		case 167: return da.getAttr167();
		case 168: return da.getAttr168();
		case 169: return da.getAttr169();
		case 170: return da.getAttr170();
		case 171: return da.getAttr171();
		case 172: return da.getAttr172();
		case 173: return da.getAttr173();
		case 174: return da.getAttr174();
		case 175: return da.getAttr175();
		case 176: return da.getAttr176();
		case 177: return da.getAttr177();
		case 178: return da.getAttr178();
		case 179: return da.getAttr179();
		case 180: return da.getAttr180();
		case 181: return da.getAttr181();
		case 182: return da.getAttr182();
		case 183: return da.getAttr183();
		case 184: return da.getAttr184();
		case 185: return da.getAttr185();
		case 186: return da.getAttr186();
		case 187: return da.getAttr187();
		case 188: return da.getAttr188();
		case 189: return da.getAttr189();
		case 190: return da.getAttr190();
		case 191: return da.getAttr191();
		case 192: return da.getAttr192();
		case 193: return da.getAttr193();
		case 194: return da.getAttr194();
		case 195: return da.getAttr195();
		case 196: return da.getAttr196();
		case 197: return da.getAttr197();
		case 198: return da.getAttr198();
		case 199: return da.getAttr199();
		case 200: return da.getAttr200();
		case 201: return da.getAttr201();
		case 202: return da.getAttr202();
		case 203: return da.getAttr203();
		case 204: return da.getAttr204();
		case 205: return da.getAttr205();
		case 206: return da.getAttr206();
		case 207: return da.getAttr207();
		case 208: return da.getAttr208();
		case 209: return da.getAttr209();
		case 210: return da.getAttr210();
		case 211: return da.getAttr211();
		case 212: return da.getAttr212();
		case 213: return da.getAttr213();
		case 214: return da.getAttr214();
		case 215: return da.getAttr215();
		case 216: return da.getAttr216();
		case 217: return da.getAttr217();
		case 218: return da.getAttr218();
		case 219: return da.getAttr219();
		case 220: return da.getAttr220();
		case 221: return da.getAttr221();
		case 222: return da.getAttr222();
		case 223: return da.getAttr223();
		case 224: return da.getAttr224();
		case 225: return da.getAttr225();
		case 226: return da.getAttr226();
		case 227: return da.getAttr227();
		case 228: return da.getAttr228();
		case 229: return da.getAttr229();
		case 230: return da.getAttr230();
		case 231: return da.getAttr231();
		case 232: return da.getAttr232();
		case 233: return da.getAttr233();
		case 234: return da.getAttr234();
		case 235: return da.getAttr235();
		case 236: return da.getAttr236();
		case 237: return da.getAttr237();
		case 238: return da.getAttr238();
		case 239: return da.getAttr239();
		case 240: return da.getAttr240();
		case 241: return da.getAttr241();
		case 242: return da.getAttr242();
		case 243: return da.getAttr243();
		case 244: return da.getAttr244();
		case 245: return da.getAttr245();
		case 246: return da.getAttr246();
		case 247: return da.getAttr247();
		case 248: return da.getAttr248();
		case 249: return da.getAttr249();
		case 250: return da.getAttr250();
		case 251: return da.getAttr251();
		case 252: return da.getAttr252();
		case 253: return da.getAttr253();
		case 254: return da.getAttr254();
		case 255: return da.getAttr255();
		case 256: return da.getAttr256();
		case 257: return da.getAttr257();
		case 258: return da.getAttr258();
		case 259: return da.getAttr259();
		case 260: return da.getAttr260();
		case 261: return da.getAttr261();
		case 262: return da.getAttr262();
		case 263: return da.getAttr263();
		case 264: return da.getAttr264();
		case 265: return da.getAttr265();
		case 266: return da.getAttr266();
		case 267: return da.getAttr267();
		case 268: return da.getAttr268();
		case 269: return da.getAttr269();
		case 270: return da.getAttr270();
		case 271: return da.getAttr271();
		case 272: return da.getAttr272();
		case 273: return da.getAttr273();
		case 274: return da.getAttr274();
		case 275: return da.getAttr275();
		case 276: return da.getAttr276();
		case 277: return da.getAttr277();
		case 278: return da.getAttr278();
		case 279: return da.getAttr279();
		case 280: return da.getAttr280();
		case 281: return da.getAttr281();
		case 282: return da.getAttr282();
		case 283: return da.getAttr283();
		case 284: return da.getAttr284();
		case 285: return da.getAttr285();
		case 286: return da.getAttr286();
		case 287: return da.getAttr287();
		case 288: return da.getAttr288();
		case 289: return da.getAttr289();
		case 290: return da.getAttr290();
		case 291: return da.getAttr291();
		case 292: return da.getAttr292();
		case 293: return da.getAttr293();
		case 294: return da.getAttr294();
		case 295: return da.getAttr295();
		case 296: return da.getAttr296();
		case 297: return da.getAttr297();
		case 298: return da.getAttr298();
		case 299: return da.getAttr299();
		case 300: return da.getAttr300();
		case 301: return da.getAttr301();
		case 302: return da.getAttr302();
		case 303: return da.getAttr303();
		case 304: return da.getAttr304();
		case 305: return da.getAttr305();
		case 306: return da.getAttr306();
		case 307: return da.getAttr307();
		case 308: return da.getAttr308();
		case 309: return da.getAttr309();
		case 310: return da.getAttr310();
		case 311: return da.getAttr311();
		case 312: return da.getAttr312();
		case 313: return da.getAttr313();
		case 314: return da.getAttr314();
		case 315: return da.getAttr315();
		case 316: return da.getAttr316();
		case 317: return da.getAttr317();
		case 318: return da.getAttr318();
		case 319: return da.getAttr319();
		case 320: return da.getAttr320();
		case 321: return da.getAttr321();
		case 322: return da.getAttr322();
		case 323: return da.getAttr323();
		case 324: return da.getAttr324();
	
		/*case 325: return da.getAttr325();
		case 326: return da.getAttr326();
		case 327: return da.getAttr327();
		case 328: return da.getAttr328();
		case 329: return da.getAttr329();
		case 330: return da.getAttr330();
		case 331: return da.getAttr331();
		case 332: return da.getAttr332();
		case 333: return da.getAttr333();
		case 334: return da.getAttr334();
		case 335: return da.getAttr335();
		case 336: return da.getAttr336();
		case 337: return da.getAttr337();
		case 338: return da.getAttr338();
		case 339: return da.getAttr339();
		case 340: return da.getAttr340();
		case 341: return da.getAttr341();
		case 342: return da.getAttr342();
		case 343: return da.getAttr343();
		case 344: return da.getAttr344();
		case 345: return da.getAttr345();
		case 346: return da.getAttr346();
		case 347: return da.getAttr347();
		case 348: return da.getAttr348();
		case 349: return da.getAttr349();
		case 350: return da.getAttr350();*/
		default :
			return result;
		}
	}
	
	public static String getSblAttrValue(int dataRowIndex,SnapshotSiebelAssetDa da){
		String result = "NA"; 

		switch(dataRowIndex){
		case 1  : return da.getAttr1();
		case 2  : return da.getAttr2();
		case 3  : return da.getAttr3();
		case 4  : return da.getAttr4();
		case 5  : return da.getAttr5();
		case 6  : return da.getAttr6();
		case 7  : return da.getAttr7();
		case 8  : return da.getAttr8();
		case 9  : return da.getAttr9();
		case 10 : return da.getAttr10();
		case 11 : return da.getAttr11();
		case 12 : return da.getAttr12();
		case 13 : return da.getAttr13();
		case 14 : return da.getAttr14();
		case 15 : return da.getAttr15();
		case 16 : return da.getAttr16();
		case 17 : return da.getAttr17();
		case 18 : return da.getAttr18();
		case 19 : return da.getAttr19();
		case 20 : return da.getAttr20();
		case 21 : return da.getAttr21();
		case 22 : return da.getAttr22();
		case 23 : return da.getAttr23();
		case 24 : return da.getAttr24();
		case 25 : return da.getAttr25();
		case 26 : return da.getAttr26();
		case 27 : return da.getAttr27();
		case 28 : return da.getAttr28();
		case 29 : return da.getAttr29();
		case 30 : return da.getAttr30();
		case 31 : return da.getAttr31();
		case 32 : return da.getAttr32();
		case 33 : return da.getAttr33();
		case 34 : return da.getAttr34();
		case 35 : return da.getAttr35();
		case 36 : return da.getAttr36();
		case 37 : return da.getAttr37();
		case 38 : return da.getAttr38();
		case 39 : return da.getAttr39();
		case 40 : return da.getAttr40();
		case 41 : return da.getAttr41();
		case 42 : return da.getAttr42();
		case 43 : return da.getAttr43();
		case 44 : return da.getAttr44();
		case 45 : return da.getAttr45();
		case 46 : return da.getAttr46();
		case 47 : return da.getAttr47();
		case 48 : return da.getAttr48();
		case 49 : return da.getAttr49();
		case 50 : return da.getAttr50();
		case 51 : return da.getAttr51();
		case 52 : return da.getAttr52();
		case 53 : return da.getAttr53();
		case 54 : return da.getAttr54();
		case 55 : return da.getAttr55();
		case 56 : return da.getAttr56();
		case 57 : return da.getAttr57();
		case 58 : return da.getAttr58();
		case 59 : return da.getAttr59();
		case 60 : return da.getAttr60();
		case 61 : return da.getAttr61();
		case 62 : return da.getAttr62();
		case 63 : return da.getAttr63();
		case 64 : return da.getAttr64();
		case 65 : return da.getAttr65();
		case 66 : return da.getAttr66();
		case 67 : return da.getAttr67();
		case 68 : return da.getAttr68();
		case 69 : return da.getAttr69();
		case 70 : return da.getAttr70();
		case 71 : return da.getAttr71();
		case 72 : return da.getAttr72();
		case 73 : return da.getAttr73();
		case 74 : return da.getAttr74();
		case 75 : return da.getAttr75();
		case 76 : return da.getAttr76();
		case 77 : return da.getAttr77();
		case 78 : return da.getAttr78();
		case 79 : return da.getAttr79();
		case 80 : return da.getAttr80();
		case 81 : return da.getAttr81();
		case 82 : return da.getAttr82();
		case 83 : return da.getAttr83();
		case 84 : return da.getAttr84();
		case 85 : return da.getAttr85();
		case 86 : return da.getAttr86();
		case 87 : return da.getAttr87();
		case 88 : return da.getAttr88();
		case 89 : return da.getAttr89();
		case 90 : return da.getAttr90();
		case 91 : return da.getAttr91();
		case 92 : return da.getAttr92();
		case 93 : return da.getAttr93();
		case 94 : return da.getAttr94();
		case 95 : return da.getAttr95();
		case 96 : return da.getAttr96();
		case 97 : return da.getAttr97();
		case 98 : return da.getAttr98();
		case 99 : return da.getAttr99();
		case 100: return da.getAttr100();
		case 101: return da.getAttr101();
		case 102: return da.getAttr102();
		case 103: return da.getAttr103();
		case 104: return da.getAttr104();
		case 105: return da.getAttr105();
		case 106: return da.getAttr106();
		case 107: return da.getAttr107();
		case 108: return da.getAttr108();
		case 109: return da.getAttr109();
		case 110: return da.getAttr110();
		case 111: return da.getAttr111();
		case 112: return da.getAttr112();
		case 113: return da.getAttr113();
		case 114: return da.getAttr114();
		case 115: return da.getAttr115();
		case 116: return da.getAttr116();
		case 117: return da.getAttr117();
		case 118: return da.getAttr118();
		case 119: return da.getAttr119();
		case 120: return da.getAttr120();
		case 121: return da.getAttr121();
		case 122: return da.getAttr122();
		case 123: return da.getAttr123();
		case 124: return da.getAttr124();
		case 125: return da.getAttr125();
		case 126: return da.getAttr126();
		case 127: return da.getAttr127();
		case 128: return da.getAttr128();
		case 129: return da.getAttr129();
		case 130: return da.getAttr130();
		case 131: return da.getAttr131();
		case 132: return da.getAttr132();
		case 133: return da.getAttr133();
		case 134: return da.getAttr134();
		case 135: return da.getAttr135();
		case 136: return da.getAttr136();
		case 137: return da.getAttr137();
		case 138: return da.getAttr138();
		case 139: return da.getAttr139();
		case 140: return da.getAttr140();
		case 141: return da.getAttr141();
		case 142: return da.getAttr142();
		case 143: return da.getAttr143();
		case 144: return da.getAttr144();
		case 145: return da.getAttr145();
		case 146: return da.getAttr146();
		case 147: return da.getAttr147();
		case 148: return da.getAttr148();
		case 149: return da.getAttr149();
		case 150: return da.getAttr150();
		case 151: return da.getAttr151();
		case 152: return da.getAttr152();
		case 153: return da.getAttr153();
		case 154: return da.getAttr154();
		case 155: return da.getAttr155();
		case 156: return da.getAttr156();
		case 157: return da.getAttr157();
		case 158: return da.getAttr158();
		case 159: return da.getAttr159();
		case 160: return da.getAttr160();
		case 161: return da.getAttr161();
		case 162: return da.getAttr162();
		case 163: return da.getAttr163();
		case 164: return da.getAttr164();
		case 165: return da.getAttr165();
		case 166: return da.getAttr166();
		case 167: return da.getAttr167();
		case 168: return da.getAttr168();
		case 169: return da.getAttr169();
		case 170: return da.getAttr170();
		case 171: return da.getAttr171();
		case 172: return da.getAttr172();
		case 173: return da.getAttr173();
		case 174: return da.getAttr174();
		case 175: return da.getAttr175();
		case 176: return da.getAttr176();
		case 177: return da.getAttr177();
		case 178: return da.getAttr178();
		case 179: return da.getAttr179();
		case 180: return da.getAttr180();
		case 181: return da.getAttr181();
		case 182: return da.getAttr182();
		case 183: return da.getAttr183();
		case 184: return da.getAttr184();
		case 185: return da.getAttr185();
		case 186: return da.getAttr186();
		case 187: return da.getAttr187();
		case 188: return da.getAttr188();
		case 189: return da.getAttr189();
		case 190: return da.getAttr190();
		case 191: return da.getAttr191();
		case 192: return da.getAttr192();
		case 193: return da.getAttr193();
		case 194: return da.getAttr194();
		case 195: return da.getAttr195();
		case 196: return da.getAttr196();
		case 197: return da.getAttr197();
		case 198: return da.getAttr198();
		case 199: return da.getAttr199();
		case 200: return da.getAttr200();
		case 201: return da.getAttr201();
		case 202: return da.getAttr202();
		case 203: return da.getAttr203();
		case 204: return da.getAttr204();
		case 205: return da.getAttr205();
		case 206: return da.getAttr206();
		case 207: return da.getAttr207();
		case 208: return da.getAttr208();
		case 209: return da.getAttr209();
		case 210: return da.getAttr210();
		case 211: return da.getAttr211();
		case 212: return da.getAttr212();
		case 213: return da.getAttr213();
		case 214: return da.getAttr214();
		case 215: return da.getAttr215();
		case 216: return da.getAttr216();
		case 217: return da.getAttr217();
		case 218: return da.getAttr218();
		case 219: return da.getAttr219();
		case 220: return da.getAttr220();
		case 221: return da.getAttr221();
		case 222: return da.getAttr222();
		case 223: return da.getAttr223();
		case 224: return da.getAttr224();
		case 225: return da.getAttr225();
		case 226: return da.getAttr226();
		case 227: return da.getAttr227();
		case 228: return da.getAttr228();
		case 229: return da.getAttr229();
		case 230: return da.getAttr230();
		case 231: return da.getAttr231();
		case 232: return da.getAttr232();
		case 233: return da.getAttr233();
		case 234: return da.getAttr234();
		case 235: return da.getAttr235();
		case 236: return da.getAttr236();
		case 237: return da.getAttr237();
		case 238: return da.getAttr238();
		case 239: return da.getAttr239();
		case 240: return da.getAttr240();
		case 241: return da.getAttr241();
		case 242: return da.getAttr242();
		case 243: return da.getAttr243();
		case 244: return da.getAttr244();
		case 245: return da.getAttr245();
		case 246: return da.getAttr246();
		case 247: return da.getAttr247();
		case 248: return da.getAttr248();
		case 249: return da.getAttr249();
		case 250: return da.getAttr250();
		case 251: return da.getAttr251();
		case 252: return da.getAttr252();
		case 253: return da.getAttr253();
		case 254: return da.getAttr254();
		case 255: return da.getAttr255();
		case 256: return da.getAttr256();
		case 257: return da.getAttr257();
		case 258: return da.getAttr258();
		case 259: return da.getAttr259();
		case 260: return da.getAttr260();
		case 261: return da.getAttr261();
		case 262: return da.getAttr262();
		case 263: return da.getAttr263();
		case 264: return da.getAttr264();
		case 265: return da.getAttr265();
		case 266: return da.getAttr266();
		case 267: return da.getAttr267();
		case 268: return da.getAttr268();
		case 269: return da.getAttr269();
		case 270: return da.getAttr270();
		case 271: return da.getAttr271();
		case 272: return da.getAttr272();
		case 273: return da.getAttr273();
		case 274: return da.getAttr274();
		case 275: return da.getAttr275();
		case 276: return da.getAttr276();
		case 277: return da.getAttr277();
		case 278: return da.getAttr278();
		case 279: return da.getAttr279();
		case 280: return da.getAttr280();
		case 281: return da.getAttr281();
		case 282: return da.getAttr282();
		case 283: return da.getAttr283();
		case 284: return da.getAttr284();
		case 285: return da.getAttr285();
		case 286: return da.getAttr286();
		case 287: return da.getAttr287();
		case 288: return da.getAttr288();
		case 289: return da.getAttr289();
		case 290: return da.getAttr290();
		case 291: return da.getAttr291();
		case 292: return da.getAttr292();
		case 293: return da.getAttr293();
		case 294: return da.getAttr294();
		case 295: return da.getAttr295();
		case 296: return da.getAttr296();
		case 297: return da.getAttr297();
		case 298: return da.getAttr298();
		case 299: return da.getAttr299();
		case 300: return da.getAttr300();
		case 301: return da.getAttr301();
		case 302: return da.getAttr302();
		case 303: return da.getAttr303();
		case 304: return da.getAttr304();
		case 305: return da.getAttr305();
		case 306: return da.getAttr306();
		case 307: return da.getAttr307();
		case 308: return da.getAttr308();
		case 309: return da.getAttr309();
		case 310: return da.getAttr310();
		case 311: return da.getAttr311();
		case 312: return da.getAttr312();
		case 313: return da.getAttr313();
		case 314: return da.getAttr314();
		case 315: return da.getAttr315();
		case 316: return da.getAttr316();
		case 317: return da.getAttr317();
		case 318: return da.getAttr318();
		case 319: return da.getAttr319();
		case 320: return da.getAttr320();
		case 321: return da.getAttr321();
		case 322: return da.getAttr322();
		case 323: return da.getAttr323();
		case 324: return da.getAttr324();
	/*	case 325: return da.getAttr325();
		case 326: return da.getAttr326();
		case 327: return da.getAttr327();
		case 328: return da.getAttr328();
		case 329: return da.getAttr329();
		case 330: return da.getAttr330();
		case 331: return da.getAttr331();
		case 332: return da.getAttr332();
		case 333: return da.getAttr333();
		case 334: return da.getAttr334();
		case 335: return da.getAttr335();
		case 336: return da.getAttr336();
		case 337: return da.getAttr337();
		case 338: return da.getAttr338();
		case 339: return da.getAttr339();
		case 340: return da.getAttr340();
		case 341: return da.getAttr341();
		case 342: return da.getAttr342();
		case 343: return da.getAttr343();
		case 344: return da.getAttr344();
		case 345: return da.getAttr345();
		case 346: return da.getAttr346();
		case 347: return da.getAttr347();
		case 348: return da.getAttr348();
		case 349: return da.getAttr349();
		case 350: return da.getAttr350();*/
		default :
			return result;
		}
	}
	public static String getSvAttrValue(int dataRowIndex,SvAssetDa da){
		String result = "NA"; 

		switch(dataRowIndex){
		case 1  : return da.getAttr1();
		case 2  : return da.getAttr2();
		case 3  : return da.getAttr3();
		case 4  : return da.getAttr4();
		case 5  : return da.getAttr5();
		case 6  : return da.getAttr6();
		case 7  : return da.getAttr7();
		case 8  : return da.getAttr8();
		case 9  : return da.getAttr9();
		case 10 : return da.getAttr10();
		case 11 : return da.getAttr11();
		case 12 : return da.getAttr12();
		case 13 : return da.getAttr13();
		case 14 : return da.getAttr14();
		case 15 : return da.getAttr15();
		case 16 : return da.getAttr16();
		case 17 : return da.getAttr17();
		case 18 : return da.getAttr18();
		case 19 : return da.getAttr19();
		case 20 : return da.getAttr20();
		case 21 : return da.getAttr21();
		case 22 : return da.getAttr22();
		case 23 : return da.getAttr23();
		case 24 : return da.getAttr24();
		case 25 : return da.getAttr25();
		case 26 : return da.getAttr26();
		case 27 : return da.getAttr27();
		case 28 : return da.getAttr28();
		case 29 : return da.getAttr29();
		case 30 : return da.getAttr30();
		case 31 : return da.getAttr31();
		case 32 : return da.getAttr32();
		case 33 : return da.getAttr33();
		case 34 : return da.getAttr34();
		case 35 : return da.getAttr35();
		case 36 : return da.getAttr36();
		case 37 : return da.getAttr37();
		case 38 : return da.getAttr38();
		case 39 : return da.getAttr39();
		case 40 : return da.getAttr40();
		case 41 : return da.getAttr41();
		case 42 : return da.getAttr42();
		case 43 : return da.getAttr43();
		case 44 : return da.getAttr44();
		case 45 : return da.getAttr45();
		case 46 : return da.getAttr46();
		case 47 : return da.getAttr47();
		case 48 : return da.getAttr48();
		case 49 : return da.getAttr49();
		case 50 : return da.getAttr50();
		case 51 : return da.getAttr51();
		case 52 : return da.getAttr52();
		case 53 : return da.getAttr53();
		case 54 : return da.getAttr54();
		case 55 : return da.getAttr55();
		case 56 : return da.getAttr56();
		case 57 : return da.getAttr57();
		case 58 : return da.getAttr58();
		case 59 : return da.getAttr59();
		case 60 : return da.getAttr60();
		case 61 : return da.getAttr61();
		case 62 : return da.getAttr62();
		case 63 : return da.getAttr63();
		case 64 : return da.getAttr64();
		case 65 : return da.getAttr65();
		case 66 : return da.getAttr66();
		case 67 : return da.getAttr67();
		case 68 : return da.getAttr68();
		case 69 : return da.getAttr69();
		case 70 : return da.getAttr70();
		case 71 : return da.getAttr71();
		case 72 : return da.getAttr72();
		case 73 : return da.getAttr73();
		case 74 : return da.getAttr74();
		case 75 : return da.getAttr75();
		case 76 : return da.getAttr76();
		case 77 : return da.getAttr77();
		case 78 : return da.getAttr78();
		case 79 : return da.getAttr79();
		case 80 : return da.getAttr80();
		case 81 : return da.getAttr81();
		case 82 : return da.getAttr82();
		case 83 : return da.getAttr83();
		case 84 : return da.getAttr84();
		case 85 : return da.getAttr85();
		case 86 : return da.getAttr86();
		case 87 : return da.getAttr87();
		case 88 : return da.getAttr88();
		case 89 : return da.getAttr89();
		case 90 : return da.getAttr90();
		case 91 : return da.getAttr91();
		case 92 : return da.getAttr92();
		case 93 : return da.getAttr93();
		case 94 : return da.getAttr94();
		case 95 : return da.getAttr95();
		case 96 : return da.getAttr96();
		case 97 : return da.getAttr97();
		case 98 : return da.getAttr98();
		case 99 : return da.getAttr99();
		case 100: return da.getAttr100();
		case 101: return da.getAttr101();
		case 102: return da.getAttr102();
		case 103: return da.getAttr103();
		case 104: return da.getAttr104();
		case 105: return da.getAttr105();
		case 106: return da.getAttr106();
		case 107: return da.getAttr107();
		case 108: return da.getAttr108();
		case 109: return da.getAttr109();
		case 110: return da.getAttr110();
		case 111: return da.getAttr111();
		case 112: return da.getAttr112();
		case 113: return da.getAttr113();
		case 114: return da.getAttr114();
		case 115: return da.getAttr115();
		case 116: return da.getAttr116();
		case 117: return da.getAttr117();
		case 118: return da.getAttr118();
		case 119: return da.getAttr119();
		case 120: return da.getAttr120();
		case 121: return da.getAttr121();
		case 122: return da.getAttr122();
		case 123: return da.getAttr123();
		case 124: return da.getAttr124();
		case 125: return da.getAttr125();
		case 126: return da.getAttr126();
		case 127: return da.getAttr127();
		case 128: return da.getAttr128();
		case 129: return da.getAttr129();
		case 130: return da.getAttr130();
		case 131: return da.getAttr131();
		case 132: return da.getAttr132();
		case 133: return da.getAttr133();
		case 134: return da.getAttr134();
		case 135: return da.getAttr135();
		case 136: return da.getAttr136();
		case 137: return da.getAttr137();
		case 138: return da.getAttr138();
		case 139: return da.getAttr139();
		case 140: return da.getAttr140();
		case 141: return da.getAttr141();
		case 142: return da.getAttr142();
		case 143: return da.getAttr143();
		case 144: return da.getAttr144();
		case 145: return da.getAttr145();
		case 146: return da.getAttr146();
		case 147: return da.getAttr147();
		case 148: return da.getAttr148();
		case 149: return da.getAttr149();
		case 150: return da.getAttr150();
		case 151: return da.getAttr151();
		case 152: return da.getAttr152();
		case 153: return da.getAttr153();
		case 154: return da.getAttr154();
		case 155: return da.getAttr155();
		case 156: return da.getAttr156();
		case 157: return da.getAttr157();
		case 158: return da.getAttr158();
		case 159: return da.getAttr159();
		case 160: return da.getAttr160();
		case 161: return da.getAttr161();
		case 162: return da.getAttr162();
		case 163: return da.getAttr163();
		case 164: return da.getAttr164();
		case 165: return da.getAttr165();
		case 166: return da.getAttr166();
		case 167: return da.getAttr167();
		case 168: return da.getAttr168();
		case 169: return da.getAttr169();
		case 170: return da.getAttr170();
		case 171: return da.getAttr171();
		case 172: return da.getAttr172();
		case 173: return da.getAttr173();
		case 174: return da.getAttr174();
		case 175: return da.getAttr175();
		case 176: return da.getAttr176();
		case 177: return da.getAttr177();
		case 178: return da.getAttr178();
		case 179: return da.getAttr179();
		case 180: return da.getAttr180();
		case 181: return da.getAttr181();
		case 182: return da.getAttr182();
		case 183: return da.getAttr183();
		case 184: return da.getAttr184();
		case 185: return da.getAttr185();
		case 186: return da.getAttr186();
		case 187: return da.getAttr187();
		case 188: return da.getAttr188();
		case 189: return da.getAttr189();
		case 190: return da.getAttr190();
		case 191: return da.getAttr191();
		case 192: return da.getAttr192();
		case 193: return da.getAttr193();
		case 194: return da.getAttr194();
		case 195: return da.getAttr195();
		case 196: return da.getAttr196();
		case 197: return da.getAttr197();
		case 198: return da.getAttr198();
		case 199: return da.getAttr199();
		case 200: return da.getAttr200();
		case 201: return da.getAttr201();
		case 202: return da.getAttr202();
		case 203: return da.getAttr203();
		case 204: return da.getAttr204();
		case 205: return da.getAttr205();
		case 206: return da.getAttr206();
		case 207: return da.getAttr207();
		case 208: return da.getAttr208();
		case 209: return da.getAttr209();
		case 210: return da.getAttr210();
		case 211: return da.getAttr211();
		case 212: return da.getAttr212();
		case 213: return da.getAttr213();
		case 214: return da.getAttr214();
		case 215: return da.getAttr215();
		case 216: return da.getAttr216();
		case 217: return da.getAttr217();
		case 218: return da.getAttr218();
		case 219: return da.getAttr219();
		case 220: return da.getAttr220();
		case 221: return da.getAttr221();
		case 222: return da.getAttr222();
		case 223: return da.getAttr223();
		case 224: return da.getAttr224();
		case 225: return da.getAttr225();
		case 226: return da.getAttr226();
		case 227: return da.getAttr227();
		case 228: return da.getAttr228();
		case 229: return da.getAttr229();
		case 230: return da.getAttr230();
		case 231: return da.getAttr231();
		case 232: return da.getAttr232();
		case 233: return da.getAttr233();
		case 234: return da.getAttr234();
		case 235: return da.getAttr235();
		case 236: return da.getAttr236();
		case 237: return da.getAttr237();
		case 238: return da.getAttr238();
		case 239: return da.getAttr239();
		case 240: return da.getAttr240();
		case 241: return da.getAttr241();
		case 242: return da.getAttr242();
		case 243: return da.getAttr243();
		case 244: return da.getAttr244();
		case 245: return da.getAttr245();
		case 246: return da.getAttr246();
		case 247: return da.getAttr247();
		case 248: return da.getAttr248();
		case 249: return da.getAttr249();
		case 250: return da.getAttr250();
		case 251: return da.getAttr251();
		case 252: return da.getAttr252();
		case 253: return da.getAttr253();
		case 254: return da.getAttr254();
		case 255: return da.getAttr255();
		case 256: return da.getAttr256();
		case 257: return da.getAttr257();
		case 258: return da.getAttr258();
		case 259: return da.getAttr259();
		case 260: return da.getAttr260();
		case 261: return da.getAttr261();
		case 262: return da.getAttr262();
		case 263: return da.getAttr263();
		case 264: return da.getAttr264();
		case 265: return da.getAttr265();
		case 266: return da.getAttr266();
		case 267: return da.getAttr267();
		case 268: return da.getAttr268();
		case 269: return da.getAttr269();
		case 270: return da.getAttr270();
		case 271: return da.getAttr271();
		case 272: return da.getAttr272();
		case 273: return da.getAttr273();
		case 274: return da.getAttr274();
		case 275: return da.getAttr275();
		case 276: return da.getAttr276();
		case 277: return da.getAttr277();
		case 278: return da.getAttr278();
		case 279: return da.getAttr279();
		case 280: return da.getAttr280();
		case 281: return da.getAttr281();
		case 282: return da.getAttr282();
		case 283: return da.getAttr283();
		case 284: return da.getAttr284();
		case 285: return da.getAttr285();
		case 286: return da.getAttr286();
		case 287: return da.getAttr287();
		case 288: return da.getAttr288();
		case 289: return da.getAttr289();
		case 290: return da.getAttr290();
		case 291: return da.getAttr291();
		case 292: return da.getAttr292();
		case 293: return da.getAttr293();
		case 294: return da.getAttr294();
		case 295: return da.getAttr295();
		case 296: return da.getAttr296();
		case 297: return da.getAttr297();
		case 298: return da.getAttr298();
		case 299: return da.getAttr299();
		case 300: return da.getAttr300();
		case 301: return da.getAttr301();
		case 302: return da.getAttr302();
		case 303: return da.getAttr303();
		case 304: return da.getAttr304();
		case 305: return da.getAttr305();
		case 306: return da.getAttr306();
		case 307: return da.getAttr307();
		case 308: return da.getAttr308();
		case 309: return da.getAttr309();
		case 310: return da.getAttr310();
		case 311: return da.getAttr311();
		case 312: return da.getAttr312();
		case 313: return da.getAttr313();
		case 314: return da.getAttr314();
		case 315: return da.getAttr315();
		case 316: return da.getAttr316();
		case 317: return da.getAttr317();
		case 318: return da.getAttr318();
		case 319: return da.getAttr319();
		case 320: return da.getAttr320();
		case 321: return da.getAttr321();
		case 322: return da.getAttr322();
		case 323: return da.getAttr323();
		case 324: return da.getAttr324();
	/*	case 325: return da.getAttr325();
		case 326: return da.getAttr326();
		case 327: return da.getAttr327();
		case 328: return da.getAttr328();
		case 329: return da.getAttr329();
		case 330: return da.getAttr330();
		case 331: return da.getAttr331();
		case 332: return da.getAttr332();
		case 333: return da.getAttr333();
		case 334: return da.getAttr334();
		case 335: return da.getAttr335();
		case 336: return da.getAttr336();
		case 337: return da.getAttr337();
		case 338: return da.getAttr338();
		case 339: return da.getAttr339();
		case 340: return da.getAttr340();
		case 341: return da.getAttr341();
		case 342: return da.getAttr342();
		case 343: return da.getAttr343();
		case 344: return da.getAttr344();
		case 345: return da.getAttr345();
		case 346: return da.getAttr346();
		case 347: return da.getAttr347();
		case 348: return da.getAttr348();
		case 349: return da.getAttr349();
		case 350: return da.getAttr350();*/
		default :
			return result;
		}
	}

	public static String getSvAttrValue(int dataRowIndex,SnapshotSvAssetDa da){
		String result = "NA"; 

		switch(dataRowIndex){
		case 1  : return da.getAttr1();
		case 2  : return da.getAttr2();
		case 3  : return da.getAttr3();
		case 4  : return da.getAttr4();
		case 5  : return da.getAttr5();
		case 6  : return da.getAttr6();
		case 7  : return da.getAttr7();
		case 8  : return da.getAttr8();
		case 9  : return da.getAttr9();
		case 10 : return da.getAttr10();
		case 11 : return da.getAttr11();
		case 12 : return da.getAttr12();
		case 13 : return da.getAttr13();
		case 14 : return da.getAttr14();
		case 15 : return da.getAttr15();
		case 16 : return da.getAttr16();
		case 17 : return da.getAttr17();
		case 18 : return da.getAttr18();
		case 19 : return da.getAttr19();
		case 20 : return da.getAttr20();
		case 21 : return da.getAttr21();
		case 22 : return da.getAttr22();
		case 23 : return da.getAttr23();
		case 24 : return da.getAttr24();
		case 25 : return da.getAttr25();
		case 26 : return da.getAttr26();
		case 27 : return da.getAttr27();
		case 28 : return da.getAttr28();
		case 29 : return da.getAttr29();
		case 30 : return da.getAttr30();
		case 31 : return da.getAttr31();
		case 32 : return da.getAttr32();
		case 33 : return da.getAttr33();
		case 34 : return da.getAttr34();
		case 35 : return da.getAttr35();
		case 36 : return da.getAttr36();
		case 37 : return da.getAttr37();
		case 38 : return da.getAttr38();
		case 39 : return da.getAttr39();
		case 40 : return da.getAttr40();
		case 41 : return da.getAttr41();
		case 42 : return da.getAttr42();
		case 43 : return da.getAttr43();
		case 44 : return da.getAttr44();
		case 45 : return da.getAttr45();
		case 46 : return da.getAttr46();
		case 47 : return da.getAttr47();
		case 48 : return da.getAttr48();
		case 49 : return da.getAttr49();
		case 50 : return da.getAttr50();
		case 51 : return da.getAttr51();
		case 52 : return da.getAttr52();
		case 53 : return da.getAttr53();
		case 54 : return da.getAttr54();
		case 55 : return da.getAttr55();
		case 56 : return da.getAttr56();
		case 57 : return da.getAttr57();
		case 58 : return da.getAttr58();
		case 59 : return da.getAttr59();
		case 60 : return da.getAttr60();
		case 61 : return da.getAttr61();
		case 62 : return da.getAttr62();
		case 63 : return da.getAttr63();
		case 64 : return da.getAttr64();
		case 65 : return da.getAttr65();
		case 66 : return da.getAttr66();
		case 67 : return da.getAttr67();
		case 68 : return da.getAttr68();
		case 69 : return da.getAttr69();
		case 70 : return da.getAttr70();
		case 71 : return da.getAttr71();
		case 72 : return da.getAttr72();
		case 73 : return da.getAttr73();
		case 74 : return da.getAttr74();
		case 75 : return da.getAttr75();
		case 76 : return da.getAttr76();
		case 77 : return da.getAttr77();
		case 78 : return da.getAttr78();
		case 79 : return da.getAttr79();
		case 80 : return da.getAttr80();
		case 81 : return da.getAttr81();
		case 82 : return da.getAttr82();
		case 83 : return da.getAttr83();
		case 84 : return da.getAttr84();
		case 85 : return da.getAttr85();
		case 86 : return da.getAttr86();
		case 87 : return da.getAttr87();
		case 88 : return da.getAttr88();
		case 89 : return da.getAttr89();
		case 90 : return da.getAttr90();
		case 91 : return da.getAttr91();
		case 92 : return da.getAttr92();
		case 93 : return da.getAttr93();
		case 94 : return da.getAttr94();
		case 95 : return da.getAttr95();
		case 96 : return da.getAttr96();
		case 97 : return da.getAttr97();
		case 98 : return da.getAttr98();
		case 99 : return da.getAttr99();
		case 100: return da.getAttr100();
		case 101: return da.getAttr101();
		case 102: return da.getAttr102();
		case 103: return da.getAttr103();
		case 104: return da.getAttr104();
		case 105: return da.getAttr105();
		case 106: return da.getAttr106();
		case 107: return da.getAttr107();
		case 108: return da.getAttr108();
		case 109: return da.getAttr109();
		case 110: return da.getAttr110();
		case 111: return da.getAttr111();
		case 112: return da.getAttr112();
		case 113: return da.getAttr113();
		case 114: return da.getAttr114();
		case 115: return da.getAttr115();
		case 116: return da.getAttr116();
		case 117: return da.getAttr117();
		case 118: return da.getAttr118();
		case 119: return da.getAttr119();
		case 120: return da.getAttr120();
		case 121: return da.getAttr121();
		case 122: return da.getAttr122();
		case 123: return da.getAttr123();
		case 124: return da.getAttr124();
		case 125: return da.getAttr125();
		case 126: return da.getAttr126();
		case 127: return da.getAttr127();
		case 128: return da.getAttr128();
		case 129: return da.getAttr129();
		case 130: return da.getAttr130();
		case 131: return da.getAttr131();
		case 132: return da.getAttr132();
		case 133: return da.getAttr133();
		case 134: return da.getAttr134();
		case 135: return da.getAttr135();
		case 136: return da.getAttr136();
		case 137: return da.getAttr137();
		case 138: return da.getAttr138();
		case 139: return da.getAttr139();
		case 140: return da.getAttr140();
		case 141: return da.getAttr141();
		case 142: return da.getAttr142();
		case 143: return da.getAttr143();
		case 144: return da.getAttr144();
		case 145: return da.getAttr145();
		case 146: return da.getAttr146();
		case 147: return da.getAttr147();
		case 148: return da.getAttr148();
		case 149: return da.getAttr149();
		case 150: return da.getAttr150();
		case 151: return da.getAttr151();
		case 152: return da.getAttr152();
		case 153: return da.getAttr153();
		case 154: return da.getAttr154();
		case 155: return da.getAttr155();
		case 156: return da.getAttr156();
		case 157: return da.getAttr157();
		case 158: return da.getAttr158();
		case 159: return da.getAttr159();
		case 160: return da.getAttr160();
		case 161: return da.getAttr161();
		case 162: return da.getAttr162();
		case 163: return da.getAttr163();
		case 164: return da.getAttr164();
		case 165: return da.getAttr165();
		case 166: return da.getAttr166();
		case 167: return da.getAttr167();
		case 168: return da.getAttr168();
		case 169: return da.getAttr169();
		case 170: return da.getAttr170();
		case 171: return da.getAttr171();
		case 172: return da.getAttr172();
		case 173: return da.getAttr173();
		case 174: return da.getAttr174();
		case 175: return da.getAttr175();
		case 176: return da.getAttr176();
		case 177: return da.getAttr177();
		case 178: return da.getAttr178();
		case 179: return da.getAttr179();
		case 180: return da.getAttr180();
		case 181: return da.getAttr181();
		case 182: return da.getAttr182();
		case 183: return da.getAttr183();
		case 184: return da.getAttr184();
		case 185: return da.getAttr185();
		case 186: return da.getAttr186();
		case 187: return da.getAttr187();
		case 188: return da.getAttr188();
		case 189: return da.getAttr189();
		case 190: return da.getAttr190();
		case 191: return da.getAttr191();
		case 192: return da.getAttr192();
		case 193: return da.getAttr193();
		case 194: return da.getAttr194();
		case 195: return da.getAttr195();
		case 196: return da.getAttr196();
		case 197: return da.getAttr197();
		case 198: return da.getAttr198();
		case 199: return da.getAttr199();
		case 200: return da.getAttr200();
		case 201: return da.getAttr201();
		case 202: return da.getAttr202();
		case 203: return da.getAttr203();
		case 204: return da.getAttr204();
		case 205: return da.getAttr205();
		case 206: return da.getAttr206();
		case 207: return da.getAttr207();
		case 208: return da.getAttr208();
		case 209: return da.getAttr209();
		case 210: return da.getAttr210();
		case 211: return da.getAttr211();
		case 212: return da.getAttr212();
		case 213: return da.getAttr213();
		case 214: return da.getAttr214();
		case 215: return da.getAttr215();
		case 216: return da.getAttr216();
		case 217: return da.getAttr217();
		case 218: return da.getAttr218();
		case 219: return da.getAttr219();
		case 220: return da.getAttr220();
		case 221: return da.getAttr221();
		case 222: return da.getAttr222();
		case 223: return da.getAttr223();
		case 224: return da.getAttr224();
		case 225: return da.getAttr225();
		case 226: return da.getAttr226();
		case 227: return da.getAttr227();
		case 228: return da.getAttr228();
		case 229: return da.getAttr229();
		case 230: return da.getAttr230();
		case 231: return da.getAttr231();
		case 232: return da.getAttr232();
		case 233: return da.getAttr233();
		case 234: return da.getAttr234();
		case 235: return da.getAttr235();
		case 236: return da.getAttr236();
		case 237: return da.getAttr237();
		case 238: return da.getAttr238();
		case 239: return da.getAttr239();
		case 240: return da.getAttr240();
		case 241: return da.getAttr241();
		case 242: return da.getAttr242();
		case 243: return da.getAttr243();
		case 244: return da.getAttr244();
		case 245: return da.getAttr245();
		case 246: return da.getAttr246();
		case 247: return da.getAttr247();
		case 248: return da.getAttr248();
		case 249: return da.getAttr249();
		case 250: return da.getAttr250();
		case 251: return da.getAttr251();
		case 252: return da.getAttr252();
		case 253: return da.getAttr253();
		case 254: return da.getAttr254();
		case 255: return da.getAttr255();
		case 256: return da.getAttr256();
		case 257: return da.getAttr257();
		case 258: return da.getAttr258();
		case 259: return da.getAttr259();
		case 260: return da.getAttr260();
		case 261: return da.getAttr261();
		case 262: return da.getAttr262();
		case 263: return da.getAttr263();
		case 264: return da.getAttr264();
		case 265: return da.getAttr265();
		case 266: return da.getAttr266();
		case 267: return da.getAttr267();
		case 268: return da.getAttr268();
		case 269: return da.getAttr269();
		case 270: return da.getAttr270();
		case 271: return da.getAttr271();
		case 272: return da.getAttr272();
		case 273: return da.getAttr273();
		case 274: return da.getAttr274();
		case 275: return da.getAttr275();
		case 276: return da.getAttr276();
		case 277: return da.getAttr277();
		case 278: return da.getAttr278();
		case 279: return da.getAttr279();
		case 280: return da.getAttr280();
		case 281: return da.getAttr281();
		case 282: return da.getAttr282();
		case 283: return da.getAttr283();
		case 284: return da.getAttr284();
		case 285: return da.getAttr285();
		case 286: return da.getAttr286();
		case 287: return da.getAttr287();
		case 288: return da.getAttr288();
		case 289: return da.getAttr289();
		case 290: return da.getAttr290();
		case 291: return da.getAttr291();
		case 292: return da.getAttr292();
		case 293: return da.getAttr293();
		case 294: return da.getAttr294();
		case 295: return da.getAttr295();
		case 296: return da.getAttr296();
		case 297: return da.getAttr297();
		case 298: return da.getAttr298();
		case 299: return da.getAttr299();
		case 300: return da.getAttr300();
		case 301: return da.getAttr301();
		case 302: return da.getAttr302();
		case 303: return da.getAttr303();
		case 304: return da.getAttr304();
		case 305: return da.getAttr305();
		case 306: return da.getAttr306();
		case 307: return da.getAttr307();
		case 308: return da.getAttr308();
		case 309: return da.getAttr309();
		case 310: return da.getAttr310();
		case 311: return da.getAttr311();
		case 312: return da.getAttr312();
		case 313: return da.getAttr313();
		case 314: return da.getAttr314();
		case 315: return da.getAttr315();
		case 316: return da.getAttr316();
		case 317: return da.getAttr317();
		case 318: return da.getAttr318();
		case 319: return da.getAttr319();
		case 320: return da.getAttr320();
		case 321: return da.getAttr321();
		case 322: return da.getAttr322();
		case 323: return da.getAttr323();
		case 324: return da.getAttr324();
		/*case 325: return da.getAttr325();
		case 326: return da.getAttr326();
		case 327: return da.getAttr327();
		case 328: return da.getAttr328();
		case 329: return da.getAttr329();
		case 330: return da.getAttr330();
		case 331: return da.getAttr331();
		case 332: return da.getAttr332();
		case 333: return da.getAttr333();
		case 334: return da.getAttr334();
		case 335: return da.getAttr335();
		case 336: return da.getAttr336();
		case 337: return da.getAttr337();
		case 338: return da.getAttr338();
		case 339: return da.getAttr339();
		case 340: return da.getAttr340();
		case 341: return da.getAttr341();
		case 342: return da.getAttr342();
		case 343: return da.getAttr343();
		case 344: return da.getAttr344();
		case 345: return da.getAttr345();
		case 346: return da.getAttr346();
		case 347: return da.getAttr347();
		case 348: return da.getAttr348();
		case 349: return da.getAttr349();
		case 350: return da.getAttr350();*/
		default :
			return result;
		}
	}
	public static String getClxAttrValue(int dataRowIndex,ClxAssetDa da){
		String result = "NA"; 

		switch(dataRowIndex){
		case 1  : return da.getAttr1();
		case 2  : return da.getAttr2();
		case 3  : return da.getAttr3();
		case 4  : return da.getAttr4();
		case 5  : return da.getAttr5();
		case 6  : return da.getAttr6();
		case 7  : return da.getAttr7();
		case 8  : return da.getAttr8();
		case 9  : return da.getAttr9();
		case 10 : return da.getAttr10();
		case 11 : return da.getAttr11();
		case 12 : return da.getAttr12();
		case 13 : return da.getAttr13();
		case 14 : return da.getAttr14();
		case 15 : return da.getAttr15();
		case 16 : return da.getAttr16();
		case 17 : return da.getAttr17();
		case 18 : return da.getAttr18();
		case 19 : return da.getAttr19();
		case 20 : return da.getAttr20();
		case 21 : return da.getAttr21();
		case 22 : return da.getAttr22();
		case 23 : return da.getAttr23();
		case 24 : return da.getAttr24();
		case 25 : return da.getAttr25();
		case 26 : return da.getAttr26();
		case 27 : return da.getAttr27();
		case 28 : return da.getAttr28();
		case 29 : return da.getAttr29();
		case 30 : return da.getAttr30();
		case 31 : return da.getAttr31();
		case 32 : return da.getAttr32();
		case 33 : return da.getAttr33();
		case 34 : return da.getAttr34();
		case 35 : return da.getAttr35();
		case 36 : return da.getAttr36();
		case 37 : return da.getAttr37();
		case 38 : return da.getAttr38();
		case 39 : return da.getAttr39();
		case 40 : return da.getAttr40();
		case 41 : return da.getAttr41();
		case 42 : return da.getAttr42();
		case 43 : return da.getAttr43();
		case 44 : return da.getAttr44();
		case 45 : return da.getAttr45();
		case 46 : return da.getAttr46();
		case 47 : return da.getAttr47();
		case 48 : return da.getAttr48();
		case 49 : return da.getAttr49();
		case 50 : return da.getAttr50();
		case 51 : return da.getAttr51();
		case 52 : return da.getAttr52();
		case 53 : return da.getAttr53();
		case 54 : return da.getAttr54();
		case 55 : return da.getAttr55();
		case 56 : return da.getAttr56();
		case 57 : return da.getAttr57();
		case 58 : return da.getAttr58();
		case 59 : return da.getAttr59();
		case 60 : return da.getAttr60();
		case 61 : return da.getAttr61();
		case 62 : return da.getAttr62();
		case 63 : return da.getAttr63();
		case 64 : return da.getAttr64();
		case 65 : return da.getAttr65();
		case 66 : return da.getAttr66();
		case 67 : return da.getAttr67();
		case 68 : return da.getAttr68();
		case 69 : return da.getAttr69();
		case 70 : return da.getAttr70();
		case 71 : return da.getAttr71();
		case 72 : return da.getAttr72();
		case 73 : return da.getAttr73();
		case 74 : return da.getAttr74();
		case 75 : return da.getAttr75();
		case 76 : return da.getAttr76();
		case 77 : return da.getAttr77();
		case 78 : return da.getAttr78();
		case 79 : return da.getAttr79();
		case 80 : return da.getAttr80();
		case 81 : return da.getAttr81();
		case 82 : return da.getAttr82();
		case 83 : return da.getAttr83();
		case 84 : return da.getAttr84();
		case 85 : return da.getAttr85();
		case 86 : return da.getAttr86();
		case 87 : return da.getAttr87();
		case 88 : return da.getAttr88();
		case 89 : return da.getAttr89();
		case 90 : return da.getAttr90();
		case 91 : return da.getAttr91();
		case 92 : return da.getAttr92();
		case 93 : return da.getAttr93();
		case 94 : return da.getAttr94();
		case 95 : return da.getAttr95();
		case 96 : return da.getAttr96();
		case 97 : return da.getAttr97();
		case 98 : return da.getAttr98();
		case 99 : return da.getAttr99();
		case 100: return da.getAttr100();
		case 101: return da.getAttr101();
		case 102: return da.getAttr102();
		case 103: return da.getAttr103();
		case 104: return da.getAttr104();
		case 105: return da.getAttr105();
		case 106: return da.getAttr106();
		case 107: return da.getAttr107();
		case 108: return da.getAttr108();
		case 109: return da.getAttr109();
		case 110: return da.getAttr110();
		case 111: return da.getAttr111();
		case 112: return da.getAttr112();
		case 113: return da.getAttr113();
		case 114: return da.getAttr114();
		case 115: return da.getAttr115();
		case 116: return da.getAttr116();
		case 117: return da.getAttr117();
		case 118: return da.getAttr118();
		case 119: return da.getAttr119();
		case 120: return da.getAttr120();
		case 121: return da.getAttr121();
		case 122: return da.getAttr122();
		case 123: return da.getAttr123();
		case 124: return da.getAttr124();
		case 125: return da.getAttr125();
		case 126: return da.getAttr126();
		case 127: return da.getAttr127();
		case 128: return da.getAttr128();
		case 129: return da.getAttr129();
		case 130: return da.getAttr130();
		case 131: return da.getAttr131();
		case 132: return da.getAttr132();
		case 133: return da.getAttr133();
		case 134: return da.getAttr134();
		case 135: return da.getAttr135();
		case 136: return da.getAttr136();
		case 137: return da.getAttr137();
		case 138: return da.getAttr138();
		case 139: return da.getAttr139();
		case 140: return da.getAttr140();
		case 141: return da.getAttr141();
		case 142: return da.getAttr142();
		case 143: return da.getAttr143();
		case 144: return da.getAttr144();
		case 145: return da.getAttr145();
		case 146: return da.getAttr146();
		case 147: return da.getAttr147();
		case 148: return da.getAttr148();
		case 149: return da.getAttr149();
		case 150: return da.getAttr150();
		case 151: return da.getAttr151();
		case 152: return da.getAttr152();
		case 153: return da.getAttr153();
		case 154: return da.getAttr154();
		case 155: return da.getAttr155();
		case 156: return da.getAttr156();
		case 157: return da.getAttr157();
		case 158: return da.getAttr158();
		case 159: return da.getAttr159();
		case 160: return da.getAttr160();
		case 161: return da.getAttr161();
		case 162: return da.getAttr162();
		case 163: return da.getAttr163();
		case 164: return da.getAttr164();
		case 165: return da.getAttr165();
		case 166: return da.getAttr166();
		case 167: return da.getAttr167();
		case 168: return da.getAttr168();
		case 169: return da.getAttr169();
		case 170: return da.getAttr170();
		case 171: return da.getAttr171();
		case 172: return da.getAttr172();
		case 173: return da.getAttr173();
		case 174: return da.getAttr174();
		case 175: return da.getAttr175();
		case 176: return da.getAttr176();
		case 177: return da.getAttr177();
		case 178: return da.getAttr178();
		case 179: return da.getAttr179();
		case 180: return da.getAttr180();
		case 181: return da.getAttr181();
		case 182: return da.getAttr182();
		case 183: return da.getAttr183();
		case 184: return da.getAttr184();
		case 185: return da.getAttr185();
		case 186: return da.getAttr186();
		case 187: return da.getAttr187();
		case 188: return da.getAttr188();
		case 189: return da.getAttr189();
		case 190: return da.getAttr190();
		case 191: return da.getAttr191();
		case 192: return da.getAttr192();
		case 193: return da.getAttr193();
		case 194: return da.getAttr194();
		case 195: return da.getAttr195();
		case 196: return da.getAttr196();
		case 197: return da.getAttr197();
		case 198: return da.getAttr198();
		case 199: return da.getAttr199();
		case 200: return da.getAttr200();
		case 201: return da.getAttr201();
		case 202: return da.getAttr202();
		case 203: return da.getAttr203();
		case 204: return da.getAttr204();
		case 205: return da.getAttr205();
		case 206: return da.getAttr206();
		case 207: return da.getAttr207();
		case 208: return da.getAttr208();
		case 209: return da.getAttr209();
		case 210: return da.getAttr210();
		case 211: return da.getAttr211();
		case 212: return da.getAttr212();
		case 213: return da.getAttr213();
		case 214: return da.getAttr214();
		case 215: return da.getAttr215();
		case 216: return da.getAttr216();
		case 217: return da.getAttr217();
		case 218: return da.getAttr218();
		case 219: return da.getAttr219();
		case 220: return da.getAttr220();
		case 221: return da.getAttr221();
		case 222: return da.getAttr222();
		case 223: return da.getAttr223();
		case 224: return da.getAttr224();
		case 225: return da.getAttr225();
		case 226: return da.getAttr226();
		case 227: return da.getAttr227();
		case 228: return da.getAttr228();
		case 229: return da.getAttr229();
		case 230: return da.getAttr230();
		case 231: return da.getAttr231();
		case 232: return da.getAttr232();
		case 233: return da.getAttr233();
		case 234: return da.getAttr234();
		case 235: return da.getAttr235();
		case 236: return da.getAttr236();
		case 237: return da.getAttr237();
		case 238: return da.getAttr238();
		case 239: return da.getAttr239();
		case 240: return da.getAttr240();
		case 241: return da.getAttr241();
		case 242: return da.getAttr242();
		case 243: return da.getAttr243();
		case 244: return da.getAttr244();
		case 245: return da.getAttr245();
		case 246: return da.getAttr246();
		case 247: return da.getAttr247();
		case 248: return da.getAttr248();
		case 249: return da.getAttr249();
		case 250: return da.getAttr250();
		case 251: return da.getAttr251();
		case 252: return da.getAttr252();
		case 253: return da.getAttr253();
		case 254: return da.getAttr254();
		case 255: return da.getAttr255();
		case 256: return da.getAttr256();
		case 257: return da.getAttr257();
		case 258: return da.getAttr258();
		case 259: return da.getAttr259();
		case 260: return da.getAttr260();
		case 261: return da.getAttr261();
		case 262: return da.getAttr262();
		case 263: return da.getAttr263();
		case 264: return da.getAttr264();
		case 265: return da.getAttr265();
		case 266: return da.getAttr266();
		case 267: return da.getAttr267();
		case 268: return da.getAttr268();
		case 269: return da.getAttr269();
		case 270: return da.getAttr270();
		case 271: return da.getAttr271();
		case 272: return da.getAttr272();
		case 273: return da.getAttr273();
		case 274: return da.getAttr274();
		case 275: return da.getAttr275();
		case 276: return da.getAttr276();
		case 277: return da.getAttr277();
		case 278: return da.getAttr278();
		case 279: return da.getAttr279();
		case 280: return da.getAttr280();
		case 281: return da.getAttr281();
		case 282: return da.getAttr282();
		case 283: return da.getAttr283();
		case 284: return da.getAttr284();
		case 285: return da.getAttr285();
		case 286: return da.getAttr286();
		case 287: return da.getAttr287();
		case 288: return da.getAttr288();
		case 289: return da.getAttr289();
		case 290: return da.getAttr290();
		case 291: return da.getAttr291();
		case 292: return da.getAttr292();
		case 293: return da.getAttr293();
		case 294: return da.getAttr294();
		case 295: return da.getAttr295();
		case 296: return da.getAttr296();
		case 297: return da.getAttr297();
		case 298: return da.getAttr298();
		case 299: return da.getAttr299();
		case 300: return da.getAttr300();
		case 301: return da.getAttr301();
		case 302: return da.getAttr302();
		case 303: return da.getAttr303();
		case 304: return da.getAttr304();
		case 305: return da.getAttr305();
		case 306: return da.getAttr306();
		case 307: return da.getAttr307();
		case 308: return da.getAttr308();
		case 309: return da.getAttr309();
		case 310: return da.getAttr310();
		case 311: return da.getAttr311();
		case 312: return da.getAttr312();
		case 313: return da.getAttr313();
		case 314: return da.getAttr314();
		case 315: return da.getAttr315();
		case 316: return da.getAttr316();
		case 317: return da.getAttr317();
		case 318: return da.getAttr318();
		case 319: return da.getAttr319();
		case 320: return da.getAttr320();
		case 321: return da.getAttr321();
		case 322: return da.getAttr322();
		case 323: return da.getAttr323();
		case 324: return da.getAttr324();
		/*case 325: return da.getAttr325();
		case 326: return da.getAttr326();
		case 327: return da.getAttr327();
		case 328: return da.getAttr328();
		case 329: return da.getAttr329();
		case 330: return da.getAttr330();
		case 331: return da.getAttr331();
		case 332: return da.getAttr332();
		case 333: return da.getAttr333();
		case 334: return da.getAttr334();
		case 335: return da.getAttr335();
		case 336: return da.getAttr336();
		case 337: return da.getAttr337();
		case 338: return da.getAttr338();
		case 339: return da.getAttr339();
		case 340: return da.getAttr340();
		case 341: return da.getAttr341();
		case 342: return da.getAttr342();
		case 343: return da.getAttr343();
		case 344: return da.getAttr344();
		case 345: return da.getAttr345();
		case 346: return da.getAttr346();
		case 347: return da.getAttr347();
		case 348: return da.getAttr348();
		case 349: return da.getAttr349();
		case 350: return da.getAttr350();*/
		default :
			return result;
		}
	}
	
	public static String getClxAttrValue(int dataRowIndex,SnapshotClxAssetDa da){
		String result = "NA"; 

		switch(dataRowIndex){
		case 1  : return da.getAttr1();
		case 2  : return da.getAttr2();
		case 3  : return da.getAttr3();
		case 4  : return da.getAttr4();
		case 5  : return da.getAttr5();
		case 6  : return da.getAttr6();
		case 7  : return da.getAttr7();
		case 8  : return da.getAttr8();
		case 9  : return da.getAttr9();
		case 10 : return da.getAttr10();
		case 11 : return da.getAttr11();
		case 12 : return da.getAttr12();
		case 13 : return da.getAttr13();
		case 14 : return da.getAttr14();
		case 15 : return da.getAttr15();
		case 16 : return da.getAttr16();
		case 17 : return da.getAttr17();
		case 18 : return da.getAttr18();
		case 19 : return da.getAttr19();
		case 20 : return da.getAttr20();
		case 21 : return da.getAttr21();
		case 22 : return da.getAttr22();
		case 23 : return da.getAttr23();
		case 24 : return da.getAttr24();
		case 25 : return da.getAttr25();
		case 26 : return da.getAttr26();
		case 27 : return da.getAttr27();
		case 28 : return da.getAttr28();
		case 29 : return da.getAttr29();
		case 30 : return da.getAttr30();
		case 31 : return da.getAttr31();
		case 32 : return da.getAttr32();
		case 33 : return da.getAttr33();
		case 34 : return da.getAttr34();
		case 35 : return da.getAttr35();
		case 36 : return da.getAttr36();
		case 37 : return da.getAttr37();
		case 38 : return da.getAttr38();
		case 39 : return da.getAttr39();
		case 40 : return da.getAttr40();
		case 41 : return da.getAttr41();
		case 42 : return da.getAttr42();
		case 43 : return da.getAttr43();
		case 44 : return da.getAttr44();
		case 45 : return da.getAttr45();
		case 46 : return da.getAttr46();
		case 47 : return da.getAttr47();
		case 48 : return da.getAttr48();
		case 49 : return da.getAttr49();
		case 50 : return da.getAttr50();
		case 51 : return da.getAttr51();
		case 52 : return da.getAttr52();
		case 53 : return da.getAttr53();
		case 54 : return da.getAttr54();
		case 55 : return da.getAttr55();
		case 56 : return da.getAttr56();
		case 57 : return da.getAttr57();
		case 58 : return da.getAttr58();
		case 59 : return da.getAttr59();
		case 60 : return da.getAttr60();
		case 61 : return da.getAttr61();
		case 62 : return da.getAttr62();
		case 63 : return da.getAttr63();
		case 64 : return da.getAttr64();
		case 65 : return da.getAttr65();
		case 66 : return da.getAttr66();
		case 67 : return da.getAttr67();
		case 68 : return da.getAttr68();
		case 69 : return da.getAttr69();
		case 70 : return da.getAttr70();
		case 71 : return da.getAttr71();
		case 72 : return da.getAttr72();
		case 73 : return da.getAttr73();
		case 74 : return da.getAttr74();
		case 75 : return da.getAttr75();
		case 76 : return da.getAttr76();
		case 77 : return da.getAttr77();
		case 78 : return da.getAttr78();
		case 79 : return da.getAttr79();
		case 80 : return da.getAttr80();
		case 81 : return da.getAttr81();
		case 82 : return da.getAttr82();
		case 83 : return da.getAttr83();
		case 84 : return da.getAttr84();
		case 85 : return da.getAttr85();
		case 86 : return da.getAttr86();
		case 87 : return da.getAttr87();
		case 88 : return da.getAttr88();
		case 89 : return da.getAttr89();
		case 90 : return da.getAttr90();
		case 91 : return da.getAttr91();
		case 92 : return da.getAttr92();
		case 93 : return da.getAttr93();
		case 94 : return da.getAttr94();
		case 95 : return da.getAttr95();
		case 96 : return da.getAttr96();
		case 97 : return da.getAttr97();
		case 98 : return da.getAttr98();
		case 99 : return da.getAttr99();
		case 100: return da.getAttr100();
		case 101: return da.getAttr101();
		case 102: return da.getAttr102();
		case 103: return da.getAttr103();
		case 104: return da.getAttr104();
		case 105: return da.getAttr105();
		case 106: return da.getAttr106();
		case 107: return da.getAttr107();
		case 108: return da.getAttr108();
		case 109: return da.getAttr109();
		case 110: return da.getAttr110();
		case 111: return da.getAttr111();
		case 112: return da.getAttr112();
		case 113: return da.getAttr113();
		case 114: return da.getAttr114();
		case 115: return da.getAttr115();
		case 116: return da.getAttr116();
		case 117: return da.getAttr117();
		case 118: return da.getAttr118();
		case 119: return da.getAttr119();
		case 120: return da.getAttr120();
		case 121: return da.getAttr121();
		case 122: return da.getAttr122();
		case 123: return da.getAttr123();
		case 124: return da.getAttr124();
		case 125: return da.getAttr125();
		case 126: return da.getAttr126();
		case 127: return da.getAttr127();
		case 128: return da.getAttr128();
		case 129: return da.getAttr129();
		case 130: return da.getAttr130();
		case 131: return da.getAttr131();
		case 132: return da.getAttr132();
		case 133: return da.getAttr133();
		case 134: return da.getAttr134();
		case 135: return da.getAttr135();
		case 136: return da.getAttr136();
		case 137: return da.getAttr137();
		case 138: return da.getAttr138();
		case 139: return da.getAttr139();
		case 140: return da.getAttr140();
		case 141: return da.getAttr141();
		case 142: return da.getAttr142();
		case 143: return da.getAttr143();
		case 144: return da.getAttr144();
		case 145: return da.getAttr145();
		case 146: return da.getAttr146();
		case 147: return da.getAttr147();
		case 148: return da.getAttr148();
		case 149: return da.getAttr149();
		case 150: return da.getAttr150();
		case 151: return da.getAttr151();
		case 152: return da.getAttr152();
		case 153: return da.getAttr153();
		case 154: return da.getAttr154();
		case 155: return da.getAttr155();
		case 156: return da.getAttr156();
		case 157: return da.getAttr157();
		case 158: return da.getAttr158();
		case 159: return da.getAttr159();
		case 160: return da.getAttr160();
		case 161: return da.getAttr161();
		case 162: return da.getAttr162();
		case 163: return da.getAttr163();
		case 164: return da.getAttr164();
		case 165: return da.getAttr165();
		case 166: return da.getAttr166();
		case 167: return da.getAttr167();
		case 168: return da.getAttr168();
		case 169: return da.getAttr169();
		case 170: return da.getAttr170();
		case 171: return da.getAttr171();
		case 172: return da.getAttr172();
		case 173: return da.getAttr173();
		case 174: return da.getAttr174();
		case 175: return da.getAttr175();
		case 176: return da.getAttr176();
		case 177: return da.getAttr177();
		case 178: return da.getAttr178();
		case 179: return da.getAttr179();
		case 180: return da.getAttr180();
		case 181: return da.getAttr181();
		case 182: return da.getAttr182();
		case 183: return da.getAttr183();
		case 184: return da.getAttr184();
		case 185: return da.getAttr185();
		case 186: return da.getAttr186();
		case 187: return da.getAttr187();
		case 188: return da.getAttr188();
		case 189: return da.getAttr189();
		case 190: return da.getAttr190();
		case 191: return da.getAttr191();
		case 192: return da.getAttr192();
		case 193: return da.getAttr193();
		case 194: return da.getAttr194();
		case 195: return da.getAttr195();
		case 196: return da.getAttr196();
		case 197: return da.getAttr197();
		case 198: return da.getAttr198();
		case 199: return da.getAttr199();
		case 200: return da.getAttr200();
		case 201: return da.getAttr201();
		case 202: return da.getAttr202();
		case 203: return da.getAttr203();
		case 204: return da.getAttr204();
		case 205: return da.getAttr205();
		case 206: return da.getAttr206();
		case 207: return da.getAttr207();
		case 208: return da.getAttr208();
		case 209: return da.getAttr209();
		case 210: return da.getAttr210();
		case 211: return da.getAttr211();
		case 212: return da.getAttr212();
		case 213: return da.getAttr213();
		case 214: return da.getAttr214();
		case 215: return da.getAttr215();
		case 216: return da.getAttr216();
		case 217: return da.getAttr217();
		case 218: return da.getAttr218();
		case 219: return da.getAttr219();
		case 220: return da.getAttr220();
		case 221: return da.getAttr221();
		case 222: return da.getAttr222();
		case 223: return da.getAttr223();
		case 224: return da.getAttr224();
		case 225: return da.getAttr225();
		case 226: return da.getAttr226();
		case 227: return da.getAttr227();
		case 228: return da.getAttr228();
		case 229: return da.getAttr229();
		case 230: return da.getAttr230();
		case 231: return da.getAttr231();
		case 232: return da.getAttr232();
		case 233: return da.getAttr233();
		case 234: return da.getAttr234();
		case 235: return da.getAttr235();
		case 236: return da.getAttr236();
		case 237: return da.getAttr237();
		case 238: return da.getAttr238();
		case 239: return da.getAttr239();
		case 240: return da.getAttr240();
		case 241: return da.getAttr241();
		case 242: return da.getAttr242();
		case 243: return da.getAttr243();
		case 244: return da.getAttr244();
		case 245: return da.getAttr245();
		case 246: return da.getAttr246();
		case 247: return da.getAttr247();
		case 248: return da.getAttr248();
		case 249: return da.getAttr249();
		case 250: return da.getAttr250();
		case 251: return da.getAttr251();
		case 252: return da.getAttr252();
		case 253: return da.getAttr253();
		case 254: return da.getAttr254();
		case 255: return da.getAttr255();
		case 256: return da.getAttr256();
		case 257: return da.getAttr257();
		case 258: return da.getAttr258();
		case 259: return da.getAttr259();
		case 260: return da.getAttr260();
		case 261: return da.getAttr261();
		case 262: return da.getAttr262();
		case 263: return da.getAttr263();
		case 264: return da.getAttr264();
		case 265: return da.getAttr265();
		case 266: return da.getAttr266();
		case 267: return da.getAttr267();
		case 268: return da.getAttr268();
		case 269: return da.getAttr269();
		case 270: return da.getAttr270();
		case 271: return da.getAttr271();
		case 272: return da.getAttr272();
		case 273: return da.getAttr273();
		case 274: return da.getAttr274();
		case 275: return da.getAttr275();
		case 276: return da.getAttr276();
		case 277: return da.getAttr277();
		case 278: return da.getAttr278();
		case 279: return da.getAttr279();
		case 280: return da.getAttr280();
		case 281: return da.getAttr281();
		case 282: return da.getAttr282();
		case 283: return da.getAttr283();
		case 284: return da.getAttr284();
		case 285: return da.getAttr285();
		case 286: return da.getAttr286();
		case 287: return da.getAttr287();
		case 288: return da.getAttr288();
		case 289: return da.getAttr289();
		case 290: return da.getAttr290();
		case 291: return da.getAttr291();
		case 292: return da.getAttr292();
		case 293: return da.getAttr293();
		case 294: return da.getAttr294();
		case 295: return da.getAttr295();
		case 296: return da.getAttr296();
		case 297: return da.getAttr297();
		case 298: return da.getAttr298();
		case 299: return da.getAttr299();
		case 300: return da.getAttr300();
		case 301: return da.getAttr301();
		case 302: return da.getAttr302();
		case 303: return da.getAttr303();
		case 304: return da.getAttr304();
		case 305: return da.getAttr305();
		case 306: return da.getAttr306();
		case 307: return da.getAttr307();
		case 308: return da.getAttr308();
		case 309: return da.getAttr309();
		case 310: return da.getAttr310();
		case 311: return da.getAttr311();
		case 312: return da.getAttr312();
		case 313: return da.getAttr313();
		case 314: return da.getAttr314();
		case 315: return da.getAttr315();
		case 316: return da.getAttr316();
		case 317: return da.getAttr317();
		case 318: return da.getAttr318();
		case 319: return da.getAttr319();
		case 320: return da.getAttr320();
		case 321: return da.getAttr321();
		case 322: return da.getAttr322();
		case 323: return da.getAttr323();
		case 324: return da.getAttr324();
		/*case 325: return da.getAttr325();
		case 326: return da.getAttr326();
		case 327: return da.getAttr327();
		case 328: return da.getAttr328();
		case 329: return da.getAttr329();
		case 330: return da.getAttr330();
		case 331: return da.getAttr331();
		case 332: return da.getAttr332();
		case 333: return da.getAttr333();
		case 334: return da.getAttr334();
		case 335: return da.getAttr335();
		case 336: return da.getAttr336();
		case 337: return da.getAttr337();
		case 338: return da.getAttr338();
		case 339: return da.getAttr339();
		case 340: return da.getAttr340();
		case 341: return da.getAttr341();
		case 342: return da.getAttr342();
		case 343: return da.getAttr343();
		case 344: return da.getAttr344();
		case 345: return da.getAttr345();
		case 346: return da.getAttr346();
		case 347: return da.getAttr347();
		case 348: return da.getAttr348();
		case 349: return da.getAttr349();
		case 350: return da.getAttr350();*/
		default :
			return result;
		}
	}
	
	public static final String getHeaderMappedAttrEntityProperty(String headerKey){

		switch (headerKey){
		case "header67" : 
			return "attr325";
		case "header68" : 
			return "attr326";
		case "header69" : 
			return "attr327";
		case "header70" : 
			return "attr328";
		case "header71" : 
			return "attr329";
		case "header72" : 
			return "attr330";
		case "header73" : 
			return "attr331";
		case "header74" : 
			return "attr332";
		case "header75" : 
			return "attr333";
		case "header76" : 
			return "attr334";
		case "header77" : 
			return "attr335";
		case "header78" : 
			return "attr336";
		case "header79" : 
			return "attr337";
		case "header80" : 
			return "attr338";
		case "header81" : 
			return "attr339";
		case "header82" : 
			return "attr340";
		case "header83" : 
			return "attr341";
		case "header84" : 
			return "attr342";
		case "header85" : 
			return "attr343";
		case "header86" : 
			return "attr344";
		case "header87" : 
			return "attr345";
		case "header88" : 
			return "attr346";
		case "header89" : 
			return "attr347";
		case "header90" : 
			return "attr348";
		case "header91" : 
			return "attr349";
		case "header92" : 
			return "attr350";	 
		default : 
			return headerKey;
		}
	}
	
	/**
	 * @Sandeep Singh
	 * Filter SBl,Clx,Sv list for initiate DFR
	 * 
	 * @param assetDas
	 * @param selected
	 * @param productFilter
	 * @return
	 */
	public static List<SiebelAssetDa> filterSiebelAssetDa(List <SiebelAssetDa> assetDas, List<String> selected) {
		List<SiebelAssetDa> list = new ArrayList<>();
		selected.forEach(p ->assetDas.stream()
                .filter(p1 -> p.equals(ServiceUtil.getSblHeaderValue("header20", p1)))
                .forEach(list::add));
		
        return list;
    }
	public static List<ClxAssetDa> filterClxAssetDa(List <ClxAssetDa> assetDas, Set<String> selected) {
		List<ClxAssetDa> list = new ArrayList<>();
		selected.forEach(p ->assetDas.stream()
                .filter(p1 -> p.equals(ServiceUtil.getClxHeaderValue("header20", p1)))
                .forEach(list::add));
		
        return list;
    }
	public static List<SvAssetDa> filterSvAssetDa(List <SvAssetDa> assetDas, Set<String> selected) {
		List<SvAssetDa> list = new ArrayList<>();
		selected.forEach(p ->assetDas.stream()
                .filter(p1 -> p.equals(ServiceUtil.getSvHeaderValue("header20", p1)))
                .forEach(list::add));
		
        return list;
    }
	
	public static String getSblHeaderValue(String headerName,SiebelAssetDa da){
		String result = "NA"; 
		
		switch(headerName){
		case "header1" : return da.getHeader1();
		case "header2" : return da.getHeader2();
		case "header3" : return da.getHeader3();
		case "header4" : return da.getHeader4();
		case "header5" : return da.getHeader5();
		case "header6" : return da.getHeader6();
		case "header7" : return da.getHeader7();
		case "header8" : return da.getHeader8();
		case "header9" : return da.getHeader9();
		case "header10": return da.getHeader10();
		case "header11": return da.getHeader11();
		case "header12": return da.getHeader12();
		case "header13": return da.getHeader13();
		case "header14": return da.getHeader14();
		case "header15": return da.getHeader15();
		case "header16": return da.getHeader16();
		case "header17": return DartUtil.dateToString(da.getHeader17());
		case "header18": return da.getHeader18();
		case "header19": return da.getHeader19();
		case "header20": return da.getHeader20();
		case "header21": return da.getHeader21();
		case "header22": return da.getHeader22();
		case "header23": return da.getHeader23();
		case "header24": return da.getHeader24();
		case "header25": return da.getHeader25();
		case "header26": return da.getHeader26();
		case "header27": return da.getHeader27();
		case "header28": return da.getHeader28();
		case "header29": return da.getHeader29();
		case "header30": return da.getHeader30();
		case "header31": return da.getHeader31();
		case "header32": return da.getHeader32();
		case "header33": return da.getHeader33();
		case "header34": return DartUtil.dateToString(da.getHeader34());
		case "header35": return da.getHeader35();
		case "header36": return DartUtil.dateToString(da.getHeader36());
		case "header37": return da.getHeader37();
		case "header38": return da.getHeader38();
		case "header39": return da.getHeader39();
		case "header40": return da.getHeader40();
		case "header41": return da.getHeader41();
		case "header42": return da.getHeader42();
		case "header43": return da.getHeader43();
		case "header44": return da.getHeader44();
		case "header45": return da.getHeader45();
		case "header46": return da.getHeader46();
		case "header47": return da.getHeader47();
		case "header48": return da.getHeader48();
		case "header49": return da.getHeader49();
		case "header50": return da.getHeader50();
		case "header51": return da.getHeader51();
		case "header52": return da.getHeader52();
		case "header53": return da.getHeader53();
		case "header54": return da.getHeader54();
		case "header55": return DartUtil.dateToString(da.getHeader55());
		case "header56": return da.getHeader56();
		case "header57": return da.getHeader57();
		case "header58": return da.getHeader58();
		case "header59": return da.getHeader59();
		case "header60": return da.getHeader60();
		case "header61": return da.getHeader61();
		case "header62": return da.getHeader62();
		case "header63": return da.getHeader63();
		case "header64": return da.getHeader64();
		case "header65": return da.getHeader65();
		case "header66": return da.getHeader66();
		
		/*case 67: return da.getAttr325();
		case 68: return da.getAttr326();
		case 69: return da.getAttr327();
		case 70: return da.getAttr328();
		case 71: return da.getAttr329();
		case 72: return da.getAttr330();
		case 73: return da.getAttr331();
		case 74: return da.getAttr332();
		case 75: return da.getAttr333();
		case 76: return da.getAttr334();
		case 77: return da.getAttr335();
		case 78: return da.getAttr336();
		case 79: return da.getAttr337();
		case 80: return da.getAttr338();
		case 81: return da.getAttr339();
		case 82: return da.getAttr340();
		case 83: return da.getAttr341();
		case 84: return da.getAttr342();
		case 85: return da.getAttr343();
		case 86: return da.getAttr344();
		case 87: return da.getAttr345();
		case 88: return da.getAttr346();
		case 89: return da.getAttr347();
		case 90: return da.getAttr348();
		case 91: return da.getAttr349();
		case 92: return da.getAttr350();*/
		default :
				return result;
		}
	}
	/**
	 * Header by header Name
	 * @param dataRowIndex
	 * @param da
	 * @return
	 */
	
	public static String getClxHeaderValue(String headerName,ClxAssetDa da){
		String result = "NA"; 
		
		switch(headerName){
		case "header1" : return da.getHeader1();
		case "header2" : return da.getHeader2();
		case "header3" : return da.getHeader3();
		case "header4" : return da.getHeader4();
		case "header5" : return da.getHeader5();
		case "header6" : return da.getHeader6();
		case "header7" : return da.getHeader7();
		case "header8" : return da.getHeader8();
		case "header9" : return da.getHeader9();
		case "header10": return da.getHeader10();
		case "header11": return da.getHeader11();
		case "header12": return da.getHeader12();
		case "header13": return da.getHeader13();
		case "header14": return da.getHeader14();
		case "header15": return da.getHeader15();
		case "header16": return da.getHeader16();
		case "header17": return DartUtil.dateToString(da.getHeader17());
		case "header18": return da.getHeader18();
		case "header19": return da.getHeader19();
		case "header20": return da.getHeader20();
		case "header21": return da.getHeader21();
		case "header22": return da.getHeader22();
		case "header23": return da.getHeader23();
		case "header24": return da.getHeader24();
		case "header25": return da.getHeader25();
		case "header26": return da.getHeader26();
		case "header27": return da.getHeader27();
		case "header28": return da.getHeader28();
		case "header29": return da.getHeader29();
		case "header30": return da.getHeader30();
		case "header31": return da.getHeader31();
		case "header32": return da.getHeader32();
		case "header33": return da.getHeader33();
		case "header34": return DartUtil.dateToString(da.getHeader34());
		case "header35": return da.getHeader35();
		case "header36": return DartUtil.dateToString(da.getHeader36());
		case "header37": return da.getHeader37();
		case "header38": return da.getHeader38();
		case "header39": return da.getHeader39();
		case "header40": return da.getHeader40();
		case "header41": return da.getHeader41();
		case "header42": return da.getHeader42();
		case "header43": return da.getHeader43();
		case "header44": return da.getHeader44();
		case "header45": return da.getHeader45();
		case "header46": return da.getHeader46();
		case "header47": return da.getHeader47();
		case "header48": return da.getHeader48();
		case "header49": return da.getHeader49();
		case "header50": return da.getHeader50();
		case "header51": return da.getHeader51();
		case "header52": return da.getHeader52();
		case "header53": return da.getHeader53();
		case "header54": return da.getHeader54();
		case "header55": return DartUtil.dateToString(da.getHeader55());
		case "header56": return da.getHeader56();
		case "header57": return da.getHeader57();
		case "header58": return da.getHeader58();
		case "header59": return da.getHeader59();
		case "header60": return da.getHeader60();
		case "header61": return da.getHeader61();
		case "header62": return da.getHeader62();
		case "header63": return da.getHeader63();
		case "header64": return da.getHeader64();
		case "header65": return da.getHeader65();
		case "header66": return da.getHeader66();
		
		/*case 67: return da.getAttr325();
		case 68: return da.getAttr326();
		case 69: return da.getAttr327();
		case 70: return da.getAttr328();
		case 71: return da.getAttr329();
		case 72: return da.getAttr330();
		case 73: return da.getAttr331();
		case 74: return da.getAttr332();
		case 75: return da.getAttr333();
		case 76: return da.getAttr334();
		case 77: return da.getAttr335();
		case 78: return da.getAttr336();
		case 79: return da.getAttr337();
		case 80: return da.getAttr338();
		case 81: return da.getAttr339();
		case 82: return da.getAttr340();
		case 83: return da.getAttr341();
		case 84: return da.getAttr342();
		case 85: return da.getAttr343();
		case 86: return da.getAttr344();
		case 87: return da.getAttr345();
		case 88: return da.getAttr346();
		case 89: return da.getAttr347();
		case 90: return da.getAttr348();
		case 91: return da.getAttr349();
		case 92: return da.getAttr350();*/
		default :
				return result;
		}
	}
	/**
	 * Header by header Name
	 * @param dataRowIndex
	 * @param da
	 * @return
	 */
	
	public static String getSvHeaderValue(String headerName,SvAssetDa da){
		String result = "NA"; 
		
		switch(headerName){
		case "header1" : return da.getHeader1();
		case "header2" : return da.getHeader2();
		case "header3" : return da.getHeader3();
		case "header4" : return da.getHeader4();
		case "header5" : return da.getHeader5();
		case "header6" : return da.getHeader6();
		case "header7" : return da.getHeader7();
		case "header8" : return da.getHeader8();
		case "header9" : return da.getHeader9();
		case "header10": return da.getHeader10();
		case "header11": return da.getHeader11();
		case "header12": return da.getHeader12();
		case "header13": return da.getHeader13();
		case "header14": return da.getHeader14();
		case "header15": return da.getHeader15();
		case "header16": return da.getHeader16();
		case "header17": return DartUtil.dateToString(da.getHeader17());
		case "header18": return da.getHeader18();
		case "header19": return da.getHeader19();
		case "header20": return da.getHeader20();
		case "header21": return da.getHeader21();
		case "header22": return da.getHeader22();
		case "header23": return da.getHeader23();
		case "header24": return da.getHeader24();
		case "header25": return da.getHeader25();
		case "header26": return da.getHeader26();
		case "header27": return da.getHeader27();
		case "header28": return da.getHeader28();
		case "header29": return da.getHeader29();
		case "header30": return da.getHeader30();
		case "header31": return da.getHeader31();
		case "header32": return da.getHeader32();
		case "header33": return da.getHeader33();
		case "header34": return DartUtil.dateToString(da.getHeader34());
		case "header35": return da.getHeader35();
		case "header36": return DartUtil.dateToString(da.getHeader36());
		case "header37": return da.getHeader37();
		case "header38": return da.getHeader38();
		case "header39": return da.getHeader39();
		case "header40": return da.getHeader40();
		case "header41": return da.getHeader41();
		case "header42": return da.getHeader42();
		case "header43": return da.getHeader43();
		case "header44": return da.getHeader44();
		case "header45": return da.getHeader45();
		case "header46": return da.getHeader46();
		case "header47": return da.getHeader47();
		case "header48": return da.getHeader48();
		case "header49": return da.getHeader49();
		case "header50": return da.getHeader50();
		case "header51": return da.getHeader51();
		case "header52": return da.getHeader52();
		case "header53": return da.getHeader53();
		case "header54": return da.getHeader54();
		case "header55": return DartUtil.dateToString(da.getHeader55());
		case "header56": return da.getHeader56();
		case "header57": return da.getHeader57();
		case "header58": return da.getHeader58();
		case "header59": return da.getHeader59();
		case "header60": return da.getHeader60();
		case "header61": return da.getHeader61();
		case "header62": return da.getHeader62();
		case "header63": return da.getHeader63();
		case "header64": return da.getHeader64();
		case "header65": return da.getHeader65();
		case "header66": return da.getHeader66();
		
		/*case 67: return da.getAttr325();
		case 68: return da.getAttr326();
		case 69: return da.getAttr327();
		case 70: return da.getAttr328();
		case 71: return da.getAttr329();
		case 72: return da.getAttr330();
		case 73: return da.getAttr331();
		case 74: return da.getAttr332();
		case 75: return da.getAttr333();
		case 76: return da.getAttr334();
		case 77: return da.getAttr335();
		case 78: return da.getAttr336();
		case 79: return da.getAttr337();
		case 80: return da.getAttr338();
		case 81: return da.getAttr339();
		case 82: return da.getAttr340();
		case 83: return da.getAttr341();
		case 84: return da.getAttr342();
		case 85: return da.getAttr343();
		case 86: return da.getAttr344();
		case 87: return da.getAttr345();
		case 88: return da.getAttr346();
		case 89: return da.getAttr347();
		case 90: return da.getAttr348();
		case 91: return da.getAttr349();
		case 92: return da.getAttr350();*/
		default :
				return result;
		}
	}
	public static List<SiebelAssetDa> sortSblProductWise(List<SiebelAssetDa> sblList, String products) {
		List<SiebelAssetDa> siebelAssetDas = new ArrayList<>();
		List<String> prods = new ArrayList<>();
		if(products.contains(",")){
			String[] strs = products.split(",");
			for(String s : strs){
				prods.add(s);
			}
		}else{
			prods.add(products);
		}
		if (CollectionUtils.isNotEmpty(sblList)&& CollectionUtils.isNotEmpty(prods)) {
			for(String prod : prods){
				for (SiebelAssetDa siebelAssetDa : sblList) {
					if (siebelAssetDa.getHeader20().equalsIgnoreCase(prod)) {
						siebelAssetDas.add(siebelAssetDa);
					}
				}
			}
		}
		return siebelAssetDas;
	}
	
	/**
	 * Snapshot
	 * 
	 */
	
	public static List<SnapshotSiebelAssetDa> filterSnapshotSiebelAssetDa(List <SnapshotSiebelAssetDa> assetDas, List <String> selected,ProductFilter productFilter) {
		List<SnapshotSiebelAssetDa> list = new ArrayList<>();
		selected.forEach(p ->assetDas.stream()
                .filter(p1 -> p.equals(ServiceUtil.getSblHeaderValue(productFilter.getHeader(), p1)))
                .forEach(list::add));
		
        return list;
    }
	public static List<SnapshotClxAssetDa> filterSnapshotClxAssetDa(List <SnapshotClxAssetDa> assetDas, List <String> selected,ProductFilter productFilter) {
		List<SnapshotClxAssetDa> list = new ArrayList<>();
		selected.forEach(p ->assetDas.stream()
                .filter(p1 -> p.equals(ServiceUtil.getClxHeaderValue(productFilter.getHeader(), p1)))
                .forEach(list::add));
		
        return list;
    }
	public static List<SnapshotSvAssetDa> filterSnapshotSvAssetDa(List <SnapshotSvAssetDa> assetDas, List <String> selected,ProductFilter productFilter) {
		List<SnapshotSvAssetDa> list = new ArrayList<>();
		selected.forEach(p ->assetDas.stream()
                .filter(p1 -> p.equals(ServiceUtil.getSvHeaderValue(productFilter.getHeader(), p1)))
                .forEach(list::add));
		  return list;
    }
	
	public static String getSblHeaderValue(String headerName,SnapshotSiebelAssetDa da){
		String result = "NA"; 
		
		switch(headerName){
		case "header1" : return da.getHeader1();
		case "header2" : return da.getHeader2();
		case "header3" : return da.getHeader3();
		case "header4" : return da.getHeader4();
		case "header5" : return da.getHeader5();
		case "header6" : return da.getHeader6();
		case "header7" : return da.getHeader7();
		case "header8" : return da.getHeader8();
		case "header9" : return da.getHeader9();
		case "header10": return da.getHeader10();
		case "header11": return da.getHeader11();
		case "header12": return da.getHeader12();
		case "header13": return da.getHeader13();
		case "header14": return da.getHeader14();
		case "header15": return da.getHeader15();
		case "header16": return da.getHeader16();
		case "header17": return DartUtil.dateToString(da.getHeader17());
		case "header18": return da.getHeader18();
		case "header19": return da.getHeader19();
		case "header20": return da.getHeader20();
		case "header21": return da.getHeader21();
		case "header22": return da.getHeader22();
		case "header23": return da.getHeader23();
		case "header24": return da.getHeader24();
		case "header25": return da.getHeader25();
		case "header26": return da.getHeader26();
		case "header27": return da.getHeader27();
		case "header28": return da.getHeader28();
		case "header29": return da.getHeader29();
		case "header30": return da.getHeader30();
		case "header31": return da.getHeader31();
		case "header32": return da.getHeader32();
		case "header33": return da.getHeader33();
		case "header34": return DartUtil.dateToString(da.getHeader34());
		case "header35": return da.getHeader35();
		case "header36": return DartUtil.dateToString(da.getHeader36());
		case "header37": return da.getHeader37();
		case "header38": return da.getHeader38();
		case "header39": return da.getHeader39();
		case "header40": return da.getHeader40();
		case "header41": return da.getHeader41();
		case "header42": return da.getHeader42();
		case "header43": return da.getHeader43();
		case "header44": return da.getHeader44();
		case "header45": return da.getHeader45();
		case "header46": return da.getHeader46();
		case "header47": return da.getHeader47();
		case "header48": return da.getHeader48();
		case "header49": return da.getHeader49();
		case "header50": return da.getHeader50();
		case "header51": return da.getHeader51();
		case "header52": return da.getHeader52();
		case "header53": return da.getHeader53();
		case "header54": return da.getHeader54();
		case "header55": return DartUtil.dateToString(da.getHeader55());
		case "header56": return da.getHeader56();
		case "header57": return da.getHeader57();
		case "header58": return da.getHeader58();
		case "header59": return da.getHeader59();
		case "header60": return da.getHeader60();
		case "header61": return da.getHeader61();
		case "header62": return da.getHeader62();
		case "header63": return da.getHeader63();
		case "header64": return da.getHeader64();
		case "header65": return da.getHeader65();
		case "header66": return da.getHeader66();
		
		/*case 67: return da.getAttr325();
		case 68: return da.getAttr326();
		case 69: return da.getAttr327();
		case 70: return da.getAttr328();
		case 71: return da.getAttr329();
		case 72: return da.getAttr330();
		case 73: return da.getAttr331();
		case 74: return da.getAttr332();
		case 75: return da.getAttr333();
		case 76: return da.getAttr334();
		case 77: return da.getAttr335();
		case 78: return da.getAttr336();
		case 79: return da.getAttr337();
		case 80: return da.getAttr338();
		case 81: return da.getAttr339();
		case 82: return da.getAttr340();
		case 83: return da.getAttr341();
		case 84: return da.getAttr342();
		case 85: return da.getAttr343();
		case 86: return da.getAttr344();
		case 87: return da.getAttr345();
		case 88: return da.getAttr346();
		case 89: return da.getAttr347();
		case 90: return da.getAttr348();
		case 91: return da.getAttr349();
		case 92: return da.getAttr350();*/
		default :
				return result;
		}
	}
	
	public static String getClxHeaderValue(String headerName,SnapshotClxAssetDa da){
		String result = "NA"; 
		
		switch(headerName){
		case "header1" : return da.getHeader1();
		case "header2" : return da.getHeader2();
		case "header3" : return da.getHeader3();
		case "header4" : return da.getHeader4();
		case "header5" : return da.getHeader5();
		case "header6" : return da.getHeader6();
		case "header7" : return da.getHeader7();
		case "header8" : return da.getHeader8();
		case "header9" : return da.getHeader9();
		case "header10": return da.getHeader10();
		case "header11": return da.getHeader11();
		case "header12": return da.getHeader12();
		case "header13": return da.getHeader13();
		case "header14": return da.getHeader14();
		case "header15": return da.getHeader15();
		case "header16": return da.getHeader16();
		case "header17": return DartUtil.dateToString(da.getHeader17());
		case "header18": return da.getHeader18();
		case "header19": return da.getHeader19();
		case "header20": return da.getHeader20();
		case "header21": return da.getHeader21();
		case "header22": return da.getHeader22();
		case "header23": return da.getHeader23();
		case "header24": return da.getHeader24();
		case "header25": return da.getHeader25();
		case "header26": return da.getHeader26();
		case "header27": return da.getHeader27();
		case "header28": return da.getHeader28();
		case "header29": return da.getHeader29();
		case "header30": return da.getHeader30();
		case "header31": return da.getHeader31();
		case "header32": return da.getHeader32();
		case "header33": return da.getHeader33();
		case "header34": return DartUtil.dateToString(da.getHeader34());
		case "header35": return da.getHeader35();
		case "header36": return DartUtil.dateToString(da.getHeader36());
		case "header37": return da.getHeader37();
		case "header38": return da.getHeader38();
		case "header39": return da.getHeader39();
		case "header40": return da.getHeader40();
		case "header41": return da.getHeader41();
		case "header42": return da.getHeader42();
		case "header43": return da.getHeader43();
		case "header44": return da.getHeader44();
		case "header45": return da.getHeader45();
		case "header46": return da.getHeader46();
		case "header47": return da.getHeader47();
		case "header48": return da.getHeader48();
		case "header49": return da.getHeader49();
		case "header50": return da.getHeader50();
		case "header51": return da.getHeader51();
		case "header52": return da.getHeader52();
		case "header53": return da.getHeader53();
		case "header54": return da.getHeader54();
		case "header55": return DartUtil.dateToString(da.getHeader55());
		case "header56": return da.getHeader56();
		case "header57": return da.getHeader57();
		case "header58": return da.getHeader58();
		case "header59": return da.getHeader59();
		case "header60": return da.getHeader60();
		case "header61": return da.getHeader61();
		case "header62": return da.getHeader62();
		case "header63": return da.getHeader63();
		case "header64": return da.getHeader64();
		case "header65": return da.getHeader65();
		case "header66": return da.getHeader66();
		
		/*case 67: return da.getAttr325();
		case 68: return da.getAttr326();
		case 69: return da.getAttr327();
		case 70: return da.getAttr328();
		case 71: return da.getAttr329();
		case 72: return da.getAttr330();
		case 73: return da.getAttr331();
		case 74: return da.getAttr332();
		case 75: return da.getAttr333();
		case 76: return da.getAttr334();
		case 77: return da.getAttr335();
		case 78: return da.getAttr336();
		case 79: return da.getAttr337();
		case 80: return da.getAttr338();
		case 81: return da.getAttr339();
		case 82: return da.getAttr340();
		case 83: return da.getAttr341();
		case 84: return da.getAttr342();
		case 85: return da.getAttr343();
		case 86: return da.getAttr344();
		case 87: return da.getAttr345();
		case 88: return da.getAttr346();
		case 89: return da.getAttr347();
		case 90: return da.getAttr348();
		case 91: return da.getAttr349();
		case 92: return da.getAttr350();*/
		default :
				return result;
		}
	}	
	public static String getSvHeaderValue(String headerName,SnapshotSvAssetDa da){
		String result = "NA"; 
		
		switch(headerName){
		case "header1" : return da.getHeader1();
		case "header2" : return da.getHeader2();
		case "header3" : return da.getHeader3();
		case "header4" : return da.getHeader4();
		case "header5" : return da.getHeader5();
		case "header6" : return da.getHeader6();
		case "header7" : return da.getHeader7();
		case "header8" : return da.getHeader8();
		case "header9" : return da.getHeader9();
		case "header10": return da.getHeader10();
		case "header11": return da.getHeader11();
		case "header12": return da.getHeader12();
		case "header13": return da.getHeader13();
		case "header14": return da.getHeader14();
		case "header15": return da.getHeader15();
		case "header16": return da.getHeader16();
		case "header17": return DartUtil.dateToString(da.getHeader17());
		case "header18": return da.getHeader18();
		case "header19": return da.getHeader19();
		case "header20": return da.getHeader20();
		case "header21": return da.getHeader21();
		case "header22": return da.getHeader22();
		case "header23": return da.getHeader23();
		case "header24": return da.getHeader24();
		case "header25": return da.getHeader25();
		case "header26": return da.getHeader26();
		case "header27": return da.getHeader27();
		case "header28": return da.getHeader28();
		case "header29": return da.getHeader29();
		case "header30": return da.getHeader30();
		case "header31": return da.getHeader31();
		case "header32": return da.getHeader32();
		case "header33": return da.getHeader33();
		case "header34": return DartUtil.dateToString(da.getHeader34());
		case "header35": return da.getHeader35();
		case "header36": return DartUtil.dateToString(da.getHeader36());
		case "header37": return da.getHeader37();
		case "header38": return da.getHeader38();
		case "header39": return da.getHeader39();
		case "header40": return da.getHeader40();
		case "header41": return da.getHeader41();
		case "header42": return da.getHeader42();
		case "header43": return da.getHeader43();
		case "header44": return da.getHeader44();
		case "header45": return da.getHeader45();
		case "header46": return da.getHeader46();
		case "header47": return da.getHeader47();
		case "header48": return da.getHeader48();
		case "header49": return da.getHeader49();
		case "header50": return da.getHeader50();
		case "header51": return da.getHeader51();
		case "header52": return da.getHeader52();
		case "header53": return da.getHeader53();
		case "header54": return da.getHeader54();
		case "header55": return DartUtil.dateToString(da.getHeader55());
		case "header56": return da.getHeader56();
		case "header57": return da.getHeader57();
		case "header58": return da.getHeader58();
		case "header59": return da.getHeader59();
		case "header60": return da.getHeader60();
		case "header61": return da.getHeader61();
		case "header62": return da.getHeader62();
		case "header63": return da.getHeader63();
		case "header64": return da.getHeader64();
		case "header65": return da.getHeader65();
		case "header66": return da.getHeader66();
		
		/*case 67: return da.getAttr325();
		case 68: return da.getAttr326();
		case 69: return da.getAttr327();
		case 70: return da.getAttr328();
		case 71: return da.getAttr329();
		case 72: return da.getAttr330();
		case 73: return da.getAttr331();
		case 74: return da.getAttr332();
		case 75: return da.getAttr333();
		case 76: return da.getAttr334();
		case 77: return da.getAttr335();
		case 78: return da.getAttr336();
		case 79: return da.getAttr337();
		case 80: return da.getAttr338();
		case 81: return da.getAttr339();
		case 82: return da.getAttr340();
		case 83: return da.getAttr341();
		case 84: return da.getAttr342();
		case 85: return da.getAttr343();
		case 86: return da.getAttr344();
		case 87: return da.getAttr345();
		case 88: return da.getAttr346();
		case 89: return da.getAttr347();
		case 90: return da.getAttr348();
		case 91: return da.getAttr349();
		case 92: return da.getAttr350();*/
		default :
				return result;
		}
	}

	public static List<String> getProductsFromRequest(ProductFilter productFilter){
		List<PFilter> filterList = productFilter.getFilters();
		List<String> products = null;
		for (PFilter filter : filterList) {
			if (filter.getKey().equalsIgnoreCase("header20")) {
				products = filter.getListOfValues();
			}
		}
		return products;
	}
	
	public static void validateProductFilterForCommaSaparatedAssetNumsInKeyword(ProductFilter productFilter) {
		if (productFilter.getSearchDropBox() != null) {
			List<SearchDrop> searchDropList = productFilter.getSearchDropBox().getSearchDrop();
			
			if (CollectionUtils.isNotEmpty(searchDropList)) {
				for (SearchDrop searchDrop : searchDropList) {
					if (StringUtils.isNotEmpty(searchDrop.getKey())) {
						List<String> searchKewordsList = null;
						if ("header3".equalsIgnoreCase(searchDrop.getKey())||"header2".equalsIgnoreCase(searchDrop.getKey())||"header16".equalsIgnoreCase(searchDrop.getKey())||"header6".equalsIgnoreCase(searchDrop.getKey())||"header24".equalsIgnoreCase(searchDrop.getKey())) {
							searchKewordsList = new ArrayList<String>(
									Arrays.asList(productFilter.getKeyword().split(",")).stream()
									.filter(i -> i != null && !i.equalsIgnoreCase("")).map(String::trim).collect(Collectors.toList()));
							if (CollectionUtils.isNotEmpty(searchKewordsList)) {
								PFilter pFilter = new PFilter();
								pFilter.setKey(searchDrop.getKey());
								pFilter.setValue(productFilter.getKeyword());
								pFilter.setLable(searchDrop.getLabel());
								pFilter.setListOfValues(searchKewordsList);
								productFilter.getFilters().add(pFilter);
								productFilter.setKeyword("");
							}
						} 
					}
				}
			} /*
				 * else if(StringUtils.isNotBlank(productFilter.getKeyword())){
				 * boolean isAllValCode = true; String errorCodeArray[] =
				 * productFilter.getKeyword().split(",");
				 * if(errorCodeArray.length>0){ for(String errorCode :
				 * errorCodeArray ){ if(!errorCode.startsWith("VAL_")){
				 * isAllValCode = false; break; } } if(isAllValCode)
				 * productFilter.setKeyword(""); } }
				 */
		}
	}
	
	public static boolean isDisplay(AttributeConfig attConfig){
		/*if(attConfig!=null){
			logger.info("Attribute name: " + attConfig.getAttrName());
			logger.info("Display flag: " + attConfig.getDisplayFlag());
		}*/
		if(attConfig!=null && attConfig.getDisplayFlag()!=null && attConfig.getDisplayFlag().equalsIgnoreCase("Y")){
			return true;
		}else{
			return false;
		}
	}
		
}
