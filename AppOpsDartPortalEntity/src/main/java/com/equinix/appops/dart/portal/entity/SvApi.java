package com.equinix.appops.dart.portal.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the SV_API database table.
 * 
 */
@Entity
@Table(name="SV_API",schema="EQX_DART")
@NamedQuery(name="SvApi.findAll", query="SELECT s FROM SvApi s")
public class SvApi implements Serializable {
	private static final long serialVersionUID = 1L;
		
	@Column(name="ACTIONCODE")
	private String actioncode;
	@Column(name="ALLPROD")
	private String allprod;
	@Column(name="ALLPRODREN")
	private String allprodren;
	@Column(name="ASSET_ID")
	private String assetId;
	@Column(name="BILLAGRNO")
	private String billagrno;
	@Column(name="BILLFLAG")
	private String billflag;
	@Column(name="BILLINGDATE")
	private String billingdate;
	@Column(name="CONTACTUCID")
	private String contactucid;
	@Column(name="CPQINTEGRATIONID")
	private String cpqintegrationid;
	@Column(name="CPQPACKAGENAME")
	private String cpqpackagename;
	@Column(name="CURRENCYCODE")
	private String currencycode;
	@Column(name="CUSTREFNO")
	private String custrefno;
	@Column(name="DEFREVCODE")
	private String defrevcode;
	@Column(name="DFR_ID")
	private String dfrId;
	@Column(name="DISCOUNTTYPE")
	private String discounttype;
	@Column(name="EARNREVCODE")
	private String earnrevcode;
	@Column(name="EXCLINTERCONFLAG")
	private String exclinterconflag;
	@Column(name="FIRSTPIAPPAFTER")
	private String firstpiappafter;
	@Column(name="IBXNAME")
	private String ibxname;

@Column(name="ID")
	private String id;
	@Column(name="INITIALTERMS")
	private String initialterms;
	@Column(name="INSTALLDATE")
	private String installdate;
	@Column(name="INTERCONN")
	private String interconn;
	@Column(name="INTERCONREN")
	private String interconren;

	@Column(name="\"LINENO\"")
	private String lineno;
	@Column(name="LINESTATUS")
	private String linestatus;
	@Column(name="MAXPIPERCENT")
	private String maxpipercent;
	@Column(name="MAXPIVAL")
	private String maxpival;
	@Column(name="NOTICEPERIOD")
	private String noticeperiod;
	@Column(name="ONETIMEPIUPCHARGE")
	private String onetimepiupcharge;
	@Column(name="ORDERDATE")
	private String orderdate;
	@Column(name="ORDERNO")
	private String orderno;
	@Column(name="ORDERSOURCE")
	private String ordersource;
	@Column(name="ORDERSTATUS")
	private String orderstatus;
	@Column(name="ORDERSUBTYPE")
	private String ordersubtype;
	@Column(name="ORDERTYPE")
	private String ordertype;
	@Column(name="OTHERPRODREN")
	private String otherprodren;
	@Column(name="OTHERPRODUCTS")
	private String otherproducts;
	@Column(name="PARENTID")
	private String parentid;
	@Column(name="PARTNERSTATUS")
	private String partnerstatus;
	@Column(name="PARTNERSUBTYPE")
	private String partnersubtype;
	@Column(name="PARTNERTIER")
	private String partnertier;
	@Column(name="PARTNERTYPE")
	private String partnertype;
	@Column(name="PARTNUMBER")
	private String partnumber;
	@Column(name="PICATEGORY")
	private String picategory;
	@Column(name="PINOTICEPERIOD")
	private String pinoticeperiod;
	@Column(name="PONO")
	private String pono;
	@Column(name="\"POWER\"")
	private String power;
	@Column(name="POWERREN")
	private String powerren;
	@Column(name="PRICE")
	private String price;
	@Column(name="PRODUCTCODE")
	private String productcode;
	@Column(name="PRODUCTDESCRIPTION")
	private String productdescription;
	@Column(name="PRODUCTFAMILY")
	private String productfamily;
	@Column(name="PRODUCTID")
	private String productid;
	@Column(name="QTY")
	private String qty;
	@Column(name="REGIONDEFAULTCALC")
	private String regiondefaultcalc;
	@Column(name="RENEWALTERMS")
	private String renewalterms;
	@Column(name="RESALE")
	private String resale;
	@Column(name="RESELLERDISCOUNT")
	private String resellerdiscount;
	@Column(name="REVRECRULES")
	private String revrecrules;
	
	@Column(name="ROOTID")
	private String rootid;

	@Id
	@Column(name="ROW_ID")
	private Long rowId;
	@Column(name="SALESQUANTITY")
	private String salesquantity;
	@Column(name="SEQUENCENUMBER")
	private String sequencenumber;
	@Column(name="SERIALNUM")
	private String serialnum;
	@Column(name="SMARTHANDSPLAN")
	private String smarthandsplan;
	@Column(name="SMARTHANDSPLANREN")
	private String smarthandsplanren;

	@Column(name="\"SPACE\"")
	private String space;
	@Column(name="SPACEREN")
	private String spaceren;
	@Column(name="SWEEPIND")
	private String sweepind;

	@Column(name="UCM_ID")
	private String ucmId;
	@Column(name="UNITOFMEASURE")
	private String unitofmeasure;
	@Column(name="USID")
	private String usid;

	public SvApi() {
	}

	public String getActioncode() {
		return this.actioncode;
	}

	public void setActioncode(String actioncode) {
		this.actioncode = actioncode;
	}

	public String getAllprod() {
		return this.allprod;
	}

	public void setAllprod(String allprod) {
		this.allprod = allprod;
	}

	public String getAllprodren() {
		return this.allprodren;
	}

	public void setAllprodren(String allprodren) {
		this.allprodren = allprodren;
	}

	public String getAssetId() {
		return this.assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getBillagrno() {
		return this.billagrno;
	}

	public void setBillagrno(String billagrno) {
		this.billagrno = billagrno;
	}

	public String getBillflag() {
		return this.billflag;
	}

	public void setBillflag(String billflag) {
		this.billflag = billflag;
	}

	public String getBillingdate() {
		return this.billingdate;
	}

	public void setBillingdate(String billingdate) {
		this.billingdate = billingdate;
	}

	public String getContactucid() {
		return this.contactucid;
	}

	public void setContactucid(String contactucid) {
		this.contactucid = contactucid;
	}

	public String getCpqintegrationid() {
		return this.cpqintegrationid;
	}

	public void setCpqintegrationid(String cpqintegrationid) {
		this.cpqintegrationid = cpqintegrationid;
	}

	public String getCpqpackagename() {
		return this.cpqpackagename;
	}

	public void setCpqpackagename(String cpqpackagename) {
		this.cpqpackagename = cpqpackagename;
	}

	public String getCurrencycode() {
		return this.currencycode;
	}

	public void setCurrencycode(String currencycode) {
		this.currencycode = currencycode;
	}

	public String getCustrefno() {
		return this.custrefno;
	}

	public void setCustrefno(String custrefno) {
		this.custrefno = custrefno;
	}

	public String getDefrevcode() {
		return this.defrevcode;
	}

	public void setDefrevcode(String defrevcode) {
		this.defrevcode = defrevcode;
	}

	public String getDfrId() {
		return this.dfrId;
	}

	public void setDfrId(String dfrId) {
		this.dfrId = dfrId;
	}

	public String getDiscounttype() {
		return this.discounttype;
	}

	public void setDiscounttype(String discounttype) {
		this.discounttype = discounttype;
	}

	public String getEarnrevcode() {
		return this.earnrevcode;
	}

	public void setEarnrevcode(String earnrevcode) {
		this.earnrevcode = earnrevcode;
	}

	public String getExclinterconflag() {
		return this.exclinterconflag;
	}

	public void setExclinterconflag(String exclinterconflag) {
		this.exclinterconflag = exclinterconflag;
	}

	public String getFirstpiappafter() {
		return this.firstpiappafter;
	}

	public void setFirstpiappafter(String firstpiappafter) {
		this.firstpiappafter = firstpiappafter;
	}

	public String getIbxname() {
		return this.ibxname;
	}

	public void setIbxname(String ibxname) {
		this.ibxname = ibxname;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInitialterms() {
		return this.initialterms;
	}

	public void setInitialterms(String initialterms) {
		this.initialterms = initialterms;
	}

	public String getInstalldate() {
		return this.installdate;
	}

	public void setInstalldate(String installdate) {
		this.installdate = installdate;
	}

	public String getInterconn() {
		return this.interconn;
	}

	public void setInterconn(String interconn) {
		this.interconn = interconn;
	}

	public String getInterconren() {
		return this.interconren;
	}

	public void setInterconren(String interconren) {
		this.interconren = interconren;
	}

	public String getLineno() {
		return this.lineno;
	}

	public void setLineno(String lineno) {
		this.lineno = lineno;
	}

	public String getLinestatus() {
		return this.linestatus;
	}

	public void setLinestatus(String linestatus) {
		this.linestatus = linestatus;
	}

	public String getMaxpipercent() {
		return this.maxpipercent;
	}

	public void setMaxpipercent(String maxpipercent) {
		this.maxpipercent = maxpipercent;
	}

	public String getMaxpival() {
		return this.maxpival;
	}

	public void setMaxpival(String maxpival) {
		this.maxpival = maxpival;
	}

	public String getNoticeperiod() {
		return this.noticeperiod;
	}

	public void setNoticeperiod(String noticeperiod) {
		this.noticeperiod = noticeperiod;
	}

	public String getOnetimepiupcharge() {
		return this.onetimepiupcharge;
	}

	public void setOnetimepiupcharge(String onetimepiupcharge) {
		this.onetimepiupcharge = onetimepiupcharge;
	}

	public String getOrderdate() {
		return this.orderdate;
	}

	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}

	public String getOrderno() {
		return this.orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getOrdersource() {
		return this.ordersource;
	}

	public void setOrdersource(String ordersource) {
		this.ordersource = ordersource;
	}

	public String getOrderstatus() {
		return this.orderstatus;
	}

	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}

	public String getOrdersubtype() {
		return this.ordersubtype;
	}

	public void setOrdersubtype(String ordersubtype) {
		this.ordersubtype = ordersubtype;
	}

	public String getOrdertype() {
		return this.ordertype;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}

	public String getOtherprodren() {
		return this.otherprodren;
	}

	public void setOtherprodren(String otherprodren) {
		this.otherprodren = otherprodren;
	}

	public String getOtherproducts() {
		return this.otherproducts;
	}

	public void setOtherproducts(String otherproducts) {
		this.otherproducts = otherproducts;
	}

	public String getParentid() {
		return this.parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getPartnerstatus() {
		return this.partnerstatus;
	}

	public void setPartnerstatus(String partnerstatus) {
		this.partnerstatus = partnerstatus;
	}

	public String getPartnersubtype() {
		return this.partnersubtype;
	}

	public void setPartnersubtype(String partnersubtype) {
		this.partnersubtype = partnersubtype;
	}

	public String getPartnertier() {
		return this.partnertier;
	}

	public void setPartnertier(String partnertier) {
		this.partnertier = partnertier;
	}

	public String getPartnertype() {
		return this.partnertype;
	}

	public void setPartnertype(String partnertype) {
		this.partnertype = partnertype;
	}

	public String getPartnumber() {
		return this.partnumber;
	}

	public void setPartnumber(String partnumber) {
		this.partnumber = partnumber;
	}

	public String getPicategory() {
		return this.picategory;
	}

	public void setPicategory(String picategory) {
		this.picategory = picategory;
	}

	public String getPinoticeperiod() {
		return this.pinoticeperiod;
	}

	public void setPinoticeperiod(String pinoticeperiod) {
		this.pinoticeperiod = pinoticeperiod;
	}

	public String getPono() {
		return this.pono;
	}

	public void setPono(String pono) {
		this.pono = pono;
	}

	public String getPower() {
		return this.power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public String getPowerren() {
		return this.powerren;
	}

	public void setPowerren(String powerren) {
		this.powerren = powerren;
	}

	public String getPrice() {
		return this.price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getProductcode() {
		return this.productcode;
	}

	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}

	public String getProductdescription() {
		return this.productdescription;
	}

	public void setProductdescription(String productdescription) {
		this.productdescription = productdescription;
	}

	public String getProductfamily() {
		return this.productfamily;
	}

	public void setProductfamily(String productfamily) {
		this.productfamily = productfamily;
	}

	public String getProductid() {
		return this.productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public String getQty() {
		return this.qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getRegiondefaultcalc() {
		return this.regiondefaultcalc;
	}

	public void setRegiondefaultcalc(String regiondefaultcalc) {
		this.regiondefaultcalc = regiondefaultcalc;
	}

	public String getRenewalterms() {
		return this.renewalterms;
	}

	public void setRenewalterms(String renewalterms) {
		this.renewalterms = renewalterms;
	}

	public String getResale() {
		return this.resale;
	}

	public void setResale(String resale) {
		this.resale = resale;
	}

	public String getResellerdiscount() {
		return this.resellerdiscount;
	}

	public void setResellerdiscount(String resellerdiscount) {
		this.resellerdiscount = resellerdiscount;
	}

	public String getRevrecrules() {
		return this.revrecrules;
	}

	public void setRevrecrules(String revrecrules) {
		this.revrecrules = revrecrules;
	}

	public String getRootid() {
		return this.rootid;
	}

	public void setRootid(String rootid) {
		this.rootid = rootid;
	}

	public Long getRowId() {
		return this.rowId;
	}

	public void setRowId(Long rowId) {
		this.rowId = rowId;
	}

	public String getSalesquantity() {
		return this.salesquantity;
	}

	public void setSalesquantity(String salesquantity) {
		this.salesquantity = salesquantity;
	}

	public String getSequencenumber() {
		return this.sequencenumber;
	}

	public void setSequencenumber(String sequencenumber) {
		this.sequencenumber = sequencenumber;
	}

	public String getSerialnum() {
		return this.serialnum;
	}

	public void setSerialnum(String serialnum) {
		this.serialnum = serialnum;
	}

	public String getSmarthandsplan() {
		return this.smarthandsplan;
	}

	public void setSmarthandsplan(String smarthandsplan) {
		this.smarthandsplan = smarthandsplan;
	}

	public String getSmarthandsplanren() {
		return this.smarthandsplanren;
	}

	public void setSmarthandsplanren(String smarthandsplanren) {
		this.smarthandsplanren = smarthandsplanren;
	}

	public String getSpace() {
		return this.space;
	}

	public void setSpace(String space) {
		this.space = space;
	}

	public String getSpaceren() {
		return this.spaceren;
	}

	public void setSpaceren(String spaceren) {
		this.spaceren = spaceren;
	}

	public String getSweepind() {
		return this.sweepind;
	}

	public void setSweepind(String sweepind) {
		this.sweepind = sweepind;
	}

	public String getUcmId() {
		return this.ucmId;
	}

	public void setUcmId(String ucmId) {
		this.ucmId = ucmId;
	}

	public String getUnitofmeasure() {
		return this.unitofmeasure;
	}

	public void setUnitofmeasure(String unitofmeasure) {
		this.unitofmeasure = unitofmeasure;
	}

	public String getUsid() {
		return this.usid;
	}

	public void setUsid(String usid) {
		this.usid = usid;
	}

}