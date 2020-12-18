package com.equinix.appops.dart.portal.mapper.pro;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.equinix.appops.dart.portal.constant.CacheConstant;
import com.equinix.appops.dart.portal.entity.SnapshotClxAssetDa;

public class ClxSnapshotMapper implements RowMapper<SnapshotClxAssetDa> {

	@Override
	public SnapshotClxAssetDa mapRow(ResultSet rs, int arg1) throws SQLException {
		SnapshotClxAssetDa snapshotClxAssetDa = new SnapshotClxAssetDa();
		snapshotClxAssetDa.setDfrLineId(this.processRs(rs, "DFR_LINE_ID"));
		snapshotClxAssetDa.setDfrId(this.processRs(rs, "DFR_ID"));
		snapshotClxAssetDa.setHeader1(this.processRs(rs, "ROW_ID"));
		snapshotClxAssetDa.setHeader2(this.processRs(rs, "ASSET_NUM"));
		snapshotClxAssetDa.setHeader3(this.processRs(rs, "SERIAL_NUM"));
		snapshotClxAssetDa.setHeader4(this.processRs(rs, "X_ATTR_25"));
		snapshotClxAssetDa.setHeader5(this.processRs(rs, "X_CUST_UCID"));
		snapshotClxAssetDa.setHeader6(this.processRs(rs, "OU_NUM"));
		snapshotClxAssetDa.setHeader7(this.processRs(rs, "ACCOUNT_NAME"));
		snapshotClxAssetDa.setHeader8(this.processRs(rs, "IBX"));
		snapshotClxAssetDa.setHeader9(this.processRs(rs, "X_UNIQUE_SPACE_ID"));
		snapshotClxAssetDa.setHeader10(this.processRs(rs, "CAGE_UNIQUE_SPACE_ID"));
		snapshotClxAssetDa.setHeader11(this.processRs(rs, "X_CAB_USID"));
		snapshotClxAssetDa.setHeader12(this.processRs(rs, "CAB_UNIQUE_SPACE_ID"));
		snapshotClxAssetDa.setHeader13(this.processRs(rs, "CAGE_NUMBER"));
		snapshotClxAssetDa.setHeader14(this.processRs(rs, "X_CAB_ID"));
		snapshotClxAssetDa.setHeader15(this.processRs(rs, "BASE_CURRENCY_CD"));
		snapshotClxAssetDa.setHeader16(this.processRs(rs, "SYSTEM_NAME"));
		snapshotClxAssetDa.setHeader17(rs.getDate("ASSET_INSTALL_DATE"));
		snapshotClxAssetDa.setHeader18(this.processRs(rs, "STATUS_CD"));
		snapshotClxAssetDa.setHeader19(this.processRs(rs, "ASSET_SUB_STATUS"));
		snapshotClxAssetDa.setHeader20(this.processRs(rs, "NAME"));
		snapshotClxAssetDa.setHeader21(this.processRs(rs, "PAR_ASSET_ID"));
		snapshotClxAssetDa.setHeader22(this.processRs(rs, "ROOT_ASSET_ID"));
		snapshotClxAssetDa.setHeader23(this.processRs(rs, "PAR_AST_NUM"));
		snapshotClxAssetDa.setHeader24(this.processRs(rs, "ROOT_AST_NUM"));
		snapshotClxAssetDa.setHeader25(this.processRs(rs, "PARENT_PRODUCT_NAME"));
		snapshotClxAssetDa.setHeader26(this.processRs(rs, "POF_NAME"));
		snapshotClxAssetDa.setHeader27(this.processRs(rs, "X_OPS_PAR_ASSET_NUM"));
		snapshotClxAssetDa.setHeader28(this.processRs(rs, "X_ROOT_ASSET_NUM"));
		snapshotClxAssetDa.setHeader29(this.processRs(rs, "X_ATTR_10"));
		snapshotClxAssetDa.setHeader30(this.processRs(rs, "X_ATTR_22"));
		snapshotClxAssetDa.setHeader31(this.processRs(rs, "BILLING_AGR_NUM"));
		snapshotClxAssetDa.setHeader32(this.processRs(rs, "BILLING_AGR_LN_NUM"));
		snapshotClxAssetDa.setHeader33(this.processRs(rs, "PRICE"));
		snapshotClxAssetDa.setHeader34(rs.getDate("CREATED"));
		snapshotClxAssetDa.setHeader35(this.processRs(rs, "CREATED_BY"));
		snapshotClxAssetDa.setHeader36(rs.getDate("LAST_UPD"));
		snapshotClxAssetDa.setHeader37(this.processRs(rs, "LAST_UPD_BY"));
		snapshotClxAssetDa.setHeader38(this.processRs(rs, "DQM_ERR_FLG"));
		snapshotClxAssetDa.setHeader39(this.processRs(rs, "PROD_ID"));
		snapshotClxAssetDa.setHeader40(this.processRs(rs, "X_REL_ACCNT_ID"));
		snapshotClxAssetDa.setHeader41(this.processRs(rs, "RELATED_ACCNT_NUM"));
		snapshotClxAssetDa.setHeader42(this.processRs(rs, "RELATED_ACCNT_NAME"));
		snapshotClxAssetDa.setHeader43(this.processRs(rs, "X_CABLE_ID"));
		snapshotClxAssetDa.setHeader44(this.processRs(rs, "X_MASS_CREATE_ASSET"));
		snapshotClxAssetDa.setHeader45(this.processRs(rs, "DESC_TEXT"));
		snapshotClxAssetDa.setHeader46(this.processRs(rs, "PART_NUM"));
		snapshotClxAssetDa.setHeader47(this.processRs(rs, "X_MACD_SPLIT_M"));
		snapshotClxAssetDa.setHeader48(this.processRs(rs, "POF_X_MACD_SPLIT_M"));
		snapshotClxAssetDa.setHeader49(this.processRs(rs, "POF_PART_NUM"));
		snapshotClxAssetDa.setHeader50(this.processRs(rs, "POF_STATUS_CD"));
		snapshotClxAssetDa.setHeader51(this.processRs(rs, "REGION"));
		snapshotClxAssetDa.setHeader52(this.processRs(rs, "X_SYS_NAME"));
		snapshotClxAssetDa.setHeader53(this.processRs(rs, "OWNER_ACCNT_ID"));
		snapshotClxAssetDa.setHeader54(this.processRs(rs, "X_PP_ID"));
		snapshotClxAssetDa.setHeader55(rs.getDate("END_DT"));
		snapshotClxAssetDa.setHeader56(this.processRs(rs, "COUNTRY"));
		snapshotClxAssetDa.setHeader57(this.processRs(rs, "HEADER_01"));
		snapshotClxAssetDa.setHeader58(this.processRs(rs, "HEADER_02"));
		snapshotClxAssetDa.setHeader59(this.processRs(rs, "HEADER_03"));
		snapshotClxAssetDa.setHeader60(this.processRs(rs, "HEADER_04"));
		snapshotClxAssetDa.setHeader61(this.processRs(rs, "HEADER_05"));
		snapshotClxAssetDa.setHeader62(this.processRs(rs, "HEADER_06"));
		snapshotClxAssetDa.setHeader63(this.processRs(rs, "HEADER_07"));
		snapshotClxAssetDa.setHeader64(this.processRs(rs, "HEADER_08"));
		snapshotClxAssetDa.setHeader65(this.processRs(rs, "HEADER_09"));
		snapshotClxAssetDa.setHeader66(this.processRs(rs, "HEADER_10"));
		snapshotClxAssetDa.setAttr1(this.processRs(rs, "ATTR_1"));
		snapshotClxAssetDa.setAttr2(this.processRs(rs, "ATTR_2"));
		snapshotClxAssetDa.setAttr3(this.processRs(rs, "ATTR_3"));
		snapshotClxAssetDa.setAttr4(this.processRs(rs, "ATTR_4"));
		snapshotClxAssetDa.setAttr5(this.processRs(rs, "ATTR_5"));
		snapshotClxAssetDa.setAttr6(this.processRs(rs, "ATTR_6"));
		snapshotClxAssetDa.setAttr7(this.processRs(rs, "ATTR_7"));
		snapshotClxAssetDa.setAttr8(this.processRs(rs, "ATTR_8"));
		snapshotClxAssetDa.setAttr9(this.processRs(rs, "ATTR_9"));
		snapshotClxAssetDa.setAttr10(this.processRs(rs, "ATTR_10"));
		snapshotClxAssetDa.setAttr11(this.processRs(rs, "ATTR_11"));
		snapshotClxAssetDa.setAttr12(this.processRs(rs, "ATTR_12"));
		snapshotClxAssetDa.setAttr13(this.processRs(rs, "ATTR_13"));
		snapshotClxAssetDa.setAttr14(this.processRs(rs, "ATTR_14"));
		snapshotClxAssetDa.setAttr15(this.processRs(rs, "ATTR_15"));
		snapshotClxAssetDa.setAttr16(this.processRs(rs, "ATTR_16"));
		snapshotClxAssetDa.setAttr17(this.processRs(rs, "ATTR_17"));
		snapshotClxAssetDa.setAttr18(this.processRs(rs, "ATTR_18"));
		snapshotClxAssetDa.setAttr19(this.processRs(rs, "ATTR_19"));
		snapshotClxAssetDa.setAttr20(this.processRs(rs, "ATTR_20"));
		snapshotClxAssetDa.setAttr21(this.processRs(rs, "ATTR_21"));
		snapshotClxAssetDa.setAttr22(this.processRs(rs, "ATTR_22"));
		snapshotClxAssetDa.setAttr23(this.processRs(rs, "ATTR_23"));
		snapshotClxAssetDa.setAttr24(this.processRs(rs, "ATTR_24"));
		snapshotClxAssetDa.setAttr25(this.processRs(rs, "ATTR_25"));
		snapshotClxAssetDa.setAttr26(this.processRs(rs, "ATTR_26"));
		snapshotClxAssetDa.setAttr27(this.processRs(rs, "ATTR_27"));
		snapshotClxAssetDa.setAttr28(this.processRs(rs, "ATTR_28"));
		snapshotClxAssetDa.setAttr29(this.processRs(rs, "ATTR_29"));
		snapshotClxAssetDa.setAttr30(this.processRs(rs, "ATTR_30"));
		snapshotClxAssetDa.setAttr31(this.processRs(rs, "ATTR_31"));
		snapshotClxAssetDa.setAttr32(this.processRs(rs, "ATTR_32"));
		snapshotClxAssetDa.setAttr33(this.processRs(rs, "ATTR_33"));
		snapshotClxAssetDa.setAttr34(this.processRs(rs, "ATTR_34"));
		snapshotClxAssetDa.setAttr35(this.processRs(rs, "ATTR_35"));
		snapshotClxAssetDa.setAttr36(this.processRs(rs, "ATTR_36"));
		snapshotClxAssetDa.setAttr37(this.processRs(rs, "ATTR_37"));
		snapshotClxAssetDa.setAttr38(this.processRs(rs, "ATTR_38"));
		snapshotClxAssetDa.setAttr39(this.processRs(rs, "ATTR_39"));
		snapshotClxAssetDa.setAttr40(this.processRs(rs, "ATTR_40"));
		snapshotClxAssetDa.setAttr41(this.processRs(rs, "ATTR_41"));
		snapshotClxAssetDa.setAttr42(this.processRs(rs, "ATTR_42"));
		snapshotClxAssetDa.setAttr43(this.processRs(rs, "ATTR_43"));
		snapshotClxAssetDa.setAttr44(this.processRs(rs, "ATTR_44"));
		snapshotClxAssetDa.setAttr45(this.processRs(rs, "ATTR_45"));
		snapshotClxAssetDa.setAttr46(this.processRs(rs, "ATTR_46"));
		snapshotClxAssetDa.setAttr47(this.processRs(rs, "ATTR_47"));
		snapshotClxAssetDa.setAttr48(this.processRs(rs, "ATTR_48"));
		snapshotClxAssetDa.setAttr49(this.processRs(rs, "ATTR_49"));
		snapshotClxAssetDa.setAttr50(this.processRs(rs, "ATTR_50"));
		snapshotClxAssetDa.setAttr51(this.processRs(rs, "ATTR_51"));
		snapshotClxAssetDa.setAttr52(this.processRs(rs, "ATTR_52"));
		snapshotClxAssetDa.setAttr53(this.processRs(rs, "ATTR_53"));
		snapshotClxAssetDa.setAttr54(this.processRs(rs, "ATTR_54"));
		snapshotClxAssetDa.setAttr55(this.processRs(rs, "ATTR_55"));
		snapshotClxAssetDa.setAttr56(this.processRs(rs, "ATTR_56"));
		snapshotClxAssetDa.setAttr57(this.processRs(rs, "ATTR_57"));
		snapshotClxAssetDa.setAttr58(this.processRs(rs, "ATTR_58"));
		snapshotClxAssetDa.setAttr59(this.processRs(rs, "ATTR_59"));
		snapshotClxAssetDa.setAttr60(this.processRs(rs, "ATTR_60"));
		snapshotClxAssetDa.setAttr61(this.processRs(rs, "ATTR_61"));
		snapshotClxAssetDa.setAttr62(this.processRs(rs, "ATTR_62"));
		snapshotClxAssetDa.setAttr63(this.processRs(rs, "ATTR_63"));
		snapshotClxAssetDa.setAttr64(this.processRs(rs, "ATTR_64"));
		snapshotClxAssetDa.setAttr65(this.processRs(rs, "ATTR_65"));
		snapshotClxAssetDa.setAttr66(this.processRs(rs, "ATTR_66"));
		snapshotClxAssetDa.setAttr67(this.processRs(rs, "ATTR_67"));
		snapshotClxAssetDa.setAttr68(this.processRs(rs, "ATTR_68"));
		snapshotClxAssetDa.setAttr69(this.processRs(rs, "ATTR_69"));
		snapshotClxAssetDa.setAttr70(this.processRs(rs, "ATTR_70"));
		snapshotClxAssetDa.setAttr71(this.processRs(rs, "ATTR_71"));
		snapshotClxAssetDa.setAttr72(this.processRs(rs, "ATTR_72"));
		snapshotClxAssetDa.setAttr73(this.processRs(rs, "ATTR_73"));
		snapshotClxAssetDa.setAttr74(this.processRs(rs, "ATTR_74"));
		snapshotClxAssetDa.setAttr75(this.processRs(rs, "ATTR_75"));
		snapshotClxAssetDa.setAttr76(this.processRs(rs, "ATTR_76"));
		snapshotClxAssetDa.setAttr77(this.processRs(rs, "ATTR_77"));
		snapshotClxAssetDa.setAttr78(this.processRs(rs, "ATTR_78"));
		snapshotClxAssetDa.setAttr79(this.processRs(rs, "ATTR_79"));
		snapshotClxAssetDa.setAttr80(this.processRs(rs, "ATTR_80"));
		snapshotClxAssetDa.setAttr81(this.processRs(rs, "ATTR_81"));
		snapshotClxAssetDa.setAttr82(this.processRs(rs, "ATTR_82"));
		snapshotClxAssetDa.setAttr83(this.processRs(rs, "ATTR_83"));
		snapshotClxAssetDa.setAttr84(this.processRs(rs, "ATTR_84"));
		snapshotClxAssetDa.setAttr85(this.processRs(rs, "ATTR_85"));
		snapshotClxAssetDa.setAttr86(this.processRs(rs, "ATTR_86"));
		snapshotClxAssetDa.setAttr87(this.processRs(rs, "ATTR_87"));
		snapshotClxAssetDa.setAttr88(this.processRs(rs, "ATTR_88"));
		snapshotClxAssetDa.setAttr89(this.processRs(rs, "ATTR_89"));
		snapshotClxAssetDa.setAttr90(this.processRs(rs, "ATTR_90"));
		snapshotClxAssetDa.setAttr91(this.processRs(rs, "ATTR_91"));
		snapshotClxAssetDa.setAttr92(this.processRs(rs, "ATTR_92"));
		snapshotClxAssetDa.setAttr93(this.processRs(rs, "ATTR_93"));
		snapshotClxAssetDa.setAttr94(this.processRs(rs, "ATTR_94"));
		snapshotClxAssetDa.setAttr95(this.processRs(rs, "ATTR_95"));
		snapshotClxAssetDa.setAttr96(this.processRs(rs, "ATTR_96"));
		snapshotClxAssetDa.setAttr97(this.processRs(rs, "ATTR_97"));
		snapshotClxAssetDa.setAttr98(this.processRs(rs, "ATTR_98"));
		snapshotClxAssetDa.setAttr99(this.processRs(rs, "ATTR_99"));
		snapshotClxAssetDa.setAttr100(this.processRs(rs, "ATTR_100"));
		snapshotClxAssetDa.setAttr101(this.processRs(rs, "ATTR_101"));
		snapshotClxAssetDa.setAttr102(this.processRs(rs, "ATTR_102"));
		snapshotClxAssetDa.setAttr103(this.processRs(rs, "ATTR_103"));
		snapshotClxAssetDa.setAttr104(this.processRs(rs, "ATTR_104"));
		snapshotClxAssetDa.setAttr105(this.processRs(rs, "ATTR_105"));
		snapshotClxAssetDa.setAttr106(this.processRs(rs, "ATTR_106"));
		snapshotClxAssetDa.setAttr107(this.processRs(rs, "ATTR_107"));
		snapshotClxAssetDa.setAttr108(this.processRs(rs, "ATTR_108"));
		snapshotClxAssetDa.setAttr109(this.processRs(rs, "ATTR_109"));
		snapshotClxAssetDa.setAttr110(this.processRs(rs, "ATTR_110"));
		snapshotClxAssetDa.setAttr111(this.processRs(rs, "ATTR_111"));
		snapshotClxAssetDa.setAttr112(this.processRs(rs, "ATTR_112"));
		snapshotClxAssetDa.setAttr113(this.processRs(rs, "ATTR_113"));
		snapshotClxAssetDa.setAttr114(this.processRs(rs, "ATTR_114"));
		snapshotClxAssetDa.setAttr115(this.processRs(rs, "ATTR_115"));
		snapshotClxAssetDa.setAttr116(this.processRs(rs, "ATTR_116"));
		snapshotClxAssetDa.setAttr117(this.processRs(rs, "ATTR_117"));
		snapshotClxAssetDa.setAttr118(this.processRs(rs, "ATTR_118"));
		snapshotClxAssetDa.setAttr119(this.processRs(rs, "ATTR_119"));
		snapshotClxAssetDa.setAttr120(this.processRs(rs, "ATTR_120"));
		snapshotClxAssetDa.setAttr121(this.processRs(rs, "ATTR_121"));
		snapshotClxAssetDa.setAttr122(this.processRs(rs, "ATTR_122"));
		snapshotClxAssetDa.setAttr123(this.processRs(rs, "ATTR_123"));
		snapshotClxAssetDa.setAttr124(this.processRs(rs, "ATTR_124"));
		snapshotClxAssetDa.setAttr125(this.processRs(rs, "ATTR_125"));
		snapshotClxAssetDa.setAttr126(this.processRs(rs, "ATTR_126"));
		snapshotClxAssetDa.setAttr127(this.processRs(rs, "ATTR_127"));
		snapshotClxAssetDa.setAttr128(this.processRs(rs, "ATTR_128"));
		snapshotClxAssetDa.setAttr129(this.processRs(rs, "ATTR_129"));
		snapshotClxAssetDa.setAttr130(this.processRs(rs, "ATTR_130"));
		snapshotClxAssetDa.setAttr131(this.processRs(rs, "ATTR_131"));
		snapshotClxAssetDa.setAttr132(this.processRs(rs, "ATTR_132"));
		snapshotClxAssetDa.setAttr133(this.processRs(rs, "ATTR_133"));
		snapshotClxAssetDa.setAttr134(this.processRs(rs, "ATTR_134"));
		snapshotClxAssetDa.setAttr135(this.processRs(rs, "ATTR_135"));
		snapshotClxAssetDa.setAttr136(this.processRs(rs, "ATTR_136"));
		snapshotClxAssetDa.setAttr137(this.processRs(rs, "ATTR_137"));
		snapshotClxAssetDa.setAttr138(this.processRs(rs, "ATTR_138"));
		snapshotClxAssetDa.setAttr139(this.processRs(rs, "ATTR_139"));
		snapshotClxAssetDa.setAttr140(this.processRs(rs, "ATTR_140"));
		snapshotClxAssetDa.setAttr141(this.processRs(rs, "ATTR_141"));
		snapshotClxAssetDa.setAttr142(this.processRs(rs, "ATTR_142"));
		snapshotClxAssetDa.setAttr143(this.processRs(rs, "ATTR_143"));
		snapshotClxAssetDa.setAttr144(this.processRs(rs, "ATTR_144"));
		snapshotClxAssetDa.setAttr145(this.processRs(rs, "ATTR_145"));
		snapshotClxAssetDa.setAttr146(this.processRs(rs, "ATTR_146"));
		snapshotClxAssetDa.setAttr147(this.processRs(rs, "ATTR_147"));
		snapshotClxAssetDa.setAttr148(this.processRs(rs, "ATTR_148"));
		snapshotClxAssetDa.setAttr149(this.processRs(rs, "ATTR_149"));
		snapshotClxAssetDa.setAttr150(this.processRs(rs, "ATTR_150"));
		snapshotClxAssetDa.setAttr151(this.processRs(rs, "ATTR_151"));
		snapshotClxAssetDa.setAttr152(this.processRs(rs, "ATTR_152"));
		snapshotClxAssetDa.setAttr153(this.processRs(rs, "ATTR_153"));
		snapshotClxAssetDa.setAttr154(this.processRs(rs, "ATTR_154"));
		snapshotClxAssetDa.setAttr155(this.processRs(rs, "ATTR_155"));
		snapshotClxAssetDa.setAttr156(this.processRs(rs, "ATTR_156"));
		snapshotClxAssetDa.setAttr157(this.processRs(rs, "ATTR_157"));
		snapshotClxAssetDa.setAttr158(this.processRs(rs, "ATTR_158"));
		snapshotClxAssetDa.setAttr159(this.processRs(rs, "ATTR_159"));
		snapshotClxAssetDa.setAttr160(this.processRs(rs, "ATTR_160"));
		snapshotClxAssetDa.setAttr161(this.processRs(rs, "ATTR_161"));
		snapshotClxAssetDa.setAttr162(this.processRs(rs, "ATTR_162"));
		snapshotClxAssetDa.setAttr163(this.processRs(rs, "ATTR_163"));
		snapshotClxAssetDa.setAttr164(this.processRs(rs, "ATTR_164"));
		snapshotClxAssetDa.setAttr165(this.processRs(rs, "ATTR_165"));
		snapshotClxAssetDa.setAttr166(this.processRs(rs, "ATTR_166"));
		snapshotClxAssetDa.setAttr167(this.processRs(rs, "ATTR_167"));
		snapshotClxAssetDa.setAttr168(this.processRs(rs, "ATTR_168"));
		snapshotClxAssetDa.setAttr169(this.processRs(rs, "ATTR_169"));
		snapshotClxAssetDa.setAttr170(this.processRs(rs, "ATTR_170"));
		snapshotClxAssetDa.setAttr171(this.processRs(rs, "ATTR_171"));
		snapshotClxAssetDa.setAttr172(this.processRs(rs, "ATTR_172"));
		snapshotClxAssetDa.setAttr173(this.processRs(rs, "ATTR_173"));
		snapshotClxAssetDa.setAttr174(this.processRs(rs, "ATTR_174"));
		snapshotClxAssetDa.setAttr175(this.processRs(rs, "ATTR_175"));
		snapshotClxAssetDa.setAttr176(this.processRs(rs, "ATTR_176"));
		snapshotClxAssetDa.setAttr177(this.processRs(rs, "ATTR_177"));
		snapshotClxAssetDa.setAttr178(this.processRs(rs, "ATTR_178"));
		snapshotClxAssetDa.setAttr179(this.processRs(rs, "ATTR_179"));
		snapshotClxAssetDa.setAttr180(this.processRs(rs, "ATTR_180"));
		snapshotClxAssetDa.setAttr181(this.processRs(rs, "ATTR_181"));
		snapshotClxAssetDa.setAttr182(this.processRs(rs, "ATTR_182"));
		snapshotClxAssetDa.setAttr183(this.processRs(rs, "ATTR_183"));
		snapshotClxAssetDa.setAttr184(this.processRs(rs, "ATTR_184"));
		snapshotClxAssetDa.setAttr185(this.processRs(rs, "ATTR_185"));
		snapshotClxAssetDa.setAttr186(this.processRs(rs, "ATTR_186"));
		snapshotClxAssetDa.setAttr187(this.processRs(rs, "ATTR_187"));
		snapshotClxAssetDa.setAttr188(this.processRs(rs, "ATTR_188"));
		snapshotClxAssetDa.setAttr189(this.processRs(rs, "ATTR_189"));
		snapshotClxAssetDa.setAttr190(this.processRs(rs, "ATTR_190"));
		snapshotClxAssetDa.setAttr191(this.processRs(rs, "ATTR_191"));
		snapshotClxAssetDa.setAttr192(this.processRs(rs, "ATTR_192"));
		snapshotClxAssetDa.setAttr193(this.processRs(rs, "ATTR_193"));
		snapshotClxAssetDa.setAttr194(this.processRs(rs, "ATTR_194"));
		snapshotClxAssetDa.setAttr195(this.processRs(rs, "ATTR_195"));
		snapshotClxAssetDa.setAttr196(this.processRs(rs, "ATTR_196"));
		snapshotClxAssetDa.setAttr197(this.processRs(rs, "ATTR_197"));
		snapshotClxAssetDa.setAttr198(this.processRs(rs, "ATTR_198"));
		snapshotClxAssetDa.setAttr199(this.processRs(rs, "ATTR_199"));
		snapshotClxAssetDa.setAttr200(this.processRs(rs, "ATTR_200"));
		snapshotClxAssetDa.setAttr201(this.processRs(rs, "ATTR_201"));
		snapshotClxAssetDa.setAttr202(this.processRs(rs, "ATTR_202"));
		snapshotClxAssetDa.setAttr203(this.processRs(rs, "ATTR_203"));
		snapshotClxAssetDa.setAttr204(this.processRs(rs, "ATTR_204"));
		snapshotClxAssetDa.setAttr205(this.processRs(rs, "ATTR_205"));
		snapshotClxAssetDa.setAttr206(this.processRs(rs, "ATTR_206"));
		snapshotClxAssetDa.setAttr207(this.processRs(rs, "ATTR_207"));
		snapshotClxAssetDa.setAttr208(this.processRs(rs, "ATTR_208"));
		snapshotClxAssetDa.setAttr209(this.processRs(rs, "ATTR_209"));
		snapshotClxAssetDa.setAttr210(this.processRs(rs, "ATTR_210"));
		snapshotClxAssetDa.setAttr211(this.processRs(rs, "ATTR_211"));
		snapshotClxAssetDa.setAttr212(this.processRs(rs, "ATTR_212"));
		snapshotClxAssetDa.setAttr213(this.processRs(rs, "ATTR_213"));
		snapshotClxAssetDa.setAttr214(this.processRs(rs, "ATTR_214"));
		snapshotClxAssetDa.setAttr215(this.processRs(rs, "ATTR_215"));
		snapshotClxAssetDa.setAttr216(this.processRs(rs, "ATTR_216"));
		snapshotClxAssetDa.setAttr217(this.processRs(rs, "ATTR_217"));
		snapshotClxAssetDa.setAttr218(this.processRs(rs, "ATTR_218"));
		snapshotClxAssetDa.setAttr219(this.processRs(rs, "ATTR_219"));
		snapshotClxAssetDa.setAttr220(this.processRs(rs, "ATTR_220"));
		snapshotClxAssetDa.setAttr221(this.processRs(rs, "ATTR_221"));
		snapshotClxAssetDa.setAttr222(this.processRs(rs, "ATTR_222"));
		snapshotClxAssetDa.setAttr223(this.processRs(rs, "ATTR_223"));
		snapshotClxAssetDa.setAttr224(this.processRs(rs, "ATTR_224"));
		snapshotClxAssetDa.setAttr225(this.processRs(rs, "ATTR_225"));
		snapshotClxAssetDa.setAttr226(this.processRs(rs, "ATTR_226"));
		snapshotClxAssetDa.setAttr227(this.processRs(rs, "ATTR_227"));
		snapshotClxAssetDa.setAttr228(this.processRs(rs, "ATTR_228"));
		snapshotClxAssetDa.setAttr229(this.processRs(rs, "ATTR_229"));
		snapshotClxAssetDa.setAttr230(this.processRs(rs, "ATTR_230"));
		snapshotClxAssetDa.setAttr231(this.processRs(rs, "ATTR_231"));
		snapshotClxAssetDa.setAttr232(this.processRs(rs, "ATTR_232"));
		snapshotClxAssetDa.setAttr233(this.processRs(rs, "ATTR_233"));
		snapshotClxAssetDa.setAttr234(this.processRs(rs, "ATTR_234"));
		snapshotClxAssetDa.setAttr235(this.processRs(rs, "ATTR_235"));
		snapshotClxAssetDa.setAttr236(this.processRs(rs, "ATTR_236"));
		snapshotClxAssetDa.setAttr237(this.processRs(rs, "ATTR_237"));
		snapshotClxAssetDa.setAttr238(this.processRs(rs, "ATTR_238"));
		snapshotClxAssetDa.setAttr239(this.processRs(rs, "ATTR_239"));
		snapshotClxAssetDa.setAttr240(this.processRs(rs, "ATTR_240"));
		snapshotClxAssetDa.setAttr241(this.processRs(rs, "ATTR_241"));
		snapshotClxAssetDa.setAttr242(this.processRs(rs, "ATTR_242"));
		snapshotClxAssetDa.setAttr243(this.processRs(rs, "ATTR_243"));
		snapshotClxAssetDa.setAttr244(this.processRs(rs, "ATTR_244"));
		snapshotClxAssetDa.setAttr245(this.processRs(rs, "ATTR_245"));
		snapshotClxAssetDa.setAttr246(this.processRs(rs, "ATTR_246"));
		snapshotClxAssetDa.setAttr247(this.processRs(rs, "ATTR_247"));
		snapshotClxAssetDa.setAttr248(this.processRs(rs, "ATTR_248"));
		snapshotClxAssetDa.setAttr249(this.processRs(rs, "ATTR_249"));
		snapshotClxAssetDa.setAttr250(this.processRs(rs, "ATTR_250"));
		snapshotClxAssetDa.setAttr251(this.processRs(rs, "ATTR_251"));
		snapshotClxAssetDa.setAttr252(this.processRs(rs, "ATTR_252"));
		snapshotClxAssetDa.setAttr253(this.processRs(rs, "ATTR_253"));
		snapshotClxAssetDa.setAttr254(this.processRs(rs, "ATTR_254"));
		snapshotClxAssetDa.setAttr255(this.processRs(rs, "ATTR_255"));
		snapshotClxAssetDa.setAttr256(this.processRs(rs, "ATTR_256"));
		snapshotClxAssetDa.setAttr257(this.processRs(rs, "ATTR_257"));
		snapshotClxAssetDa.setAttr258(this.processRs(rs, "ATTR_258"));
		snapshotClxAssetDa.setAttr259(this.processRs(rs, "ATTR_259"));
		snapshotClxAssetDa.setAttr260(this.processRs(rs, "ATTR_260"));
		snapshotClxAssetDa.setAttr261(this.processRs(rs, "ATTR_261"));
		snapshotClxAssetDa.setAttr262(this.processRs(rs, "ATTR_262"));
		snapshotClxAssetDa.setAttr263(this.processRs(rs, "ATTR_263"));
		snapshotClxAssetDa.setAttr264(this.processRs(rs, "ATTR_264"));
		snapshotClxAssetDa.setAttr265(this.processRs(rs, "ATTR_265"));
		snapshotClxAssetDa.setAttr266(this.processRs(rs, "ATTR_266"));
		snapshotClxAssetDa.setAttr267(this.processRs(rs, "ATTR_267"));
		snapshotClxAssetDa.setAttr268(this.processRs(rs, "ATTR_268"));
		snapshotClxAssetDa.setAttr269(this.processRs(rs, "ATTR_269"));
		snapshotClxAssetDa.setAttr270(this.processRs(rs, "ATTR_270"));
		snapshotClxAssetDa.setAttr271(this.processRs(rs, "ATTR_271"));
		snapshotClxAssetDa.setAttr272(this.processRs(rs, "ATTR_272"));
		snapshotClxAssetDa.setAttr273(this.processRs(rs, "ATTR_273"));
		snapshotClxAssetDa.setAttr274(this.processRs(rs, "ATTR_274"));
		snapshotClxAssetDa.setAttr275(this.processRs(rs, "ATTR_275"));
		snapshotClxAssetDa.setAttr276(this.processRs(rs, "ATTR_276"));
		snapshotClxAssetDa.setAttr277(this.processRs(rs, "ATTR_277"));
		snapshotClxAssetDa.setAttr278(this.processRs(rs, "ATTR_278"));
		snapshotClxAssetDa.setAttr279(this.processRs(rs, "ATTR_279"));
		snapshotClxAssetDa.setAttr280(this.processRs(rs, "ATTR_280"));
		snapshotClxAssetDa.setAttr281(this.processRs(rs, "ATTR_281"));
		snapshotClxAssetDa.setAttr282(this.processRs(rs, "ATTR_282"));
		snapshotClxAssetDa.setAttr283(this.processRs(rs, "ATTR_283"));
		snapshotClxAssetDa.setAttr284(this.processRs(rs, "ATTR_284"));
		snapshotClxAssetDa.setAttr285(this.processRs(rs, "ATTR_285"));
		snapshotClxAssetDa.setAttr286(this.processRs(rs, "ATTR_286"));
		snapshotClxAssetDa.setAttr287(this.processRs(rs, "ATTR_287"));
		snapshotClxAssetDa.setAttr288(this.processRs(rs, "ATTR_288"));
		snapshotClxAssetDa.setAttr289(this.processRs(rs, "ATTR_289"));
		snapshotClxAssetDa.setAttr290(this.processRs(rs, "ATTR_290"));
		snapshotClxAssetDa.setAttr291(this.processRs(rs, "ATTR_291"));
		snapshotClxAssetDa.setAttr292(this.processRs(rs, "ATTR_292"));
		snapshotClxAssetDa.setAttr293(this.processRs(rs, "ATTR_293"));
		snapshotClxAssetDa.setAttr294(this.processRs(rs, "ATTR_294"));
		snapshotClxAssetDa.setAttr295(this.processRs(rs, "ATTR_295"));
		snapshotClxAssetDa.setAttr296(this.processRs(rs, "ATTR_296"));
		snapshotClxAssetDa.setAttr297(this.processRs(rs, "ATTR_297"));
		snapshotClxAssetDa.setAttr298(this.processRs(rs, "ATTR_298"));
		snapshotClxAssetDa.setAttr299(this.processRs(rs, "ATTR_299"));
		snapshotClxAssetDa.setAttr300(this.processRs(rs, "ATTR_300"));
		snapshotClxAssetDa.setAttr301(this.processRs(rs, "ATTR_301"));
		snapshotClxAssetDa.setAttr302(this.processRs(rs, "ATTR_302"));
		snapshotClxAssetDa.setAttr303(this.processRs(rs, "ATTR_303"));
		snapshotClxAssetDa.setAttr304(this.processRs(rs, "ATTR_304"));
		snapshotClxAssetDa.setAttr305(this.processRs(rs, "ATTR_305"));
		snapshotClxAssetDa.setAttr306(this.processRs(rs, "ATTR_306"));
		snapshotClxAssetDa.setAttr307(this.processRs(rs, "ATTR_307"));
		snapshotClxAssetDa.setAttr308(this.processRs(rs, "ATTR_308"));
		snapshotClxAssetDa.setAttr309(this.processRs(rs, "ATTR_309"));
		snapshotClxAssetDa.setAttr310(this.processRs(rs, "ATTR_310"));
		snapshotClxAssetDa.setAttr311(this.processRs(rs, "ATTR_311"));
		snapshotClxAssetDa.setAttr312(this.processRs(rs, "ATTR_312"));
		snapshotClxAssetDa.setAttr313(this.processRs(rs, "ATTR_313"));
		snapshotClxAssetDa.setAttr314(this.processRs(rs, "ATTR_314"));
		snapshotClxAssetDa.setAttr315(this.processRs(rs, "ATTR_315"));
		snapshotClxAssetDa.setAttr316(this.processRs(rs, "ATTR_316"));
		snapshotClxAssetDa.setAttr317(this.processRs(rs, "ATTR_317"));
		snapshotClxAssetDa.setAttr318(this.processRs(rs, "ATTR_318"));
		snapshotClxAssetDa.setAttr319(this.processRs(rs, "ATTR_319"));
		snapshotClxAssetDa.setAttr320(this.processRs(rs, "ATTR_320"));
		snapshotClxAssetDa.setAttr321(this.processRs(rs, "ATTR_321"));
		snapshotClxAssetDa.setAttr322(this.processRs(rs, "ATTR_322"));
		snapshotClxAssetDa.setAttr323(this.processRs(rs, "ATTR_323"));
		snapshotClxAssetDa.setAttr324(this.processRs(rs, "ATTR_324"));
		snapshotClxAssetDa.setAttr325(this.processRs(rs, "ATTR_325"));
		snapshotClxAssetDa.setAttr326(this.processRs(rs, "ATTR_326"));
		snapshotClxAssetDa.setAttr327(this.processRs(rs, "ATTR_327"));
		snapshotClxAssetDa.setAttr328(this.processRs(rs, "ATTR_328"));
		snapshotClxAssetDa.setAttr329(this.processRs(rs, "ATTR_329"));
		snapshotClxAssetDa.setAttr330(this.processRs(rs, "ATTR_330"));
		snapshotClxAssetDa.setAttr331(this.processRs(rs, "ATTR_331"));
		snapshotClxAssetDa.setAttr332(this.processRs(rs, "ATTR_332"));
		snapshotClxAssetDa.setAttr333(this.processRs(rs, "ATTR_333"));
		snapshotClxAssetDa.setAttr334(this.processRs(rs, "ATTR_334"));
		snapshotClxAssetDa.setAttr335(this.processRs(rs, "ATTR_335"));
		snapshotClxAssetDa.setAttr336(this.processRs(rs, "ATTR_336"));
		snapshotClxAssetDa.setAttr337(this.processRs(rs, "ATTR_337"));
		snapshotClxAssetDa.setAttr338(this.processRs(rs, "ATTR_338"));
		snapshotClxAssetDa.setAttr339(this.processRs(rs, "ATTR_339"));
		snapshotClxAssetDa.setAttr340(this.processRs(rs, "ATTR_340"));
		snapshotClxAssetDa.setAttr341(this.processRs(rs, "ATTR_341"));
		snapshotClxAssetDa.setAttr342(this.processRs(rs, "ATTR_342"));
		snapshotClxAssetDa.setAttr343(this.processRs(rs, "ATTR_343"));
		snapshotClxAssetDa.setAttr344(this.processRs(rs, "ATTR_344"));
		snapshotClxAssetDa.setAttr345(this.processRs(rs, "ATTR_345"));
		snapshotClxAssetDa.setAttr346(this.processRs(rs, "ATTR_346"));
		snapshotClxAssetDa.setAttr347(this.processRs(rs, "ATTR_347"));
		snapshotClxAssetDa.setAttr348(this.processRs(rs, "ATTR_348"));
		snapshotClxAssetDa.setAttr349(this.processRs(rs, "ATTR_349"));
		snapshotClxAssetDa.setAttr350(this.processRs(rs, "ATTR_350"));


		return snapshotClxAssetDa;
	}
	
	private String processRs(ResultSet rs , String columnName){
		try{
		   return rs.getString(columnName);
		}catch(Exception e){
		   return CacheConstant.BLANK;	
		}
	}

}