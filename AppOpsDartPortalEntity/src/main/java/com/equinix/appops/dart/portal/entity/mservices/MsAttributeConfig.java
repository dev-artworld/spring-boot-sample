package com.equinix.appops.dart.portal.entity.mservices;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.equinix.appops.dart.portal.entity.SrcSLstOfVal;


/**
 * The persistent class for the ATTRIBUTE_CONFIG database table.
 * 
 */
@Entity
@Table(name="MS_ATTRIBUTE_CONFIG",schema="EQX_DART")
@NamedQuery(name="MsAttributeConfig.findAll", query="SELECT a FROM MsAttributeConfig a")
public class MsAttributeConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ROW_ID")
	private String rowId;

	@Column(name="APPROVAL_REQUIRED")
	private String approvalRequired;

	@Column(name="ATTR_NAME")
	private String attrName;

	@Column(name="ATTRIBUTE_FAMILY")
	private String attributeFamily;

	@Column(name="AUDIT_FLAG")
	private String auditFlag;
	
	@Column(name="CUSTOM_OBJECT")
	private String customObject;

	@Column(name="DATA_TYPE")
	private String dataType;

	@Column(name="DEPENDENT_ATTRIBUTE")
	private String dependentAttribute;

	@Column(name="DISPLAY_FLAG")
	private String displayFlag;

	private String editable;

	@Column(name="GROUP_SEQUENCE")
	private String groupSequence;

	@Column(name="HEADER_POSITION")
	private String headerPosition;

	private String lov;

	@Column(name="POF_ATTR")
	private String pofAttr;

	@Column(name="PRODUCT")
	private String product;

	@Column(name="\"SEQUENCE\"")
	private Long sequence;

	private String siebel;

	private String sot;

	private String snow;

	@Column(name="SYSTEM_NAME")
	private String systemName;
	
	@Column(name="AUDIT_HEADER")
	private String auditHeader;
	
	@Column(name="DISPLAY_NAME")
	private String displayName;
	
	@Column(name="SIEBEL_SEQUENCE")
	private Long siebelSequence;

	public String getAuditHeader() {
		return auditHeader;
	}

	public void setAuditHeader(String auditHeader) {
		this.auditHeader = auditHeader;
	}

	//bi-directional many-to-one association to SrcSLstOfVal
	@OneToMany(mappedBy="msAttributeConfig",fetch=FetchType.EAGER)
	private Set<SrcSLstOfVal> srcSLstOfVals;
	
	public Set<SrcSLstOfVal> getSrcSLstOfVals() {
		return srcSLstOfVals;
	}

	public void setSrcSLstOfVals(Set<SrcSLstOfVal> srcSLstOfVals) {
		this.srcSLstOfVals = srcSLstOfVals;
	}

	public MsAttributeConfig() {
	}

	public String getRowId() {
		return this.rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public String getApprovalRequired() {
		return this.approvalRequired;
	}

	public void setApprovalRequired(String approvalRequired) {
		this.approvalRequired = approvalRequired;
	}

	public String getAttrName() {
		return this.attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public String getAttributeFamily() {
		return this.attributeFamily;
	}

	public void setAttributeFamily(String attributeFamily) {
		this.attributeFamily = attributeFamily;
	}

	public String getAuditFlag() {
		return this.auditFlag;
	}

	public void setAuditFlag(String auditFlag) {
		this.auditFlag = auditFlag;
	}

	public String getCustomObject() {
		return this.customObject;
	}

	public void setCustomObject(String customObject) {
		this.customObject = customObject;
	}

	public String getDataType() {
		return this.dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDependentAttribute() {
		return this.dependentAttribute;
	}

	public void setDependentAttribute(String dependentAttribute) {
		this.dependentAttribute = dependentAttribute;
	}

	public String getDisplayFlag() {
		return this.displayFlag;
	}

	public void setDisplayFlag(String displayFlag) {
		this.displayFlag = displayFlag;
	}

	public String getEditable() {
		return this.editable;
	}

	public void setEditable(String editable) {
		this.editable = editable;
	}

	public String getGroupSequence() {
		return this.groupSequence;
	}

	public void setGroupSequence(String groupSequence) {
		this.groupSequence = groupSequence;
	}

	public String getHeaderPosition() {
		return this.headerPosition;
	}

	public void setHeaderPosition(String headerPosition) {
		this.headerPosition = headerPosition;
	}

	public String getLov() {
		return this.lov;
	}

	public void setLov(String lov) {
		this.lov = lov;
	}

	public String getPofAttr() {
		return this.pofAttr;
	}

	public void setPofAttr(String pofAttr) {
		this.pofAttr = pofAttr;
	}

	public String getProduct() {
		return this.product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Long getSequence() {
		return this.sequence;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

	public String getSiebel() {
		return this.siebel;
	}

	public void setSiebel(String siebel) {
		this.siebel = siebel;
	}

	public String getSot() {
		return this.sot;
	}

	public void setSot(String sot) {
		this.sot = sot;
	}

	public String getSystemName() {
		return this.systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getSnow() {
		return snow;
	}

	public void setSnow(String snow) {
		this.snow = snow;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	

	public Long getSiebelSequence() {
		return siebelSequence;
	}

	public void setSiebelSequence(Long siebelSequence) {
		this.siebelSequence = siebelSequence;
	}

	@Override
	public String toString() {
		return "AttributeConfig [attrName=" + attrName + ", headerPosition=" + headerPosition + "]";
	}

	
	
}