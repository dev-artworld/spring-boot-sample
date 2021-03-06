package com.equinix.appops.dart.portal.mapper.pro;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.equinix.appops.dart.portal.constant.CacheConstant;
import com.equinix.appops.dart.portal.entity.SnapshotSiebelAssetDa;

public class SblSnapshotMapper implements RowMapper<SnapshotSiebelAssetDa> {

	@Override
	public SnapshotSiebelAssetDa mapRow(ResultSet rs, int arg1) throws SQLException {
		SnapshotSiebelAssetDa snapshotSiebelAssetDa = new SnapshotSiebelAssetDa();
		snapshotSiebelAssetDa.setDfrLineId(this.processRs(rs, "DFR_LINE_ID"));
		snapshotSiebelAssetDa.setDfrId(this.processRs(rs, "DFR_ID"));
		snapshotSiebelAssetDa.setHeader1(this.processRs(rs, "ROW_ID"));
		snapshotSiebelAssetDa.setHeader2(this.processRs(rs, "ASSET_NUM"));
		snapshotSiebelAssetDa.setHeader3(this.processRs(rs, "SERIAL_NUM"));
		snapshotSiebelAssetDa.setHeader4(this.processRs(rs, "X_ATTR_25"));
		snapshotSiebelAssetDa.setHeader5(this.processRs(rs, "X_CUST_UCID"));
		snapshotSiebelAssetDa.setHeader6(this.processRs(rs, "OU_NUM"));
		snapshotSiebelAssetDa.setHeader7(this.processRs(rs, "ACCOUNT_NAME"));
		snapshotSiebelAssetDa.setHeader8(this.processRs(rs, "IBX"));
		snapshotSiebelAssetDa.setHeader9(this.processRs(rs, "X_UNIQUE_SPACE_ID"));
		snapshotSiebelAssetDa.setHeader10(this.processRs(rs, "CAGE_UNIQUE_SPACE_ID"));
		snapshotSiebelAssetDa.setHeader11(this.processRs(rs, "X_CAB_USID"));
		snapshotSiebelAssetDa.setHeader12(this.processRs(rs, "CAB_UNIQUE_SPACE_ID"));
		snapshotSiebelAssetDa.setHeader13(this.processRs(rs, "CAGE_NUMBER"));
		snapshotSiebelAssetDa.setHeader14(this.processRs(rs, "X_CAB_ID"));
		snapshotSiebelAssetDa.setHeader15(this.processRs(rs, "BASE_CURRENCY_CD"));
		snapshotSiebelAssetDa.setHeader16(this.processRs(rs, "SYSTEM_NAME"));
		snapshotSiebelAssetDa.setHeader17(rs.getDate("ASSET_INSTALL_DATE"));
		snapshotSiebelAssetDa.setHeader18(this.processRs(rs, "STATUS_CD"));
		snapshotSiebelAssetDa.setHeader19(this.processRs(rs, "ASSET_SUB_STATUS"));
		snapshotSiebelAssetDa.setHeader20(this.processRs(rs, "NAME"));
		snapshotSiebelAssetDa.setHeader21(this.processRs(rs, "PAR_ASSET_ID"));
		snapshotSiebelAssetDa.setHeader22(this.processRs(rs, "ROOT_ASSET_ID"));
		snapshotSiebelAssetDa.setHeader23(this.processRs(rs, "PAR_AST_NUM"));
		snapshotSiebelAssetDa.setHeader24(this.processRs(rs, "ROOT_AST_NUM"));
		snapshotSiebelAssetDa.setHeader25(this.processRs(rs, "PARENT_PRODUCT_NAME"));
		snapshotSiebelAssetDa.setHeader26(this.processRs(rs, "POF_NAME"));
		snapshotSiebelAssetDa.setHeader27(this.processRs(rs, "X_OPS_PAR_ASSET_NUM"));
		snapshotSiebelAssetDa.setHeader28(this.processRs(rs, "X_ROOT_ASSET_NUM"));
		snapshotSiebelAssetDa.setHeader29(this.processRs(rs, "X_ATTR_10"));
		snapshotSiebelAssetDa.setHeader30(this.processRs(rs, "X_ATTR_22"));
		snapshotSiebelAssetDa.setHeader31(this.processRs(rs, "BILLING_AGR_NUM"));
		snapshotSiebelAssetDa.setHeader32(this.processRs(rs, "BILLING_AGR_LN_NUM"));
		snapshotSiebelAssetDa.setHeader33(this.processRs(rs, "PRICE"));
		snapshotSiebelAssetDa.setHeader34(rs.getDate("CREATED"));
		snapshotSiebelAssetDa.setHeader35(this.processRs(rs, "CREATED_BY"));
		snapshotSiebelAssetDa.setHeader36(rs.getDate("LAST_UPD"));
		snapshotSiebelAssetDa.setHeader37(this.processRs(rs, "LAST_UPD_BY"));
		snapshotSiebelAssetDa.setHeader38(this.processRs(rs, "DQM_ERR_FLG"));
		snapshotSiebelAssetDa.setHeader39(this.processRs(rs, "PROD_ID"));
		snapshotSiebelAssetDa.setHeader40(this.processRs(rs, "X_REL_ACCNT_ID"));
		snapshotSiebelAssetDa.setHeader41(this.processRs(rs, "RELATED_ACCNT_NUM"));
		snapshotSiebelAssetDa.setHeader42(this.processRs(rs, "RELATED_ACCNT_NAME"));
		snapshotSiebelAssetDa.setHeader43(this.processRs(rs, "X_CABLE_ID"));
		snapshotSiebelAssetDa.setHeader44(this.processRs(rs, "X_MASS_CREATE_ASSET"));
		snapshotSiebelAssetDa.setHeader45(this.processRs(rs, "DESC_TEXT"));
		snapshotSiebelAssetDa.setHeader46(this.processRs(rs, "PART_NUM"));
		snapshotSiebelAssetDa.setHeader47(this.processRs(rs, "X_MACD_SPLIT_M"));
		snapshotSiebelAssetDa.setHeader48(this.processRs(rs, "POF_X_MACD_SPLIT_M"));
		snapshotSiebelAssetDa.setHeader49(this.processRs(rs, "POF_PART_NUM"));
		snapshotSiebelAssetDa.setHeader50(this.processRs(rs, "POF_STATUS_CD"));
		snapshotSiebelAssetDa.setHeader51(this.processRs(rs, "REGION"));
		snapshotSiebelAssetDa.setHeader52(this.processRs(rs, "X_SYS_NAME"));
		snapshotSiebelAssetDa.setHeader53(this.processRs(rs, "OWNER_ACCNT_ID"));
		snapshotSiebelAssetDa.setHeader54(this.processRs(rs, "X_PP_ID"));
		snapshotSiebelAssetDa.setHeader55(rs.getDate("END_DT"));
		snapshotSiebelAssetDa.setHeader56(this.processRs(rs, "COUNTRY"));
		snapshotSiebelAssetDa.setHeader57(this.processRs(rs, "HEADER_01"));
		snapshotSiebelAssetDa.setHeader58(this.processRs(rs, "HEADER_02"));
		snapshotSiebelAssetDa.setHeader59(this.processRs(rs, "HEADER_03"));
		snapshotSiebelAssetDa.setHeader60(this.processRs(rs, "HEADER_04"));
		snapshotSiebelAssetDa.setHeader61(this.processRs(rs, "HEADER_05"));
		snapshotSiebelAssetDa.setHeader62(this.processRs(rs, "HEADER_06"));
		snapshotSiebelAssetDa.setHeader63(this.processRs(rs, "HEADER_07"));
		snapshotSiebelAssetDa.setHeader64(this.processRs(rs, "HEADER_08"));
		snapshotSiebelAssetDa.setHeader65(this.processRs(rs, "HEADER_09"));
		snapshotSiebelAssetDa.setHeader66(this.processRs(rs, "HEADER_10"));
		snapshotSiebelAssetDa.setAttr1(this.processRs(rs, "ATTR_1"));
		snapshotSiebelAssetDa.setAttr2(this.processRs(rs, "ATTR_2"));
		snapshotSiebelAssetDa.setAttr3(this.processRs(rs, "ATTR_3"));
		snapshotSiebelAssetDa.setAttr4(this.processRs(rs, "ATTR_4"));
		snapshotSiebelAssetDa.setAttr5(this.processRs(rs, "ATTR_5"));
		snapshotSiebelAssetDa.setAttr6(this.processRs(rs, "ATTR_6"));
		snapshotSiebelAssetDa.setAttr7(this.processRs(rs, "ATTR_7"));
		snapshotSiebelAssetDa.setAttr8(this.processRs(rs, "ATTR_8"));
		snapshotSiebelAssetDa.setAttr9(this.processRs(rs, "ATTR_9"));
		snapshotSiebelAssetDa.setAttr10(this.processRs(rs, "ATTR_10"));
		snapshotSiebelAssetDa.setAttr11(this.processRs(rs, "ATTR_11"));
		snapshotSiebelAssetDa.setAttr12(this.processRs(rs, "ATTR_12"));
		snapshotSiebelAssetDa.setAttr13(this.processRs(rs, "ATTR_13"));
		snapshotSiebelAssetDa.setAttr14(this.processRs(rs, "ATTR_14"));
		snapshotSiebelAssetDa.setAttr15(this.processRs(rs, "ATTR_15"));
		snapshotSiebelAssetDa.setAttr16(this.processRs(rs, "ATTR_16"));
		snapshotSiebelAssetDa.setAttr17(this.processRs(rs, "ATTR_17"));
		snapshotSiebelAssetDa.setAttr18(this.processRs(rs, "ATTR_18"));
		snapshotSiebelAssetDa.setAttr19(this.processRs(rs, "ATTR_19"));
		snapshotSiebelAssetDa.setAttr20(this.processRs(rs, "ATTR_20"));
		snapshotSiebelAssetDa.setAttr21(this.processRs(rs, "ATTR_21"));
		snapshotSiebelAssetDa.setAttr22(this.processRs(rs, "ATTR_22"));
		snapshotSiebelAssetDa.setAttr23(this.processRs(rs, "ATTR_23"));
		snapshotSiebelAssetDa.setAttr24(this.processRs(rs, "ATTR_24"));
		snapshotSiebelAssetDa.setAttr25(this.processRs(rs, "ATTR_25"));
		snapshotSiebelAssetDa.setAttr26(this.processRs(rs, "ATTR_26"));
		snapshotSiebelAssetDa.setAttr27(this.processRs(rs, "ATTR_27"));
		snapshotSiebelAssetDa.setAttr28(this.processRs(rs, "ATTR_28"));
		snapshotSiebelAssetDa.setAttr29(this.processRs(rs, "ATTR_29"));
		snapshotSiebelAssetDa.setAttr30(this.processRs(rs, "ATTR_30"));
		snapshotSiebelAssetDa.setAttr31(this.processRs(rs, "ATTR_31"));
		snapshotSiebelAssetDa.setAttr32(this.processRs(rs, "ATTR_32"));
		snapshotSiebelAssetDa.setAttr33(this.processRs(rs, "ATTR_33"));
		snapshotSiebelAssetDa.setAttr34(this.processRs(rs, "ATTR_34"));
		snapshotSiebelAssetDa.setAttr35(this.processRs(rs, "ATTR_35"));
		snapshotSiebelAssetDa.setAttr36(this.processRs(rs, "ATTR_36"));
		snapshotSiebelAssetDa.setAttr37(this.processRs(rs, "ATTR_37"));
		snapshotSiebelAssetDa.setAttr38(this.processRs(rs, "ATTR_38"));
		snapshotSiebelAssetDa.setAttr39(this.processRs(rs, "ATTR_39"));
		snapshotSiebelAssetDa.setAttr40(this.processRs(rs, "ATTR_40"));
		snapshotSiebelAssetDa.setAttr41(this.processRs(rs, "ATTR_41"));
		snapshotSiebelAssetDa.setAttr42(this.processRs(rs, "ATTR_42"));
		snapshotSiebelAssetDa.setAttr43(this.processRs(rs, "ATTR_43"));
		snapshotSiebelAssetDa.setAttr44(this.processRs(rs, "ATTR_44"));
		snapshotSiebelAssetDa.setAttr45(this.processRs(rs, "ATTR_45"));
		snapshotSiebelAssetDa.setAttr46(this.processRs(rs, "ATTR_46"));
		snapshotSiebelAssetDa.setAttr47(this.processRs(rs, "ATTR_47"));
		snapshotSiebelAssetDa.setAttr48(this.processRs(rs, "ATTR_48"));
		snapshotSiebelAssetDa.setAttr49(this.processRs(rs, "ATTR_49"));
		snapshotSiebelAssetDa.setAttr50(this.processRs(rs, "ATTR_50"));
		snapshotSiebelAssetDa.setAttr51(this.processRs(rs, "ATTR_51"));
		snapshotSiebelAssetDa.setAttr52(this.processRs(rs, "ATTR_52"));
		snapshotSiebelAssetDa.setAttr53(this.processRs(rs, "ATTR_53"));
		snapshotSiebelAssetDa.setAttr54(this.processRs(rs, "ATTR_54"));
		snapshotSiebelAssetDa.setAttr55(this.processRs(rs, "ATTR_55"));
		snapshotSiebelAssetDa.setAttr56(this.processRs(rs, "ATTR_56"));
		snapshotSiebelAssetDa.setAttr57(this.processRs(rs, "ATTR_57"));
		snapshotSiebelAssetDa.setAttr58(this.processRs(rs, "ATTR_58"));
		snapshotSiebelAssetDa.setAttr59(this.processRs(rs, "ATTR_59"));
		snapshotSiebelAssetDa.setAttr60(this.processRs(rs, "ATTR_60"));
		snapshotSiebelAssetDa.setAttr61(this.processRs(rs, "ATTR_61"));
		snapshotSiebelAssetDa.setAttr62(this.processRs(rs, "ATTR_62"));
		snapshotSiebelAssetDa.setAttr63(this.processRs(rs, "ATTR_63"));
		snapshotSiebelAssetDa.setAttr64(this.processRs(rs, "ATTR_64"));
		snapshotSiebelAssetDa.setAttr65(this.processRs(rs, "ATTR_65"));
		snapshotSiebelAssetDa.setAttr66(this.processRs(rs, "ATTR_66"));
		snapshotSiebelAssetDa.setAttr67(this.processRs(rs, "ATTR_67"));
		snapshotSiebelAssetDa.setAttr68(this.processRs(rs, "ATTR_68"));
		snapshotSiebelAssetDa.setAttr69(this.processRs(rs, "ATTR_69"));
		snapshotSiebelAssetDa.setAttr70(this.processRs(rs, "ATTR_70"));
		snapshotSiebelAssetDa.setAttr71(this.processRs(rs, "ATTR_71"));
		snapshotSiebelAssetDa.setAttr72(this.processRs(rs, "ATTR_72"));
		snapshotSiebelAssetDa.setAttr73(this.processRs(rs, "ATTR_73"));
		snapshotSiebelAssetDa.setAttr74(this.processRs(rs, "ATTR_74"));
		snapshotSiebelAssetDa.setAttr75(this.processRs(rs, "ATTR_75"));
		snapshotSiebelAssetDa.setAttr76(this.processRs(rs, "ATTR_76"));
		snapshotSiebelAssetDa.setAttr77(this.processRs(rs, "ATTR_77"));
		snapshotSiebelAssetDa.setAttr78(this.processRs(rs, "ATTR_78"));
		snapshotSiebelAssetDa.setAttr79(this.processRs(rs, "ATTR_79"));
		snapshotSiebelAssetDa.setAttr80(this.processRs(rs, "ATTR_80"));
		snapshotSiebelAssetDa.setAttr81(this.processRs(rs, "ATTR_81"));
		snapshotSiebelAssetDa.setAttr82(this.processRs(rs, "ATTR_82"));
		snapshotSiebelAssetDa.setAttr83(this.processRs(rs, "ATTR_83"));
		snapshotSiebelAssetDa.setAttr84(this.processRs(rs, "ATTR_84"));
		snapshotSiebelAssetDa.setAttr85(this.processRs(rs, "ATTR_85"));
		snapshotSiebelAssetDa.setAttr86(this.processRs(rs, "ATTR_86"));
		snapshotSiebelAssetDa.setAttr87(this.processRs(rs, "ATTR_87"));
		snapshotSiebelAssetDa.setAttr88(this.processRs(rs, "ATTR_88"));
		snapshotSiebelAssetDa.setAttr89(this.processRs(rs, "ATTR_89"));
		snapshotSiebelAssetDa.setAttr90(this.processRs(rs, "ATTR_90"));
		snapshotSiebelAssetDa.setAttr91(this.processRs(rs, "ATTR_91"));
		snapshotSiebelAssetDa.setAttr92(this.processRs(rs, "ATTR_92"));
		snapshotSiebelAssetDa.setAttr93(this.processRs(rs, "ATTR_93"));
		snapshotSiebelAssetDa.setAttr94(this.processRs(rs, "ATTR_94"));
		snapshotSiebelAssetDa.setAttr95(this.processRs(rs, "ATTR_95"));
		snapshotSiebelAssetDa.setAttr96(this.processRs(rs, "ATTR_96"));
		snapshotSiebelAssetDa.setAttr97(this.processRs(rs, "ATTR_97"));
		snapshotSiebelAssetDa.setAttr98(this.processRs(rs, "ATTR_98"));
		snapshotSiebelAssetDa.setAttr99(this.processRs(rs, "ATTR_99"));
		snapshotSiebelAssetDa.setAttr100(this.processRs(rs, "ATTR_100"));
		snapshotSiebelAssetDa.setAttr101(this.processRs(rs, "ATTR_101"));
		snapshotSiebelAssetDa.setAttr102(this.processRs(rs, "ATTR_102"));
		snapshotSiebelAssetDa.setAttr103(this.processRs(rs, "ATTR_103"));
		snapshotSiebelAssetDa.setAttr104(this.processRs(rs, "ATTR_104"));
		snapshotSiebelAssetDa.setAttr105(this.processRs(rs, "ATTR_105"));
		snapshotSiebelAssetDa.setAttr106(this.processRs(rs, "ATTR_106"));
		snapshotSiebelAssetDa.setAttr107(this.processRs(rs, "ATTR_107"));
		snapshotSiebelAssetDa.setAttr108(this.processRs(rs, "ATTR_108"));
		snapshotSiebelAssetDa.setAttr109(this.processRs(rs, "ATTR_109"));
		snapshotSiebelAssetDa.setAttr110(this.processRs(rs, "ATTR_110"));
		snapshotSiebelAssetDa.setAttr111(this.processRs(rs, "ATTR_111"));
		snapshotSiebelAssetDa.setAttr112(this.processRs(rs, "ATTR_112"));
		snapshotSiebelAssetDa.setAttr113(this.processRs(rs, "ATTR_113"));
		snapshotSiebelAssetDa.setAttr114(this.processRs(rs, "ATTR_114"));
		snapshotSiebelAssetDa.setAttr115(this.processRs(rs, "ATTR_115"));
		snapshotSiebelAssetDa.setAttr116(this.processRs(rs, "ATTR_116"));
		snapshotSiebelAssetDa.setAttr117(this.processRs(rs, "ATTR_117"));
		snapshotSiebelAssetDa.setAttr118(this.processRs(rs, "ATTR_118"));
		snapshotSiebelAssetDa.setAttr119(this.processRs(rs, "ATTR_119"));
		snapshotSiebelAssetDa.setAttr120(this.processRs(rs, "ATTR_120"));
		snapshotSiebelAssetDa.setAttr121(this.processRs(rs, "ATTR_121"));
		snapshotSiebelAssetDa.setAttr122(this.processRs(rs, "ATTR_122"));
		snapshotSiebelAssetDa.setAttr123(this.processRs(rs, "ATTR_123"));
		snapshotSiebelAssetDa.setAttr124(this.processRs(rs, "ATTR_124"));
		snapshotSiebelAssetDa.setAttr125(this.processRs(rs, "ATTR_125"));
		snapshotSiebelAssetDa.setAttr126(this.processRs(rs, "ATTR_126"));
		snapshotSiebelAssetDa.setAttr127(this.processRs(rs, "ATTR_127"));
		snapshotSiebelAssetDa.setAttr128(this.processRs(rs, "ATTR_128"));
		snapshotSiebelAssetDa.setAttr129(this.processRs(rs, "ATTR_129"));
		snapshotSiebelAssetDa.setAttr130(this.processRs(rs, "ATTR_130"));
		snapshotSiebelAssetDa.setAttr131(this.processRs(rs, "ATTR_131"));
		snapshotSiebelAssetDa.setAttr132(this.processRs(rs, "ATTR_132"));
		snapshotSiebelAssetDa.setAttr133(this.processRs(rs, "ATTR_133"));
		snapshotSiebelAssetDa.setAttr134(this.processRs(rs, "ATTR_134"));
		snapshotSiebelAssetDa.setAttr135(this.processRs(rs, "ATTR_135"));
		snapshotSiebelAssetDa.setAttr136(this.processRs(rs, "ATTR_136"));
		snapshotSiebelAssetDa.setAttr137(this.processRs(rs, "ATTR_137"));
		snapshotSiebelAssetDa.setAttr138(this.processRs(rs, "ATTR_138"));
		snapshotSiebelAssetDa.setAttr139(this.processRs(rs, "ATTR_139"));
		snapshotSiebelAssetDa.setAttr140(this.processRs(rs, "ATTR_140"));
		snapshotSiebelAssetDa.setAttr141(this.processRs(rs, "ATTR_141"));
		snapshotSiebelAssetDa.setAttr142(this.processRs(rs, "ATTR_142"));
		snapshotSiebelAssetDa.setAttr143(this.processRs(rs, "ATTR_143"));
		snapshotSiebelAssetDa.setAttr144(this.processRs(rs, "ATTR_144"));
		snapshotSiebelAssetDa.setAttr145(this.processRs(rs, "ATTR_145"));
		snapshotSiebelAssetDa.setAttr146(this.processRs(rs, "ATTR_146"));
		snapshotSiebelAssetDa.setAttr147(this.processRs(rs, "ATTR_147"));
		snapshotSiebelAssetDa.setAttr148(this.processRs(rs, "ATTR_148"));
		snapshotSiebelAssetDa.setAttr149(this.processRs(rs, "ATTR_149"));
		snapshotSiebelAssetDa.setAttr150(this.processRs(rs, "ATTR_150"));
		snapshotSiebelAssetDa.setAttr151(this.processRs(rs, "ATTR_151"));
		snapshotSiebelAssetDa.setAttr152(this.processRs(rs, "ATTR_152"));
		snapshotSiebelAssetDa.setAttr153(this.processRs(rs, "ATTR_153"));
		snapshotSiebelAssetDa.setAttr154(this.processRs(rs, "ATTR_154"));
		snapshotSiebelAssetDa.setAttr155(this.processRs(rs, "ATTR_155"));
		snapshotSiebelAssetDa.setAttr156(this.processRs(rs, "ATTR_156"));
		snapshotSiebelAssetDa.setAttr157(this.processRs(rs, "ATTR_157"));
		snapshotSiebelAssetDa.setAttr158(this.processRs(rs, "ATTR_158"));
		snapshotSiebelAssetDa.setAttr159(this.processRs(rs, "ATTR_159"));
		snapshotSiebelAssetDa.setAttr160(this.processRs(rs, "ATTR_160"));
		snapshotSiebelAssetDa.setAttr161(this.processRs(rs, "ATTR_161"));
		snapshotSiebelAssetDa.setAttr162(this.processRs(rs, "ATTR_162"));
		snapshotSiebelAssetDa.setAttr163(this.processRs(rs, "ATTR_163"));
		snapshotSiebelAssetDa.setAttr164(this.processRs(rs, "ATTR_164"));
		snapshotSiebelAssetDa.setAttr165(this.processRs(rs, "ATTR_165"));
		snapshotSiebelAssetDa.setAttr166(this.processRs(rs, "ATTR_166"));
		snapshotSiebelAssetDa.setAttr167(this.processRs(rs, "ATTR_167"));
		snapshotSiebelAssetDa.setAttr168(this.processRs(rs, "ATTR_168"));
		snapshotSiebelAssetDa.setAttr169(this.processRs(rs, "ATTR_169"));
		snapshotSiebelAssetDa.setAttr170(this.processRs(rs, "ATTR_170"));
		snapshotSiebelAssetDa.setAttr171(this.processRs(rs, "ATTR_171"));
		snapshotSiebelAssetDa.setAttr172(this.processRs(rs, "ATTR_172"));
		snapshotSiebelAssetDa.setAttr173(this.processRs(rs, "ATTR_173"));
		snapshotSiebelAssetDa.setAttr174(this.processRs(rs, "ATTR_174"));
		snapshotSiebelAssetDa.setAttr175(this.processRs(rs, "ATTR_175"));
		snapshotSiebelAssetDa.setAttr176(this.processRs(rs, "ATTR_176"));
		snapshotSiebelAssetDa.setAttr177(this.processRs(rs, "ATTR_177"));
		snapshotSiebelAssetDa.setAttr178(this.processRs(rs, "ATTR_178"));
		snapshotSiebelAssetDa.setAttr179(this.processRs(rs, "ATTR_179"));
		snapshotSiebelAssetDa.setAttr180(this.processRs(rs, "ATTR_180"));
		snapshotSiebelAssetDa.setAttr181(this.processRs(rs, "ATTR_181"));
		snapshotSiebelAssetDa.setAttr182(this.processRs(rs, "ATTR_182"));
		snapshotSiebelAssetDa.setAttr183(this.processRs(rs, "ATTR_183"));
		snapshotSiebelAssetDa.setAttr184(this.processRs(rs, "ATTR_184"));
		snapshotSiebelAssetDa.setAttr185(this.processRs(rs, "ATTR_185"));
		snapshotSiebelAssetDa.setAttr186(this.processRs(rs, "ATTR_186"));
		snapshotSiebelAssetDa.setAttr187(this.processRs(rs, "ATTR_187"));
		snapshotSiebelAssetDa.setAttr188(this.processRs(rs, "ATTR_188"));
		snapshotSiebelAssetDa.setAttr189(this.processRs(rs, "ATTR_189"));
		snapshotSiebelAssetDa.setAttr190(this.processRs(rs, "ATTR_190"));
		snapshotSiebelAssetDa.setAttr191(this.processRs(rs, "ATTR_191"));
		snapshotSiebelAssetDa.setAttr192(this.processRs(rs, "ATTR_192"));
		snapshotSiebelAssetDa.setAttr193(this.processRs(rs, "ATTR_193"));
		snapshotSiebelAssetDa.setAttr194(this.processRs(rs, "ATTR_194"));
		snapshotSiebelAssetDa.setAttr195(this.processRs(rs, "ATTR_195"));
		snapshotSiebelAssetDa.setAttr196(this.processRs(rs, "ATTR_196"));
		snapshotSiebelAssetDa.setAttr197(this.processRs(rs, "ATTR_197"));
		snapshotSiebelAssetDa.setAttr198(this.processRs(rs, "ATTR_198"));
		snapshotSiebelAssetDa.setAttr199(this.processRs(rs, "ATTR_199"));
		snapshotSiebelAssetDa.setAttr200(this.processRs(rs, "ATTR_200"));
		snapshotSiebelAssetDa.setAttr201(this.processRs(rs, "ATTR_201"));
		snapshotSiebelAssetDa.setAttr202(this.processRs(rs, "ATTR_202"));
		snapshotSiebelAssetDa.setAttr203(this.processRs(rs, "ATTR_203"));
		snapshotSiebelAssetDa.setAttr204(this.processRs(rs, "ATTR_204"));
		snapshotSiebelAssetDa.setAttr205(this.processRs(rs, "ATTR_205"));
		snapshotSiebelAssetDa.setAttr206(this.processRs(rs, "ATTR_206"));
		snapshotSiebelAssetDa.setAttr207(this.processRs(rs, "ATTR_207"));
		snapshotSiebelAssetDa.setAttr208(this.processRs(rs, "ATTR_208"));
		snapshotSiebelAssetDa.setAttr209(this.processRs(rs, "ATTR_209"));
		snapshotSiebelAssetDa.setAttr210(this.processRs(rs, "ATTR_210"));
		snapshotSiebelAssetDa.setAttr211(this.processRs(rs, "ATTR_211"));
		snapshotSiebelAssetDa.setAttr212(this.processRs(rs, "ATTR_212"));
		snapshotSiebelAssetDa.setAttr213(this.processRs(rs, "ATTR_213"));
		snapshotSiebelAssetDa.setAttr214(this.processRs(rs, "ATTR_214"));
		snapshotSiebelAssetDa.setAttr215(this.processRs(rs, "ATTR_215"));
		snapshotSiebelAssetDa.setAttr216(this.processRs(rs, "ATTR_216"));
		snapshotSiebelAssetDa.setAttr217(this.processRs(rs, "ATTR_217"));
		snapshotSiebelAssetDa.setAttr218(this.processRs(rs, "ATTR_218"));
		snapshotSiebelAssetDa.setAttr219(this.processRs(rs, "ATTR_219"));
		snapshotSiebelAssetDa.setAttr220(this.processRs(rs, "ATTR_220"));
		snapshotSiebelAssetDa.setAttr221(this.processRs(rs, "ATTR_221"));
		snapshotSiebelAssetDa.setAttr222(this.processRs(rs, "ATTR_222"));
		snapshotSiebelAssetDa.setAttr223(this.processRs(rs, "ATTR_223"));
		snapshotSiebelAssetDa.setAttr224(this.processRs(rs, "ATTR_224"));
		snapshotSiebelAssetDa.setAttr225(this.processRs(rs, "ATTR_225"));
		snapshotSiebelAssetDa.setAttr226(this.processRs(rs, "ATTR_226"));
		snapshotSiebelAssetDa.setAttr227(this.processRs(rs, "ATTR_227"));
		snapshotSiebelAssetDa.setAttr228(this.processRs(rs, "ATTR_228"));
		snapshotSiebelAssetDa.setAttr229(this.processRs(rs, "ATTR_229"));
		snapshotSiebelAssetDa.setAttr230(this.processRs(rs, "ATTR_230"));
		snapshotSiebelAssetDa.setAttr231(this.processRs(rs, "ATTR_231"));
		snapshotSiebelAssetDa.setAttr232(this.processRs(rs, "ATTR_232"));
		snapshotSiebelAssetDa.setAttr233(this.processRs(rs, "ATTR_233"));
		snapshotSiebelAssetDa.setAttr234(this.processRs(rs, "ATTR_234"));
		snapshotSiebelAssetDa.setAttr235(this.processRs(rs, "ATTR_235"));
		snapshotSiebelAssetDa.setAttr236(this.processRs(rs, "ATTR_236"));
		snapshotSiebelAssetDa.setAttr237(this.processRs(rs, "ATTR_237"));
		snapshotSiebelAssetDa.setAttr238(this.processRs(rs, "ATTR_238"));
		snapshotSiebelAssetDa.setAttr239(this.processRs(rs, "ATTR_239"));
		snapshotSiebelAssetDa.setAttr240(this.processRs(rs, "ATTR_240"));
		snapshotSiebelAssetDa.setAttr241(this.processRs(rs, "ATTR_241"));
		snapshotSiebelAssetDa.setAttr242(this.processRs(rs, "ATTR_242"));
		snapshotSiebelAssetDa.setAttr243(this.processRs(rs, "ATTR_243"));
		snapshotSiebelAssetDa.setAttr244(this.processRs(rs, "ATTR_244"));
		snapshotSiebelAssetDa.setAttr245(this.processRs(rs, "ATTR_245"));
		snapshotSiebelAssetDa.setAttr246(this.processRs(rs, "ATTR_246"));
		snapshotSiebelAssetDa.setAttr247(this.processRs(rs, "ATTR_247"));
		snapshotSiebelAssetDa.setAttr248(this.processRs(rs, "ATTR_248"));
		snapshotSiebelAssetDa.setAttr249(this.processRs(rs, "ATTR_249"));
		snapshotSiebelAssetDa.setAttr250(this.processRs(rs, "ATTR_250"));
		snapshotSiebelAssetDa.setAttr251(this.processRs(rs, "ATTR_251"));
		snapshotSiebelAssetDa.setAttr252(this.processRs(rs, "ATTR_252"));
		snapshotSiebelAssetDa.setAttr253(this.processRs(rs, "ATTR_253"));
		snapshotSiebelAssetDa.setAttr254(this.processRs(rs, "ATTR_254"));
		snapshotSiebelAssetDa.setAttr255(this.processRs(rs, "ATTR_255"));
		snapshotSiebelAssetDa.setAttr256(this.processRs(rs, "ATTR_256"));
		snapshotSiebelAssetDa.setAttr257(this.processRs(rs, "ATTR_257"));
		snapshotSiebelAssetDa.setAttr258(this.processRs(rs, "ATTR_258"));
		snapshotSiebelAssetDa.setAttr259(this.processRs(rs, "ATTR_259"));
		snapshotSiebelAssetDa.setAttr260(this.processRs(rs, "ATTR_260"));
		snapshotSiebelAssetDa.setAttr261(this.processRs(rs, "ATTR_261"));
		snapshotSiebelAssetDa.setAttr262(this.processRs(rs, "ATTR_262"));
		snapshotSiebelAssetDa.setAttr263(this.processRs(rs, "ATTR_263"));
		snapshotSiebelAssetDa.setAttr264(this.processRs(rs, "ATTR_264"));
		snapshotSiebelAssetDa.setAttr265(this.processRs(rs, "ATTR_265"));
		snapshotSiebelAssetDa.setAttr266(this.processRs(rs, "ATTR_266"));
		snapshotSiebelAssetDa.setAttr267(this.processRs(rs, "ATTR_267"));
		snapshotSiebelAssetDa.setAttr268(this.processRs(rs, "ATTR_268"));
		snapshotSiebelAssetDa.setAttr269(this.processRs(rs, "ATTR_269"));
		snapshotSiebelAssetDa.setAttr270(this.processRs(rs, "ATTR_270"));
		snapshotSiebelAssetDa.setAttr271(this.processRs(rs, "ATTR_271"));
		snapshotSiebelAssetDa.setAttr272(this.processRs(rs, "ATTR_272"));
		snapshotSiebelAssetDa.setAttr273(this.processRs(rs, "ATTR_273"));
		snapshotSiebelAssetDa.setAttr274(this.processRs(rs, "ATTR_274"));
		snapshotSiebelAssetDa.setAttr275(this.processRs(rs, "ATTR_275"));
		snapshotSiebelAssetDa.setAttr276(this.processRs(rs, "ATTR_276"));
		snapshotSiebelAssetDa.setAttr277(this.processRs(rs, "ATTR_277"));
		snapshotSiebelAssetDa.setAttr278(this.processRs(rs, "ATTR_278"));
		snapshotSiebelAssetDa.setAttr279(this.processRs(rs, "ATTR_279"));
		snapshotSiebelAssetDa.setAttr280(this.processRs(rs, "ATTR_280"));
		snapshotSiebelAssetDa.setAttr281(this.processRs(rs, "ATTR_281"));
		snapshotSiebelAssetDa.setAttr282(this.processRs(rs, "ATTR_282"));
		snapshotSiebelAssetDa.setAttr283(this.processRs(rs, "ATTR_283"));
		snapshotSiebelAssetDa.setAttr284(this.processRs(rs, "ATTR_284"));
		snapshotSiebelAssetDa.setAttr285(this.processRs(rs, "ATTR_285"));
		snapshotSiebelAssetDa.setAttr286(this.processRs(rs, "ATTR_286"));
		snapshotSiebelAssetDa.setAttr287(this.processRs(rs, "ATTR_287"));
		snapshotSiebelAssetDa.setAttr288(this.processRs(rs, "ATTR_288"));
		snapshotSiebelAssetDa.setAttr289(this.processRs(rs, "ATTR_289"));
		snapshotSiebelAssetDa.setAttr290(this.processRs(rs, "ATTR_290"));
		snapshotSiebelAssetDa.setAttr291(this.processRs(rs, "ATTR_291"));
		snapshotSiebelAssetDa.setAttr292(this.processRs(rs, "ATTR_292"));
		snapshotSiebelAssetDa.setAttr293(this.processRs(rs, "ATTR_293"));
		snapshotSiebelAssetDa.setAttr294(this.processRs(rs, "ATTR_294"));
		snapshotSiebelAssetDa.setAttr295(this.processRs(rs, "ATTR_295"));
		snapshotSiebelAssetDa.setAttr296(this.processRs(rs, "ATTR_296"));
		snapshotSiebelAssetDa.setAttr297(this.processRs(rs, "ATTR_297"));
		snapshotSiebelAssetDa.setAttr298(this.processRs(rs, "ATTR_298"));
		snapshotSiebelAssetDa.setAttr299(this.processRs(rs, "ATTR_299"));
		snapshotSiebelAssetDa.setAttr300(this.processRs(rs, "ATTR_300"));
		snapshotSiebelAssetDa.setAttr301(this.processRs(rs, "ATTR_301"));
		snapshotSiebelAssetDa.setAttr302(this.processRs(rs, "ATTR_302"));
		snapshotSiebelAssetDa.setAttr303(this.processRs(rs, "ATTR_303"));
		snapshotSiebelAssetDa.setAttr304(this.processRs(rs, "ATTR_304"));
		snapshotSiebelAssetDa.setAttr305(this.processRs(rs, "ATTR_305"));
		snapshotSiebelAssetDa.setAttr306(this.processRs(rs, "ATTR_306"));
		snapshotSiebelAssetDa.setAttr307(this.processRs(rs, "ATTR_307"));
		snapshotSiebelAssetDa.setAttr308(this.processRs(rs, "ATTR_308"));
		snapshotSiebelAssetDa.setAttr309(this.processRs(rs, "ATTR_309"));
		snapshotSiebelAssetDa.setAttr310(this.processRs(rs, "ATTR_310"));
		snapshotSiebelAssetDa.setAttr311(this.processRs(rs, "ATTR_311"));
		snapshotSiebelAssetDa.setAttr312(this.processRs(rs, "ATTR_312"));
		snapshotSiebelAssetDa.setAttr313(this.processRs(rs, "ATTR_313"));
		snapshotSiebelAssetDa.setAttr314(this.processRs(rs, "ATTR_314"));
		snapshotSiebelAssetDa.setAttr315(this.processRs(rs, "ATTR_315"));
		snapshotSiebelAssetDa.setAttr316(this.processRs(rs, "ATTR_316"));
		snapshotSiebelAssetDa.setAttr317(this.processRs(rs, "ATTR_317"));
		snapshotSiebelAssetDa.setAttr318(this.processRs(rs, "ATTR_318"));
		snapshotSiebelAssetDa.setAttr319(this.processRs(rs, "ATTR_319"));
		snapshotSiebelAssetDa.setAttr320(this.processRs(rs, "ATTR_320"));
		snapshotSiebelAssetDa.setAttr321(this.processRs(rs, "ATTR_321"));
		snapshotSiebelAssetDa.setAttr322(this.processRs(rs, "ATTR_322"));
		snapshotSiebelAssetDa.setAttr323(this.processRs(rs, "ATTR_323"));
		snapshotSiebelAssetDa.setAttr324(this.processRs(rs, "ATTR_324"));
		snapshotSiebelAssetDa.setAttr325(this.processRs(rs, "ATTR_325"));
		snapshotSiebelAssetDa.setAttr326(this.processRs(rs, "ATTR_326"));
		snapshotSiebelAssetDa.setAttr327(this.processRs(rs, "ATTR_327"));
		snapshotSiebelAssetDa.setAttr328(this.processRs(rs, "ATTR_328"));
		snapshotSiebelAssetDa.setAttr329(this.processRs(rs, "ATTR_329"));
		snapshotSiebelAssetDa.setAttr330(this.processRs(rs, "ATTR_330"));
		snapshotSiebelAssetDa.setAttr331(this.processRs(rs, "ATTR_331"));
		snapshotSiebelAssetDa.setAttr332(this.processRs(rs, "ATTR_332"));
		snapshotSiebelAssetDa.setAttr333(this.processRs(rs, "ATTR_333"));
		snapshotSiebelAssetDa.setAttr334(this.processRs(rs, "ATTR_334"));
		snapshotSiebelAssetDa.setAttr335(this.processRs(rs, "ATTR_335"));
		snapshotSiebelAssetDa.setAttr336(this.processRs(rs, "ATTR_336"));
		snapshotSiebelAssetDa.setAttr337(this.processRs(rs, "ATTR_337"));
		snapshotSiebelAssetDa.setAttr338(this.processRs(rs, "ATTR_338"));
		snapshotSiebelAssetDa.setAttr339(this.processRs(rs, "ATTR_339"));
		snapshotSiebelAssetDa.setAttr340(this.processRs(rs, "ATTR_340"));
		snapshotSiebelAssetDa.setAttr341(this.processRs(rs, "ATTR_341"));
		snapshotSiebelAssetDa.setAttr342(this.processRs(rs, "ATTR_342"));
		snapshotSiebelAssetDa.setAttr343(this.processRs(rs, "ATTR_343"));
		snapshotSiebelAssetDa.setAttr344(this.processRs(rs, "ATTR_344"));
		snapshotSiebelAssetDa.setAttr345(this.processRs(rs, "ATTR_345"));
		snapshotSiebelAssetDa.setAttr346(this.processRs(rs, "ATTR_346"));
		snapshotSiebelAssetDa.setAttr347(this.processRs(rs, "ATTR_347"));
		snapshotSiebelAssetDa.setAttr348(this.processRs(rs, "ATTR_348"));
		snapshotSiebelAssetDa.setAttr349(this.processRs(rs, "ATTR_349"));
		snapshotSiebelAssetDa.setAttr350(this.processRs(rs, "ATTR_350"));
		snapshotSiebelAssetDa.setDqmErrorDescription(this.processRs(rs, "DQM_ERROR_DESCRIPTION"));
		snapshotSiebelAssetDa.setFixedValCodes(this.processRs(rs, "FIXED_VAL_CODES"));
		snapshotSiebelAssetDa.setRedAttrNames(this.processRs(rs, "RED_ATTR_NAMES"));
		snapshotSiebelAssetDa.setGreenAttrNames(this.processRs(rs, "GREEN_ATTR_NAMES"));
		snapshotSiebelAssetDa.setRedRowIdentifier(this.processRs(rs, "RED_ROW_IDENTIFIER"));
		snapshotSiebelAssetDa.setGreenRowIdentifier(this.processRs(rs, "GREEN_ROW_IDENTIFIER"));
		
		return snapshotSiebelAssetDa;
	}
	
	private String processRs(ResultSet rs , String columnName){
		try{
		   return rs.getString(columnName);
		}catch(Exception e){
		   return CacheConstant.BLANK;	
		}
	}

}
