package com.equinix.appops.dart.portal.entity.mservices;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name="SRC_SNOW_CONTRACT_ITEM",schema="EQX_DART")
@NamedQuery(name="ContractItem.findAll", query="SELECT a FROM ContractItem a")

public class ContractItem {

    @Column(name="ACCOUNT")
    @JsonProperty("account")
    private String account;
    @Column(name="ACTIVE")
    @JsonProperty("active")
    private String active;
    @Column(name="LICENSE_TYPE")
    @JsonProperty("license_type")
    private String licenseType;
    @Column(name="APPLICATION_MODEL")
    @JsonProperty("application_model")
    private String applicationModel;
    @Column(name="APPROVAL_HISTORY")
    @JsonProperty("approval_history")
    private String approvalHistory;
    @Column(name="APPROVER")
    @JsonProperty("approver")
    private String approver;
    @Column(name="COMMITMENT")
    @JsonProperty("commitment")
    private String commitment;
    @Column(name="CONSUMER")
    @JsonProperty("consumer")
    private String consumer;
    @Column(name="CONTRACT_COMPOSITE")
    @JsonProperty("contract_composite")
    private String contractComposite;
    @Column(name="CONTRACT_ADMINISTRATOR")
    @JsonProperty("contract_administrator")
    private String contractAdministrator;
    @Column(name="CONTRACT_MODEL")
    @JsonProperty("contract_model")
    private String contractModel;
    @Column(name="VENDOR_CONTRACT")
    @JsonProperty("vendor_contract")
    private String vendorContract;
    @Column(name="SYS_CLASS_NAME")
    @JsonProperty("sys_class_name")
    private String sysClassName;
    @Column(name="COST_ADJUSTMENT")
    @JsonProperty("cost_adjustment")
    private String costAdjustment;
    @Column(name="COST_ADJUSTMENT_PERCENTAGE")
    @JsonProperty("cost_adjustment_percentage")
    private String costAdjustmentPercentage;
    @Column(name="COST_ADJUSTMENT_REASON")
    @JsonProperty("cost_adjustment_reason")
    private String costAdjustmentReason;
    @Column(name="COST_ADJUSTMENT_TYPE")
    @JsonProperty("cost_adjustment_type")
    private String costAdjustmentType;
    @Column(name="COST_CENTER")
    @JsonProperty("cost_center")
    private String costCenter;
    @Column(name="COST_PER_UNIT")
    @JsonProperty("cost_per_unit")
    private String costPerUnit;
    @Column(name="SYS_CREATED_ON")
    @JsonProperty("sys_created_on")
    @JsonFormat(pattern  = "YYY-MM-dd")
    private Date sysCreatedOn;
    @Column(name="SYS_CREATED_BY")
    @JsonProperty("sys_created_by")
    private String sysCreatedBy;
    @Column(name="DESCRIPTION")
    @JsonProperty("description")
    private String description;
    @Column(name="DISCOUNT")
    @JsonProperty("discount")
    private String discount;
    @Column(name="SYS_DOMAIN")
    @JsonProperty("sys_domain")
    private String sysDomain;
    @Column(name="SYS_DOMAIN_PATH")
    @JsonProperty("sys_domain_path")
    private String sysDomainPath;
    @Column(name="TAX_RATE")
    @JsonProperty("tax_rate")
    private String taxRate;
    @Column(name="ENDS")
    @JsonProperty("ends")
    @JsonFormat(pattern  = "YYY-MM-dd")
    private Date ends;
    @Column(name="EXPIRATION")
    @JsonProperty("expiration")
    private String expiration;
    @Column(name="U_HW_ASSET_NUMBER")
    @JsonProperty("u_hw_asset_number")
    private String u_hw_asset_number;
    @Column(name="RATECARD")
    @JsonProperty("ratecard")
    private String ratecard;
    @Column(name="INVOICE_PAYMENT_TERMS")
    @JsonProperty("invoice_payment_terms")
    private String invoicePaymentTerms;
    @Column(name="LICENSE_QUANTITY_ENTITLED")
    @JsonProperty("license_quantity_entitled")
    private String licenseQuantityEntitled;
    @Column(name="LIFETIME_COST")
    @JsonProperty("lifetime_cost")
    private String lifetimeCost;
    @Column(name="LOCATION")
    @JsonProperty("location")
    private String location;
    @Column(name="MONTHLY_COST")
    @JsonProperty("monthly_cost")
    private String monthlyCost;
    @Column(name="CNTRNUMBER")
    @JsonProperty("number")
    private String cntrnumber;
    @Column(name="U_ORDER_LINE_ITEM")
    @JsonProperty("u_order_line_item")
    private String u_order_line_item;
    @Column(name="PARENT")
    @JsonProperty("parent")
    private String parent;
    @Column(name="PARENT_CONTRACT")
    @JsonProperty("parent_contract")
    private String parentContract;
    @Column(name="PAYMENT_AMOUNT")
    @JsonProperty("payment_amount")
    private String paymentAmount;
    @Column(name="PAYMENT_SCHEDULE")
    @JsonProperty("payment_schedule")
    private String paymentSchedule;
    @Column(name="PO_NUMBER")
    @JsonProperty("po_number")
    private String poNumber;
    @Column(name="PROCESS")
    @JsonProperty("process")
    private String process;
    @Column(name="PROCESS_NON_CONTRACTUAL_SLAS")
    @JsonProperty("process_non_contractual_slas")
    private String processNonContractualSlas;
    @Column(name="U_PRODUCT")
    @JsonProperty("u_product")
    private String u_product;
    @Column(name="RENEWABLE")
    @JsonProperty("renewable")
    private String renewable;
    @Column(name="RENEWAL_CONTACT")
    @JsonProperty("renewal_contact")
    private String renewalContact;
    @Column(name="RENEWAL_DATE")
    @JsonProperty("renewal_date")
    @JsonFormat(pattern  = "YYY-MM-dd")
    private Date renewalDate;
    @Column(name="RENEWAL_END_DATE")
    @JsonProperty("renewal_end_date")
    @JsonFormat(pattern  = "YYY-MM-dd")
    private Date renewalEndDate;
    @Column(name="RENEWAL_OPTIONS")
    @JsonProperty("renewal_options")
    private String renewalOptions;
    @Column(name="SALES_TAX")
    @JsonProperty("sales_tax")
    private String salesTax;
    @Column(name="SHORT_DESCRIPTION")
    @JsonProperty("short_description")
    private String shortDescription;
    @Column(name="U_SIEBEL_ASSET_ID")
    @JsonProperty("u_siebel_asset_id")
    private String u_siebel_asset_id;
    @Column(name="U_SIEBEL_ASSET_NUMBER")
    @JsonProperty("u_siebel_asset_number")
    private String u_siebel_asset_number;
    @Column(name="U_SIEBEL_ASSET_STATUS")
    @JsonProperty("u_siebel_asset_status")
    private String u_siebel_asset_status;
    @Column(name="U_SIEBEL_ORDER_NUMBER")
    @JsonProperty("u_siebel_order_number")
    private String u_siebel_order_number;
    @Column(name="U_SOFT_DISCONNECT")
    @JsonProperty("u_soft_disconnect")
    private String u_soft_disconnect;
    @Column(name="STARTS")
    @JsonProperty("starts")
    @JsonFormat(pattern  = "YYY-MM-dd")
    private Date starts;
    @Column(name="STATE")
    @JsonProperty("state")
    private String state;
    @Column(name="SUB_TOTAL_COST")
    @JsonProperty("sub_total_cost")
    private String subTotalCost;
    @Column(name="SUBSTATE")
    @JsonProperty("substate")
    private String substate;
    @Column(name="SYS_ID",unique=true,nullable=false)
    @JsonProperty("sys_id")
    @Id
    private String sys_id;
    @Column(name="TAX_COST")
    @JsonProperty("tax_cost")
    private String taxCost;
    @Column(name="TAX_EXEMPT")
    @JsonProperty("tax_exempt")
    private String taxExempt;
    @Column(name="TEMPLATE")
    @JsonProperty("template")
    private String template;
    @Column(name="TERMS_AND_CONDITIONS")
    @JsonProperty("terms_and_conditions")
    private String termsAndConditions;
    @Column(name="TOTAL_COST")
    @JsonProperty("total_cost")
    private String totalCost;
    @Column(name="SYS_UPDATED_ON")
    @JsonProperty("sys_updated_on")
    @JsonFormat(pattern  = "YYY-MM-dd")
    private Date sysUpdOn;
    @Column(name="SYS_UPDATED_BY")
    @JsonProperty("sys_updated_by")
    private String sysUpdBy;
    @Column(name="SYS_MOD_COUNT")
    @JsonProperty("sys_mod_count")
    private String sysModCount;
    @Column(name="VENDOR")
    @JsonProperty("vendor")
    private String vendor;
    @Column(name="VENDOR_ACCOUNT")
    @JsonProperty("vendor_account")
    private String vendorAccount;
    @Column(name="YEARLY_COST")
    @JsonProperty("yearly_cost")
    private String yearlyCost;
    @Transient
    @JsonProperty("sys_tags")
    private String sysTags;
    @Column(name="BUSINESS_OWNER")
    @JsonProperty("business_owner")
    private String businessOwner;
    @Column(name="APPLICABLE_TAXES")
    @JsonProperty("applicable_taxes")
    private String applicableTaxes;
    @Column(name="DTL__CAPXUSER")
    private String dtlCapxUser ;
    @Column(name="DTL__CAPXTIMESTAMP")
    private String dtlCapxTimestamp;
    @Column(name="DTL__CAPXACTION")
    private String dtlCapxAction;

    public ContractItem(){}

    public String getAccount() {
        return account;
    }

    public String getActive() {
        return active;
    }

    public String getApplicableTaxes() {
        return applicableTaxes;
    }

    public String getApplicationModel() {
        return applicationModel;
    }

    public String getApprovalHistory() {
        return approvalHistory;
    }

    public String getApprover() {
        return approver;
    }

    public String getBusinessOwner() {
        return businessOwner;
    }

    public String getCntrnumber() {
        return cntrnumber;
    }

    public String getCommitment() {
        return commitment;
    }

    public String getConsumer() {
        return consumer;
    }

    public String getContractAdministrator() {
        return contractAdministrator;
    }

    public String getContractComposite() {
        return contractComposite;
    }

    public String getContractModel() {
        return contractModel;
    }

    public String getCostAdjustment() {
        return costAdjustment;
    }

    public String getCostAdjustmentPercentage() {
        return costAdjustmentPercentage;
    }

    public String getCostAdjustmentReason() {
        return costAdjustmentReason;
    }

    public String getCostAdjustmentType() {
        return costAdjustmentType;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public String getCostPerUnit() {
        return costPerUnit;
    }

    public String getDescription() {
        return description;
    }

    public String getDiscount() {
        return discount;
    }

    public Date getEnds() {
        return ends;
    }

    public String getExpiration() {
        return expiration;
    }

    public String getInvoicePaymentTerms() {
        return invoicePaymentTerms;
    }

    public String getLicenseQuantityEntitled() {
        return licenseQuantityEntitled;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public String getLifetimeCost() {
        return lifetimeCost;
    }

    public String getLocation() {
        return location;
    }

    public String getMonthlyCost() {
        return monthlyCost;
    }

    public String getParent() {
        return parent;
    }

    public String getParentContract() {
        return parentContract;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public String getPaymentSchedule() {
        return paymentSchedule;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public String getProcess() {
        return process;
    }

    public String getProcessNonContractualSlas() {
        return processNonContractualSlas;
    }

    public String getRatecard() {
        return ratecard;
    }

    public String getRenewable() {
        return renewable;
    }

    public String getRenewalContact() {
        return renewalContact;
    }

    public Date getRenewalDate() {
        return renewalDate;
    }

    public Date getRenewalEndDate() {
        return renewalEndDate;
    }

    public String getRenewalOptions() {
        return renewalOptions;
    }

    public String getSalesTax() {
        return salesTax;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public Date getStarts() {
        return starts;
    }

    public String getState() {
        return state;
    }

    public String getSubstate() {
        return substate;
    }

    public String getSubTotalCost() {
        return subTotalCost;
    }

    public String getSys_id() {
        return sys_id;
    }

    public String getSysClassName() {
        return sysClassName;
    }

    public String getSysCreatedBy() {
        return sysCreatedBy;
    }

    public Date getSysCreatedOn() {
        return sysCreatedOn;
    }

    public String getSysDomain() {
        return sysDomain;
    }

    public String getSysDomainPath() {
        return sysDomainPath;
    }

    public String getSysModCount() {
        return sysModCount;
    }

    public String getSysTags() {
        return sysTags;
    }

    public String getSysUpdBy() {
        return sysUpdBy;
    }

    public Date getSysUpdOn() {
        return sysUpdOn;
    }

    public String getTaxCost() {
        return taxCost;
    }

    public String getTaxExempt() {
        return taxExempt;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public String getTemplate() {
        return template;
    }

    public String getTermsAndConditions() {
        return termsAndConditions;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public String getU_hw_asset_number() {
        return u_hw_asset_number;
    }

    public String getU_order_line_item() {
        return u_order_line_item;
    }

    public String getU_product() {
        return u_product;
    }

    public String getU_siebel_asset_id() {
        return u_siebel_asset_id;
    }

    public String getU_siebel_asset_number() {
        return u_siebel_asset_number;
    }

    public String getU_siebel_asset_status() {
        return u_siebel_asset_status;
    }

    public String getU_siebel_order_number() {
        return u_siebel_order_number;
    }

    public String getU_soft_disconnect() {
        return u_soft_disconnect;
    }

    public String getVendor() {
        return vendor;
    }

    public String getVendorAccount() {
        return vendorAccount;
    }

    public String getVendorContract() {
        return vendorContract;
    }

    public String getYearlyCost() {
        return yearlyCost;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public void setApplicableTaxes(String applicableTaxes) {
        this.applicableTaxes = applicableTaxes;
    }

    public void setApplicationModel(String applicationModel) {
        this.applicationModel = applicationModel;
    }

    public void setApprovalHistory(String approvalHistory) {
        this.approvalHistory = approvalHistory;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public void setBusinessOwner(String businessOwner) {
        this.businessOwner = businessOwner;
    }

    public void setCntrnumber(String cntrnumber) {
        this.cntrnumber = cntrnumber;
    }

    public void setCommitment(String commitment) {
        this.commitment = commitment;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public void setContractAdministrator(String contractAdministrator) {
        this.contractAdministrator = contractAdministrator;
    }

    public void setContractComposite(String contractComposite) {
        this.contractComposite = contractComposite;
    }

    public void setContractModel(String contractModel) {
        this.contractModel = contractModel;
    }

    public void setCostAdjustment(String costAdjustment) {
        this.costAdjustment = costAdjustment;
    }

    public void setCostAdjustmentPercentage(String costAdjustmentPercentage) {
        this.costAdjustmentPercentage = costAdjustmentPercentage;
    }

    public void setCostAdjustmentReason(String costAdjustmentReason) {
        this.costAdjustmentReason = costAdjustmentReason;
    }

    public void setCostAdjustmentType(String costAdjustmentType) {
        this.costAdjustmentType = costAdjustmentType;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public void setCostPerUnit(String costPerUnit) {
        this.costPerUnit = costPerUnit;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public void setEnds(Date ends) {
        this.ends = ends;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public void setInvoicePaymentTerms(String invoicePaymentTerms) {
        this.invoicePaymentTerms = invoicePaymentTerms;
    }

    public void setLicenseQuantityEntitled(String licenseQuantityEntitled) {
        this.licenseQuantityEntitled = licenseQuantityEntitled;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public void setLifetimeCost(String lifetimeCost) {
        this.lifetimeCost = lifetimeCost;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setMonthlyCost(String monthlyCost) {
        this.monthlyCost = monthlyCost;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public void setParentContract(String parentContract) {
        this.parentContract = parentContract;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public void setPaymentSchedule(String paymentSchedule) {
        this.paymentSchedule = paymentSchedule;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public void setProcessNonContractualSlas(String processNonContractualSlas) {
        this.processNonContractualSlas = processNonContractualSlas;
    }

    public void setRatecard(String ratecard) {
        this.ratecard = ratecard;
    }

    public void setRenewable(String renewable) {
        this.renewable = renewable;
    }

    public void setRenewalContact(String renewalContact) {
        this.renewalContact = renewalContact;
    }

    public void setRenewalDate(Date renewalDate) {
        this.renewalDate = renewalDate;
    }

    public void setRenewalEndDate(Date renewalEndDate) {
        this.renewalEndDate = renewalEndDate;
    }

    public void setRenewalOptions(String renewalOptions) {
        this.renewalOptions = renewalOptions;
    }

    public void setSalesTax(String salesTax) {
        this.salesTax = salesTax;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setStarts(Date starts) {
        this.starts = starts;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setSubstate(String substate) {
        this.substate = substate;
    }

    public void setSubTotalCost(String subTotalCost) {
        this.subTotalCost = subTotalCost;
    }

    public void setSys_id(String sys_id) {
        this.sys_id = sys_id;
    }

    public void setSysClassName(String sysClassName) {
        this.sysClassName = sysClassName;
    }

    public void setSysCreatedBy(String sysCreatedBy) {
        this.sysCreatedBy = sysCreatedBy;
    }

    public void setSysCreatedOn(Date sysCreatedOn) {
        this.sysCreatedOn = sysCreatedOn;
    }

    public void setSysDomain(String sysDomain) {
        this.sysDomain = sysDomain;
    }

    public void setSysDomainPath(String sysDomainPath) {
        this.sysDomainPath = sysDomainPath;
    }

    public void setSysModCount(String sysModCount) {
        this.sysModCount = sysModCount;
    }

    public void setSysTags(String sysTags) {
        this.sysTags = sysTags;
    }

    public void setSysUpdBy(String sysUpdBy) {
        this.sysUpdBy = sysUpdBy;
    }

    public void setSysUpdOn(Date sysUpdOn) {
        this.sysUpdOn = sysUpdOn;
    }

    public void setTaxCost(String taxCost) {
        this.taxCost = taxCost;
    }

    public void setTaxExempt(String taxExempt) {
        this.taxExempt = taxExempt;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public void setTermsAndConditions(String termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public void setU_hw_asset_number(String u_hw_asset_number) {
        this.u_hw_asset_number = u_hw_asset_number;
    }

    public void setU_order_line_item(String u_order_line_item) {
        this.u_order_line_item = u_order_line_item;
    }

    public void setU_product(String u_product) {
        this.u_product = u_product;
    }

    public void setU_siebel_asset_id(String u_siebel_asset_id) {
        this.u_siebel_asset_id = u_siebel_asset_id;
    }

    public void setU_siebel_asset_number(String u_siebel_asset_number) {
        this.u_siebel_asset_number = u_siebel_asset_number;
    }

    public void setU_siebel_asset_status(String u_siebel_asset_status) {
        this.u_siebel_asset_status = u_siebel_asset_status;
    }

    public void setU_siebel_order_number(String u_siebel_order_number) {
        this.u_siebel_order_number = u_siebel_order_number;
    }

    public void setU_soft_disconnect(String u_soft_disconnect) {
        this.u_soft_disconnect = u_soft_disconnect;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public void setVendorAccount(String vendorAccount) {
        this.vendorAccount = vendorAccount;
    }

    public void setVendorContract(String vendorContract) {
        this.vendorContract = vendorContract;
    }

    public void setYearlyCost(String yearlyCost) {
        this.yearlyCost = yearlyCost;
    }

	public String getDtlCapxUser() {
		return dtlCapxUser;
	}

	public void setDtlCapxUser(String dtlCapxUser) {
		this.dtlCapxUser = dtlCapxUser;
	}

	public String getDtlCapxTimestamp() {
		return dtlCapxTimestamp;
	}

	public void setDtlCapxTimestamp(String dtlCapxTimestamp) {
		this.dtlCapxTimestamp = dtlCapxTimestamp;
	}

	public String getDtlCapxAction() {
		return dtlCapxAction;
	}

	public void setDtlCapxAction(String dtlCapxAction) {
		this.dtlCapxAction = dtlCapxAction;
	}

    
}
