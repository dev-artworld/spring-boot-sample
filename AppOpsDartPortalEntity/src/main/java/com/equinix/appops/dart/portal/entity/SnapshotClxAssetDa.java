package com.equinix.appops.dart.portal.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the SNAPSHOT_CLX_ASSET_DA database table.
 * 
 * @author Sandeep Singh
 * 
 */
@Entity
@Table(name="SNAPSHOT_CLX_ASSET_DA",schema="EQX_DART")
@NamedQuery(name="SnapshotClxAssetDa.findAll", query="SELECT c FROM SnapshotClxAssetDa c")
public class SnapshotClxAssetDa implements Serializable {


	   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	

	public SnapshotClxAssetDa() {
	
	}


	/**
		* DFR_LINE_ID
		*/
		@Id
		@Column(name="DFR_LINE_ID")
		private String dfrLineId;

		
		
		/**
		* DFR_ID
		*/
		
		@Column(name="DFR_ID")
		private String dfrId;

		/**
		* ROW_ID
		*/
		@Column(name="ROW_ID")
		private String header1;

		/**
		* ASSET_NUM
		*/
		@Column(name="ASSET_NUM")
		private String header2;


		/**
		* SERIAL_NUM
		*/
		@Column(name="SERIAL_NUM")
		private String header3;


		/**
		* X_ATTR_25
		*/
		@Column(name="X_ATTR_25")
		private String header4;


		/**
		* X_CUST_UCID
		*/
		@Column(name="X_CUST_UCID")
		private String header5;


		/**
		* OU_NUM
		*/
		@Column(name="OU_NUM")
		private String header6;


		/**
		* ACCOUNT_NAME
		*/
		@Column(name="ACCOUNT_NAME")
		private String header7;


		/**
		* IBX
		*/
		@Column(name="IBX")
		private String header8;


		/**
		* X_UNIQUE_SPACE_ID
		*/
		@Column(name="X_UNIQUE_SPACE_ID")
		private String header9;


		/**
		* CAGE_UNIQUE_SPACE_ID
		*/
		@Column(name="CAGE_UNIQUE_SPACE_ID")
		private String header10;


		/**
		* X_CAB_USID
		*/
		@Column(name="X_CAB_USID")
		private String header11;


		/**
		* CAB_UNIQUE_SPACE_ID
		*/
		@Column(name="CAB_UNIQUE_SPACE_ID")
		private String header12;


		/**
		* CAGE_NUMBER
		*/
		@Column(name="CAGE_NUMBER")
		private String header13;


		/**
		* X_CAB_ID
		*/
		@Column(name="X_CAB_ID")
		private String header14;


		/**
		* BASE_CURRENCY_CD
		*/
		@Column(name="BASE_CURRENCY_CD")
		private String header15;


		/**
		* SYSTEM_NAME
		*/
		@Column(name="SYSTEM_NAME")
		private String header16;


		/**
		* ASSET_INSTALL_DATE
		*/
		@Temporal(TemporalType.DATE)
		@Column(name="ASSET_INSTALL_DATE")
		private Date header17;


		/**
		* STATUS_CD
		*/
		@Column(name="STATUS_CD")
		private String header18;


		/**
		* ASSET_SUB_STATUS
		*/
		@Column(name="ASSET_SUB_STATUS")
		private String header19;


		/**
		* NAME
		*/
		@Column(name="NAME")
		private String header20;


		/**
		* PAR_ASSET_ID
		*/
		@Column(name="PAR_ASSET_ID")
		private String header21;


		/**
		* ROOT_ASSET_ID
		*/
		@Column(name="ROOT_ASSET_ID")
		private String header22;


		/**
		* PAR_AST_NUM
		*/
		@Column(name="PAR_AST_NUM")
		private String header23;


		/**
		* ROOT_AST_NUM
		*/
		@Column(name="ROOT_AST_NUM")
		private String header24;


		/**
		* PARENT_PRODUCT_NAME
		*/
		@Column(name="PARENT_PRODUCT_NAME")
		private String header25;


		/**
		* POF_NAME
		*/
		@Column(name="POF_NAME")
		private String header26;


		/**
		* X_OPS_PAR_ASSET_NUM
		*/
		@Column(name="X_OPS_PAR_ASSET_NUM")
		private String header27;


		/**
		* X_ROOT_ASSET_NUM
		*/
		@Column(name="X_ROOT_ASSET_NUM")
		private String header28;


		/**
		* X_ATTR_10
		*/
		@Column(name="X_ATTR_10")
		private String header29;


		/**
		* X_ATTR_22
		*/
		@Column(name="X_ATTR_22")
		private String header30;


		/**
		* BILLING_AGR_NUM
		*/
		@Column(name="BILLING_AGR_NUM")
		private String header31;


		/**
		* BILLING_AGR_LN_NUM
		*/
		@Column(name="BILLING_AGR_LN_NUM")
		private String header32;


		/**
		* PRICE
		*/
		@Column(name="PRICE")
		private String header33;


		/**
		* CREATED
		*/
		@Temporal(TemporalType.DATE)
		@Column(name="CREATED")
		private Date header34;


		/**
		* CREATED_BY
		*/
		@Column(name="CREATED_BY")
		private String header35;


		/**
		* LAST_UPD
		*/
		@Temporal(TemporalType.DATE)
		@Column(name="LAST_UPD")
		private Date header36;


		/**
		* LAST_UPD_BY
		*/
		@Column(name="LAST_UPD_BY")
		private String header37;


		/**
		* DQM_ERR_FLG
		*/
		@Column(name="DQM_ERR_FLG")
		private String header38;


		/**
		* PROD_ID
		*/
		@Column(name="PROD_ID")
		private String header39;


		/**
		* X_REL_ACCNT_ID
		*/
		@Column(name="X_REL_ACCNT_ID")
		private String header40;


		/**
		* RELATED_ACCNT_NUM
		*/
		@Column(name="RELATED_ACCNT_NUM")
		private String header41;


		/**
		* RELATED_ACCNT_NAME
		*/
		@Column(name="RELATED_ACCNT_NAME")
		private String header42;


		/**
		* X_CABLE_ID
		*/
		@Column(name="X_CABLE_ID")
		private String header43;


		/**
		* X_MASS_CREATE_ASSET
		*/
		@Column(name="X_MASS_CREATE_ASSET")
		private String header44;


		/**
		* DESC_TEXT
		*/
		@Column(name="DESC_TEXT")
		private String header45;


		/**
		* PART_NUM
		*/
		@Column(name="PART_NUM")
		private String header46;


		/**
		* X_MACD_SPLIT_M
		*/
		@Column(name="X_MACD_SPLIT_M")
		private String header47;


		/**
		* POF_X_MACD_SPLIT_M
		*/
		@Column(name="POF_X_MACD_SPLIT_M")
		private String header48;


		/**
		* POF_PART_NUM
		*/
		@Column(name="POF_PART_NUM")
		private String header49;


		/**
		* POF_STATUS_CD
		*/
		@Column(name="POF_STATUS_CD")
		private String header50;


		/**
		* REGION
		*/
		@Column(name="REGION")
		private String header51;


		/**
		* X_SYS_NAME
		*/
		@Column(name="X_SYS_NAME")
		private String header52;


		/**
		* OWNER_ACCNT_ID
		*/
		@Column(name="OWNER_ACCNT_ID")
		private String header53;


		/**
		* X_PP_ID
		*/
		@Column(name="X_PP_ID")
		private String header54;


		/**
		* END_DT
		*/
		@Temporal(TemporalType.DATE)
		@Column(name="END_DT")
		private Date header55;


		/**
		* COUNTRY
		*/
		@Column(name="COUNTRY")
		private String header56;


		/**
		* HEADER_01
		*/
		@Column(name="HEADER_01")
		private String header57;


		/**
		* HEADER_02
		*/
		@Column(name="HEADER_02")
		private String header58;


		/**
		* HEADER_03
		*/
		@Column(name="HEADER_03")
		private String header59;


		/**
		* HEADER_04
		*/
		@Column(name="HEADER_04")
		private String header60;


		/**
		* HEADER_05
		*/
		@Column(name="HEADER_05")
		private String header61;


		/**
		* HEADER_06
		*/
		@Column(name="HEADER_06")
		private String header62;


		/**
		* HEADER_07
		*/
		@Column(name="HEADER_07")
		private String header63;


		/**
		* HEADER_08
		*/
		@Column(name="HEADER_08")
		private String header64;


		/**
		* HEADER_09
		*/
		@Column(name="HEADER_09")
		private String header65;


		/**
		* HEADER_10
		*/
		@Column(name="HEADER_10")
		private String header66;


		/**
		* HEADER_11
		*/
//		@Column(name="HEADER_11")
//		private String header67;


		/**
		* HEADER_12
		*/
//		@Column(name="HEADER_12")
//		private String header68;


		/**
		* HEADER_13
		*/
//		@Column(name="HEADER_13")
//		private String header69;


		/**
		* HEADER_14
		*/
//		@Column(name="HEADER_14")
//		private String header70;


		/**
		* ATTR_1
		*/
		@Column(name="ATTR_1")
		private String attr1;


		/**
		* ATTR_2
		*/
		@Column(name="ATTR_2")
		private String attr2;


		/**
		* ATTR_3
		*/
		@Column(name="ATTR_3")
		private String attr3;


		/**
		* ATTR_4
		*/
		@Column(name="ATTR_4")
		private String attr4;


		/**
		* ATTR_5
		*/
		@Column(name="ATTR_5")
		private String attr5;


		/**
		* ATTR_6
		*/
		@Column(name="ATTR_6")
		private String attr6;


		/**
		* ATTR_7
		*/
		@Column(name="ATTR_7")
		private String attr7;


		/**
		* ATTR_8
		*/
		@Column(name="ATTR_8")
		private String attr8;


		/**
		* ATTR_9
		*/
		@Column(name="ATTR_9")
		private String attr9;


		/**
		* ATTR_10
		*/
		@Column(name="ATTR_10")
		private String attr10;


		/**
		* ATTR_11
		*/
		@Column(name="ATTR_11")
		private String attr11;


		/**
		* ATTR_12
		*/
		@Column(name="ATTR_12")
		private String attr12;


		/**
		* ATTR_13
		*/
		@Column(name="ATTR_13")
		private String attr13;


		/**
		* ATTR_14
		*/
		@Column(name="ATTR_14")
		private String attr14;


		/**
		* ATTR_15
		*/
		@Column(name="ATTR_15")
		private String attr15;


		/**
		* ATTR_16
		*/
		@Column(name="ATTR_16")
		private String attr16;


		/**
		* ATTR_17
		*/
		@Column(name="ATTR_17")
		private String attr17;


		/**
		* ATTR_18
		*/
		@Column(name="ATTR_18")
		private String attr18;


		/**
		* ATTR_19
		*/
		@Column(name="ATTR_19")
		private String attr19;


		/**
		* ATTR_20
		*/
		@Column(name="ATTR_20")
		private String attr20;


		/**
		* ATTR_21
		*/
		@Column(name="ATTR_21")
		private String attr21;


		/**
		* ATTR_22
		*/
		@Column(name="ATTR_22")
		private String attr22;


		/**
		* ATTR_23
		*/
		@Column(name="ATTR_23")
		private String attr23;


		/**
		* ATTR_24
		*/
		@Column(name="ATTR_24")
		private String attr24;


		/**
		* ATTR_25
		*/
		@Column(name="ATTR_25")
		private String attr25;


		/**
		* ATTR_26
		*/
		@Column(name="ATTR_26")
		private String attr26;


		/**
		* ATTR_27
		*/
		@Column(name="ATTR_27")
		private String attr27;


		/**
		* ATTR_28
		*/
		@Column(name="ATTR_28")
		private String attr28;


		/**
		* ATTR_29
		*/
		@Column(name="ATTR_29")
		private String attr29;


		/**
		* ATTR_30
		*/
		@Column(name="ATTR_30")
		private String attr30;


		/**
		* ATTR_31
		*/
		@Column(name="ATTR_31")
		private String attr31;


		/**
		* ATTR_32
		*/
		@Column(name="ATTR_32")
		private String attr32;


		/**
		* ATTR_33
		*/
		@Column(name="ATTR_33")
		private String attr33;


		/**
		* ATTR_34
		*/
		@Column(name="ATTR_34")
		private String attr34;


		/**
		* ATTR_35
		*/
		@Column(name="ATTR_35")
		private String attr35;


		/**
		* ATTR_36
		*/
		@Column(name="ATTR_36")
		private String attr36;


		/**
		* ATTR_37
		*/
		@Column(name="ATTR_37")
		private String attr37;


		/**
		* ATTR_38
		*/
		@Column(name="ATTR_38")
		private String attr38;


		/**
		* ATTR_39
		*/
		@Column(name="ATTR_39")
		private String attr39;


		/**
		* ATTR_40
		*/
		@Column(name="ATTR_40")
		private String attr40;


		/**
		* ATTR_41
		*/
		@Column(name="ATTR_41")
		private String attr41;


		/**
		* ATTR_42
		*/
		@Column(name="ATTR_42")
		private String attr42;


		/**
		* ATTR_43
		*/
		@Column(name="ATTR_43")
		private String attr43;


		/**
		* ATTR_44
		*/
		@Column(name="ATTR_44")
		private String attr44;


		/**
		* ATTR_45
		*/
		@Column(name="ATTR_45")
		private String attr45;


		/**
		* ATTR_46
		*/
		@Column(name="ATTR_46")
		private String attr46;


		/**
		* ATTR_47
		*/
		@Column(name="ATTR_47")
		private String attr47;


		/**
		* ATTR_48
		*/
		@Column(name="ATTR_48")
		private String attr48;


		/**
		* ATTR_49
		*/
		@Column(name="ATTR_49")
		private String attr49;


		/**
		* ATTR_50
		*/
		@Column(name="ATTR_50")
		private String attr50;


		/**
		* ATTR_51
		*/
		@Column(name="ATTR_51")
		private String attr51;


		/**
		* ATTR_52
		*/
		@Column(name="ATTR_52")
		private String attr52;


		/**
		* ATTR_53
		*/
		@Column(name="ATTR_53")
		private String attr53;


		/**
		* ATTR_54
		*/
		@Column(name="ATTR_54")
		private String attr54;


		/**
		* ATTR_55
		*/
		@Column(name="ATTR_55")
		private String attr55;


		/**
		* ATTR_56
		*/
		@Column(name="ATTR_56")
		private String attr56;


		/**
		* ATTR_57
		*/
		@Column(name="ATTR_57")
		private String attr57;


		/**
		* ATTR_58
		*/
		@Column(name="ATTR_58")
		private String attr58;


		/**
		* ATTR_59
		*/
		@Column(name="ATTR_59")
		private String attr59;


		/**
		* ATTR_60
		*/
		@Column(name="ATTR_60")
		private String attr60;


		/**
		* ATTR_61
		*/
		@Column(name="ATTR_61")
		private String attr61;


		/**
		* ATTR_62
		*/
		@Column(name="ATTR_62")
		private String attr62;


		/**
		* ATTR_63
		*/
		@Column(name="ATTR_63")
		private String attr63;


		/**
		* ATTR_64
		*/
		@Column(name="ATTR_64")
		private String attr64;


		/**
		* ATTR_65
		*/
		@Column(name="ATTR_65")
		private String attr65;


		/**
		* ATTR_66
		*/
		@Column(name="ATTR_66")
		private String attr66;


		/**
		* ATTR_67
		*/
		@Column(name="ATTR_67")
		private String attr67;


		/**
		* ATTR_68
		*/
		@Column(name="ATTR_68")
		private String attr68;


		/**
		* ATTR_69
		*/
		@Column(name="ATTR_69")
		private String attr69;


		/**
		* ATTR_70
		*/
		@Column(name="ATTR_70")
		private String attr70;


		/**
		* ATTR_71
		*/
		@Column(name="ATTR_71")
		private String attr71;


		/**
		* ATTR_72
		*/
		@Column(name="ATTR_72")
		private String attr72;


		/**
		* ATTR_73
		*/
		@Column(name="ATTR_73")
		private String attr73;


		/**
		* ATTR_74
		*/
		@Column(name="ATTR_74")
		private String attr74;


		/**
		* ATTR_75
		*/
		@Column(name="ATTR_75")
		private String attr75;


		/**
		* ATTR_76
		*/
		@Column(name="ATTR_76")
		private String attr76;


		/**
		* ATTR_77
		*/
		@Column(name="ATTR_77")
		private String attr77;


		/**
		* ATTR_78
		*/
		@Column(name="ATTR_78")
		private String attr78;


		/**
		* ATTR_79
		*/
		@Column(name="ATTR_79")
		private String attr79;


		/**
		* ATTR_80
		*/
		@Column(name="ATTR_80")
		private String attr80;


		/**
		* ATTR_81
		*/
		@Column(name="ATTR_81")
		private String attr81;


		/**
		* ATTR_82
		*/
		@Column(name="ATTR_82")
		private String attr82;


		/**
		* ATTR_83
		*/
		@Column(name="ATTR_83")
		private String attr83;


		/**
		* ATTR_84
		*/
		@Column(name="ATTR_84")
		private String attr84;


		/**
		* ATTR_85
		*/
		@Column(name="ATTR_85")
		private String attr85;


		/**
		* ATTR_86
		*/
		@Column(name="ATTR_86")
		private String attr86;


		/**
		* ATTR_87
		*/
		@Column(name="ATTR_87")
		private String attr87;


		/**
		* ATTR_88
		*/
		@Column(name="ATTR_88")
		private String attr88;


		/**
		* ATTR_89
		*/
		@Column(name="ATTR_89")
		private String attr89;


		/**
		* ATTR_90
		*/
		@Column(name="ATTR_90")
		private String attr90;


		/**
		* ATTR_91
		*/
		@Column(name="ATTR_91")
		private String attr91;


		/**
		* ATTR_92
		*/
		@Column(name="ATTR_92")
		private String attr92;


		/**
		* ATTR_93
		*/
		@Column(name="ATTR_93")
		private String attr93;


		/**
		* ATTR_94
		*/
		@Column(name="ATTR_94")
		private String attr94;


		/**
		* ATTR_95
		*/
		@Column(name="ATTR_95")
		private String attr95;


		/**
		* ATTR_96
		*/
		@Column(name="ATTR_96")
		private String attr96;


		/**
		* ATTR_97
		*/
		@Column(name="ATTR_97")
		private String attr97;


		/**
		* ATTR_98
		*/
		@Column(name="ATTR_98")
		private String attr98;


		/**
		* ATTR_99
		*/
		@Column(name="ATTR_99")
		private String attr99;


		/**
		* ATTR_100
		*/
		@Column(name="ATTR_100")
		private String attr100;


		/**
		* ATTR_101
		*/
		@Column(name="ATTR_101")
		private String attr101;


		/**
		* ATTR_102
		*/
		@Column(name="ATTR_102")
		private String attr102;


		/**
		* ATTR_103
		*/
		@Column(name="ATTR_103")
		private String attr103;


		/**
		* ATTR_104
		*/
		@Column(name="ATTR_104")
		private String attr104;


		/**
		* ATTR_105
		*/
		@Column(name="ATTR_105")
		private String attr105;


		/**
		* ATTR_106
		*/
		@Column(name="ATTR_106")
		private String attr106;


		/**
		* ATTR_107
		*/
		@Column(name="ATTR_107")
		private String attr107;


		/**
		* ATTR_108
		*/
		@Column(name="ATTR_108")
		private String attr108;


		/**
		* ATTR_109
		*/
		@Column(name="ATTR_109")
		private String attr109;


		/**
		* ATTR_110
		*/
		@Column(name="ATTR_110")
		private String attr110;


		/**
		* ATTR_111
		*/
		@Column(name="ATTR_111")
		private String attr111;


		/**
		* ATTR_112
		*/
		@Column(name="ATTR_112")
		private String attr112;


		/**
		* ATTR_113
		*/
		@Column(name="ATTR_113")
		private String attr113;


		/**
		* ATTR_114
		*/
		@Column(name="ATTR_114")
		private String attr114;


		/**
		* ATTR_115
		*/
		@Column(name="ATTR_115")
		private String attr115;


		/**
		* ATTR_116
		*/
		@Column(name="ATTR_116")
		private String attr116;


		/**
		* ATTR_117
		*/
		@Column(name="ATTR_117")
		private String attr117;


		/**
		* ATTR_118
		*/
		@Column(name="ATTR_118")
		private String attr118;


		/**
		* ATTR_119
		*/
		@Column(name="ATTR_119")
		private String attr119;


		/**
		* ATTR_120
		*/
		@Column(name="ATTR_120")
		private String attr120;


		/**
		* ATTR_121
		*/
		@Column(name="ATTR_121")
		private String attr121;


		/**
		* ATTR_122
		*/
		@Column(name="ATTR_122")
		private String attr122;


		/**
		* ATTR_123
		*/
		@Column(name="ATTR_123")
		private String attr123;


		/**
		* ATTR_124
		*/
		@Column(name="ATTR_124")
		private String attr124;


		/**
		* ATTR_125
		*/
		@Column(name="ATTR_125")
		private String attr125;


		/**
		* ATTR_126
		*/
		@Column(name="ATTR_126")
		private String attr126;


		/**
		* ATTR_127
		*/
		@Column(name="ATTR_127")
		private String attr127;


		/**
		* ATTR_128
		*/
		@Column(name="ATTR_128")
		private String attr128;


		/**
		* ATTR_129
		*/
		@Column(name="ATTR_129")
		private String attr129;


		/**
		* ATTR_130
		*/
		@Column(name="ATTR_130")
		private String attr130;


		/**
		* ATTR_131
		*/
		@Column(name="ATTR_131")
		private String attr131;


		/**
		* ATTR_132
		*/
		@Column(name="ATTR_132")
		private String attr132;


		/**
		* ATTR_133
		*/
		@Column(name="ATTR_133")
		private String attr133;


		/**
		* ATTR_134
		*/
		@Column(name="ATTR_134")
		private String attr134;


		/**
		* ATTR_135
		*/
		@Column(name="ATTR_135")
		private String attr135;


		/**
		* ATTR_136
		*/
		@Column(name="ATTR_136")
		private String attr136;


		/**
		* ATTR_137
		*/
		@Column(name="ATTR_137")
		private String attr137;


		/**
		* ATTR_138
		*/
		@Column(name="ATTR_138")
		private String attr138;


		/**
		* ATTR_139
		*/
		@Column(name="ATTR_139")
		private String attr139;


		/**
		* ATTR_140
		*/
		@Column(name="ATTR_140")
		private String attr140;


		/**
		* ATTR_141
		*/
		@Column(name="ATTR_141")
		private String attr141;


		/**
		* ATTR_142
		*/
		@Column(name="ATTR_142")
		private String attr142;


		/**
		* ATTR_143
		*/
		@Column(name="ATTR_143")
		private String attr143;


		/**
		* ATTR_144
		*/
		@Column(name="ATTR_144")
		private String attr144;


		/**
		* ATTR_145
		*/
		@Column(name="ATTR_145")
		private String attr145;


		/**
		* ATTR_146
		*/
		@Column(name="ATTR_146")
		private String attr146;


		/**
		* ATTR_147
		*/
		@Column(name="ATTR_147")
		private String attr147;


		/**
		* ATTR_148
		*/
		@Column(name="ATTR_148")
		private String attr148;


		/**
		* ATTR_149
		*/
		@Column(name="ATTR_149")
		private String attr149;


		/**
		* ATTR_150
		*/
		@Column(name="ATTR_150")
		private String attr150;


		/**
		* ATTR_151
		*/
		@Column(name="ATTR_151")
		private String attr151;


		/**
		* ATTR_152
		*/
		@Column(name="ATTR_152")
		private String attr152;


		/**
		* ATTR_153
		*/
		@Column(name="ATTR_153")
		private String attr153;


		/**
		* ATTR_154
		*/
		@Column(name="ATTR_154")
		private String attr154;


		/**
		* ATTR_155
		*/
		@Column(name="ATTR_155")
		private String attr155;


		/**
		* ATTR_156
		*/
		@Column(name="ATTR_156")
		private String attr156;


		/**
		* ATTR_157
		*/
		@Column(name="ATTR_157")
		private String attr157;


		/**
		* ATTR_158
		*/
		@Column(name="ATTR_158")
		private String attr158;


		/**
		* ATTR_159
		*/
		@Column(name="ATTR_159")
		private String attr159;


		/**
		* ATTR_160
		*/
		@Column(name="ATTR_160")
		private String attr160;


		/**
		* ATTR_161
		*/
		@Column(name="ATTR_161")
		private String attr161;


		/**
		* ATTR_162
		*/
		@Column(name="ATTR_162")
		private String attr162;


		/**
		* ATTR_163
		*/
		@Column(name="ATTR_163")
		private String attr163;


		/**
		* ATTR_164
		*/
		@Column(name="ATTR_164")
		private String attr164;


		/**
		* ATTR_165
		*/
		@Column(name="ATTR_165")
		private String attr165;


		/**
		* ATTR_166
		*/
		@Column(name="ATTR_166")
		private String attr166;


		/**
		* ATTR_167
		*/
		@Column(name="ATTR_167")
		private String attr167;


		/**
		* ATTR_168
		*/
		@Column(name="ATTR_168")
		private String attr168;


		/**
		* ATTR_169
		*/
		@Column(name="ATTR_169")
		private String attr169;


		/**
		* ATTR_170
		*/
		@Column(name="ATTR_170")
		private String attr170;


		/**
		* ATTR_171
		*/
		@Column(name="ATTR_171")
		private String attr171;


		/**
		* ATTR_172
		*/
		@Column(name="ATTR_172")
		private String attr172;


		/**
		* ATTR_173
		*/
		@Column(name="ATTR_173")
		private String attr173;


		/**
		* ATTR_174
		*/
		@Column(name="ATTR_174")
		private String attr174;


		/**
		* ATTR_175
		*/
		@Column(name="ATTR_175")
		private String attr175;


		/**
		* ATTR_176
		*/
		@Column(name="ATTR_176")
		private String attr176;


		/**
		* ATTR_177
		*/
		@Column(name="ATTR_177")
		private String attr177;


		/**
		* ATTR_178
		*/
		@Column(name="ATTR_178")
		private String attr178;


		/**
		* ATTR_179
		*/
		@Column(name="ATTR_179")
		private String attr179;


		/**
		* ATTR_180
		*/
		@Column(name="ATTR_180")
		private String attr180;


		/**
		* ATTR_181
		*/
		@Column(name="ATTR_181")
		private String attr181;


		/**
		* ATTR_182
		*/
		@Column(name="ATTR_182")
		private String attr182;


		/**
		* ATTR_183
		*/
		@Column(name="ATTR_183")
		private String attr183;


		/**
		* ATTR_184
		*/
		@Column(name="ATTR_184")
		private String attr184;


		/**
		* ATTR_185
		*/
		@Column(name="ATTR_185")
		private String attr185;


		/**
		* ATTR_186
		*/
		@Column(name="ATTR_186")
		private String attr186;


		/**
		* ATTR_187
		*/
		@Column(name="ATTR_187")
		private String attr187;


		/**
		* ATTR_188
		*/
		@Column(name="ATTR_188")
		private String attr188;


		/**
		* ATTR_189
		*/
		@Column(name="ATTR_189")
		private String attr189;


		/**
		* ATTR_190
		*/
		@Column(name="ATTR_190")
		private String attr190;


		/**
		* ATTR_191
		*/
		@Column(name="ATTR_191")
		private String attr191;


		/**
		* ATTR_192
		*/
		@Column(name="ATTR_192")
		private String attr192;


		/**
		* ATTR_193
		*/
		@Column(name="ATTR_193")
		private String attr193;


		/**
		* ATTR_194
		*/
		@Column(name="ATTR_194")
		private String attr194;


		/**
		* ATTR_195
		*/
		@Column(name="ATTR_195")
		private String attr195;


		/**
		* ATTR_196
		*/
		@Column(name="ATTR_196")
		private String attr196;


		/**
		* ATTR_197
		*/
		@Column(name="ATTR_197")
		private String attr197;


		/**
		* ATTR_198
		*/
		@Column(name="ATTR_198")
		private String attr198;


		/**
		* ATTR_199
		*/
		@Column(name="ATTR_199")
		private String attr199;


		/**
		* ATTR_200
		*/
		@Column(name="ATTR_200")
		private String attr200;


		/**
		* ATTR_201
		*/
		@Column(name="ATTR_201")
		private String attr201;


		/**
		* ATTR_202
		*/
		@Column(name="ATTR_202")
		private String attr202;


		/**
		* ATTR_203
		*/
		@Column(name="ATTR_203")
		private String attr203;


		/**
		* ATTR_204
		*/
		@Column(name="ATTR_204")
		private String attr204;


		/**
		* ATTR_205
		*/
		@Column(name="ATTR_205")
		private String attr205;


		/**
		* ATTR_206
		*/
		@Column(name="ATTR_206")
		private String attr206;


		/**
		* ATTR_207
		*/
		@Column(name="ATTR_207")
		private String attr207;


		/**
		* ATTR_208
		*/
		@Column(name="ATTR_208")
		private String attr208;


		/**
		* ATTR_209
		*/
		@Column(name="ATTR_209")
		private String attr209;


		/**
		* ATTR_210
		*/
		@Column(name="ATTR_210")
		private String attr210;


		/**
		* ATTR_211
		*/
		@Column(name="ATTR_211")
		private String attr211;


		/**
		* ATTR_212
		*/
		@Column(name="ATTR_212")
		private String attr212;


		/**
		* ATTR_213
		*/
		@Column(name="ATTR_213")
		private String attr213;


		/**
		* ATTR_214
		*/
		@Column(name="ATTR_214")
		private String attr214;


		/**
		* ATTR_215
		*/
		@Column(name="ATTR_215")
		private String attr215;


		/**
		* ATTR_216
		*/
		@Column(name="ATTR_216")
		private String attr216;


		/**
		* ATTR_217
		*/
		@Column(name="ATTR_217")
		private String attr217;


		/**
		* ATTR_218
		*/
		@Column(name="ATTR_218")
		private String attr218;


		/**
		* ATTR_219
		*/
		@Column(name="ATTR_219")
		private String attr219;


		/**
		* ATTR_220
		*/
		@Column(name="ATTR_220")
		private String attr220;


		/**
		* ATTR_221
		*/
		@Column(name="ATTR_221")
		private String attr221;


		/**
		* ATTR_222
		*/
		@Column(name="ATTR_222")
		private String attr222;


		/**
		* ATTR_223
		*/
		@Column(name="ATTR_223")
		private String attr223;


		/**
		* ATTR_224
		*/
		@Column(name="ATTR_224")
		private String attr224;


		/**
		* ATTR_225
		*/
		@Column(name="ATTR_225")
		private String attr225;


		/**
		* ATTR_226
		*/
		@Column(name="ATTR_226")
		private String attr226;


		/**
		* ATTR_227
		*/
		@Column(name="ATTR_227")
		private String attr227;


		/**
		* ATTR_228
		*/
		@Column(name="ATTR_228")
		private String attr228;


		/**
		* ATTR_229
		*/
		@Column(name="ATTR_229")
		private String attr229;


		/**
		* ATTR_230
		*/
		@Column(name="ATTR_230")
		private String attr230;


		/**
		* ATTR_231
		*/
		@Column(name="ATTR_231")
		private String attr231;


		/**
		* ATTR_232
		*/
		@Column(name="ATTR_232")
		private String attr232;


		/**
		* ATTR_233
		*/
		@Column(name="ATTR_233")
		private String attr233;


		/**
		* ATTR_234
		*/
		@Column(name="ATTR_234")
		private String attr234;


		/**
		* ATTR_235
		*/
		@Column(name="ATTR_235")
		private String attr235;


		/**
		* ATTR_236
		*/
		@Column(name="ATTR_236")
		private String attr236;


		/**
		* ATTR_237
		*/
		@Column(name="ATTR_237")
		private String attr237;


		/**
		* ATTR_238
		*/
		@Column(name="ATTR_238")
		private String attr238;


		/**
		* ATTR_239
		*/
		@Column(name="ATTR_239")
		private String attr239;


		/**
		* ATTR_240
		*/
		@Column(name="ATTR_240")
		private String attr240;


		/**
		* ATTR_241
		*/
		@Column(name="ATTR_241")
		private String attr241;


		/**
		* ATTR_242
		*/
		@Column(name="ATTR_242")
		private String attr242;


		/**
		* ATTR_243
		*/
		@Column(name="ATTR_243")
		private String attr243;


		/**
		* ATTR_244
		*/
		@Column(name="ATTR_244")
		private String attr244;


		/**
		* ATTR_245
		*/
		@Column(name="ATTR_245")
		private String attr245;


		/**
		* ATTR_246
		*/
		@Column(name="ATTR_246")
		private String attr246;


		/**
		* ATTR_247
		*/
		@Column(name="ATTR_247")
		private String attr247;


		/**
		* ATTR_248
		*/
		@Column(name="ATTR_248")
		private String attr248;


		/**
		* ATTR_249
		*/
		@Column(name="ATTR_249")
		private String attr249;


		/**
		* ATTR_250
		*/
		@Column(name="ATTR_250")
		private String attr250;


		/**
		* ATTR_251
		*/
		@Column(name="ATTR_251")
		private String attr251;


		/**
		* ATTR_252
		*/
		@Column(name="ATTR_252")
		private String attr252;


		/**
		* ATTR_253
		*/
		@Column(name="ATTR_253")
		private String attr253;


		/**
		* ATTR_254
		*/
		@Column(name="ATTR_254")
		private String attr254;


		/**
		* ATTR_255
		*/
		@Column(name="ATTR_255")
		private String attr255;


		/**
		* ATTR_256
		*/
		@Column(name="ATTR_256")
		private String attr256;


		/**
		* ATTR_257
		*/
		@Column(name="ATTR_257")
		private String attr257;


		/**
		* ATTR_258
		*/
		@Column(name="ATTR_258")
		private String attr258;


		/**
		* ATTR_259
		*/
		@Column(name="ATTR_259")
		private String attr259;


		/**
		* ATTR_260
		*/
		@Column(name="ATTR_260")
		private String attr260;


		/**
		* ATTR_261
		*/
		@Column(name="ATTR_261")
		private String attr261;


		/**
		* ATTR_262
		*/
		@Column(name="ATTR_262")
		private String attr262;


		/**
		* ATTR_263
		*/
		@Column(name="ATTR_263")
		private String attr263;


		/**
		* ATTR_264
		*/
		@Column(name="ATTR_264")
		private String attr264;


		/**
		* ATTR_265
		*/
		@Column(name="ATTR_265")
		private String attr265;


		/**
		* ATTR_266
		*/
		@Column(name="ATTR_266")
		private String attr266;


		/**
		* ATTR_267
		*/
		@Column(name="ATTR_267")
		private String attr267;


		/**
		* ATTR_268
		*/
		@Column(name="ATTR_268")
		private String attr268;


		/**
		* ATTR_269
		*/
		@Column(name="ATTR_269")
		private String attr269;


		/**
		* ATTR_270
		*/
		@Column(name="ATTR_270")
		private String attr270;


		/**
		* ATTR_271
		*/
		@Column(name="ATTR_271")
		private String attr271;


		/**
		* ATTR_272
		*/
		@Column(name="ATTR_272")
		private String attr272;


		/**
		* ATTR_273
		*/
		@Column(name="ATTR_273")
		private String attr273;


		/**
		* ATTR_274
		*/
		@Column(name="ATTR_274")
		private String attr274;


		/**
		* ATTR_275
		*/
		@Column(name="ATTR_275")
		private String attr275;


		/**
		* ATTR_276
		*/
		@Column(name="ATTR_276")
		private String attr276;


		/**
		* ATTR_277
		*/
		@Column(name="ATTR_277")
		private String attr277;


		/**
		* ATTR_278
		*/
		@Column(name="ATTR_278")
		private String attr278;


		/**
		* ATTR_279
		*/
		@Column(name="ATTR_279")
		private String attr279;


		/**
		* ATTR_280
		*/
		@Column(name="ATTR_280")
		private String attr280;


		/**
		* ATTR_281
		*/
		@Column(name="ATTR_281")
		private String attr281;


		/**
		* ATTR_282
		*/
		@Column(name="ATTR_282")
		private String attr282;


		/**
		* ATTR_283
		*/
		@Column(name="ATTR_283")
		private String attr283;


		/**
		* ATTR_284
		*/
		@Column(name="ATTR_284")
		private String attr284;


		/**
		* ATTR_285
		*/
		@Column(name="ATTR_285")
		private String attr285;


		/**
		* ATTR_286
		*/
		@Column(name="ATTR_286")
		private String attr286;


		/**
		* ATTR_287
		*/
		@Column(name="ATTR_287")
		private String attr287;


		/**
		* ATTR_288
		*/
		@Column(name="ATTR_288")
		private String attr288;


		/**
		* ATTR_289
		*/
		@Column(name="ATTR_289")
		private String attr289;


		/**
		* ATTR_290
		*/
		@Column(name="ATTR_290")
		private String attr290;


		/**
		* ATTR_291
		*/
		@Column(name="ATTR_291")
		private String attr291;


		/**
		* ATTR_292
		*/
		@Column(name="ATTR_292")
		private String attr292;


		/**
		* ATTR_293
		*/
		@Column(name="ATTR_293")
		private String attr293;


		/**
		* ATTR_294
		*/
		@Column(name="ATTR_294")
		private String attr294;


		/**
		* ATTR_295
		*/
		@Column(name="ATTR_295")
		private String attr295;


		/**
		* ATTR_296
		*/
		@Column(name="ATTR_296")
		private String attr296;


		/**
		* ATTR_297
		*/
		@Column(name="ATTR_297")
		private String attr297;


		/**
		* ATTR_298
		*/
		@Column(name="ATTR_298")
		private String attr298;


		/**
		* ATTR_299
		*/
		@Column(name="ATTR_299")
		private String attr299;


		/**
		* ATTR_300
		*/
		@Column(name="ATTR_300")
		private String attr300;


		/**
		* ATTR_301
		*/
		@Column(name="ATTR_301")
		private String attr301;


		/**
		* ATTR_302
		*/
		@Column(name="ATTR_302")
		private String attr302;


		/**
		* ATTR_303
		*/
		@Column(name="ATTR_303")
		private String attr303;


		/**
		* ATTR_304
		*/
		@Column(name="ATTR_304")
		private String attr304;


		/**
		* ATTR_305
		*/
		@Column(name="ATTR_305")
		private String attr305;


		/**
		* ATTR_306
		*/
		@Column(name="ATTR_306")
		private String attr306;


		/**
		* ATTR_307
		*/
		@Column(name="ATTR_307")
		private String attr307;


		/**
		* ATTR_308
		*/
		@Column(name="ATTR_308")
		private String attr308;


		/**
		* ATTR_309
		*/
		@Column(name="ATTR_309")
		private String attr309;


		/**
		* ATTR_310
		*/
		@Column(name="ATTR_310")
		private String attr310;


		/**
		* ATTR_311
		*/
		@Column(name="ATTR_311")
		private String attr311;


		/**
		* ATTR_312
		*/
		@Column(name="ATTR_312")
		private String attr312;


		/**
		* ATTR_313
		*/
		@Column(name="ATTR_313")
		private String attr313;


		/**
		* ATTR_314
		*/
		@Column(name="ATTR_314")
		private String attr314;


		/**
		* ATTR_315
		*/
		@Column(name="ATTR_315")
		private String attr315;


		/**
		* ATTR_316
		*/
		@Column(name="ATTR_316")
		private String attr316;


		/**
		* ATTR_317
		*/
		@Column(name="ATTR_317")
		private String attr317;


		/**
		* ATTR_318
		*/
		@Column(name="ATTR_318")
		private String attr318;


		/**
		* ATTR_319
		*/
		@Column(name="ATTR_319")
		private String attr319;


		/**
		* ATTR_320
		*/
		@Column(name="ATTR_320")
		private String attr320;


		/**
		* ATTR_321
		*/
		@Column(name="ATTR_321")
		private String attr321;


		/**
		* ATTR_322
		*/
		@Column(name="ATTR_322")
		private String attr322;


		/**
		* ATTR_323
		*/
		@Column(name="ATTR_323")
		private String attr323;


		/**
		* ATTR_324
		*/
		@Column(name="ATTR_324")
		private String attr324;


		/**
		* ATTR_325
		*/
		@Column(name="ATTR_325")
		private String attr325;


		/**
		* ATTR_326
		*/
		@Column(name="ATTR_326")
		private String attr326;


		/**
		* ATTR_327
		*/
		@Column(name="ATTR_327")
		private String attr327;


		/**
		* ATTR_328
		*/
		@Column(name="ATTR_328")
		private String attr328;


		/**
		* ATTR_329
		*/
		@Column(name="ATTR_329")
		private String attr329;


		/**
		* ATTR_330
		*/
		@Column(name="ATTR_330")
		private String attr330;


		/**
		* ATTR_331
		*/
		@Column(name="ATTR_331")
		private String attr331;


		/**
		* ATTR_332
		*/
		@Column(name="ATTR_332")
		private String attr332;


		/**
		* ATTR_333
		*/
		@Column(name="ATTR_333")
		private String attr333;


		/**
		* ATTR_334
		*/
		@Column(name="ATTR_334")
		private String attr334;


		/**
		* ATTR_335
		*/
		@Column(name="ATTR_335")
		private String attr335;


		/**
		* ATTR_336
		*/
		@Column(name="ATTR_336")
		private String attr336;


		/**
		* ATTR_337
		*/
		@Column(name="ATTR_337")
		private String attr337;


		/**
		* ATTR_338
		*/
		@Column(name="ATTR_338")
		private String attr338;


		/**
		* ATTR_339
		*/
		@Column(name="ATTR_339")
		private String attr339;


		/**
		* ATTR_340
		*/
		@Column(name="ATTR_340")
		private String attr340;


		/**
		* ATTR_341
		*/
		@Column(name="ATTR_341")
		private String attr341;


		/**
		* ATTR_342
		*/
		@Column(name="ATTR_342")
		private String attr342;


		/**
		* ATTR_343
		*/
		@Column(name="ATTR_343")
		private String attr343;


		/**
		* ATTR_344
		*/
		@Column(name="ATTR_344")
		private String attr344;


		/**
		* ATTR_345
		*/
		@Column(name="ATTR_345")
		private String attr345;


		/**
		* ATTR_346
		*/
		@Column(name="ATTR_346")
		private String attr346;


		/**
		* ATTR_347
		*/
		@Column(name="ATTR_347")
		private String attr347;


		/**
		* ATTR_348
		*/
		@Column(name="ATTR_348")
		private String attr348;


		/**
		* ATTR_349
		*/
		@Column(name="ATTR_349")
		private String attr349;


		/**
		* ATTR_350
		*/
		@Column(name="ATTR_350")
		private String attr350;

		
		//bi-directional many-to-one association to SrcCxiErrorTbl
		@OneToMany(mappedBy="snapshotClxAssetDa"/*,fetch=FetchType.EAGER*/)
		private List<CxiErrorTbl> cxiErrorTbls;
		
		
		
		
		public List<CxiErrorTbl> getCxiErrorTbls() {
			return cxiErrorTbls;
		}


		public void setCxiErrorTbls(List<CxiErrorTbl> cxiErrorTbls) {
			this.cxiErrorTbls = cxiErrorTbls;
		}
		
		
		/**
		 * DFR_LINE_ID
		 * 
		 */

		public String getDfrLineId() {
			return dfrLineId;
		}


		public void setDfrLineId(String dfrLineId) {
			this.dfrLineId = dfrLineId;
		}

		/**
		 * DFR_ID
		 * 
		 */
		public String getDfrId() {
			return dfrId;
		}


		public void setDfrId(String dfrId) {
			this.dfrId = dfrId;
		}


		/**
		* ROW_ID
		*/
		public String getHeader1() {
			return header1;
		}


		public void setHeader1(String header1) {
			this.header1 = header1;
		}

		/**
		* ASSET_NUM
		*/
		public String getHeader2() {
			return header2;
		}


		public void setHeader2(String header2) {
			this.header2 = header2;
		}

		/**
		* SERIAL_NUM
		*/
		public String getHeader3() {
			return header3;
		}

		
		public void setHeader3(String header3) {
			this.header3 = header3;
		}

		/**
		* X_ATTR_25
		*/
		public String getHeader4() {
			return header4;
		}


		public void setHeader4(String header4) {
			this.header4 = header4;
		}

		/**
		* X_CUST_UCID
		*/
		public String getHeader5() {
			return header5;
		}


		public void setHeader5(String header5) {
			this.header5 = header5;
		}

		/**
		* OU_NUM
		*/
		public String getHeader6() {
			return header6;
		}


		public void setHeader6(String header6) {
			this.header6 = header6;
		}

		/**
		* ACCOUNT_NAME
		*/
		public String getHeader7() {
			return header7;
		}


		public void setHeader7(String header7) {
			this.header7 = header7;
		}

		/**
		* IBX
		*/
		public String getHeader8() {
			return header8;
		}


		public void setHeader8(String header8) {
			this.header8 = header8;
		}

		/**
		* X_UNIQUE_SPACE_ID
		*/
		public String getHeader9() {
			return header9;
		}

		
		public void setHeader9(String header9) {
			this.header9 = header9;
		}

		/**
		* CAGE_UNIQUE_SPACE_ID
		*/
		public String getHeader10() {
			return header10;
		}


		public void setHeader10(String header10) {
			this.header10 = header10;
		}

		/**
		* X_CAB_USID
		*/
		public String getHeader11() {
			return header11;
		}


		public void setHeader11(String header11) {
			this.header11 = header11;
		}

		/**
		* CAB_UNIQUE_SPACE_ID
		*/
		public String getHeader12() {
			return header12;
		}


		public void setHeader12(String header12) {
			this.header12 = header12;
		}

		/**
		* CAGE_NUMBER
		*/
		public String getHeader13() {
			return header13;
		}


		public void setHeader13(String header13) {
			this.header13 = header13;
		}

		/**
		* X_CAB_ID
		*/
		public String getHeader14() {
			return header14;
		}

		/**
		* X_CAB_ID
		*/
		public void setHeader14(String header14) {
			this.header14 = header14;
		}

		/**
		* BASE_CURRENCY_CD
		*/
		public String getHeader15() {
			return header15;
		}

		/**
		* BASE_CURRENCY_CD
		*/
		public void setHeader15(String header15) {
			this.header15 = header15;
		}

		/**
		* SYSTEM_NAME
		*/
		public String getHeader16() {
			return header16;
		}

		/**
		* SYSTEM_NAME
		*/
		public void setHeader16(String header16) {
			this.header16 = header16;
		}

		/**
		* ASSET_INSTALL_DATE
		*/
		public Date getHeader17() {
			return header17;
		}

		/**
		* ASSET_INSTALL_DATE
		*/
		public void setHeader17(Date header17) {
			this.header17 = header17;
		}

		/**
		* STATUS_CD
		*/
		public String getHeader18() {
			return header18;
		}

		/**
		* STATUS_CD
		*/
		public void setHeader18(String header18) {
			this.header18 = header18;
		}

		/**
		* ASSET_SUB_STATUS
		*/
		public String getHeader19() {
			return header19;
		}

		/**
		* ASSET_SUB_STATUS
		*/
		public void setHeader19(String header19) {
			this.header19 = header19;
		}

		/**
		* NAME
		*/
		public String getHeader20() {
			return header20;
		}

		/**
		* NAME
		*/
		public void setHeader20(String header20) {
			this.header20 = header20;
		}

		/**
		* PAR_ASSET_ID
		*/
		public String getHeader21() {
			return header21;
		}

		/**
		* PAR_ASSET_ID
		*/
		public void setHeader21(String header21) {
			this.header21 = header21;
		}

		/**
		* ROOT_ASSET_ID
		*/
		public String getHeader22() {
			return header22;
		}

		/**
		* ROOT_ASSET_ID
		*/
		public void setHeader22(String header22) {
			this.header22 = header22;
		}

		/**
		* PAR_AST_NUM
		*/
		public String getHeader23() {
			return header23;
		}

		/**
		* PAR_AST_NUM
		*/
		public void setHeader23(String header23) {
			this.header23 = header23;
		}

		/**
		* ROOT_AST_NUM
		*/
		public String getHeader24() {
			return header24;
		}

		/**
		* ROOT_AST_NUM
		*/
		public void setHeader24(String header24) {
			this.header24 = header24;
		}

		/**
		* PARENT_PRODUCT_NAME
		*/
		public String getHeader25() {
			return header25;
		}

		/**
		* PARENT_PRODUCT_NAME
		*/
		public void setHeader25(String header25) {
			this.header25 = header25;
		}

		/**
		* POF_NAME
		*/
		public String getHeader26() {
			return header26;
		}

		/**
		* POF_NAME
		*/
		public void setHeader26(String header26) {
			this.header26 = header26;
		}

		/**
		* X_OPS_PAR_ASSET_NUM
		*/
		public String getHeader27() {
			return header27;
		}

		/**
		* X_OPS_PAR_ASSET_NUM
		*/
		public void setHeader27(String header27) {
			this.header27 = header27;
		}

		/**
		* X_ROOT_ASSET_NUM
		*/
		public String getHeader28() {
			return header28;
		}

		/**
		* X_ROOT_ASSET_NUM
		*/
		public void setHeader28(String header28) {
			this.header28 = header28;
		}

		/**
		* X_ATTR_10
		*/
		public String getHeader29() {
			return header29;
		}

		/**
		* X_ATTR_10
		*/
		public void setHeader29(String header29) {
			this.header29 = header29;
		}

		/**
		* X_ATTR_22
		*/
		public String getHeader30() {
			return header30;
		}

		/**
		* X_ATTR_22
		*/
		public void setHeader30(String header30) {
			this.header30 = header30;
		}

		/**
		* BILLING_AGR_NUM
		*/
		public String getHeader31() {
			return header31;
		}

		/**
		* BILLING_AGR_NUM
		*/
		public void setHeader31(String header31) {
			this.header31 = header31;
		}


		/**
		* BILLING_AGR_LN_NUM
		*/
		public String getHeader32() {
			return header32;
		}


		/**
		* BILLING_AGR_LN_NUM
		*/
		public void setHeader32(String header32) {
			this.header32 = header32;
		}

		/**
		* PRICE
		*/
		public String getHeader33() {
			return header33;
		}

		/**
		* PRICE
		*/
		public void setHeader33(String header33) {
			this.header33 = header33;
		}

		/**
		* CREATED
		*/
		public Date getHeader34() {
			return header34;
		}

		/**
		* CREATED
		*/
		public void setHeader34(Date header34) {
			this.header34 = header34;
		}

		/**
		* CREATED_BY
		*/
		public String getHeader35() {
			return header35;
		}

		/**
		* CREATED_BY
		*/
		public void setHeader35(String header35) {
			this.header35 = header35;
		}

		/**
		* LAST_UPD
		*/
		public Date getHeader36() {
			return header36;
		}

		/**
		* LAST_UPD
		*/
		public void setHeader36(Date header36) {
			this.header36 = header36;
		}

		/**
		* LAST_UPD_BY
		*/
		public String getHeader37() {
			return header37;
		}

		/**
		* LAST_UPD_BY
		*/
		public void setHeader37(String header37) {
			this.header37 = header37;
		}

		/**
		* DQM_ERR_FLG
		*/
		public String getHeader38() {
			return header38;
		}

		/**
		* DQM_ERR_FLG
		*/
		public void setHeader38(String header38) {
			this.header38 = header38;
		}

		/**
		* PROD_ID
		*/
		public String getHeader39() {
			return header39;
		}

		/**
		* PROD_ID
		*/
		public void setHeader39(String header39) {
			this.header39 = header39;
		}

		/**
		* X_REL_ACCNT_ID
		*/
		public String getHeader40() {
			return header40;
		}

		/**
		* X_REL_ACCNT_ID
		*/
		public void setHeader40(String header40) {
			this.header40 = header40;
		}


		/**
		* RELATED_ACCNT_NUM
		*/
		public String getHeader41() {
			return header41;
		}


		/**
		* RELATED_ACCNT_NUM
		*/
		public void setHeader41(String header41) {
			this.header41 = header41;
		}

		/**
		* RELATED_ACCNT_NAME
		*/
		public String getHeader42() {
			return header42;
		}

		/**
		* RELATED_ACCNT_NAME
		*/
		public void setHeader42(String header42) {
			this.header42 = header42;
		}

		/**
		* X_CABLE_ID
		*/
		public String getHeader43() {
			return header43;
		}

		/**
		* X_CABLE_ID
		*/
		public void setHeader43(String header43) {
			this.header43 = header43;
		}

		/**
		* X_MASS_CREATE_ASSET
		*/
		public String getHeader44() {
			return header44;
		}

		/**
		* X_MASS_CREATE_ASSET
		*/
		public void setHeader44(String header44) {
			this.header44 = header44;
		}

		/**
		* DESC_TEXT
		*/
		public String getHeader45() {
			return header45;
		}

		/**
		* DESC_TEXT
		*/
		public void setHeader45(String header45) {
			this.header45 = header45;
		}

		/**
		* PART_NUM
		*/
		public String getHeader46() {
			return header46;
		}

		/**
		* PART_NUM
		*/
		public void setHeader46(String header46) {
			this.header46 = header46;
		}

		/**
		* X_MACD_SPLIT_M
		*/
		public String getHeader47() {
			return header47;
		}

		/**
		* X_MACD_SPLIT_M
		*/
		public void setHeader47(String header47) {
			this.header47 = header47;
		}

		/**
		* POF_X_MACD_SPLIT_M
		*/
		public String getHeader48() {
			return header48;
		}

		/**
		* POF_X_MACD_SPLIT_M
		*/
		public void setHeader48(String header48) {
			this.header48 = header48;
		}

		/**
		* POF_PART_NUM
		*/
		public String getHeader49() {
			return header49;
		}

		/**
		* POF_PART_NUM
		*/
		public void setHeader49(String header49) {
			this.header49 = header49;
		}

		/**
		* POF_STATUS_CD
		*/
		public String getHeader50() {
			return header50;
		}

		/**
		* POF_STATUS_CD
		*/
		public void setHeader50(String header50) {
			this.header50 = header50;
		}

		/**
		* REGION
		*/
		public String getHeader51() {
			return header51;
		}

		/**
		* REGION
		*/
		public void setHeader51(String header51) {
			this.header51 = header51;
		}

		/**
		* X_SYS_NAME
		*/
		public String getHeader52() {
			return header52;
		}

		/**
		* X_SYS_NAME
		*/
		public void setHeader52(String header52) {
			this.header52 = header52;
		}

		/**
		* OWNER_ACCNT_ID
		*/
		public String getHeader53() {
			return header53;
		}

		/**
		* OWNER_ACCNT_ID
		*/
		public void setHeader53(String header53) {
			this.header53 = header53;
		}

		/**
		* X_PP_ID
		*/
		public String getHeader54() {
			return header54;
		}

		/**
		* X_PP_ID
		*/
		public void setHeader54(String header54) {
			this.header54 = header54;
		}

		/**
		* END_DT
		*/
		public Date getHeader55() {
			return header55;
		}

		/**
		* END_DT
		*/
		public void setHeader55(Date header55) {
			this.header55 = header55;
		}

		/**
		* COUNTRY
		*/
		public String getHeader56() {
			return header56;
		}

		/**
		* COUNTRY
		*/
		public void setHeader56(String header56) {
			this.header56 = header56;
		}

		/**
		* HEADER_01
		*/
		public String getHeader57() {
			return header57;
		}

		/**
		* HEADER_01
		*/
		public void setHeader57(String header57) {
			this.header57 = header57;
		}

		/**
		* HEADER_02
		*/
		public String getHeader58() {
			return header58;
		}

		/**
		* HEADER_02
		*/
		public void setHeader58(String header58) {
			this.header58 = header58;
		}

		/**
		* HEADER_03
		*/
		public String getHeader59() {
			return header59;
		}

		/**
		* HEADER_03
		*/
		public void setHeader59(String header59) {
			this.header59 = header59;
		}

		/**
		* HEADER_04
		*/
		public String getHeader60() {
			return header60;
		}

		/**
		* HEADER_04
		*/
		public void setHeader60(String header60) {
			this.header60 = header60;
		}

		/**
		* HEADER_05
		*/
		public String getHeader61() {
			return header61;
		}

		/**
		* HEADER_05
		*/
		public void setHeader61(String header61) {
			this.header61 = header61;
		}

		/**
		* HEADER_06
		*/
		public String getHeader62() {
			return header62;
		}
		/**
		* HEADER_06
		*/

		public void setHeader62(String header62) {
			this.header62 = header62;
		}

		/**
		* HEADER_07
		*/
		public String getHeader63() {
			return header63;
		}

		/**
		* HEADER_07
		*/
		public void setHeader63(String header63) {
			this.header63 = header63;
		}

		/**
		* HEADER_08
		*/
		public String getHeader64() {
			return header64;
		}

		/**
		* HEADER_08
		*/
		public void setHeader64(String header64) {
			this.header64 = header64;
		}


		/**
		* HEADER_09
		*/
		public String getHeader65() {
			return header65;
		}


		/**
		* HEADER_09
		*/
		public void setHeader65(String header65) {
			this.header65 = header65;
		}

		/**
		* HEADER_10
		*/
		public String getHeader66() {
			return header66;
		}

		/**
		* HEADER_10
		*/
		public void setHeader66(String header66) {
			this.header66 = header66;
		}

		/**
		* HEADER_11
		*/
		/*public String getHeader67() {
			return header67;
		}*/

		/**
		* HEADER_11
		*/
		/*public void setHeader67(String header67) {
			this.header67 = header67;
		}*/

		/**
		* HEADER_12
		*/
		/*public String getHeader68() {
			return header68;
		}*/

		/**
		* HEADER_12
		*/
		/*public void setHeader68(String header68) {
			this.header68 = header68;
		}*/

		/**
		* HEADER_13
		*/
		/*public String getHeader69() {
			return header69;
		}*/

		/**
		* HEADER_13
		*/
		/*public void setHeader69(String header69) {
			this.header69 = header69;
		}*/

		/**
		* HEADER_14
		*/
		/*public String getHeader70() {
			return header70;
		}*/

		/**
		* HEADER_14
		*/
		/*public void setHeader70(String header70) {
			this.header70 = header70;
		}*/


		public String getAttr1() {
			return attr1;
		}


		public void setAttr1(String attr1) {
			this.attr1 = attr1;
		}


		public String getAttr2() {
			return attr2;
		}


		public void setAttr2(String attr2) {
			this.attr2 = attr2;
		}


		public String getAttr3() {
			return attr3;
		}


		public void setAttr3(String attr3) {
			this.attr3 = attr3;
		}


		public String getAttr4() {
			return attr4;
		}


		public void setAttr4(String attr4) {
			this.attr4 = attr4;
		}


		public String getAttr5() {
			return attr5;
		}


		public void setAttr5(String attr5) {
			this.attr5 = attr5;
		}


		public String getAttr6() {
			return attr6;
		}


		public void setAttr6(String attr6) {
			this.attr6 = attr6;
		}


		public String getAttr7() {
			return attr7;
		}


		public void setAttr7(String attr7) {
			this.attr7 = attr7;
		}


		public String getAttr8() {
			return attr8;
		}


		public void setAttr8(String attr8) {
			this.attr8 = attr8;
		}


		public String getAttr9() {
			return attr9;
		}


		public void setAttr9(String attr9) {
			this.attr9 = attr9;
		}


		public String getAttr10() {
			return attr10;
		}


		public void setAttr10(String attr10) {
			this.attr10 = attr10;
		}


		public String getAttr11() {
			return attr11;
		}


		public void setAttr11(String attr11) {
			this.attr11 = attr11;
		}


		public String getAttr12() {
			return attr12;
		}


		public void setAttr12(String attr12) {
			this.attr12 = attr12;
		}


		public String getAttr13() {
			return attr13;
		}


		public void setAttr13(String attr13) {
			this.attr13 = attr13;
		}


		public String getAttr14() {
			return attr14;
		}


		public void setAttr14(String attr14) {
			this.attr14 = attr14;
		}


		public String getAttr15() {
			return attr15;
		}


		public void setAttr15(String attr15) {
			this.attr15 = attr15;
		}


		public String getAttr16() {
			return attr16;
		}


		public void setAttr16(String attr16) {
			this.attr16 = attr16;
		}


		public String getAttr17() {
			return attr17;
		}


		public void setAttr17(String attr17) {
			this.attr17 = attr17;
		}


		public String getAttr18() {
			return attr18;
		}


		public void setAttr18(String attr18) {
			this.attr18 = attr18;
		}


		public String getAttr19() {
			return attr19;
		}


		public void setAttr19(String attr19) {
			this.attr19 = attr19;
		}


		public String getAttr20() {
			return attr20;
		}


		public void setAttr20(String attr20) {
			this.attr20 = attr20;
		}


		public String getAttr21() {
			return attr21;
		}


		public void setAttr21(String attr21) {
			this.attr21 = attr21;
		}


		public String getAttr22() {
			return attr22;
		}


		public void setAttr22(String attr22) {
			this.attr22 = attr22;
		}


		public String getAttr23() {
			return attr23;
		}


		public void setAttr23(String attr23) {
			this.attr23 = attr23;
		}


		public String getAttr24() {
			return attr24;
		}


		public void setAttr24(String attr24) {
			this.attr24 = attr24;
		}


		public String getAttr25() {
			return attr25;
		}


		public void setAttr25(String attr25) {
			this.attr25 = attr25;
		}


		public String getAttr26() {
			return attr26;
		}


		public void setAttr26(String attr26) {
			this.attr26 = attr26;
		}


		public String getAttr27() {
			return attr27;
		}


		public void setAttr27(String attr27) {
			this.attr27 = attr27;
		}


		public String getAttr28() {
			return attr28;
		}


		public void setAttr28(String attr28) {
			this.attr28 = attr28;
		}


		public String getAttr29() {
			return attr29;
		}


		public void setAttr29(String attr29) {
			this.attr29 = attr29;
		}


		public String getAttr30() {
			return attr30;
		}


		public void setAttr30(String attr30) {
			this.attr30 = attr30;
		}


		public String getAttr31() {
			return attr31;
		}


		public void setAttr31(String attr31) {
			this.attr31 = attr31;
		}


		public String getAttr32() {
			return attr32;
		}


		public void setAttr32(String attr32) {
			this.attr32 = attr32;
		}


		public String getAttr33() {
			return attr33;
		}


		public void setAttr33(String attr33) {
			this.attr33 = attr33;
		}


		public String getAttr34() {
			return attr34;
		}


		public void setAttr34(String attr34) {
			this.attr34 = attr34;
		}


		public String getAttr35() {
			return attr35;
		}


		public void setAttr35(String attr35) {
			this.attr35 = attr35;
		}


		public String getAttr36() {
			return attr36;
		}


		public void setAttr36(String attr36) {
			this.attr36 = attr36;
		}


		public String getAttr37() {
			return attr37;
		}


		public void setAttr37(String attr37) {
			this.attr37 = attr37;
		}


		public String getAttr38() {
			return attr38;
		}


		public void setAttr38(String attr38) {
			this.attr38 = attr38;
		}


		public String getAttr39() {
			return attr39;
		}


		public void setAttr39(String attr39) {
			this.attr39 = attr39;
		}


		public String getAttr40() {
			return attr40;
		}


		public void setAttr40(String attr40) {
			this.attr40 = attr40;
		}


		public String getAttr41() {
			return attr41;
		}


		public void setAttr41(String attr41) {
			this.attr41 = attr41;
		}


		public String getAttr42() {
			return attr42;
		}


		public void setAttr42(String attr42) {
			this.attr42 = attr42;
		}


		public String getAttr43() {
			return attr43;
		}


		public void setAttr43(String attr43) {
			this.attr43 = attr43;
		}


		public String getAttr44() {
			return attr44;
		}


		public void setAttr44(String attr44) {
			this.attr44 = attr44;
		}


		public String getAttr45() {
			return attr45;
		}


		public void setAttr45(String attr45) {
			this.attr45 = attr45;
		}


		public String getAttr46() {
			return attr46;
		}


		public void setAttr46(String attr46) {
			this.attr46 = attr46;
		}


		public String getAttr47() {
			return attr47;
		}


		public void setAttr47(String attr47) {
			this.attr47 = attr47;
		}


		public String getAttr48() {
			return attr48;
		}


		public void setAttr48(String attr48) {
			this.attr48 = attr48;
		}


		public String getAttr49() {
			return attr49;
		}


		public void setAttr49(String attr49) {
			this.attr49 = attr49;
		}


		public String getAttr50() {
			return attr50;
		}


		public void setAttr50(String attr50) {
			this.attr50 = attr50;
		}


		public String getAttr51() {
			return attr51;
		}


		public void setAttr51(String attr51) {
			this.attr51 = attr51;
		}


		public String getAttr52() {
			return attr52;
		}


		public void setAttr52(String attr52) {
			this.attr52 = attr52;
		}


		public String getAttr53() {
			return attr53;
		}


		public void setAttr53(String attr53) {
			this.attr53 = attr53;
		}


		public String getAttr54() {
			return attr54;
		}


		public void setAttr54(String attr54) {
			this.attr54 = attr54;
		}


		public String getAttr55() {
			return attr55;
		}


		public void setAttr55(String attr55) {
			this.attr55 = attr55;
		}


		public String getAttr56() {
			return attr56;
		}


		public void setAttr56(String attr56) {
			this.attr56 = attr56;
		}


		public String getAttr57() {
			return attr57;
		}


		public void setAttr57(String attr57) {
			this.attr57 = attr57;
		}


		public String getAttr58() {
			return attr58;
		}


		public void setAttr58(String attr58) {
			this.attr58 = attr58;
		}


		public String getAttr59() {
			return attr59;
		}


		public void setAttr59(String attr59) {
			this.attr59 = attr59;
		}


		public String getAttr60() {
			return attr60;
		}


		public void setAttr60(String attr60) {
			this.attr60 = attr60;
		}


		public String getAttr61() {
			return attr61;
		}


		public void setAttr61(String attr61) {
			this.attr61 = attr61;
		}


		public String getAttr62() {
			return attr62;
		}


		public void setAttr62(String attr62) {
			this.attr62 = attr62;
		}


		public String getAttr63() {
			return attr63;
		}


		public void setAttr63(String attr63) {
			this.attr63 = attr63;
		}


		public String getAttr64() {
			return attr64;
		}


		public void setAttr64(String attr64) {
			this.attr64 = attr64;
		}


		public String getAttr65() {
			return attr65;
		}


		public void setAttr65(String attr65) {
			this.attr65 = attr65;
		}


		public String getAttr66() {
			return attr66;
		}


		public void setAttr66(String attr66) {
			this.attr66 = attr66;
		}


		public String getAttr67() {
			return attr67;
		}


		public void setAttr67(String attr67) {
			this.attr67 = attr67;
		}


		public String getAttr68() {
			return attr68;
		}


		public void setAttr68(String attr68) {
			this.attr68 = attr68;
		}


		public String getAttr69() {
			return attr69;
		}


		public void setAttr69(String attr69) {
			this.attr69 = attr69;
		}


		public String getAttr70() {
			return attr70;
		}


		public void setAttr70(String attr70) {
			this.attr70 = attr70;
		}


		public String getAttr71() {
			return attr71;
		}


		public void setAttr71(String attr71) {
			this.attr71 = attr71;
		}


		public String getAttr72() {
			return attr72;
		}


		public void setAttr72(String attr72) {
			this.attr72 = attr72;
		}


		public String getAttr73() {
			return attr73;
		}


		public void setAttr73(String attr73) {
			this.attr73 = attr73;
		}


		public String getAttr74() {
			return attr74;
		}


		public void setAttr74(String attr74) {
			this.attr74 = attr74;
		}


		public String getAttr75() {
			return attr75;
		}


		public void setAttr75(String attr75) {
			this.attr75 = attr75;
		}


		public String getAttr76() {
			return attr76;
		}


		public void setAttr76(String attr76) {
			this.attr76 = attr76;
		}


		public String getAttr77() {
			return attr77;
		}


		public void setAttr77(String attr77) {
			this.attr77 = attr77;
		}


		public String getAttr78() {
			return attr78;
		}


		public void setAttr78(String attr78) {
			this.attr78 = attr78;
		}


		public String getAttr79() {
			return attr79;
		}


		public void setAttr79(String attr79) {
			this.attr79 = attr79;
		}


		public String getAttr80() {
			return attr80;
		}


		public void setAttr80(String attr80) {
			this.attr80 = attr80;
		}


		public String getAttr81() {
			return attr81;
		}


		public void setAttr81(String attr81) {
			this.attr81 = attr81;
		}


		public String getAttr82() {
			return attr82;
		}


		public void setAttr82(String attr82) {
			this.attr82 = attr82;
		}


		public String getAttr83() {
			return attr83;
		}


		public void setAttr83(String attr83) {
			this.attr83 = attr83;
		}


		public String getAttr84() {
			return attr84;
		}


		public void setAttr84(String attr84) {
			this.attr84 = attr84;
		}


		public String getAttr85() {
			return attr85;
		}


		public void setAttr85(String attr85) {
			this.attr85 = attr85;
		}


		public String getAttr86() {
			return attr86;
		}


		public void setAttr86(String attr86) {
			this.attr86 = attr86;
		}


		public String getAttr87() {
			return attr87;
		}


		public void setAttr87(String attr87) {
			this.attr87 = attr87;
		}


		public String getAttr88() {
			return attr88;
		}


		public void setAttr88(String attr88) {
			this.attr88 = attr88;
		}


		public String getAttr89() {
			return attr89;
		}


		public void setAttr89(String attr89) {
			this.attr89 = attr89;
		}


		public String getAttr90() {
			return attr90;
		}


		public void setAttr90(String attr90) {
			this.attr90 = attr90;
		}


		public String getAttr91() {
			return attr91;
		}


		public void setAttr91(String attr91) {
			this.attr91 = attr91;
		}


		public String getAttr92() {
			return attr92;
		}


		public void setAttr92(String attr92) {
			this.attr92 = attr92;
		}


		public String getAttr93() {
			return attr93;
		}


		public void setAttr93(String attr93) {
			this.attr93 = attr93;
		}


		public String getAttr94() {
			return attr94;
		}


		public void setAttr94(String attr94) {
			this.attr94 = attr94;
		}


		public String getAttr95() {
			return attr95;
		}


		public void setAttr95(String attr95) {
			this.attr95 = attr95;
		}


		public String getAttr96() {
			return attr96;
		}


		public void setAttr96(String attr96) {
			this.attr96 = attr96;
		}


		public String getAttr97() {
			return attr97;
		}


		public void setAttr97(String attr97) {
			this.attr97 = attr97;
		}


		public String getAttr98() {
			return attr98;
		}


		public void setAttr98(String attr98) {
			this.attr98 = attr98;
		}


		public String getAttr99() {
			return attr99;
		}


		public void setAttr99(String attr99) {
			this.attr99 = attr99;
		}


		public String getAttr100() {
			return attr100;
		}


		public void setAttr100(String attr100) {
			this.attr100 = attr100;
		}


		public String getAttr101() {
			return attr101;
		}


		public void setAttr101(String attr101) {
			this.attr101 = attr101;
		}


		public String getAttr102() {
			return attr102;
		}


		public void setAttr102(String attr102) {
			this.attr102 = attr102;
		}


		public String getAttr103() {
			return attr103;
		}


		public void setAttr103(String attr103) {
			this.attr103 = attr103;
		}


		public String getAttr104() {
			return attr104;
		}


		public void setAttr104(String attr104) {
			this.attr104 = attr104;
		}


		public String getAttr105() {
			return attr105;
		}


		public void setAttr105(String attr105) {
			this.attr105 = attr105;
		}


		public String getAttr106() {
			return attr106;
		}


		public void setAttr106(String attr106) {
			this.attr106 = attr106;
		}


		public String getAttr107() {
			return attr107;
		}


		public void setAttr107(String attr107) {
			this.attr107 = attr107;
		}


		public String getAttr108() {
			return attr108;
		}


		public void setAttr108(String attr108) {
			this.attr108 = attr108;
		}


		public String getAttr109() {
			return attr109;
		}


		public void setAttr109(String attr109) {
			this.attr109 = attr109;
		}


		public String getAttr110() {
			return attr110;
		}


		public void setAttr110(String attr110) {
			this.attr110 = attr110;
		}


		public String getAttr111() {
			return attr111;
		}


		public void setAttr111(String attr111) {
			this.attr111 = attr111;
		}


		public String getAttr112() {
			return attr112;
		}


		public void setAttr112(String attr112) {
			this.attr112 = attr112;
		}


		public String getAttr113() {
			return attr113;
		}


		public void setAttr113(String attr113) {
			this.attr113 = attr113;
		}


		public String getAttr114() {
			return attr114;
		}


		public void setAttr114(String attr114) {
			this.attr114 = attr114;
		}


		public String getAttr115() {
			return attr115;
		}


		public void setAttr115(String attr115) {
			this.attr115 = attr115;
		}


		public String getAttr116() {
			return attr116;
		}


		public void setAttr116(String attr116) {
			this.attr116 = attr116;
		}


		public String getAttr117() {
			return attr117;
		}


		public void setAttr117(String attr117) {
			this.attr117 = attr117;
		}


		public String getAttr118() {
			return attr118;
		}


		public void setAttr118(String attr118) {
			this.attr118 = attr118;
		}


		public String getAttr119() {
			return attr119;
		}


		public void setAttr119(String attr119) {
			this.attr119 = attr119;
		}


		public String getAttr120() {
			return attr120;
		}


		public void setAttr120(String attr120) {
			this.attr120 = attr120;
		}


		public String getAttr121() {
			return attr121;
		}


		public void setAttr121(String attr121) {
			this.attr121 = attr121;
		}


		public String getAttr122() {
			return attr122;
		}


		public void setAttr122(String attr122) {
			this.attr122 = attr122;
		}


		public String getAttr123() {
			return attr123;
		}


		public void setAttr123(String attr123) {
			this.attr123 = attr123;
		}


		public String getAttr124() {
			return attr124;
		}


		public void setAttr124(String attr124) {
			this.attr124 = attr124;
		}


		public String getAttr125() {
			return attr125;
		}


		public void setAttr125(String attr125) {
			this.attr125 = attr125;
		}


		public String getAttr126() {
			return attr126;
		}


		public void setAttr126(String attr126) {
			this.attr126 = attr126;
		}


		public String getAttr127() {
			return attr127;
		}


		public void setAttr127(String attr127) {
			this.attr127 = attr127;
		}


		public String getAttr128() {
			return attr128;
		}


		public void setAttr128(String attr128) {
			this.attr128 = attr128;
		}


		public String getAttr129() {
			return attr129;
		}


		public void setAttr129(String attr129) {
			this.attr129 = attr129;
		}


		public String getAttr130() {
			return attr130;
		}


		public void setAttr130(String attr130) {
			this.attr130 = attr130;
		}


		public String getAttr131() {
			return attr131;
		}


		public void setAttr131(String attr131) {
			this.attr131 = attr131;
		}


		public String getAttr132() {
			return attr132;
		}


		public void setAttr132(String attr132) {
			this.attr132 = attr132;
		}


		public String getAttr133() {
			return attr133;
		}


		public void setAttr133(String attr133) {
			this.attr133 = attr133;
		}


		public String getAttr134() {
			return attr134;
		}


		public void setAttr134(String attr134) {
			this.attr134 = attr134;
		}


		public String getAttr135() {
			return attr135;
		}


		public void setAttr135(String attr135) {
			this.attr135 = attr135;
		}


		public String getAttr136() {
			return attr136;
		}


		public void setAttr136(String attr136) {
			this.attr136 = attr136;
		}


		public String getAttr137() {
			return attr137;
		}


		public void setAttr137(String attr137) {
			this.attr137 = attr137;
		}


		public String getAttr138() {
			return attr138;
		}


		public void setAttr138(String attr138) {
			this.attr138 = attr138;
		}


		public String getAttr139() {
			return attr139;
		}


		public void setAttr139(String attr139) {
			this.attr139 = attr139;
		}


		public String getAttr140() {
			return attr140;
		}


		public void setAttr140(String attr140) {
			this.attr140 = attr140;
		}


		public String getAttr141() {
			return attr141;
		}


		public void setAttr141(String attr141) {
			this.attr141 = attr141;
		}


		public String getAttr142() {
			return attr142;
		}


		public void setAttr142(String attr142) {
			this.attr142 = attr142;
		}


		public String getAttr143() {
			return attr143;
		}


		public void setAttr143(String attr143) {
			this.attr143 = attr143;
		}


		public String getAttr144() {
			return attr144;
		}


		public void setAttr144(String attr144) {
			this.attr144 = attr144;
		}


		public String getAttr145() {
			return attr145;
		}


		public void setAttr145(String attr145) {
			this.attr145 = attr145;
		}


		public String getAttr146() {
			return attr146;
		}


		public void setAttr146(String attr146) {
			this.attr146 = attr146;
		}


		public String getAttr147() {
			return attr147;
		}


		public void setAttr147(String attr147) {
			this.attr147 = attr147;
		}


		public String getAttr148() {
			return attr148;
		}


		public void setAttr148(String attr148) {
			this.attr148 = attr148;
		}


		public String getAttr149() {
			return attr149;
		}


		public void setAttr149(String attr149) {
			this.attr149 = attr149;
		}


		public String getAttr150() {
			return attr150;
		}


		public void setAttr150(String attr150) {
			this.attr150 = attr150;
		}


		public String getAttr151() {
			return attr151;
		}


		public void setAttr151(String attr151) {
			this.attr151 = attr151;
		}


		public String getAttr152() {
			return attr152;
		}


		public void setAttr152(String attr152) {
			this.attr152 = attr152;
		}


		public String getAttr153() {
			return attr153;
		}


		public void setAttr153(String attr153) {
			this.attr153 = attr153;
		}


		public String getAttr154() {
			return attr154;
		}


		public void setAttr154(String attr154) {
			this.attr154 = attr154;
		}


		public String getAttr155() {
			return attr155;
		}


		public void setAttr155(String attr155) {
			this.attr155 = attr155;
		}


		public String getAttr156() {
			return attr156;
		}


		public void setAttr156(String attr156) {
			this.attr156 = attr156;
		}


		public String getAttr157() {
			return attr157;
		}


		public void setAttr157(String attr157) {
			this.attr157 = attr157;
		}


		public String getAttr158() {
			return attr158;
		}


		public void setAttr158(String attr158) {
			this.attr158 = attr158;
		}


		public String getAttr159() {
			return attr159;
		}


		public void setAttr159(String attr159) {
			this.attr159 = attr159;
		}


		public String getAttr160() {
			return attr160;
		}


		public void setAttr160(String attr160) {
			this.attr160 = attr160;
		}


		public String getAttr161() {
			return attr161;
		}


		public void setAttr161(String attr161) {
			this.attr161 = attr161;
		}


		public String getAttr162() {
			return attr162;
		}


		public void setAttr162(String attr162) {
			this.attr162 = attr162;
		}


		public String getAttr163() {
			return attr163;
		}


		public void setAttr163(String attr163) {
			this.attr163 = attr163;
		}


		public String getAttr164() {
			return attr164;
		}


		public void setAttr164(String attr164) {
			this.attr164 = attr164;
		}


		public String getAttr165() {
			return attr165;
		}


		public void setAttr165(String attr165) {
			this.attr165 = attr165;
		}


		public String getAttr166() {
			return attr166;
		}


		public void setAttr166(String attr166) {
			this.attr166 = attr166;
		}


		public String getAttr167() {
			return attr167;
		}


		public void setAttr167(String attr167) {
			this.attr167 = attr167;
		}


		public String getAttr168() {
			return attr168;
		}


		public void setAttr168(String attr168) {
			this.attr168 = attr168;
		}


		public String getAttr169() {
			return attr169;
		}


		public void setAttr169(String attr169) {
			this.attr169 = attr169;
		}


		public String getAttr170() {
			return attr170;
		}


		public void setAttr170(String attr170) {
			this.attr170 = attr170;
		}


		public String getAttr171() {
			return attr171;
		}


		public void setAttr171(String attr171) {
			this.attr171 = attr171;
		}


		public String getAttr172() {
			return attr172;
		}


		public void setAttr172(String attr172) {
			this.attr172 = attr172;
		}


		public String getAttr173() {
			return attr173;
		}


		public void setAttr173(String attr173) {
			this.attr173 = attr173;
		}


		public String getAttr174() {
			return attr174;
		}


		public void setAttr174(String attr174) {
			this.attr174 = attr174;
		}


		public String getAttr175() {
			return attr175;
		}


		public void setAttr175(String attr175) {
			this.attr175 = attr175;
		}


		public String getAttr176() {
			return attr176;
		}


		public void setAttr176(String attr176) {
			this.attr176 = attr176;
		}


		public String getAttr177() {
			return attr177;
		}


		public void setAttr177(String attr177) {
			this.attr177 = attr177;
		}


		public String getAttr178() {
			return attr178;
		}


		public void setAttr178(String attr178) {
			this.attr178 = attr178;
		}


		public String getAttr179() {
			return attr179;
		}


		public void setAttr179(String attr179) {
			this.attr179 = attr179;
		}


		public String getAttr180() {
			return attr180;
		}


		public void setAttr180(String attr180) {
			this.attr180 = attr180;
		}


		public String getAttr181() {
			return attr181;
		}


		public void setAttr181(String attr181) {
			this.attr181 = attr181;
		}


		public String getAttr182() {
			return attr182;
		}


		public void setAttr182(String attr182) {
			this.attr182 = attr182;
		}


		public String getAttr183() {
			return attr183;
		}


		public void setAttr183(String attr183) {
			this.attr183 = attr183;
		}


		public String getAttr184() {
			return attr184;
		}


		public void setAttr184(String attr184) {
			this.attr184 = attr184;
		}


		public String getAttr185() {
			return attr185;
		}


		public void setAttr185(String attr185) {
			this.attr185 = attr185;
		}


		public String getAttr186() {
			return attr186;
		}


		public void setAttr186(String attr186) {
			this.attr186 = attr186;
		}


		public String getAttr187() {
			return attr187;
		}


		public void setAttr187(String attr187) {
			this.attr187 = attr187;
		}


		public String getAttr188() {
			return attr188;
		}


		public void setAttr188(String attr188) {
			this.attr188 = attr188;
		}


		public String getAttr189() {
			return attr189;
		}


		public void setAttr189(String attr189) {
			this.attr189 = attr189;
		}


		public String getAttr190() {
			return attr190;
		}


		public void setAttr190(String attr190) {
			this.attr190 = attr190;
		}


		public String getAttr191() {
			return attr191;
		}


		public void setAttr191(String attr191) {
			this.attr191 = attr191;
		}


		public String getAttr192() {
			return attr192;
		}


		public void setAttr192(String attr192) {
			this.attr192 = attr192;
		}


		public String getAttr193() {
			return attr193;
		}


		public void setAttr193(String attr193) {
			this.attr193 = attr193;
		}


		public String getAttr194() {
			return attr194;
		}


		public void setAttr194(String attr194) {
			this.attr194 = attr194;
		}


		public String getAttr195() {
			return attr195;
		}


		public void setAttr195(String attr195) {
			this.attr195 = attr195;
		}


		public String getAttr196() {
			return attr196;
		}


		public void setAttr196(String attr196) {
			this.attr196 = attr196;
		}


		public String getAttr197() {
			return attr197;
		}


		public void setAttr197(String attr197) {
			this.attr197 = attr197;
		}


		public String getAttr198() {
			return attr198;
		}


		public void setAttr198(String attr198) {
			this.attr198 = attr198;
		}


		public String getAttr199() {
			return attr199;
		}


		public void setAttr199(String attr199) {
			this.attr199 = attr199;
		}


		public String getAttr200() {
			return attr200;
		}


		public void setAttr200(String attr200) {
			this.attr200 = attr200;
		}


		public String getAttr201() {
			return attr201;
		}


		public void setAttr201(String attr201) {
			this.attr201 = attr201;
		}


		public String getAttr202() {
			return attr202;
		}


		public void setAttr202(String attr202) {
			this.attr202 = attr202;
		}


		public String getAttr203() {
			return attr203;
		}


		public void setAttr203(String attr203) {
			this.attr203 = attr203;
		}


		public String getAttr204() {
			return attr204;
		}


		public void setAttr204(String attr204) {
			this.attr204 = attr204;
		}


		public String getAttr205() {
			return attr205;
		}


		public void setAttr205(String attr205) {
			this.attr205 = attr205;
		}


		public String getAttr206() {
			return attr206;
		}


		public void setAttr206(String attr206) {
			this.attr206 = attr206;
		}


		public String getAttr207() {
			return attr207;
		}


		public void setAttr207(String attr207) {
			this.attr207 = attr207;
		}


		public String getAttr208() {
			return attr208;
		}


		public void setAttr208(String attr208) {
			this.attr208 = attr208;
		}


		public String getAttr209() {
			return attr209;
		}


		public void setAttr209(String attr209) {
			this.attr209 = attr209;
		}


		public String getAttr210() {
			return attr210;
		}


		public void setAttr210(String attr210) {
			this.attr210 = attr210;
		}


		public String getAttr211() {
			return attr211;
		}


		public void setAttr211(String attr211) {
			this.attr211 = attr211;
		}


		public String getAttr212() {
			return attr212;
		}


		public void setAttr212(String attr212) {
			this.attr212 = attr212;
		}


		public String getAttr213() {
			return attr213;
		}


		public void setAttr213(String attr213) {
			this.attr213 = attr213;
		}


		public String getAttr214() {
			return attr214;
		}


		public void setAttr214(String attr214) {
			this.attr214 = attr214;
		}


		public String getAttr215() {
			return attr215;
		}


		public void setAttr215(String attr215) {
			this.attr215 = attr215;
		}


		public String getAttr216() {
			return attr216;
		}


		public void setAttr216(String attr216) {
			this.attr216 = attr216;
		}


		public String getAttr217() {
			return attr217;
		}


		public void setAttr217(String attr217) {
			this.attr217 = attr217;
		}


		public String getAttr218() {
			return attr218;
		}


		public void setAttr218(String attr218) {
			this.attr218 = attr218;
		}


		public String getAttr219() {
			return attr219;
		}


		public void setAttr219(String attr219) {
			this.attr219 = attr219;
		}


		public String getAttr220() {
			return attr220;
		}


		public void setAttr220(String attr220) {
			this.attr220 = attr220;
		}


		public String getAttr221() {
			return attr221;
		}


		public void setAttr221(String attr221) {
			this.attr221 = attr221;
		}


		public String getAttr222() {
			return attr222;
		}


		public void setAttr222(String attr222) {
			this.attr222 = attr222;
		}


		public String getAttr223() {
			return attr223;
		}


		public void setAttr223(String attr223) {
			this.attr223 = attr223;
		}


		public String getAttr224() {
			return attr224;
		}


		public void setAttr224(String attr224) {
			this.attr224 = attr224;
		}


		public String getAttr225() {
			return attr225;
		}


		public void setAttr225(String attr225) {
			this.attr225 = attr225;
		}


		public String getAttr226() {
			return attr226;
		}


		public void setAttr226(String attr226) {
			this.attr226 = attr226;
		}


		public String getAttr227() {
			return attr227;
		}


		public void setAttr227(String attr227) {
			this.attr227 = attr227;
		}


		public String getAttr228() {
			return attr228;
		}


		public void setAttr228(String attr228) {
			this.attr228 = attr228;
		}


		public String getAttr229() {
			return attr229;
		}


		public void setAttr229(String attr229) {
			this.attr229 = attr229;
		}


		public String getAttr230() {
			return attr230;
		}


		public void setAttr230(String attr230) {
			this.attr230 = attr230;
		}


		public String getAttr231() {
			return attr231;
		}


		public void setAttr231(String attr231) {
			this.attr231 = attr231;
		}


		public String getAttr232() {
			return attr232;
		}


		public void setAttr232(String attr232) {
			this.attr232 = attr232;
		}


		public String getAttr233() {
			return attr233;
		}


		public void setAttr233(String attr233) {
			this.attr233 = attr233;
		}


		public String getAttr234() {
			return attr234;
		}


		public void setAttr234(String attr234) {
			this.attr234 = attr234;
		}


		public String getAttr235() {
			return attr235;
		}


		public void setAttr235(String attr235) {
			this.attr235 = attr235;
		}


		public String getAttr236() {
			return attr236;
		}


		public void setAttr236(String attr236) {
			this.attr236 = attr236;
		}


		public String getAttr237() {
			return attr237;
		}


		public void setAttr237(String attr237) {
			this.attr237 = attr237;
		}


		public String getAttr238() {
			return attr238;
		}


		public void setAttr238(String attr238) {
			this.attr238 = attr238;
		}


		public String getAttr239() {
			return attr239;
		}


		public void setAttr239(String attr239) {
			this.attr239 = attr239;
		}


		public String getAttr240() {
			return attr240;
		}


		public void setAttr240(String attr240) {
			this.attr240 = attr240;
		}


		public String getAttr241() {
			return attr241;
		}


		public void setAttr241(String attr241) {
			this.attr241 = attr241;
		}


		public String getAttr242() {
			return attr242;
		}


		public void setAttr242(String attr242) {
			this.attr242 = attr242;
		}


		public String getAttr243() {
			return attr243;
		}


		public void setAttr243(String attr243) {
			this.attr243 = attr243;
		}


		public String getAttr244() {
			return attr244;
		}


		public void setAttr244(String attr244) {
			this.attr244 = attr244;
		}


		public String getAttr245() {
			return attr245;
		}


		public void setAttr245(String attr245) {
			this.attr245 = attr245;
		}


		public String getAttr246() {
			return attr246;
		}


		public void setAttr246(String attr246) {
			this.attr246 = attr246;
		}


		public String getAttr247() {
			return attr247;
		}


		public void setAttr247(String attr247) {
			this.attr247 = attr247;
		}


		public String getAttr248() {
			return attr248;
		}


		public void setAttr248(String attr248) {
			this.attr248 = attr248;
		}


		public String getAttr249() {
			return attr249;
		}


		public void setAttr249(String attr249) {
			this.attr249 = attr249;
		}


		public String getAttr250() {
			return attr250;
		}


		public void setAttr250(String attr250) {
			this.attr250 = attr250;
		}


		public String getAttr251() {
			return attr251;
		}


		public void setAttr251(String attr251) {
			this.attr251 = attr251;
		}


		public String getAttr252() {
			return attr252;
		}


		public void setAttr252(String attr252) {
			this.attr252 = attr252;
		}


		public String getAttr253() {
			return attr253;
		}


		public void setAttr253(String attr253) {
			this.attr253 = attr253;
		}


		public String getAttr254() {
			return attr254;
		}


		public void setAttr254(String attr254) {
			this.attr254 = attr254;
		}


		public String getAttr255() {
			return attr255;
		}


		public void setAttr255(String attr255) {
			this.attr255 = attr255;
		}


		public String getAttr256() {
			return attr256;
		}


		public void setAttr256(String attr256) {
			this.attr256 = attr256;
		}


		public String getAttr257() {
			return attr257;
		}


		public void setAttr257(String attr257) {
			this.attr257 = attr257;
		}


		public String getAttr258() {
			return attr258;
		}


		public void setAttr258(String attr258) {
			this.attr258 = attr258;
		}


		public String getAttr259() {
			return attr259;
		}


		public void setAttr259(String attr259) {
			this.attr259 = attr259;
		}


		public String getAttr260() {
			return attr260;
		}


		public void setAttr260(String attr260) {
			this.attr260 = attr260;
		}


		public String getAttr261() {
			return attr261;
		}


		public void setAttr261(String attr261) {
			this.attr261 = attr261;
		}


		public String getAttr262() {
			return attr262;
		}


		public void setAttr262(String attr262) {
			this.attr262 = attr262;
		}


		public String getAttr263() {
			return attr263;
		}


		public void setAttr263(String attr263) {
			this.attr263 = attr263;
		}


		public String getAttr264() {
			return attr264;
		}


		public void setAttr264(String attr264) {
			this.attr264 = attr264;
		}


		public String getAttr265() {
			return attr265;
		}


		public void setAttr265(String attr265) {
			this.attr265 = attr265;
		}


		public String getAttr266() {
			return attr266;
		}


		public void setAttr266(String attr266) {
			this.attr266 = attr266;
		}


		public String getAttr267() {
			return attr267;
		}


		public void setAttr267(String attr267) {
			this.attr267 = attr267;
		}


		public String getAttr268() {
			return attr268;
		}


		public void setAttr268(String attr268) {
			this.attr268 = attr268;
		}


		public String getAttr269() {
			return attr269;
		}


		public void setAttr269(String attr269) {
			this.attr269 = attr269;
		}


		public String getAttr270() {
			return attr270;
		}


		public void setAttr270(String attr270) {
			this.attr270 = attr270;
		}


		public String getAttr271() {
			return attr271;
		}


		public void setAttr271(String attr271) {
			this.attr271 = attr271;
		}


		public String getAttr272() {
			return attr272;
		}


		public void setAttr272(String attr272) {
			this.attr272 = attr272;
		}


		public String getAttr273() {
			return attr273;
		}


		public void setAttr273(String attr273) {
			this.attr273 = attr273;
		}


		public String getAttr274() {
			return attr274;
		}


		public void setAttr274(String attr274) {
			this.attr274 = attr274;
		}


		public String getAttr275() {
			return attr275;
		}


		public void setAttr275(String attr275) {
			this.attr275 = attr275;
		}


		public String getAttr276() {
			return attr276;
		}


		public void setAttr276(String attr276) {
			this.attr276 = attr276;
		}


		public String getAttr277() {
			return attr277;
		}


		public void setAttr277(String attr277) {
			this.attr277 = attr277;
		}


		public String getAttr278() {
			return attr278;
		}


		public void setAttr278(String attr278) {
			this.attr278 = attr278;
		}


		public String getAttr279() {
			return attr279;
		}


		public void setAttr279(String attr279) {
			this.attr279 = attr279;
		}


		public String getAttr280() {
			return attr280;
		}


		public void setAttr280(String attr280) {
			this.attr280 = attr280;
		}


		public String getAttr281() {
			return attr281;
		}


		public void setAttr281(String attr281) {
			this.attr281 = attr281;
		}


		public String getAttr282() {
			return attr282;
		}


		public void setAttr282(String attr282) {
			this.attr282 = attr282;
		}


		public String getAttr283() {
			return attr283;
		}


		public void setAttr283(String attr283) {
			this.attr283 = attr283;
		}


		public String getAttr284() {
			return attr284;
		}


		public void setAttr284(String attr284) {
			this.attr284 = attr284;
		}


		public String getAttr285() {
			return attr285;
		}


		public void setAttr285(String attr285) {
			this.attr285 = attr285;
		}


		public String getAttr286() {
			return attr286;
		}


		public void setAttr286(String attr286) {
			this.attr286 = attr286;
		}


		public String getAttr287() {
			return attr287;
		}


		public void setAttr287(String attr287) {
			this.attr287 = attr287;
		}


		public String getAttr288() {
			return attr288;
		}


		public void setAttr288(String attr288) {
			this.attr288 = attr288;
		}


		public String getAttr289() {
			return attr289;
		}


		public void setAttr289(String attr289) {
			this.attr289 = attr289;
		}


		public String getAttr290() {
			return attr290;
		}


		public void setAttr290(String attr290) {
			this.attr290 = attr290;
		}


		public String getAttr291() {
			return attr291;
		}


		public void setAttr291(String attr291) {
			this.attr291 = attr291;
		}


		public String getAttr292() {
			return attr292;
		}


		public void setAttr292(String attr292) {
			this.attr292 = attr292;
		}


		public String getAttr293() {
			return attr293;
		}


		public void setAttr293(String attr293) {
			this.attr293 = attr293;
		}


		public String getAttr294() {
			return attr294;
		}


		public void setAttr294(String attr294) {
			this.attr294 = attr294;
		}


		public String getAttr295() {
			return attr295;
		}


		public void setAttr295(String attr295) {
			this.attr295 = attr295;
		}


		public String getAttr296() {
			return attr296;
		}


		public void setAttr296(String attr296) {
			this.attr296 = attr296;
		}


		public String getAttr297() {
			return attr297;
		}


		public void setAttr297(String attr297) {
			this.attr297 = attr297;
		}


		public String getAttr298() {
			return attr298;
		}


		public void setAttr298(String attr298) {
			this.attr298 = attr298;
		}


		public String getAttr299() {
			return attr299;
		}


		public void setAttr299(String attr299) {
			this.attr299 = attr299;
		}


		public String getAttr300() {
			return attr300;
		}


		public void setAttr300(String attr300) {
			this.attr300 = attr300;
		}


		public String getAttr301() {
			return attr301;
		}


		public void setAttr301(String attr301) {
			this.attr301 = attr301;
		}


		public String getAttr302() {
			return attr302;
		}


		public void setAttr302(String attr302) {
			this.attr302 = attr302;
		}


		public String getAttr303() {
			return attr303;
		}


		public void setAttr303(String attr303) {
			this.attr303 = attr303;
		}


		public String getAttr304() {
			return attr304;
		}


		public void setAttr304(String attr304) {
			this.attr304 = attr304;
		}


		public String getAttr305() {
			return attr305;
		}


		public void setAttr305(String attr305) {
			this.attr305 = attr305;
		}


		public String getAttr306() {
			return attr306;
		}


		public void setAttr306(String attr306) {
			this.attr306 = attr306;
		}


		public String getAttr307() {
			return attr307;
		}


		public void setAttr307(String attr307) {
			this.attr307 = attr307;
		}


		public String getAttr308() {
			return attr308;
		}


		public void setAttr308(String attr308) {
			this.attr308 = attr308;
		}


		public String getAttr309() {
			return attr309;
		}


		public void setAttr309(String attr309) {
			this.attr309 = attr309;
		}


		public String getAttr310() {
			return attr310;
		}


		public void setAttr310(String attr310) {
			this.attr310 = attr310;
		}


		public String getAttr311() {
			return attr311;
		}


		public void setAttr311(String attr311) {
			this.attr311 = attr311;
		}


		public String getAttr312() {
			return attr312;
		}


		public void setAttr312(String attr312) {
			this.attr312 = attr312;
		}


		public String getAttr313() {
			return attr313;
		}


		public void setAttr313(String attr313) {
			this.attr313 = attr313;
		}


		public String getAttr314() {
			return attr314;
		}


		public void setAttr314(String attr314) {
			this.attr314 = attr314;
		}


		public String getAttr315() {
			return attr315;
		}


		public void setAttr315(String attr315) {
			this.attr315 = attr315;
		}


		public String getAttr316() {
			return attr316;
		}


		public void setAttr316(String attr316) {
			this.attr316 = attr316;
		}


		public String getAttr317() {
			return attr317;
		}


		public void setAttr317(String attr317) {
			this.attr317 = attr317;
		}


		public String getAttr318() {
			return attr318;
		}


		public void setAttr318(String attr318) {
			this.attr318 = attr318;
		}


		public String getAttr319() {
			return attr319;
		}


		public void setAttr319(String attr319) {
			this.attr319 = attr319;
		}


		public String getAttr320() {
			return attr320;
		}


		public void setAttr320(String attr320) {
			this.attr320 = attr320;
		}


		public String getAttr321() {
			return attr321;
		}


		public void setAttr321(String attr321) {
			this.attr321 = attr321;
		}


		public String getAttr322() {
			return attr322;
		}


		public void setAttr322(String attr322) {
			this.attr322 = attr322;
		}


		public String getAttr323() {
			return attr323;
		}


		public void setAttr323(String attr323) {
			this.attr323 = attr323;
		}


		public String getAttr324() {
			return attr324;
		}


		public void setAttr324(String attr324) {
			this.attr324 = attr324;
		}


		public String getAttr325() {
			return attr325;
		}


		public void setAttr325(String attr325) {
			this.attr325 = attr325;
		}


		public String getAttr326() {
			return attr326;
		}


		public void setAttr326(String attr326) {
			this.attr326 = attr326;
		}


		public String getAttr327() {
			return attr327;
		}


		public void setAttr327(String attr327) {
			this.attr327 = attr327;
		}


		public String getAttr328() {
			return attr328;
		}


		public void setAttr328(String attr328) {
			this.attr328 = attr328;
		}


		public String getAttr329() {
			return attr329;
		}


		public void setAttr329(String attr329) {
			this.attr329 = attr329;
		}


		public String getAttr330() {
			return attr330;
		}


		public void setAttr330(String attr330) {
			this.attr330 = attr330;
		}


		public String getAttr331() {
			return attr331;
		}


		public void setAttr331(String attr331) {
			this.attr331 = attr331;
		}


		public String getAttr332() {
			return attr332;
		}


		public void setAttr332(String attr332) {
			this.attr332 = attr332;
		}


		public String getAttr333() {
			return attr333;
		}


		public void setAttr333(String attr333) {
			this.attr333 = attr333;
		}


		public String getAttr334() {
			return attr334;
		}


		public void setAttr334(String attr334) {
			this.attr334 = attr334;
		}


		public String getAttr335() {
			return attr335;
		}


		public void setAttr335(String attr335) {
			this.attr335 = attr335;
		}


		public String getAttr336() {
			return attr336;
		}


		public void setAttr336(String attr336) {
			this.attr336 = attr336;
		}


		public String getAttr337() {
			return attr337;
		}


		public void setAttr337(String attr337) {
			this.attr337 = attr337;
		}


		public String getAttr338() {
			return attr338;
		}


		public void setAttr338(String attr338) {
			this.attr338 = attr338;
		}


		public String getAttr339() {
			return attr339;
		}


		public void setAttr339(String attr339) {
			this.attr339 = attr339;
		}


		public String getAttr340() {
			return attr340;
		}


		public void setAttr340(String attr340) {
			this.attr340 = attr340;
		}


		public String getAttr341() {
			return attr341;
		}


		public void setAttr341(String attr341) {
			this.attr341 = attr341;
		}


		public String getAttr342() {
			return attr342;
		}


		public void setAttr342(String attr342) {
			this.attr342 = attr342;
		}


		public String getAttr343() {
			return attr343;
		}


		public void setAttr343(String attr343) {
			this.attr343 = attr343;
		}


		public String getAttr344() {
			return attr344;
		}


		public void setAttr344(String attr344) {
			this.attr344 = attr344;
		}


		public String getAttr345() {
			return attr345;
		}


		public void setAttr345(String attr345) {
			this.attr345 = attr345;
		}


		public String getAttr346() {
			return attr346;
		}


		public void setAttr346(String attr346) {
			this.attr346 = attr346;
		}


		public String getAttr347() {
			return attr347;
		}


		public void setAttr347(String attr347) {
			this.attr347 = attr347;
		}


		public String getAttr348() {
			return attr348;
		}


		public void setAttr348(String attr348) {
			this.attr348 = attr348;
		}


		public String getAttr349() {
			return attr349;
		}


		public void setAttr349(String attr349) {
			this.attr349 = attr349;
		}


		public String getAttr350() {
			return attr350;
		}


		public void setAttr350(String attr350) {
			this.attr350 = attr350;
		}

		
}