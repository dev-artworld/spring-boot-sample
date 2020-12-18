package com.equinix.appops.dart.portal.entity.mservices;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.util.Date;


@Entity
@Table(name="SRC_SNOW_CONFIG_ITEM",schema="EQX_DART")
@NamedQuery(name="ConfigItem.findAll", query="SELECT a FROM ConfigItem a")
public class ConfigItem {

    @Column(name="ASSET")
    @JsonProperty("asset")
    private String asset;
    @Column(name="ASSET_TAG")
    @JsonProperty("asset_tag")
    private String assetTag;
    @Column(name="ASSIGNED")
    @JsonProperty("assigned")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date assigned;
    @Column(name="ASSIGNED_TO")
    @JsonProperty("assigned_to")
    private String assignedTo;
    @Column(name="ASSIGNMENT_GROUP")
    @JsonProperty("assignment_group")
    private String assignmentGroup;
    @Column(name="ATTRIBUTES")
    @JsonProperty("attributes")
    private String attributes;
    @Column(name="CAN_PRINT")
    @JsonProperty("can_print")
    private String canPrint;
    @Column(name="CATEGORY")
    @JsonProperty("category")
    private String category;
    @Column(name="CHANGE_CONTROL")
    @JsonProperty("change_control")
    private String changeControl;
    @Column(name="CHECKED_IN")
    @JsonProperty("checked_in")
    @JsonFormat(pattern  = "YYY-MM-dd")
    private Date checkedIn;
    @Column(name="CHECKED_OUT")
    @JsonProperty("checked_out")
    @JsonFormat(pattern  = "YYY-MM-dd")
    private Date checkedOut;
    @Column(name="COMMENTS")
    @JsonProperty("comments")
    private String comments;
    @Column(name="COMPANY")
    @JsonProperty("company")
    private String company;
    @Column(name="CORRELATION_ID")
    @JsonProperty("correlation_id")
    private String correlationId;
    @Column(name="COST")
    @JsonProperty("cost")
    private String cost;
    @Column(name="COST_CC")
    @JsonProperty("cost_cc")
    private String costCC;
    @Column(name="COST_CENTER")
    @JsonProperty("cost_center")
    private String costCenter;
    @Column(name="DELIVERY_DATE")
    @JsonProperty("delivery_date")
    @JsonFormat(pattern = "YYYY-MM-dd")
    private Date delivery;
    @Column(name="DEPARTMENT")
    @JsonProperty("department")
    private String department;
    @Column(name="DISCOVERY_SOURCE")
    @JsonProperty("discovery_source")
    private String discoverySource;
    @Column(name="DNS_DOMAIN")
    @JsonProperty("dns_domain")
    private String dnsDomain;
    @Column(name="DUE")
    @JsonProperty("due")
    @JsonFormat(pattern = "YYYY-MM-dd")
    private Date due;
    @Column(name="DUE_IN")
    @JsonProperty("due_in")
    private String dueIn;
    @Column(name="FAULT_COUNT")
    @JsonProperty("fault_count")
    private String faultCount;
    @Column(name="FIRST_DISCOVERED")
    @JsonProperty("first_discovered")
    @JsonFormat(pattern = "YYYY-MM-dd")
    private Date firstDiscovered;
    @Column(name="FQDN")
    @JsonProperty("fqdn")
    private String fqdn;
    @Column(name="GL_ACCOUNT")
    @JsonProperty("gl_account")
    private String glAccount;
    @Column(name="INSTALL_Date")
    @JsonProperty("install_date")
    @JsonFormat(pattern = "YYYY-MM-dd")
    private Date install;
    @Column(name="INSTALL_STATUS")
    @JsonProperty("install_status")
    private String installStatus;
    @Column(name="INVOICE_NUMBER")
    @JsonProperty("invoice_number")
    private String invoiceNumber;
    @Column(name="IP_ADDRESS")
    @JsonProperty("ip_address")
    private String ipAddress;
    @Column(name="JUSTIFICATION")
    @JsonProperty("justification")
    private String justification;
    @Column(name="LAST_DISCOVERED")
    @JsonProperty("last_discovered")
    @JsonFormat(pattern = "YYYY-MM-dd")
    private  Date lastDiscovered;
    @Column(name="LEASE_ID")
    @JsonProperty("lease_id")
    private String leaseId;
    @Column(name="LOCATION")
    @JsonProperty("location")
    private String location;
    @Column(name="MAC_ADDRESS")
    @JsonProperty("mac_address")
    private String macAddress;
    @Column(name="MAINTENANCE_SCHEDULE")
    @JsonProperty("maintenance_schedule")
    private String maintenanceSchedule;
    @Column(name="MANAGED_BY")
    @JsonProperty("managed_by")
    private String managedBy;
    @Column(name="MANUFACTURER")
    @JsonProperty("manufacturer")
    private String manufacturer;
    @Column(name="MODEL_ID")
    @JsonProperty("model_id")
    private String modelId;
    @Column(name="MODEL_NUMBER")
    @JsonProperty("model_number")
    private String modelNumber;
    @Column(name="MONITOR")
    @JsonProperty("monitor")
    private String monitor;
    @Column(name="NAME")
    @JsonProperty("name")
    private String name;
    @Column(name="OPERATIONAL_STATUS")
    @JsonProperty("operational_status")
    private String operationalStatus;
    @Column(name="ORDER_DATE")
    @JsonProperty("order_date")
    @JsonFormat(pattern = "YYYY-MM-dd")
    private Date order;
    @Column(name="OWNED_BY")
    @JsonProperty("owned_by")
    private String ownedBy;
    @Column(name="PO_NUMBER")
    @JsonProperty("po_number")
    private String poNumber;
    @Column(name="PURCHASE_DATE")
    @JsonProperty("purchase_date")
    @JsonFormat(pattern = "YYYY-MM-dd")
    private Date purchase;
    @Column(name="SCHEDULE")
    @JsonProperty("schedule")
    private String schedule;
    @Column(name="SERIAL_NUMBER")
    @JsonProperty("serial_number")
    private String serialNumber;
    @Column(name="SHORT_DESCRIPTION")
    @JsonProperty("short_description")
    private String shortDescription;
    @Column(name="SKIP_SYNC")
    @JsonProperty("skip_sync")
    private String skipSync;
    @Column(name="START_DATE")
    @JsonProperty("start_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date start;
    @Column(name="SUBCATEGORY")
    @JsonProperty("subcategory")
    private String subCategory;
    @Column(name="SUPPORTED_BY")
    @JsonProperty("supported_by")
    private String supportedBy;
    @Column(name="SUPPORT_GROUP")
    @JsonProperty("support_group")
    private String supportGroup;
    @Column(name="SYS_CLASS_NAME")
    @JsonProperty("sys_class_name")
    private String sysClassName;
    @Column(name="SYS_CLASS_PATH")
    @JsonProperty("sys_class_path")
    private String sysClassPath;
    @Column(name="SYS_CREATED_BY")
    @JsonProperty("sys_created_by")
    private String sysCreatedBy;
    @Column(name="SYS_CREATED_ON")
    @JsonProperty("sys_created_on")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date sysCreatedOn;
    @Column(name="SYS_DOMAIN")
    @JsonProperty("sys_domain")
    private String sysDomain;
    @Column(name="SYS_DOMAIN_PATH")
    @JsonProperty("sys_domain_path")
    private String sysDomainPath;
    @Column(name="SYS_ID", unique = true, nullable = false)
    @JsonProperty("sys_id")
    @Id
    private String sysId;
    @Column(name="SYS_MOD_COUNT")
    @JsonProperty("sys_mod_count")
    private String sysModCount;
    @Column(name="SYS_UPDATED_BY")
    @JsonProperty("sys_updated_by")
    private String sysUpdatedBy;
    @Column(name="SYS_UPDATED_ON")
    @JsonProperty("sys_updated_on")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date sysUpdatedOn;
    @Column(name="UNVERIFIED")
    @JsonProperty("unverified")
    private String unverified;
    @Column(name="U_ACCOUNT")
    @JsonProperty("u_account")
    private String uAccount;
    @Column(name="U_ACTIVE_FLAG")
    @JsonProperty("u_active_flag")
    private String uActiveFlag;
    @Column(name="U_AGENT_ID")
    @JsonProperty("u_agent_id")
    private String uAgentId;
    @Column(name="U_BRANCH")
    @JsonProperty("u_branch")
    private String uBranch;
    @Column(name="U_CABINET")
    @JsonProperty("u_cabinet")
    private String uCabinet;
    @Column(name="U_CI_SOURCE")
    @JsonProperty("u_ci_source")
    private String uCISource;
    @Column(name="U_COLOCATION_TYPE")
    @JsonProperty("u_colocation_type")
    private String uColocationType;
    @Column(name="U_CUSTOMER_UCM_ID")
    @JsonProperty("u_customer_ucm_id")
    private String uCustomerUCMID;
    @Column(name="U_CUSTOM_TYPE")
    @JsonProperty("u_custom_type")
    private String uCustomType;
    @Column(name="U_EQUIPMENT_ID")
    @JsonProperty("u_equipment_id")
    private String uEquipmentId;
    @Column(name="U_HARDWARE_ASSET")
    @JsonProperty("u_hardware_asset")
    private String uHardwareAsset;
    @Column(name="U_HARDWARE_PARENT")
    @JsonProperty("u_hardware_parent")
    private String uHardwareParent;
    @Column(name="U_HOSTNAME")
    @JsonProperty("u_hostname")
    private String uHostname;
    @Column(name="U_HW_ASSET_NUMBER")
    @JsonProperty("u_hw_asset_number")
    private String uHwAssetNumber;
    @Column(name="U_ID_FIELD")
    @JsonProperty("u_id_field")
    private String uIdField;
    @Column(name="U_INTEGRATION_STATUS")
    @JsonProperty("u_integration_status")
    private String uIntegrationStatus;
    @Column(name="U_LEGACY_ID")
    @JsonProperty("u_legacy_id")
    private String uLegacyId;
    @Column(name="U_LEGACY_INFO")
    @JsonProperty("u_legacy_info")
    private String uLegacyInfo;
    @Column(name="U_LEGACY_PRODUCT_DESCRIPTION")
    @JsonProperty("u_legacy_product_description")
    private String uLegacyProductDescription;
    @Column(name="U_LICENSING_TERM")
    @JsonProperty("u_licensing_term")
    private String uLicensingTerm;
    @Column(name="U_MANAGEMENT_TYPE")
    @JsonProperty("u_management_type")
    private String uManagementType;
    @Column(name="U_MONITORED")
    @JsonProperty("u_monitored")
    private String uMonitored;
    @Column(name="U_PACKAGE_CI")
    @JsonProperty("u_package_ci")
    private String uPackageCI;
    @Column(name="U_PORTAL_DISPLAY_NAME")
    @JsonProperty("u_portal_display_name")
    private String uPortalDisplayName;
    @Column(name="U_SIEBEL_ASSET_NUMBER")
    @JsonProperty("u_siebel_asset_number")
    private String uSiebelAssetNumber;
    @Column(name="U_SOFTWARE_PARENT")
    @JsonProperty("u_software_parent")
    private String uSoftwareParent;
    @Column(name="U_SPACE_ID")
    @JsonProperty("u_space_id")
    private String uSpaceId;
    @Column(name="U_STANDARD_PRODUCT")
    @JsonProperty("u_standard_product")
    private String uStandardProduct;
    @Column(name="U_SYSTEM_NAME")
    @JsonProperty("u_system_name")
    private String uSystemName;
    @Column(name="U_TERM_BAND")
    @JsonProperty("u_term_band")
    private String uTermBand;
    @Column(name="VENDOR")
    @JsonProperty("vendor")
    private String vendor;
    @Column(name="WARRANTY_EXPIRATION")
    @JsonProperty("warranty_expiration")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date warrantyExpiration;
    @Column(name="DTL__CAPXUSER")
    private String dtlCapxUser ;
    @Column(name="DTL__CAPXTIMESTAMP")
    private String dtlCapxTimestamp;
    @Column(name="DTL__CAPXACTION")
    private String dtlCapxAction;

    public ConfigItem(){};

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getAssetTag() {
        return assetTag;
    }

    public void setAssetTag(String assetTag) {
        this.assetTag = assetTag;
    }

    public Date getAssigned() {
        return assigned;
    }

    public void setAssigned(Date assigned) {
        this.assigned = assigned;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getAssignmentGroup() {
        return assignmentGroup;
    }

    public void setAssignmentGroup(String assignmentGroup) {
        this.assignmentGroup = assignmentGroup;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public String getCanPrint() {
        return canPrint;
    }

    public void setCanPrint(String canPrint) {
        this.canPrint = canPrint;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getChangeControl() {
        return changeControl;
    }

    public void setChangeControl(String changeControl) {
        this.changeControl = changeControl;
    }

    public Date getCheckedIn() {
        return checkedIn;
    }

    public void setCheckedIn(Date checkedIn) {
        this.checkedIn = checkedIn;
    }

    public Date getCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(Date checkedOut) {
        this.checkedOut = checkedOut;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getCostCC() {
        return costCC;
    }

    public void setCostCC(String costCC) {
        this.costCC = costCC;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public Date getDelivery() {
        return delivery;
    }

    public void setDelivery(Date delivery) {
        this.delivery = delivery;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDiscoverySource() {
        return discoverySource;
    }

    public void setDiscoverySource(String discoverySource) {
        this.discoverySource = discoverySource;
    }

    public String getDnsDomain() {
        return dnsDomain;
    }

    public void setDnsDomain(String dnsDomain) {
        this.dnsDomain = dnsDomain;
    }

    public Date getDue() {
        return due;
    }

    public void setDue(Date due) {
        this.due = due;
    }

    public String getDueIn() {
        return dueIn;
    }

    public void setDueIn(String dueIn) {
        this.dueIn = dueIn;
    }

    public String getFaultCount() {
        return faultCount;
    }

    public void setFaultCount(String faultCount) {
        this.faultCount = faultCount;
    }

    public Date getFirstDiscovered() {
        return firstDiscovered;
    }

    public void setFirstDiscovered(Date firstDiscovered) {
        this.firstDiscovered = firstDiscovered;
    }

    public String getFqdn() {
        return fqdn;
    }

    public void setFqdn(String fqdn) {
        this.fqdn = fqdn;
    }

    public String getGlAccount() {
        return glAccount;
    }

    public void setGlAccount(String glAccount) {
        this.glAccount = glAccount;
    }

    public Date getInstall() {
        return install;
    }

    public void setInstall(Date install) {
        this.install = install;
    }

    public String getInstallStatus() {
        return installStatus;
    }

    public void setInstallStatus(String installStatus) {
        this.installStatus = installStatus;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public Date getLastDiscovered() {
        return lastDiscovered;
    }

    public void setLastDiscovered(Date lastDiscovered) {
        this.lastDiscovered = lastDiscovered;
    }

    public String getLeaseId() {
        return leaseId;
    }

    public void setLeaseId(String leaseId) {
        this.leaseId = leaseId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getMaintenanceSchedule() {
        return maintenanceSchedule;
    }

    public void setMaintenanceSchedule(String maintenanceSchedule) {
        this.maintenanceSchedule = maintenanceSchedule;
    }

    public String getManagedBy() {
        return managedBy;
    }

    public void setManagedBy(String managedBy) {
        this.managedBy = managedBy;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getMonitor() {
        return monitor;
    }

    public void setMonitor(String monitor) {
        this.monitor = monitor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOperationalStatus() {
        return operationalStatus;
    }

    public void setOperationalStatus(String operationalStatus) {
        this.operationalStatus = operationalStatus;
    }

    public Date getOrder() {
        return order;
    }

    public void setOrder(Date order) {
        this.order = order;
    }

    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(String ownedBy) {
        this.ownedBy = ownedBy;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public Date getPurchase() {
        return purchase;
    }

    public void setPurchase(Date purchase) {
        this.purchase = purchase;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getSkipSync() {
        return skipSync;
    }

    public void setSkipSync(String skipSync) {
        this.skipSync = skipSync;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getSupportedBy() {
        return supportedBy;
    }

    public void setSupportedBy(String supportedBy) {
        this.supportedBy = supportedBy;
    }

    public String getSupportGroup() {
        return supportGroup;
    }

    public void setSupportGroup(String supportGroup) {
        this.supportGroup = supportGroup;
    }

    public String getSysClassName() {
        return sysClassName;
    }

    public void setSysClassName(String sysClassName) {
        this.sysClassName = sysClassName;
    }

    public String getSysClassPath() {
        return sysClassPath;
    }

    public void setSysClassPath(String sysClassPath) {
        this.sysClassPath = sysClassPath;
    }

    public String getSysCreatedBy() {
        return sysCreatedBy;
    }

    public void setSysCreatedBy(String sysCreatedBy) {
        this.sysCreatedBy = sysCreatedBy;
    }

    public Date getSysCreatedOn() {
        return sysCreatedOn;
    }

    public void setSysCreatedOn(Date sysCreatedOn) {
        this.sysCreatedOn = sysCreatedOn;
    }

    public String getSysDomain() {
        return sysDomain;
    }

    public void setSysDomain(String sysDomain) {
        this.sysDomain = sysDomain;
    }

    public String getSysDomainPath() {
        return sysDomainPath;
    }

    public void setSysDomainPath(String sysDomainPath) {
        this.sysDomainPath = sysDomainPath;
    }

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }

    public String getSysModCount() {
        return sysModCount;
    }

    public void setSysModCount(String sysModCount) {
        this.sysModCount = sysModCount;
    }

    public String getSysUpdatedBy() {
        return sysUpdatedBy;
    }

    public void setSysUpdatedBy(String sysUpdatedBy) {
        this.sysUpdatedBy = sysUpdatedBy;
    }

    public Date getSysUpdatedOn() {
        return sysUpdatedOn;
    }

    public void setSysUpdatedOn(Date sysUpdatedOn) {
        this.sysUpdatedOn = sysUpdatedOn;
    }

    public String getUnverified() {
        return unverified;
    }

    public void setUnverified(String unverified) {
        this.unverified = unverified;
    }

    public String getuAccount() {
        return uAccount;
    }

    public void setuAccount(String uAccount) {
        this.uAccount = uAccount;
    }

    public String getuActiveFlag() {
        return uActiveFlag;
    }

    public void setuActiveFlag(String uActiveFlag) {
        this.uActiveFlag = uActiveFlag;
    }

    public String getuAgentId() {
        return uAgentId;
    }

    public void setuAgentId(String uAgentId) {
        this.uAgentId = uAgentId;
    }

    public String getuBranch() {
        return uBranch;
    }

    public void setuBranch(String uBranch) {
        this.uBranch = uBranch;
    }

    public String getuCabinet() {
        return uCabinet;
    }

    public void setuCabinet(String uCabinet) {
        this.uCabinet = uCabinet;
    }

    public String getuCISource() {
        return uCISource;
    }

    public void setuCISource(String uCISource) {
        this.uCISource = uCISource;
    }

    public String getuColocationType() {
        return uColocationType;
    }

    public void setuColocationType(String uColocationType) {
        this.uColocationType = uColocationType;
    }

    public String getuCustomerUCMID() {
        return uCustomerUCMID;
    }

    public void setuCustomerUCMID(String uCustomerUCMID) {
        this.uCustomerUCMID = uCustomerUCMID;
    }

    public String getuCustomType() {
        return uCustomType;
    }

    public void setuCustomType(String uCustomType) {
        this.uCustomType = uCustomType;
    }

    public String getuEquipmentId() {
        return uEquipmentId;
    }

    public void setuEquipmentId(String uEquipmentId) {
        this.uEquipmentId = uEquipmentId;
    }

    public String getuHardwareAsset() {
        return uHardwareAsset;
    }

    public void setuHardwareAsset(String uHardwareAsset) {
        this.uHardwareAsset = uHardwareAsset;
    }

    public String getuHardwareParent() {
        return uHardwareParent;
    }

    public void setuHardwareParent(String uHardwareParent) {
        this.uHardwareParent = uHardwareParent;
    }

    public String getuHostname() {
        return uHostname;
    }

    public void setuHostname(String uHostname) {
        this.uHostname = uHostname;
    }

    public String getuHwAssetNumber() {
        return uHwAssetNumber;
    }

    public void setuHwAssetNumber(String uHwAssetNumber) {
        this.uHwAssetNumber = uHwAssetNumber;
    }

    public String getuIdField() {
        return uIdField;
    }

    public void setuIdField(String uIdField) {
        this.uIdField = uIdField;
    }

    public String getuIntegrationStatus() {
        return uIntegrationStatus;
    }

    public void setuIntegrationStatus(String uIntegrationStatus) {
        this.uIntegrationStatus = uIntegrationStatus;
    }

    public String getuLegacyId() {
        return uLegacyId;
    }

    public void setuLegacyId(String uLegacyId) {
        this.uLegacyId = uLegacyId;
    }

    public String getuLegacyInfo() {
        return uLegacyInfo;
    }

    public void setuLegacyInfo(String uLegacyInfo) {
        this.uLegacyInfo = uLegacyInfo;
    }

    public String getuLegacyProductDescription() {
        return uLegacyProductDescription;
    }

    public void setuLegacyProductDescription(String uLegacyProductDescription) {
        this.uLegacyProductDescription = uLegacyProductDescription;
    }

    public String getuLicensingTerm() {
        return uLicensingTerm;
    }

    public void setuLicensingTerm(String uLicensingTerm) {
        this.uLicensingTerm = uLicensingTerm;
    }

    public String getuManagementType() {
        return uManagementType;
    }

    public void setuManagementType(String uManagementType) {
        this.uManagementType = uManagementType;
    }

    public String getuMonitored() {
        return uMonitored;
    }

    public void setuMonitored(String uMonitored) {
        this.uMonitored = uMonitored;
    }

    public String getuPackageCI() {
        return uPackageCI;
    }

    public void setuPackageCI(String uPackageCI) {
        this.uPackageCI = uPackageCI;
    }

    public String getuPortalDisplayName() {
        return uPortalDisplayName;
    }

    public void setuPortalDisplayName(String uPortalDisplayName) {
        this.uPortalDisplayName = uPortalDisplayName;
    }

    public String getuSiebelAssetNumber() {
        return uSiebelAssetNumber;
    }

    public void setuSiebelAssetNumber(String uSiebelAssetNumber) {
        this.uSiebelAssetNumber = uSiebelAssetNumber;
    }

    public String getuSoftwareParent() {
        return uSoftwareParent;
    }

    public void setuSoftwareParent(String uSoftwareParent) {
        this.uSoftwareParent = uSoftwareParent;
    }

    public String getuSpaceId() {
        return uSpaceId;
    }

    public void setuSpaceId(String uSpaceId) {
        this.uSpaceId = uSpaceId;
    }

    public String getuStandardProduct() {
        return uStandardProduct;
    }

    public void setuStandardProduct(String uStandardProduct) {
        this.uStandardProduct = uStandardProduct;
    }

    public String getuSystemName() {
        return uSystemName;
    }

    public void setuSystemName(String uSystemName) {
        this.uSystemName = uSystemName;
    }

    public String getuTermBand() {
        return uTermBand;
    }

    public void setuTermBand(String uTermBand) {
        this.uTermBand = uTermBand;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public Date getWarrantyExpiration() {
        return warrantyExpiration;
    }

    public void setWarrantyExpiration(Date warrantyExpiration) {
        this.warrantyExpiration = warrantyExpiration;
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

