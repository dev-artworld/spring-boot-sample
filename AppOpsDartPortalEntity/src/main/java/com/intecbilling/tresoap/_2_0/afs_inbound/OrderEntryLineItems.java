
package com.intecbilling.tresoap._2_0.afs_inbound;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderEntry-LineItems complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderEntry-LineItems">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ActionCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AssetId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BillableFlag" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="EQXContactUCID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXAddressUCID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXSubAccountUCID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXCustomerReferenceNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CurrencyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXDeativationReasonCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EffectiveDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXBillingCommencementDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXInstallDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXIBXName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LineNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LineStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="EQXPONumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ProductDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PartNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ProductId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ExtendedPrice" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ParentOrderItemId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RootOrderItemId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXSerialNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXTermLength" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXUniqueSpaceId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Quantity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXDeferredRevenueCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXEarnedRevenueCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXNonStandardFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXProductFamily" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXProductCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXRevRecRules" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXConfigurationDelay" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="UnitOfMeasure" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXSalesQuantity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RegularAssetId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BOAAssetId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXFreeMonths" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXSVPromoPackageType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXSVPromotionId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXSVPromoPartNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXSVPromoProduct" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXExtendedPromoPrice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXPackageFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXBillReplayFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXSequenceNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="EQXEligibleForBilling" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXPICategory" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="Space"/>
 *               &lt;enumeration value="Power"/>
 *               &lt;enumeration value="Interconnection"/>
 *               &lt;enumeration value="SmartHands Plan"/>
 *               &lt;enumeration value="Other Products"/>
 *               &lt;enumeration value="NA"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="EQXPOTotalValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXPOStartDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXPOEndDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXMPOIndicator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXCPQIntegrationId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXCPQPackageName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ListOfOrderItemRamp" type="{http://tresoap.intecbilling.com/2.0/AFS-Inbound}ListOfOrderItemRamp" minOccurs="0"/>
 *         &lt;element name="ListOfESKPricingTier" type="{http://tresoap.intecbilling.com/2.0/AFS-Inbound}ListOfESKPricingTier" minOccurs="0"/>
 *         &lt;element name="ListOfOrderItemXa" type="{http://tresoap.intecbilling.com/2.0/AFS-Inbound}ListOfOrderItemXa" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderEntry-LineItems", propOrder = {
    "actionCode",
    "assetId",
    "billableFlag",
    "eqxContactUCID",
    "eqxAddressUCID",
    "eqxSubAccountUCID",
    "eqxCustomerReferenceNumber",
    "currencyCode",
    "eqxDeativationReasonCode",
    "effectiveDate",
    "eqxBillingCommencementDate",
    "eqxInstallDate",
    "eqxibxName",
    "id",
    "lineNumber",
    "lineStatus",
    "eqxpoNumber",
    "productDescription",
    "partNumber",
    "productId",
    "extendedPrice",
    "parentOrderItemId",
    "rootOrderItemId",
    "eqxSerialNumber",
    "eqxTermLength",
    "eqxUniqueSpaceId",
    "quantity",
    "eqxDeferredRevenueCode",
    "eqxEarnedRevenueCode",
    "eqxNonStandardFlag",
    "eqxProductFamily",
    "eqxProductCode",
    "eqxRevRecRules",
    "eqxConfigurationDelay",
    "unitOfMeasure",
    "eqxSalesQuantity",
    "regularAssetId",
    "boaAssetId",
    "eqxFreeMonths",
    "eqxsvPromoPackageType",
    "eqxsvPromotionId",
    "eqxsvPromoPartNum",
    "eqxsvPromoProduct",
    "eqxExtendedPromoPrice",
    "eqxPackageFlag",
    "eqxBillReplayFlag",
    "eqxSequenceNumber",
    "eqxEligibleForBilling",
    "eqxpiCategory",
    "eqxpoTotalValue",
    "eqxpoStartDate",
    "eqxpoEndDate",
    "eqxmpoIndicator",
    "eqxcpqIntegrationId",
    "eqxcpqPackageName",
    "listOfOrderItemRamp",
    "listOfESKPricingTier",
    "listOfOrderItemXa"
})
public class OrderEntryLineItems {

    @XmlElement(name = "ActionCode", required = true)
    protected String actionCode;
    @XmlElement(name = "AssetId")
    protected String assetId;
    @XmlElement(name = "BillableFlag", required = true)
    protected String billableFlag;
    @XmlElement(name = "EQXContactUCID")
    protected String eqxContactUCID;
    @XmlElement(name = "EQXAddressUCID")
    protected String eqxAddressUCID;
    @XmlElement(name = "EQXSubAccountUCID")
    protected String eqxSubAccountUCID;
    @XmlElement(name = "EQXCustomerReferenceNumber")
    protected String eqxCustomerReferenceNumber;
    @XmlElement(name = "CurrencyCode")
    protected String currencyCode;
    @XmlElement(name = "EQXDeativationReasonCode")
    protected String eqxDeativationReasonCode;
    @XmlElement(name = "EffectiveDate")
    protected String effectiveDate;
    @XmlElement(name = "EQXBillingCommencementDate")
    protected String eqxBillingCommencementDate;
    @XmlElement(name = "EQXInstallDate")
    protected String eqxInstallDate;
    @XmlElement(name = "EQXIBXName", required = true)
    protected String eqxibxName;
    @XmlElement(name = "Id", required = true)
    protected String id;
    @XmlElement(name = "LineNumber", required = true)
    protected String lineNumber;
    @XmlElement(name = "LineStatus", required = true)
    protected String lineStatus;
    @XmlElement(name = "EQXPONumber")
    protected String eqxpoNumber;
    @XmlElement(name = "ProductDescription")
    protected String productDescription;
    @XmlElement(name = "PartNumber", required = true)
    protected String partNumber;
    @XmlElement(name = "ProductId", required = true)
    protected String productId;
    @XmlElement(name = "ExtendedPrice", required = true)
    protected String extendedPrice;
    @XmlElement(name = "ParentOrderItemId")
    protected String parentOrderItemId;
    @XmlElement(name = "RootOrderItemId")
    protected String rootOrderItemId;
    @XmlElement(name = "EQXSerialNumber")
    protected String eqxSerialNumber;
    @XmlElement(name = "EQXTermLength")
    protected String eqxTermLength;
    @XmlElement(name = "EQXUniqueSpaceId")
    protected String eqxUniqueSpaceId;
    @XmlElement(name = "Quantity")
    protected String quantity;
    @XmlElement(name = "EQXDeferredRevenueCode")
    protected String eqxDeferredRevenueCode;
    @XmlElement(name = "EQXEarnedRevenueCode")
    protected String eqxEarnedRevenueCode;
    @XmlElement(name = "EQXNonStandardFlag")
    protected String eqxNonStandardFlag;
    @XmlElement(name = "EQXProductFamily")
    protected String eqxProductFamily;
    @XmlElement(name = "EQXProductCode")
    protected String eqxProductCode;
    @XmlElement(name = "EQXRevRecRules")
    protected String eqxRevRecRules;
    @XmlElement(name = "EQXConfigurationDelay")
    protected String eqxConfigurationDelay;
    @XmlElement(name = "UnitOfMeasure")
    protected String unitOfMeasure;
    @XmlElement(name = "EQXSalesQuantity")
    protected String eqxSalesQuantity;
    @XmlElement(name = "RegularAssetId")
    protected String regularAssetId;
    @XmlElement(name = "BOAAssetId")
    protected String boaAssetId;
    @XmlElement(name = "EQXFreeMonths")
    protected String eqxFreeMonths;
    @XmlElement(name = "EQXSVPromoPackageType")
    protected String eqxsvPromoPackageType;
    @XmlElement(name = "EQXSVPromotionId")
    protected String eqxsvPromotionId;
    @XmlElement(name = "EQXSVPromoPartNum")
    protected String eqxsvPromoPartNum;
    @XmlElement(name = "EQXSVPromoProduct")
    protected String eqxsvPromoProduct;
    @XmlElement(name = "EQXExtendedPromoPrice")
    protected String eqxExtendedPromoPrice;
    @XmlElement(name = "EQXPackageFlag")
    protected String eqxPackageFlag;
    @XmlElement(name = "EQXBillReplayFlag")
    protected String eqxBillReplayFlag;
    @XmlElement(name = "EQXSequenceNumber", required = true)
    protected String eqxSequenceNumber;
    @XmlElement(name = "EQXEligibleForBilling")
    protected String eqxEligibleForBilling;
    @XmlElement(name = "EQXPICategory")
    protected String eqxpiCategory;
    @XmlElement(name = "EQXPOTotalValue")
    protected String eqxpoTotalValue;
    @XmlElement(name = "EQXPOStartDate")
    protected String eqxpoStartDate;
    @XmlElement(name = "EQXPOEndDate")
    protected String eqxpoEndDate;
    @XmlElement(name = "EQXMPOIndicator")
    protected String eqxmpoIndicator;
    @XmlElement(name = "EQXCPQIntegrationId")
    protected String eqxcpqIntegrationId;
    @XmlElement(name = "EQXCPQPackageName")
    protected String eqxcpqPackageName;
    @XmlElement(name = "ListOfOrderItemRamp")
    protected ListOfOrderItemRamp listOfOrderItemRamp;
    @XmlElement(name = "ListOfESKPricingTier")
    protected ListOfESKPricingTier listOfESKPricingTier;
    @XmlElement(name = "ListOfOrderItemXa")
    protected ListOfOrderItemXa listOfOrderItemXa;

    /**
     * Gets the value of the actionCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActionCode() {
        return actionCode;
    }

    /**
     * Sets the value of the actionCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActionCode(String value) {
        this.actionCode = value;
    }

    /**
     * Gets the value of the assetId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssetId() {
        return assetId;
    }

    /**
     * Sets the value of the assetId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssetId(String value) {
        this.assetId = value;
    }

    /**
     * Gets the value of the billableFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillableFlag() {
        return billableFlag;
    }

    /**
     * Sets the value of the billableFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillableFlag(String value) {
        this.billableFlag = value;
    }

    /**
     * Gets the value of the eqxContactUCID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXContactUCID() {
        return eqxContactUCID;
    }

    /**
     * Sets the value of the eqxContactUCID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXContactUCID(String value) {
        this.eqxContactUCID = value;
    }

    /**
     * Gets the value of the eqxAddressUCID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXAddressUCID() {
        return eqxAddressUCID;
    }

    /**
     * Sets the value of the eqxAddressUCID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXAddressUCID(String value) {
        this.eqxAddressUCID = value;
    }

    /**
     * Gets the value of the eqxSubAccountUCID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXSubAccountUCID() {
        return eqxSubAccountUCID;
    }

    /**
     * Sets the value of the eqxSubAccountUCID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXSubAccountUCID(String value) {
        this.eqxSubAccountUCID = value;
    }

    /**
     * Gets the value of the eqxCustomerReferenceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXCustomerReferenceNumber() {
        return eqxCustomerReferenceNumber;
    }

    /**
     * Sets the value of the eqxCustomerReferenceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXCustomerReferenceNumber(String value) {
        this.eqxCustomerReferenceNumber = value;
    }

    /**
     * Gets the value of the currencyCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * Sets the value of the currencyCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrencyCode(String value) {
        this.currencyCode = value;
    }

    /**
     * Gets the value of the eqxDeativationReasonCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXDeativationReasonCode() {
        return eqxDeativationReasonCode;
    }

    /**
     * Sets the value of the eqxDeativationReasonCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXDeativationReasonCode(String value) {
        this.eqxDeativationReasonCode = value;
    }

    /**
     * Gets the value of the effectiveDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * Sets the value of the effectiveDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEffectiveDate(String value) {
        this.effectiveDate = value;
    }

    /**
     * Gets the value of the eqxBillingCommencementDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXBillingCommencementDate() {
        return eqxBillingCommencementDate;
    }

    /**
     * Sets the value of the eqxBillingCommencementDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXBillingCommencementDate(String value) {
        this.eqxBillingCommencementDate = value;
    }

    /**
     * Gets the value of the eqxInstallDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXInstallDate() {
        return eqxInstallDate;
    }

    /**
     * Sets the value of the eqxInstallDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXInstallDate(String value) {
        this.eqxInstallDate = value;
    }

    /**
     * Gets the value of the eqxibxName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXIBXName() {
        return eqxibxName;
    }

    /**
     * Sets the value of the eqxibxName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXIBXName(String value) {
        this.eqxibxName = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the lineNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLineNumber() {
        return lineNumber;
    }

    /**
     * Sets the value of the lineNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLineNumber(String value) {
        this.lineNumber = value;
    }

    /**
     * Gets the value of the lineStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLineStatus() {
        return lineStatus;
    }

    /**
     * Sets the value of the lineStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLineStatus(String value) {
        this.lineStatus = value;
    }

    /**
     * Gets the value of the eqxpoNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXPONumber() {
        return eqxpoNumber;
    }

    /**
     * Sets the value of the eqxpoNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXPONumber(String value) {
        this.eqxpoNumber = value;
    }

    /**
     * Gets the value of the productDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductDescription() {
        return productDescription;
    }

    /**
     * Sets the value of the productDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductDescription(String value) {
        this.productDescription = value;
    }

    /**
     * Gets the value of the partNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartNumber() {
        return partNumber;
    }

    /**
     * Sets the value of the partNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartNumber(String value) {
        this.partNumber = value;
    }

    /**
     * Gets the value of the productId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductId() {
        return productId;
    }

    /**
     * Sets the value of the productId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductId(String value) {
        this.productId = value;
    }

    /**
     * Gets the value of the extendedPrice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtendedPrice() {
        return extendedPrice;
    }

    /**
     * Sets the value of the extendedPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtendedPrice(String value) {
        this.extendedPrice = value;
    }

    /**
     * Gets the value of the parentOrderItemId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentOrderItemId() {
        return parentOrderItemId;
    }

    /**
     * Sets the value of the parentOrderItemId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentOrderItemId(String value) {
        this.parentOrderItemId = value;
    }

    /**
     * Gets the value of the rootOrderItemId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRootOrderItemId() {
        return rootOrderItemId;
    }

    /**
     * Sets the value of the rootOrderItemId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRootOrderItemId(String value) {
        this.rootOrderItemId = value;
    }

    /**
     * Gets the value of the eqxSerialNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXSerialNumber() {
        return eqxSerialNumber;
    }

    /**
     * Sets the value of the eqxSerialNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXSerialNumber(String value) {
        this.eqxSerialNumber = value;
    }

    /**
     * Gets the value of the eqxTermLength property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXTermLength() {
        return eqxTermLength;
    }

    /**
     * Sets the value of the eqxTermLength property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXTermLength(String value) {
        this.eqxTermLength = value;
    }

    /**
     * Gets the value of the eqxUniqueSpaceId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXUniqueSpaceId() {
        return eqxUniqueSpaceId;
    }

    /**
     * Sets the value of the eqxUniqueSpaceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXUniqueSpaceId(String value) {
        this.eqxUniqueSpaceId = value;
    }

    /**
     * Gets the value of the quantity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuantity(String value) {
        this.quantity = value;
    }

    /**
     * Gets the value of the eqxDeferredRevenueCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXDeferredRevenueCode() {
        return eqxDeferredRevenueCode;
    }

    /**
     * Sets the value of the eqxDeferredRevenueCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXDeferredRevenueCode(String value) {
        this.eqxDeferredRevenueCode = value;
    }

    /**
     * Gets the value of the eqxEarnedRevenueCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXEarnedRevenueCode() {
        return eqxEarnedRevenueCode;
    }

    /**
     * Sets the value of the eqxEarnedRevenueCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXEarnedRevenueCode(String value) {
        this.eqxEarnedRevenueCode = value;
    }

    /**
     * Gets the value of the eqxNonStandardFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXNonStandardFlag() {
        return eqxNonStandardFlag;
    }

    /**
     * Sets the value of the eqxNonStandardFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXNonStandardFlag(String value) {
        this.eqxNonStandardFlag = value;
    }

    /**
     * Gets the value of the eqxProductFamily property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXProductFamily() {
        return eqxProductFamily;
    }

    /**
     * Sets the value of the eqxProductFamily property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXProductFamily(String value) {
        this.eqxProductFamily = value;
    }

    /**
     * Gets the value of the eqxProductCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXProductCode() {
        return eqxProductCode;
    }

    /**
     * Sets the value of the eqxProductCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXProductCode(String value) {
        this.eqxProductCode = value;
    }

    /**
     * Gets the value of the eqxRevRecRules property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXRevRecRules() {
        return eqxRevRecRules;
    }

    /**
     * Sets the value of the eqxRevRecRules property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXRevRecRules(String value) {
        this.eqxRevRecRules = value;
    }

    /**
     * Gets the value of the eqxConfigurationDelay property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXConfigurationDelay() {
        return eqxConfigurationDelay;
    }

    /**
     * Sets the value of the eqxConfigurationDelay property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXConfigurationDelay(String value) {
        this.eqxConfigurationDelay = value;
    }

    /**
     * Gets the value of the unitOfMeasure property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    /**
     * Sets the value of the unitOfMeasure property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnitOfMeasure(String value) {
        this.unitOfMeasure = value;
    }

    /**
     * Gets the value of the eqxSalesQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXSalesQuantity() {
        return eqxSalesQuantity;
    }

    /**
     * Sets the value of the eqxSalesQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXSalesQuantity(String value) {
        this.eqxSalesQuantity = value;
    }

    /**
     * Gets the value of the regularAssetId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegularAssetId() {
        return regularAssetId;
    }

    /**
     * Sets the value of the regularAssetId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegularAssetId(String value) {
        this.regularAssetId = value;
    }

    /**
     * Gets the value of the boaAssetId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBOAAssetId() {
        return boaAssetId;
    }

    /**
     * Sets the value of the boaAssetId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBOAAssetId(String value) {
        this.boaAssetId = value;
    }

    /**
     * Gets the value of the eqxFreeMonths property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXFreeMonths() {
        return eqxFreeMonths;
    }

    /**
     * Sets the value of the eqxFreeMonths property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXFreeMonths(String value) {
        this.eqxFreeMonths = value;
    }

    /**
     * Gets the value of the eqxsvPromoPackageType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXSVPromoPackageType() {
        return eqxsvPromoPackageType;
    }

    /**
     * Sets the value of the eqxsvPromoPackageType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXSVPromoPackageType(String value) {
        this.eqxsvPromoPackageType = value;
    }

    /**
     * Gets the value of the eqxsvPromotionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXSVPromotionId() {
        return eqxsvPromotionId;
    }

    /**
     * Sets the value of the eqxsvPromotionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXSVPromotionId(String value) {
        this.eqxsvPromotionId = value;
    }

    /**
     * Gets the value of the eqxsvPromoPartNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXSVPromoPartNum() {
        return eqxsvPromoPartNum;
    }

    /**
     * Sets the value of the eqxsvPromoPartNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXSVPromoPartNum(String value) {
        this.eqxsvPromoPartNum = value;
    }

    /**
     * Gets the value of the eqxsvPromoProduct property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXSVPromoProduct() {
        return eqxsvPromoProduct;
    }

    /**
     * Sets the value of the eqxsvPromoProduct property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXSVPromoProduct(String value) {
        this.eqxsvPromoProduct = value;
    }

    /**
     * Gets the value of the eqxExtendedPromoPrice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXExtendedPromoPrice() {
        return eqxExtendedPromoPrice;
    }

    /**
     * Sets the value of the eqxExtendedPromoPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXExtendedPromoPrice(String value) {
        this.eqxExtendedPromoPrice = value;
    }

    /**
     * Gets the value of the eqxPackageFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXPackageFlag() {
        return eqxPackageFlag;
    }

    /**
     * Sets the value of the eqxPackageFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXPackageFlag(String value) {
        this.eqxPackageFlag = value;
    }

    /**
     * Gets the value of the eqxBillReplayFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXBillReplayFlag() {
        return eqxBillReplayFlag;
    }

    /**
     * Sets the value of the eqxBillReplayFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXBillReplayFlag(String value) {
        this.eqxBillReplayFlag = value;
    }

    /**
     * Gets the value of the eqxSequenceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXSequenceNumber() {
        return eqxSequenceNumber;
    }

    /**
     * Sets the value of the eqxSequenceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXSequenceNumber(String value) {
        this.eqxSequenceNumber = value;
    }

    /**
     * Gets the value of the eqxEligibleForBilling property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXEligibleForBilling() {
        return eqxEligibleForBilling;
    }

    /**
     * Sets the value of the eqxEligibleForBilling property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXEligibleForBilling(String value) {
        this.eqxEligibleForBilling = value;
    }

    /**
     * Gets the value of the eqxpiCategory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXPICategory() {
        return eqxpiCategory;
    }

    /**
     * Sets the value of the eqxpiCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXPICategory(String value) {
        this.eqxpiCategory = value;
    }

    /**
     * Gets the value of the eqxpoTotalValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXPOTotalValue() {
        return eqxpoTotalValue;
    }

    /**
     * Sets the value of the eqxpoTotalValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXPOTotalValue(String value) {
        this.eqxpoTotalValue = value;
    }

    /**
     * Gets the value of the eqxpoStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXPOStartDate() {
        return eqxpoStartDate;
    }

    /**
     * Sets the value of the eqxpoStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXPOStartDate(String value) {
        this.eqxpoStartDate = value;
    }

    /**
     * Gets the value of the eqxpoEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXPOEndDate() {
        return eqxpoEndDate;
    }

    /**
     * Sets the value of the eqxpoEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXPOEndDate(String value) {
        this.eqxpoEndDate = value;
    }

    /**
     * Gets the value of the eqxmpoIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXMPOIndicator() {
        return eqxmpoIndicator;
    }

    /**
     * Sets the value of the eqxmpoIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXMPOIndicator(String value) {
        this.eqxmpoIndicator = value;
    }

    /**
     * Gets the value of the eqxcpqIntegrationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXCPQIntegrationId() {
        return eqxcpqIntegrationId;
    }

    /**
     * Sets the value of the eqxcpqIntegrationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXCPQIntegrationId(String value) {
        this.eqxcpqIntegrationId = value;
    }

    /**
     * Gets the value of the eqxcpqPackageName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXCPQPackageName() {
        return eqxcpqPackageName;
    }

    /**
     * Sets the value of the eqxcpqPackageName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXCPQPackageName(String value) {
        this.eqxcpqPackageName = value;
    }

    /**
     * Gets the value of the listOfOrderItemRamp property.
     * 
     * @return
     *     possible object is
     *     {@link ListOfOrderItemRamp }
     *     
     */
    public ListOfOrderItemRamp getListOfOrderItemRamp() {
        return listOfOrderItemRamp;
    }

    /**
     * Sets the value of the listOfOrderItemRamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListOfOrderItemRamp }
     *     
     */
    public void setListOfOrderItemRamp(ListOfOrderItemRamp value) {
        this.listOfOrderItemRamp = value;
    }

    /**
     * Gets the value of the listOfESKPricingTier property.
     * 
     * @return
     *     possible object is
     *     {@link ListOfESKPricingTier }
     *     
     */
    public ListOfESKPricingTier getListOfESKPricingTier() {
        return listOfESKPricingTier;
    }

    /**
     * Sets the value of the listOfESKPricingTier property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListOfESKPricingTier }
     *     
     */
    public void setListOfESKPricingTier(ListOfESKPricingTier value) {
        this.listOfESKPricingTier = value;
    }

    /**
     * Gets the value of the listOfOrderItemXa property.
     * 
     * @return
     *     possible object is
     *     {@link ListOfOrderItemXa }
     *     
     */
    public ListOfOrderItemXa getListOfOrderItemXa() {
        return listOfOrderItemXa;
    }

    /**
     * Sets the value of the listOfOrderItemXa property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListOfOrderItemXa }
     *     
     */
    public void setListOfOrderItemXa(ListOfOrderItemXa value) {
        this.listOfOrderItemXa = value;
    }

}
