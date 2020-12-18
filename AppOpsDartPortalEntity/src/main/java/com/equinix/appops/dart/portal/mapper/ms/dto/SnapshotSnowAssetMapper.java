package com.equinix.appops.dart.portal.mapper.ms.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.equinix.appops.dart.portal.constant.CacheConstant;
import com.equinix.appops.dart.portal.entity.mservices.SnapshotSnowAssetDa;

public class SnapshotSnowAssetMapper implements RowMapper<SnapshotSnowAssetDa> {

	@Override
	public SnapshotSnowAssetDa mapRow(ResultSet rs, int arg1) throws SQLException {
		SnapshotSnowAssetDa snapshotSnowAssetDa = new SnapshotSnowAssetDa();
		snapshotSnowAssetDa.setDfrLineId(this.processRs(rs, "DFR_LINE_ID"));
		snapshotSnowAssetDa.setDfrId(this.processRs(rs, "DFR_ID"));
		snapshotSnowAssetDa.setHeader1(this.processRs(rs, "ROW_ID"));
		snapshotSnowAssetDa.setHeader2(this.processRs(rs, "ASSET_NUM"));
		snapshotSnowAssetDa.setHeader3(this.processRs(rs, "SERIAL_NUM"));
		snapshotSnowAssetDa.setHeader4(this.processRs(rs, "X_ATTR_25"));
		snapshotSnowAssetDa.setHeader5(this.processRs(rs, "X_CUST_UCID"));
		snapshotSnowAssetDa.setHeader6(this.processRs(rs, "OU_NUM"));
		snapshotSnowAssetDa.setHeader7(this.processRs(rs, "ACCOUNT_NAME"));
		snapshotSnowAssetDa.setHeader8(this.processRs(rs, "IBX"));
		snapshotSnowAssetDa.setHeader9(this.processRs(rs, "X_UNIQUE_SPACE_ID"));
		snapshotSnowAssetDa.setHeader10(this.processRs(rs, "CAGE_UNIQUE_SPACE_ID"));
		snapshotSnowAssetDa.setHeader11(this.processRs(rs, "X_CAB_USID"));
		snapshotSnowAssetDa.setHeader12(this.processRs(rs, "CAB_UNIQUE_SPACE_ID"));
		snapshotSnowAssetDa.setHeader13(this.processRs(rs, "CAGE_NUMBER"));
		snapshotSnowAssetDa.setHeader14(this.processRs(rs, "X_CAB_ID"));
		snapshotSnowAssetDa.setHeader15(this.processRs(rs, "BASE_CURRENCY_CD"));
		snapshotSnowAssetDa.setHeader16(this.processRs(rs, "SYSTEM_NAME"));
		snapshotSnowAssetDa.setHeader17(rs.getDate("ASSET_INSTALL_DATE"));
		snapshotSnowAssetDa.setHeader18(this.processRs(rs, "STATUS_CD"));
		snapshotSnowAssetDa.setHeader19(this.processRs(rs, "ASSET_SUB_STATUS"));
		snapshotSnowAssetDa.setHeader20(this.processRs(rs, "NAME"));
		snapshotSnowAssetDa.setHeader21(this.processRs(rs, "PAR_ASSET_ID"));
		snapshotSnowAssetDa.setHeader22(this.processRs(rs, "ROOT_ASSET_ID"));
		snapshotSnowAssetDa.setHeader23(this.processRs(rs, "PAR_AST_NUM"));
		snapshotSnowAssetDa.setHeader24(this.processRs(rs, "ROOT_AST_NUM"));
		snapshotSnowAssetDa.setHeader25(this.processRs(rs, "PARENT_PRODUCT_NAME"));
		snapshotSnowAssetDa.setHeader26(this.processRs(rs, "POF_NAME"));
		snapshotSnowAssetDa.setHeader27(this.processRs(rs, "X_OPS_PAR_ASSET_NUM"));
		snapshotSnowAssetDa.setHeader28(this.processRs(rs, "X_ROOT_ASSET_NUM"));
		snapshotSnowAssetDa.setHeader29(this.processRs(rs, "X_ATTR_10"));
		snapshotSnowAssetDa.setHeader30(this.processRs(rs, "X_ATTR_22"));
		snapshotSnowAssetDa.setHeader31(this.processRs(rs, "BILLING_AGR_NUM"));
		snapshotSnowAssetDa.setHeader32(this.processRs(rs, "BILLING_AGR_LN_NUM"));
		snapshotSnowAssetDa.setHeader33(this.processRs(rs, "PRICE"));
		snapshotSnowAssetDa.setHeader34(rs.getDate("CREATED"));
		snapshotSnowAssetDa.setHeader35(this.processRs(rs, "CREATED_BY"));
		snapshotSnowAssetDa.setHeader36(rs.getDate("LAST_UPD"));
		snapshotSnowAssetDa.setHeader37(this.processRs(rs, "LAST_UPD_BY"));
		snapshotSnowAssetDa.setHeader38(this.processRs(rs, "DQM_ERR_FLG"));
		snapshotSnowAssetDa.setHeader39(this.processRs(rs, "PROD_ID"));
		snapshotSnowAssetDa.setHeader40(this.processRs(rs, "X_REL_ACCNT_ID"));
		snapshotSnowAssetDa.setHeader41(this.processRs(rs, "RELATED_ACCNT_NUM"));
		snapshotSnowAssetDa.setHeader42(this.processRs(rs, "RELATED_ACCNT_NAME"));
		snapshotSnowAssetDa.setHeader43(this.processRs(rs, "X_CABLE_ID"));
		snapshotSnowAssetDa.setHeader44(this.processRs(rs, "X_MASS_CREATE_ASSET"));
		snapshotSnowAssetDa.setHeader45(this.processRs(rs, "DESC_TEXT"));
		snapshotSnowAssetDa.setHeader46(this.processRs(rs, "PART_NUM"));
		snapshotSnowAssetDa.setHeader47(this.processRs(rs, "X_MACD_SPLIT_M"));
		snapshotSnowAssetDa.setHeader48(this.processRs(rs, "POF_X_MACD_SPLIT_M"));
		snapshotSnowAssetDa.setHeader49(this.processRs(rs, "POF_PART_NUM"));
		snapshotSnowAssetDa.setHeader50(this.processRs(rs, "POF_STATUS_CD"));
		snapshotSnowAssetDa.setHeader51(this.processRs(rs, "REGION"));
		snapshotSnowAssetDa.setHeader52(this.processRs(rs, "X_SYS_NAME"));
		snapshotSnowAssetDa.setHeader53(this.processRs(rs, "OWNER_ACCNT_ID"));
		snapshotSnowAssetDa.setHeader54(this.processRs(rs, "X_PP_ID"));
		snapshotSnowAssetDa.setHeader55(rs.getDate("END_DT"));
		snapshotSnowAssetDa.setHeader56(this.processRs(rs, "COUNTRY"));
		snapshotSnowAssetDa.setHeader57(this.processRs(rs, "HEADER_01"));
		snapshotSnowAssetDa.setHeader58(this.processRs(rs, "HEADER_02"));
		snapshotSnowAssetDa.setHeader59(this.processRs(rs, "HEADER_03"));
		snapshotSnowAssetDa.setHeader60(this.processRs(rs, "HEADER_04"));
		snapshotSnowAssetDa.setHeader61(this.processRs(rs, "HEADER_05"));
		snapshotSnowAssetDa.setHeader62(this.processRs(rs, "HEADER_06"));
		snapshotSnowAssetDa.setHeader63(this.processRs(rs, "HEADER_07"));
		snapshotSnowAssetDa.setHeader64(this.processRs(rs, "HEADER_08"));
		snapshotSnowAssetDa.setHeader65(this.processRs(rs, "HEADER_09"));
		snapshotSnowAssetDa.setHeader66(this.processRs(rs, "HEADER_10"));
		snapshotSnowAssetDa.setAttr1(this.processRs(rs, "ATTR_1"));
		snapshotSnowAssetDa.setAttr2(this.processRs(rs, "ATTR_2"));
		snapshotSnowAssetDa.setAttr3(this.processRs(rs, "ATTR_3"));
		snapshotSnowAssetDa.setAttr4(this.processRs(rs, "ATTR_4"));
		snapshotSnowAssetDa.setAttr5(this.processRs(rs, "ATTR_5"));
		snapshotSnowAssetDa.setAttr6(this.processRs(rs, "ATTR_6"));
		snapshotSnowAssetDa.setAttr7(this.processRs(rs, "ATTR_7"));
		snapshotSnowAssetDa.setAttr8(this.processRs(rs, "ATTR_8"));
		snapshotSnowAssetDa.setAttr9(this.processRs(rs, "ATTR_9"));
		snapshotSnowAssetDa.setAttr10(this.processRs(rs, "ATTR_10"));
		snapshotSnowAssetDa.setAttr11(this.processRs(rs, "ATTR_11"));
		snapshotSnowAssetDa.setAttr12(this.processRs(rs, "ATTR_12"));
		snapshotSnowAssetDa.setAttr13(this.processRs(rs, "ATTR_13"));
		snapshotSnowAssetDa.setAttr14(this.processRs(rs, "ATTR_14"));
		snapshotSnowAssetDa.setAttr15(this.processRs(rs, "ATTR_15"));
		snapshotSnowAssetDa.setAttr16(this.processRs(rs, "ATTR_16"));
		snapshotSnowAssetDa.setAttr17(this.processRs(rs, "ATTR_17"));
		snapshotSnowAssetDa.setAttr18(this.processRs(rs, "ATTR_18"));
		snapshotSnowAssetDa.setAttr19(this.processRs(rs, "ATTR_19"));
		snapshotSnowAssetDa.setAttr20(this.processRs(rs, "ATTR_20"));
		snapshotSnowAssetDa.setAttr21(this.processRs(rs, "ATTR_21"));
		snapshotSnowAssetDa.setAttr22(this.processRs(rs, "ATTR_22"));
		snapshotSnowAssetDa.setAttr23(this.processRs(rs, "ATTR_23"));
		snapshotSnowAssetDa.setAttr24(this.processRs(rs, "ATTR_24"));
		snapshotSnowAssetDa.setAttr25(this.processRs(rs, "ATTR_25"));
		snapshotSnowAssetDa.setAttr26(this.processRs(rs, "ATTR_26"));
		snapshotSnowAssetDa.setAttr27(this.processRs(rs, "ATTR_27"));
		snapshotSnowAssetDa.setAttr28(this.processRs(rs, "ATTR_28"));
		snapshotSnowAssetDa.setAttr29(this.processRs(rs, "ATTR_29"));
		snapshotSnowAssetDa.setAttr30(this.processRs(rs, "ATTR_30"));
		snapshotSnowAssetDa.setAttr31(this.processRs(rs, "ATTR_31"));
		snapshotSnowAssetDa.setAttr32(this.processRs(rs, "ATTR_32"));
		snapshotSnowAssetDa.setAttr33(this.processRs(rs, "ATTR_33"));
		snapshotSnowAssetDa.setAttr34(this.processRs(rs, "ATTR_34"));
		snapshotSnowAssetDa.setAttr35(this.processRs(rs, "ATTR_35"));
		snapshotSnowAssetDa.setAttr36(this.processRs(rs, "ATTR_36"));
		snapshotSnowAssetDa.setAttr37(this.processRs(rs, "ATTR_37"));
		snapshotSnowAssetDa.setAttr38(this.processRs(rs, "ATTR_38"));
		snapshotSnowAssetDa.setAttr39(this.processRs(rs, "ATTR_39"));
		snapshotSnowAssetDa.setAttr40(this.processRs(rs, "ATTR_40"));
		snapshotSnowAssetDa.setAttr41(this.processRs(rs, "ATTR_41"));
		snapshotSnowAssetDa.setAttr42(this.processRs(rs, "ATTR_42"));
		snapshotSnowAssetDa.setAttr43(this.processRs(rs, "ATTR_43"));
		snapshotSnowAssetDa.setAttr44(this.processRs(rs, "ATTR_44"));
		snapshotSnowAssetDa.setAttr45(this.processRs(rs, "ATTR_45"));
		snapshotSnowAssetDa.setAttr46(this.processRs(rs, "ATTR_46"));
		snapshotSnowAssetDa.setAttr47(this.processRs(rs, "ATTR_47"));
		snapshotSnowAssetDa.setAttr48(this.processRs(rs, "ATTR_48"));
		snapshotSnowAssetDa.setAttr49(this.processRs(rs, "ATTR_49"));
		snapshotSnowAssetDa.setAttr50(this.processRs(rs, "ATTR_50"));
		snapshotSnowAssetDa.setAttr51(this.processRs(rs, "ATTR_51"));
		snapshotSnowAssetDa.setAttr52(this.processRs(rs, "ATTR_52"));
		snapshotSnowAssetDa.setAttr53(this.processRs(rs, "ATTR_53"));
		snapshotSnowAssetDa.setAttr54(this.processRs(rs, "ATTR_54"));
		snapshotSnowAssetDa.setAttr55(this.processRs(rs, "ATTR_55"));
		snapshotSnowAssetDa.setAttr56(this.processRs(rs, "ATTR_56"));
		snapshotSnowAssetDa.setAttr57(this.processRs(rs, "ATTR_57"));
		snapshotSnowAssetDa.setAttr58(this.processRs(rs, "ATTR_58"));
		snapshotSnowAssetDa.setAttr59(this.processRs(rs, "ATTR_59"));
		snapshotSnowAssetDa.setAttr60(this.processRs(rs, "ATTR_60"));
		snapshotSnowAssetDa.setAttr61(this.processRs(rs, "ATTR_61"));
		snapshotSnowAssetDa.setAttr62(this.processRs(rs, "ATTR_62"));
		snapshotSnowAssetDa.setAttr63(this.processRs(rs, "ATTR_63"));
		snapshotSnowAssetDa.setAttr64(this.processRs(rs, "ATTR_64"));
		snapshotSnowAssetDa.setAttr65(this.processRs(rs, "ATTR_65"));
		snapshotSnowAssetDa.setAttr66(this.processRs(rs, "ATTR_66"));
		snapshotSnowAssetDa.setAttr67(this.processRs(rs, "ATTR_67"));
		snapshotSnowAssetDa.setAttr68(this.processRs(rs, "ATTR_68"));
		snapshotSnowAssetDa.setAttr69(this.processRs(rs, "ATTR_69"));
		snapshotSnowAssetDa.setAttr70(this.processRs(rs, "ATTR_70"));
		snapshotSnowAssetDa.setAttr71(this.processRs(rs, "ATTR_71"));
		snapshotSnowAssetDa.setAttr72(this.processRs(rs, "ATTR_72"));
		snapshotSnowAssetDa.setAttr73(this.processRs(rs, "ATTR_73"));
		snapshotSnowAssetDa.setAttr74(this.processRs(rs, "ATTR_74"));
		snapshotSnowAssetDa.setAttr75(this.processRs(rs, "ATTR_75"));
		snapshotSnowAssetDa.setAttr76(this.processRs(rs, "ATTR_76"));
		snapshotSnowAssetDa.setAttr77(this.processRs(rs, "ATTR_77"));
		snapshotSnowAssetDa.setAttr78(this.processRs(rs, "ATTR_78"));
		snapshotSnowAssetDa.setAttr79(this.processRs(rs, "ATTR_79"));
		snapshotSnowAssetDa.setAttr80(this.processRs(rs, "ATTR_80"));
		snapshotSnowAssetDa.setAttr81(this.processRs(rs, "ATTR_81"));
		snapshotSnowAssetDa.setAttr82(this.processRs(rs, "ATTR_82"));
		snapshotSnowAssetDa.setAttr83(this.processRs(rs, "ATTR_83"));
		snapshotSnowAssetDa.setAttr84(this.processRs(rs, "ATTR_84"));
		snapshotSnowAssetDa.setAttr85(this.processRs(rs, "ATTR_85"));
		snapshotSnowAssetDa.setAttr86(this.processRs(rs, "ATTR_86"));
		snapshotSnowAssetDa.setAttr87(this.processRs(rs, "ATTR_87"));
		snapshotSnowAssetDa.setAttr88(this.processRs(rs, "ATTR_88"));
		snapshotSnowAssetDa.setAttr89(this.processRs(rs, "ATTR_89"));
		snapshotSnowAssetDa.setAttr90(this.processRs(rs, "ATTR_90"));
		snapshotSnowAssetDa.setAttr91(this.processRs(rs, "ATTR_91"));
		snapshotSnowAssetDa.setAttr92(this.processRs(rs, "ATTR_92"));
		snapshotSnowAssetDa.setAttr93(this.processRs(rs, "ATTR_93"));
		snapshotSnowAssetDa.setAttr94(this.processRs(rs, "ATTR_94"));
		snapshotSnowAssetDa.setAttr95(this.processRs(rs, "ATTR_95"));
		snapshotSnowAssetDa.setAttr96(this.processRs(rs, "ATTR_96"));
		snapshotSnowAssetDa.setAttr97(this.processRs(rs, "ATTR_97"));
		snapshotSnowAssetDa.setAttr98(this.processRs(rs, "ATTR_98"));
		snapshotSnowAssetDa.setAttr99(this.processRs(rs, "ATTR_99"));
		snapshotSnowAssetDa.setAttr100(this.processRs(rs, "ATTR_100"));
		snapshotSnowAssetDa.setAttr101(this.processRs(rs, "ATTR_101"));
		snapshotSnowAssetDa.setAttr102(this.processRs(rs, "ATTR_102"));
		snapshotSnowAssetDa.setAttr103(this.processRs(rs, "ATTR_103"));
		snapshotSnowAssetDa.setAttr104(this.processRs(rs, "ATTR_104"));
		snapshotSnowAssetDa.setAttr105(this.processRs(rs, "ATTR_105"));
		snapshotSnowAssetDa.setAttr106(this.processRs(rs, "ATTR_106"));
		snapshotSnowAssetDa.setAttr107(this.processRs(rs, "ATTR_107"));
		snapshotSnowAssetDa.setAttr108(this.processRs(rs, "ATTR_108"));
		snapshotSnowAssetDa.setAttr109(this.processRs(rs, "ATTR_109"));
		snapshotSnowAssetDa.setAttr110(this.processRs(rs, "ATTR_110"));
		snapshotSnowAssetDa.setAttr111(this.processRs(rs, "ATTR_111"));
		snapshotSnowAssetDa.setAttr112(this.processRs(rs, "ATTR_112"));
		snapshotSnowAssetDa.setAttr113(this.processRs(rs, "ATTR_113"));
		snapshotSnowAssetDa.setAttr114(this.processRs(rs, "ATTR_114"));
		snapshotSnowAssetDa.setAttr115(this.processRs(rs, "ATTR_115"));
		snapshotSnowAssetDa.setAttr116(this.processRs(rs, "ATTR_116"));
		snapshotSnowAssetDa.setAttr117(this.processRs(rs, "ATTR_117"));
		snapshotSnowAssetDa.setAttr118(this.processRs(rs, "ATTR_118"));
		snapshotSnowAssetDa.setAttr119(this.processRs(rs, "ATTR_119"));
		snapshotSnowAssetDa.setAttr120(this.processRs(rs, "ATTR_120"));
		snapshotSnowAssetDa.setAttr121(this.processRs(rs, "ATTR_121"));
		snapshotSnowAssetDa.setAttr122(this.processRs(rs, "ATTR_122"));
		snapshotSnowAssetDa.setAttr123(this.processRs(rs, "ATTR_123"));
		snapshotSnowAssetDa.setAttr124(this.processRs(rs, "ATTR_124"));
		snapshotSnowAssetDa.setAttr125(this.processRs(rs, "ATTR_125"));
		snapshotSnowAssetDa.setAttr126(this.processRs(rs, "ATTR_126"));
		snapshotSnowAssetDa.setAttr127(this.processRs(rs, "ATTR_127"));
		snapshotSnowAssetDa.setAttr128(this.processRs(rs, "ATTR_128"));
		snapshotSnowAssetDa.setAttr129(this.processRs(rs, "ATTR_129"));
		snapshotSnowAssetDa.setAttr130(this.processRs(rs, "ATTR_130"));
		snapshotSnowAssetDa.setAttr131(this.processRs(rs, "ATTR_131"));
		snapshotSnowAssetDa.setAttr132(this.processRs(rs, "ATTR_132"));
		snapshotSnowAssetDa.setAttr133(this.processRs(rs, "ATTR_133"));
		snapshotSnowAssetDa.setAttr134(this.processRs(rs, "ATTR_134"));
		snapshotSnowAssetDa.setAttr135(this.processRs(rs, "ATTR_135"));
		snapshotSnowAssetDa.setAttr136(this.processRs(rs, "ATTR_136"));
		snapshotSnowAssetDa.setAttr137(this.processRs(rs, "ATTR_137"));
		snapshotSnowAssetDa.setAttr138(this.processRs(rs, "ATTR_138"));
		snapshotSnowAssetDa.setAttr139(this.processRs(rs, "ATTR_139"));
		snapshotSnowAssetDa.setAttr140(this.processRs(rs, "ATTR_140"));
		snapshotSnowAssetDa.setAttr141(this.processRs(rs, "ATTR_141"));
		snapshotSnowAssetDa.setAttr142(this.processRs(rs, "ATTR_142"));
		snapshotSnowAssetDa.setAttr143(this.processRs(rs, "ATTR_143"));
		snapshotSnowAssetDa.setAttr144(this.processRs(rs, "ATTR_144"));
		snapshotSnowAssetDa.setAttr145(this.processRs(rs, "ATTR_145"));
		snapshotSnowAssetDa.setAttr146(this.processRs(rs, "ATTR_146"));
		snapshotSnowAssetDa.setAttr147(this.processRs(rs, "ATTR_147"));
		snapshotSnowAssetDa.setAttr148(this.processRs(rs, "ATTR_148"));
		snapshotSnowAssetDa.setAttr149(this.processRs(rs, "ATTR_149"));
		snapshotSnowAssetDa.setAttr150(this.processRs(rs, "ATTR_150"));
		snapshotSnowAssetDa.setAttr151(this.processRs(rs, "ATTR_151"));
		snapshotSnowAssetDa.setAttr152(this.processRs(rs, "ATTR_152"));
		snapshotSnowAssetDa.setAttr153(this.processRs(rs, "ATTR_153"));
		snapshotSnowAssetDa.setAttr154(this.processRs(rs, "ATTR_154"));
		snapshotSnowAssetDa.setAttr155(this.processRs(rs, "ATTR_155"));
		snapshotSnowAssetDa.setAttr156(this.processRs(rs, "ATTR_156"));
		snapshotSnowAssetDa.setAttr157(this.processRs(rs, "ATTR_157"));
		snapshotSnowAssetDa.setAttr158(this.processRs(rs, "ATTR_158"));
		snapshotSnowAssetDa.setAttr159(this.processRs(rs, "ATTR_159"));
		snapshotSnowAssetDa.setAttr160(this.processRs(rs, "ATTR_160"));
		snapshotSnowAssetDa.setAttr161(this.processRs(rs, "ATTR_161"));
		snapshotSnowAssetDa.setAttr162(this.processRs(rs, "ATTR_162"));
		snapshotSnowAssetDa.setAttr163(this.processRs(rs, "ATTR_163"));
		snapshotSnowAssetDa.setAttr164(this.processRs(rs, "ATTR_164"));
		snapshotSnowAssetDa.setAttr165(this.processRs(rs, "ATTR_165"));
		snapshotSnowAssetDa.setAttr166(this.processRs(rs, "ATTR_166"));
		snapshotSnowAssetDa.setAttr167(this.processRs(rs, "ATTR_167"));
		snapshotSnowAssetDa.setAttr168(this.processRs(rs, "ATTR_168"));
		snapshotSnowAssetDa.setAttr169(this.processRs(rs, "ATTR_169"));
		snapshotSnowAssetDa.setAttr170(this.processRs(rs, "ATTR_170"));
		snapshotSnowAssetDa.setAttr171(this.processRs(rs, "ATTR_171"));
		snapshotSnowAssetDa.setAttr172(this.processRs(rs, "ATTR_172"));
		snapshotSnowAssetDa.setAttr173(this.processRs(rs, "ATTR_173"));
		snapshotSnowAssetDa.setAttr174(this.processRs(rs, "ATTR_174"));
		snapshotSnowAssetDa.setAttr175(this.processRs(rs, "ATTR_175"));
		snapshotSnowAssetDa.setAttr176(this.processRs(rs, "ATTR_176"));
		snapshotSnowAssetDa.setAttr177(this.processRs(rs, "ATTR_177"));
		snapshotSnowAssetDa.setAttr178(this.processRs(rs, "ATTR_178"));
		snapshotSnowAssetDa.setAttr179(this.processRs(rs, "ATTR_179"));
		snapshotSnowAssetDa.setAttr180(this.processRs(rs, "ATTR_180"));
		snapshotSnowAssetDa.setAttr181(this.processRs(rs, "ATTR_181"));
		snapshotSnowAssetDa.setAttr182(this.processRs(rs, "ATTR_182"));
		snapshotSnowAssetDa.setAttr183(this.processRs(rs, "ATTR_183"));
		snapshotSnowAssetDa.setAttr184(this.processRs(rs, "ATTR_184"));
		snapshotSnowAssetDa.setAttr185(this.processRs(rs, "ATTR_185"));
		snapshotSnowAssetDa.setAttr186(this.processRs(rs, "ATTR_186"));
		snapshotSnowAssetDa.setAttr187(this.processRs(rs, "ATTR_187"));
		snapshotSnowAssetDa.setAttr188(this.processRs(rs, "ATTR_188"));
		snapshotSnowAssetDa.setAttr189(this.processRs(rs, "ATTR_189"));
		snapshotSnowAssetDa.setAttr190(this.processRs(rs, "ATTR_190"));
		snapshotSnowAssetDa.setAttr191(this.processRs(rs, "ATTR_191"));
		snapshotSnowAssetDa.setAttr192(this.processRs(rs, "ATTR_192"));
		snapshotSnowAssetDa.setAttr193(this.processRs(rs, "ATTR_193"));
		snapshotSnowAssetDa.setAttr194(this.processRs(rs, "ATTR_194"));
		snapshotSnowAssetDa.setAttr195(this.processRs(rs, "ATTR_195"));
		snapshotSnowAssetDa.setAttr196(this.processRs(rs, "ATTR_196"));
		snapshotSnowAssetDa.setAttr197(this.processRs(rs, "ATTR_197"));
		snapshotSnowAssetDa.setAttr198(this.processRs(rs, "ATTR_198"));
		snapshotSnowAssetDa.setAttr199(this.processRs(rs, "ATTR_199"));
		snapshotSnowAssetDa.setAttr200(this.processRs(rs, "ATTR_200"));
		snapshotSnowAssetDa.setAttr201(this.processRs(rs, "ATTR_201"));
		snapshotSnowAssetDa.setAttr202(this.processRs(rs, "ATTR_202"));
		snapshotSnowAssetDa.setAttr203(this.processRs(rs, "ATTR_203"));
		snapshotSnowAssetDa.setAttr204(this.processRs(rs, "ATTR_204"));
		snapshotSnowAssetDa.setAttr205(this.processRs(rs, "ATTR_205"));
		snapshotSnowAssetDa.setAttr206(this.processRs(rs, "ATTR_206"));
		snapshotSnowAssetDa.setAttr207(this.processRs(rs, "ATTR_207"));
		snapshotSnowAssetDa.setAttr208(this.processRs(rs, "ATTR_208"));
		snapshotSnowAssetDa.setAttr209(this.processRs(rs, "ATTR_209"));
		snapshotSnowAssetDa.setAttr210(this.processRs(rs, "ATTR_210"));
		snapshotSnowAssetDa.setAttr211(this.processRs(rs, "ATTR_211"));
		snapshotSnowAssetDa.setAttr212(this.processRs(rs, "ATTR_212"));
		snapshotSnowAssetDa.setAttr213(this.processRs(rs, "ATTR_213"));
		snapshotSnowAssetDa.setAttr214(this.processRs(rs, "ATTR_214"));
		snapshotSnowAssetDa.setAttr215(this.processRs(rs, "ATTR_215"));
		snapshotSnowAssetDa.setAttr216(this.processRs(rs, "ATTR_216"));
		snapshotSnowAssetDa.setAttr217(this.processRs(rs, "ATTR_217"));
		snapshotSnowAssetDa.setAttr218(this.processRs(rs, "ATTR_218"));
		snapshotSnowAssetDa.setAttr219(this.processRs(rs, "ATTR_219"));
		snapshotSnowAssetDa.setAttr220(this.processRs(rs, "ATTR_220"));
		snapshotSnowAssetDa.setAttr221(this.processRs(rs, "ATTR_221"));
		snapshotSnowAssetDa.setAttr222(this.processRs(rs, "ATTR_222"));
		snapshotSnowAssetDa.setAttr223(this.processRs(rs, "ATTR_223"));
		snapshotSnowAssetDa.setAttr224(this.processRs(rs, "ATTR_224"));
		snapshotSnowAssetDa.setAttr225(this.processRs(rs, "ATTR_225"));
		snapshotSnowAssetDa.setAttr226(this.processRs(rs, "ATTR_226"));
		snapshotSnowAssetDa.setAttr227(this.processRs(rs, "ATTR_227"));
		snapshotSnowAssetDa.setAttr228(this.processRs(rs, "ATTR_228"));
		snapshotSnowAssetDa.setAttr229(this.processRs(rs, "ATTR_229"));
		snapshotSnowAssetDa.setAttr230(this.processRs(rs, "ATTR_230"));
		snapshotSnowAssetDa.setAttr231(this.processRs(rs, "ATTR_231"));
		snapshotSnowAssetDa.setAttr232(this.processRs(rs, "ATTR_232"));
		snapshotSnowAssetDa.setAttr233(this.processRs(rs, "ATTR_233"));
		snapshotSnowAssetDa.setAttr234(this.processRs(rs, "ATTR_234"));
		snapshotSnowAssetDa.setAttr235(this.processRs(rs, "ATTR_235"));
		snapshotSnowAssetDa.setAttr236(this.processRs(rs, "ATTR_236"));
		snapshotSnowAssetDa.setAttr237(this.processRs(rs, "ATTR_237"));
		snapshotSnowAssetDa.setAttr238(this.processRs(rs, "ATTR_238"));
		snapshotSnowAssetDa.setAttr239(this.processRs(rs, "ATTR_239"));
		snapshotSnowAssetDa.setAttr240(this.processRs(rs, "ATTR_240"));
		snapshotSnowAssetDa.setAttr241(this.processRs(rs, "ATTR_241"));
		snapshotSnowAssetDa.setAttr242(this.processRs(rs, "ATTR_242"));
		snapshotSnowAssetDa.setAttr243(this.processRs(rs, "ATTR_243"));
		snapshotSnowAssetDa.setAttr244(this.processRs(rs, "ATTR_244"));
		snapshotSnowAssetDa.setAttr245(this.processRs(rs, "ATTR_245"));
		snapshotSnowAssetDa.setAttr246(this.processRs(rs, "ATTR_246"));
		snapshotSnowAssetDa.setAttr247(this.processRs(rs, "ATTR_247"));
		snapshotSnowAssetDa.setAttr248(this.processRs(rs, "ATTR_248"));
		snapshotSnowAssetDa.setAttr249(this.processRs(rs, "ATTR_249"));
		snapshotSnowAssetDa.setAttr250(this.processRs(rs, "ATTR_250"));
		snapshotSnowAssetDa.setAttr251(this.processRs(rs, "ATTR_251"));
		snapshotSnowAssetDa.setAttr252(this.processRs(rs, "ATTR_252"));
		snapshotSnowAssetDa.setAttr253(this.processRs(rs, "ATTR_253"));
		snapshotSnowAssetDa.setAttr254(this.processRs(rs, "ATTR_254"));
		snapshotSnowAssetDa.setAttr255(this.processRs(rs, "ATTR_255"));
		snapshotSnowAssetDa.setAttr256(this.processRs(rs, "ATTR_256"));
		snapshotSnowAssetDa.setAttr257(this.processRs(rs, "ATTR_257"));
		snapshotSnowAssetDa.setAttr258(this.processRs(rs, "ATTR_258"));
		snapshotSnowAssetDa.setAttr259(this.processRs(rs, "ATTR_259"));
		snapshotSnowAssetDa.setAttr260(this.processRs(rs, "ATTR_260"));
		snapshotSnowAssetDa.setAttr261(this.processRs(rs, "ATTR_261"));
		snapshotSnowAssetDa.setAttr262(this.processRs(rs, "ATTR_262"));
		snapshotSnowAssetDa.setAttr263(this.processRs(rs, "ATTR_263"));
		snapshotSnowAssetDa.setAttr264(this.processRs(rs, "ATTR_264"));
		snapshotSnowAssetDa.setAttr265(this.processRs(rs, "ATTR_265"));
		snapshotSnowAssetDa.setAttr266(this.processRs(rs, "ATTR_266"));
		snapshotSnowAssetDa.setAttr267(this.processRs(rs, "ATTR_267"));
		snapshotSnowAssetDa.setAttr268(this.processRs(rs, "ATTR_268"));
		snapshotSnowAssetDa.setAttr269(this.processRs(rs, "ATTR_269"));
		snapshotSnowAssetDa.setAttr270(this.processRs(rs, "ATTR_270"));
		snapshotSnowAssetDa.setAttr271(this.processRs(rs, "ATTR_271"));
		snapshotSnowAssetDa.setAttr272(this.processRs(rs, "ATTR_272"));
		snapshotSnowAssetDa.setAttr273(this.processRs(rs, "ATTR_273"));
		snapshotSnowAssetDa.setAttr274(this.processRs(rs, "ATTR_274"));
		snapshotSnowAssetDa.setAttr275(this.processRs(rs, "ATTR_275"));
		snapshotSnowAssetDa.setAttr276(this.processRs(rs, "ATTR_276"));
		snapshotSnowAssetDa.setAttr277(this.processRs(rs, "ATTR_277"));
		snapshotSnowAssetDa.setAttr278(this.processRs(rs, "ATTR_278"));
		snapshotSnowAssetDa.setAttr279(this.processRs(rs, "ATTR_279"));
		snapshotSnowAssetDa.setAttr280(this.processRs(rs, "ATTR_280"));
		snapshotSnowAssetDa.setAttr281(this.processRs(rs, "ATTR_281"));
		snapshotSnowAssetDa.setAttr282(this.processRs(rs, "ATTR_282"));
		snapshotSnowAssetDa.setAttr283(this.processRs(rs, "ATTR_283"));
		snapshotSnowAssetDa.setAttr284(this.processRs(rs, "ATTR_284"));
		snapshotSnowAssetDa.setAttr285(this.processRs(rs, "ATTR_285"));
		snapshotSnowAssetDa.setAttr286(this.processRs(rs, "ATTR_286"));
		snapshotSnowAssetDa.setAttr287(this.processRs(rs, "ATTR_287"));
		snapshotSnowAssetDa.setAttr288(this.processRs(rs, "ATTR_288"));
		snapshotSnowAssetDa.setAttr289(this.processRs(rs, "ATTR_289"));
		snapshotSnowAssetDa.setAttr290(this.processRs(rs, "ATTR_290"));
		snapshotSnowAssetDa.setAttr291(this.processRs(rs, "ATTR_291"));
		snapshotSnowAssetDa.setAttr292(this.processRs(rs, "ATTR_292"));
		snapshotSnowAssetDa.setAttr293(this.processRs(rs, "ATTR_293"));
		snapshotSnowAssetDa.setAttr294(this.processRs(rs, "ATTR_294"));
		snapshotSnowAssetDa.setAttr295(this.processRs(rs, "ATTR_295"));
		snapshotSnowAssetDa.setAttr296(this.processRs(rs, "ATTR_296"));
		snapshotSnowAssetDa.setAttr297(this.processRs(rs, "ATTR_297"));
		snapshotSnowAssetDa.setAttr298(this.processRs(rs, "ATTR_298"));
		snapshotSnowAssetDa.setAttr299(this.processRs(rs, "ATTR_299"));
		snapshotSnowAssetDa.setAttr300(this.processRs(rs, "ATTR_300"));
		snapshotSnowAssetDa.setAttr301(this.processRs(rs, "ATTR_301"));
		snapshotSnowAssetDa.setAttr302(this.processRs(rs, "ATTR_302"));
		snapshotSnowAssetDa.setAttr303(this.processRs(rs, "ATTR_303"));
		snapshotSnowAssetDa.setAttr304(this.processRs(rs, "ATTR_304"));
		snapshotSnowAssetDa.setAttr305(this.processRs(rs, "ATTR_305"));
		snapshotSnowAssetDa.setAttr306(this.processRs(rs, "ATTR_306"));
		snapshotSnowAssetDa.setAttr307(this.processRs(rs, "ATTR_307"));
		snapshotSnowAssetDa.setAttr308(this.processRs(rs, "ATTR_308"));
		snapshotSnowAssetDa.setAttr309(this.processRs(rs, "ATTR_309"));
		snapshotSnowAssetDa.setAttr310(this.processRs(rs, "ATTR_310"));
		snapshotSnowAssetDa.setAttr311(this.processRs(rs, "ATTR_311"));
		snapshotSnowAssetDa.setAttr312(this.processRs(rs, "ATTR_312"));
		snapshotSnowAssetDa.setAttr313(this.processRs(rs, "ATTR_313"));
		snapshotSnowAssetDa.setAttr314(this.processRs(rs, "ATTR_314"));
		snapshotSnowAssetDa.setAttr315(this.processRs(rs, "ATTR_315"));
		snapshotSnowAssetDa.setAttr316(this.processRs(rs, "ATTR_316"));
		snapshotSnowAssetDa.setAttr317(this.processRs(rs, "ATTR_317"));
		snapshotSnowAssetDa.setAttr318(this.processRs(rs, "ATTR_318"));
		snapshotSnowAssetDa.setAttr319(this.processRs(rs, "ATTR_319"));
		snapshotSnowAssetDa.setAttr320(this.processRs(rs, "ATTR_320"));
		snapshotSnowAssetDa.setAttr321(this.processRs(rs, "ATTR_321"));
		snapshotSnowAssetDa.setAttr322(this.processRs(rs, "ATTR_322"));
		snapshotSnowAssetDa.setAttr323(this.processRs(rs, "ATTR_323"));
		snapshotSnowAssetDa.setAttr324(this.processRs(rs, "ATTR_324"));
		snapshotSnowAssetDa.setAttr325(this.processRs(rs, "ATTR_325"));
		snapshotSnowAssetDa.setAttr326(this.processRs(rs, "ATTR_326"));
		snapshotSnowAssetDa.setAttr327(this.processRs(rs, "ATTR_327"));
		snapshotSnowAssetDa.setAttr328(this.processRs(rs, "ATTR_328"));
		snapshotSnowAssetDa.setAttr329(this.processRs(rs, "ATTR_329"));
		snapshotSnowAssetDa.setAttr330(this.processRs(rs, "ATTR_330"));
		snapshotSnowAssetDa.setAttr331(this.processRs(rs, "ATTR_331"));
		snapshotSnowAssetDa.setAttr332(this.processRs(rs, "ATTR_332"));
		snapshotSnowAssetDa.setAttr333(this.processRs(rs, "ATTR_333"));
		snapshotSnowAssetDa.setAttr334(this.processRs(rs, "ATTR_334"));
		snapshotSnowAssetDa.setAttr335(this.processRs(rs, "ATTR_335"));
		snapshotSnowAssetDa.setAttr336(this.processRs(rs, "ATTR_336"));
		snapshotSnowAssetDa.setAttr337(this.processRs(rs, "ATTR_337"));
		snapshotSnowAssetDa.setAttr338(this.processRs(rs, "ATTR_338"));
		snapshotSnowAssetDa.setAttr339(this.processRs(rs, "ATTR_339"));
		snapshotSnowAssetDa.setAttr340(this.processRs(rs, "ATTR_340"));
		snapshotSnowAssetDa.setAttr341(this.processRs(rs, "ATTR_341"));
		snapshotSnowAssetDa.setAttr342(this.processRs(rs, "ATTR_342"));
		snapshotSnowAssetDa.setAttr343(this.processRs(rs, "ATTR_343"));
		snapshotSnowAssetDa.setAttr344(this.processRs(rs, "ATTR_344"));
		snapshotSnowAssetDa.setAttr345(this.processRs(rs, "ATTR_345"));
		snapshotSnowAssetDa.setAttr346(this.processRs(rs, "ATTR_346"));
		snapshotSnowAssetDa.setAttr347(this.processRs(rs, "ATTR_347"));
		snapshotSnowAssetDa.setAttr348(this.processRs(rs, "ATTR_348"));
		snapshotSnowAssetDa.setAttr349(this.processRs(rs, "ATTR_349"));
		snapshotSnowAssetDa.setAttr350(this.processRs(rs, "ATTR_350"));


		return snapshotSnowAssetDa;
	}
	
	private String processRs(ResultSet rs , String columnName){
		try{
		   return rs.getString(columnName);
		}catch(Exception e){
		   return CacheConstant.BLANK;	
		}
	}

}
