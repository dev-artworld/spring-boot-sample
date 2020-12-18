package com.equinix.appops.dart.portal.entity.mservices;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the SNAPSHOT_SNOW_CONFIG_ITEM_DA database table.
 * 
 */
@Entity
@Table(name="SNAPSHOT_SNOW_CONFIG_ITEM_DA",schema="EQX_DART")
@NamedQuery(name="SnapshotSnowConfigItemDa.findAll", query="SELECT s FROM SnapshotSnowConfigItemDa s")
public class SnapshotSnowConfigItemDa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="DFR_LINE_ID")
	private String dfrLineId;

	@Column(name="ASSET_NUM")
	private String header2;

	@Column(name="ASSET_TAG")
	private String header5;

	@Column(name="COMPANY")
	private String header6;

	@Column(name="DFR_ID")
	private String dfrId;

	@Column(name="LOCATION")
	private String header7;

	@Column(name="PRODUCT")
	private String header3;

	@Column(name="ROW_ID")
	private String header1;

	@Column(name="SERIAL_NUMBER")
	private String header8;

	@Column(name="STATUS_CD")
	private String header4;

	@Column(name="SYS_CLASS_NAME")
	private String header9;

	@Column(name="U_CABINET")
	private String header10;

	@Column(name="U_CUSTOMER_UCM_ID")
	private String header11;

	@Column(name="U_HOSTNAME")
	private String header12;

	@Column(name="U_LEGACY_ID")
	private String header13;

	@Column(name="U_LEGACY_INFO")
	private String header14;

	@Column(name="U_LEGACY_PRODUCT_DESCRIPTION")
	private String header15;

	@Column(name="U_LICENSING_TERM")
	private String header16;

	@Column(name="U_MANAGEMENT_TYPE")
	private String header17;

	@Column(name="U_STANDARD_PRODUCT")
	private String header18;

	@Column(name="U_SYSTEM_NAME")
	private String header19;

	@Column(name="U_TERM_BAND")
	private String header20;
	
	@Column(name="SYS_ID")
	private String header21;

	public SnapshotSnowConfigItemDa() {
	}

	public String getDfrLineId() {
		return dfrLineId;
	}

	public void setDfrLineId(String dfrLineId) {
		this.dfrLineId = dfrLineId;
	}

	public String getHeader2() {
		return header2;
	}

	public void setHeader2(String header2) {
		this.header2 = header2;
	}

	public String getHeader5() {
		return header5;
	}

	public void setHeader5(String header5) {
		this.header5 = header5;
	}

	public String getHeader6() {
		return header6;
	}

	public void setHeader6(String header6) {
		this.header6 = header6;
	}

	public String getDfrId() {
		return dfrId;
	}

	public void setDfrId(String dfrId) {
		this.dfrId = dfrId;
	}

	public String getHeader7() {
		return header7;
	}

	public void setHeader7(String header7) {
		this.header7 = header7;
	}

	public String getHeader3() {
		return header3;
	}

	public void setHeader3(String header3) {
		this.header3 = header3;
	}

	public String getHeader1() {
		return header1;
	}

	public void setHeader1(String header1) {
		this.header1 = header1;
	}

	public String getHeader8() {
		return header8;
	}

	public void setHeader8(String header8) {
		this.header8 = header8;
	}

	public String getHeader4() {
		return header4;
	}

	public void setHeader4(String header4) {
		this.header4 = header4;
	}

	public String getHeader9() {
		return header9;
	}

	public void setHeader9(String header9) {
		this.header9 = header9;
	}

	public String getHeader10() {
		return header10;
	}

	public void setHeader10(String header10) {
		this.header10 = header10;
	}

	public String getHeader11() {
		return header11;
	}

	public void setHeader11(String header11) {
		this.header11 = header11;
	}

	public String getHeader12() {
		return header12;
	}

	public void setHeader12(String header12) {
		this.header12 = header12;
	}

	public String getHeader13() {
		return header13;
	}

	public void setHeader13(String header13) {
		this.header13 = header13;
	}

	public String getHeader14() {
		return header14;
	}

	public void setHeader14(String header14) {
		this.header14 = header14;
	}

	public String getHeader15() {
		return header15;
	}

	public void setHeader15(String header15) {
		this.header15 = header15;
	}

	public String getHeader16() {
		return header16;
	}

	public void setHeader16(String header16) {
		this.header16 = header16;
	}

	public String getHeader17() {
		return header17;
	}

	public void setHeader17(String header17) {
		this.header17 = header17;
	}

	public String getHeader18() {
		return header18;
	}

	public void setHeader18(String header18) {
		this.header18 = header18;
	}

	public String getHeader19() {
		return header19;
	}

	public void setHeader19(String header19) {
		this.header19 = header19;
	}

	public String getHeader20() {
		return header20;
	}

	public void setHeader20(String header20) {
		this.header20 = header20;
	}

	public String getHeader21() {
		return header21;
	}

	public void setHeader21(String header21) {
		this.header21 = header21;
	}

	
}