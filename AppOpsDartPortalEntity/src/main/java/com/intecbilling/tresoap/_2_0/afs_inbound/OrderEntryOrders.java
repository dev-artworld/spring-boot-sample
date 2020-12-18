
package com.intecbilling.tresoap._2_0.afs_inbound;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderEntry-Orders complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderEntry-Orders">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EQXAccountUCID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="EQXSalesEngineer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXBookedDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXOrderNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="EQXOrderSource" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="OrderStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="OrderDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="OrderType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="EQXOrderSubType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXMigrationSubType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXSalesOrderInitialTerm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXSalesOrderRenewalTerm" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXCustomerNoticePeriod" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXMaxPriceIncreasePercentage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXMaxPriceIncreaseValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXPriceIncreaseNoticePeriod" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXBillingAgreementNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ContactFirstName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ContactLastName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RequestOrigin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXOrderComment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXOriginalOrderNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXFirstPIAppAfter" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXExclInterconnectionFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXOneTimePIUpcharge" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXRegionalDefaultCalc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXAllProducts" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXOtherProducts" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXPower" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXSmartHandsPlan" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXSpace" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXInterconnection" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXAllProductsRenewal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXOtherProductsRenewal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXPowerRenewal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXSmartHandsPlanRenewal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXSpaceRenewal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXInterconnectionRenewal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXResale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXPartnerTier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXResellerDiscount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXDiscountType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXPartnerType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXPartnerSubType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXPartnerStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXSweepInIndicator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ListOfOrderEntry-LineItems" type="{http://tresoap.intecbilling.com/2.0/AFS-Inbound}ListOfOrderEntry-LineItems"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderEntry-Orders", propOrder = {
    "eqxAccountUCID",
    "eqxSalesEngineer",
    "eqxBookedDate",
    "eqxOrderNumber",
    "eqxOrderSource",
    "orderStatus",
    "orderDate",
    "orderType",
    "eqxOrderSubType",
    "eqxMigrationSubType",
    "eqxSalesOrderInitialTerm",
    "eqxSalesOrderRenewalTerm",
    "eqxCustomerNoticePeriod",
    "eqxMaxPriceIncreasePercentage",
    "eqxMaxPriceIncreaseValue",
    "eqxPriceIncreaseNoticePeriod",
    "eqxBillingAgreementNumber",
    "contactFirstName",
    "contactLastName",
    "requestOrigin",
    "eqxOrderComment",
    "eqxOriginalOrderNumber",
    "eqxFirstPIAppAfter",
    "eqxExclInterconnectionFlag",
    "eqxOneTimePIUpcharge",
    "eqxRegionalDefaultCalc",
    "eqxAllProducts",
    "eqxOtherProducts",
    "eqxPower",
    "eqxSmartHandsPlan",
    "eqxSpace",
    "eqxInterconnection",
    "eqxAllProductsRenewal",
    "eqxOtherProductsRenewal",
    "eqxPowerRenewal",
    "eqxSmartHandsPlanRenewal",
    "eqxSpaceRenewal",
    "eqxInterconnectionRenewal",
    "eqxResale",
    "eqxPartnerTier",
    "eqxResellerDiscount",
    "eqxDiscountType",
    "eqxPartnerType",
    "eqxPartnerSubType",
    "eqxPartnerStatus",
    "eqxSweepInIndicator",
    "listOfOrderEntryLineItems"
})
public class OrderEntryOrders {

    @XmlElement(name = "EQXAccountUCID", required = true)
    protected String eqxAccountUCID;
    @XmlElement(name = "EQXSalesEngineer")
    protected String eqxSalesEngineer;
    @XmlElement(name = "EQXBookedDate")
    protected String eqxBookedDate;
    @XmlElement(name = "EQXOrderNumber", required = true)
    protected String eqxOrderNumber;
    @XmlElement(name = "EQXOrderSource", required = true)
    protected String eqxOrderSource;
    @XmlElement(name = "OrderStatus", required = true)
    protected String orderStatus;
    @XmlElement(name = "OrderDate", required = true)
    protected String orderDate;
    @XmlElement(name = "OrderType", required = true)
    protected String orderType;
    @XmlElement(name = "EQXOrderSubType")
    protected String eqxOrderSubType;
    @XmlElement(name = "EQXMigrationSubType")
    protected String eqxMigrationSubType;
    @XmlElement(name = "EQXSalesOrderInitialTerm")
    protected String eqxSalesOrderInitialTerm;
    @XmlElement(name = "EQXSalesOrderRenewalTerm")
    protected String eqxSalesOrderRenewalTerm;
    @XmlElement(name = "EQXCustomerNoticePeriod")
    protected String eqxCustomerNoticePeriod;
    @XmlElement(name = "EQXMaxPriceIncreasePercentage")
    protected String eqxMaxPriceIncreasePercentage;
    @XmlElement(name = "EQXMaxPriceIncreaseValue")
    protected String eqxMaxPriceIncreaseValue;
    @XmlElement(name = "EQXPriceIncreaseNoticePeriod")
    protected String eqxPriceIncreaseNoticePeriod;
    @XmlElement(name = "EQXBillingAgreementNumber")
    protected String eqxBillingAgreementNumber;
    @XmlElement(name = "ContactFirstName")
    protected String contactFirstName;
    @XmlElement(name = "ContactLastName")
    protected String contactLastName;
    @XmlElement(name = "RequestOrigin")
    protected String requestOrigin;
    @XmlElement(name = "EQXOrderComment")
    protected String eqxOrderComment;
    @XmlElement(name = "EQXOriginalOrderNumber")
    protected String eqxOriginalOrderNumber;
    @XmlElement(name = "EQXFirstPIAppAfter")
    protected String eqxFirstPIAppAfter;
    @XmlElement(name = "EQXExclInterconnectionFlag")
    protected String eqxExclInterconnectionFlag;
    @XmlElement(name = "EQXOneTimePIUpcharge")
    protected String eqxOneTimePIUpcharge;
    @XmlElement(name = "EQXRegionalDefaultCalc")
    protected String eqxRegionalDefaultCalc;
    @XmlElement(name = "EQXAllProducts")
    protected String eqxAllProducts;
    @XmlElement(name = "EQXOtherProducts")
    protected String eqxOtherProducts;
    @XmlElement(name = "EQXPower")
    protected String eqxPower;
    @XmlElement(name = "EQXSmartHandsPlan")
    protected String eqxSmartHandsPlan;
    @XmlElement(name = "EQXSpace")
    protected String eqxSpace;
    @XmlElement(name = "EQXInterconnection")
    protected String eqxInterconnection;
    @XmlElement(name = "EQXAllProductsRenewal")
    protected String eqxAllProductsRenewal;
    @XmlElement(name = "EQXOtherProductsRenewal")
    protected String eqxOtherProductsRenewal;
    @XmlElement(name = "EQXPowerRenewal")
    protected String eqxPowerRenewal;
    @XmlElement(name = "EQXSmartHandsPlanRenewal")
    protected String eqxSmartHandsPlanRenewal;
    @XmlElement(name = "EQXSpaceRenewal")
    protected String eqxSpaceRenewal;
    @XmlElement(name = "EQXInterconnectionRenewal")
    protected String eqxInterconnectionRenewal;
    @XmlElement(name = "EQXResale")
    protected String eqxResale;
    @XmlElement(name = "EQXPartnerTier")
    protected String eqxPartnerTier;
    @XmlElement(name = "EQXResellerDiscount")
    protected String eqxResellerDiscount;
    @XmlElement(name = "EQXDiscountType")
    protected String eqxDiscountType;
    @XmlElement(name = "EQXPartnerType")
    protected String eqxPartnerType;
    @XmlElement(name = "EQXPartnerSubType")
    protected String eqxPartnerSubType;
    @XmlElement(name = "EQXPartnerStatus")
    protected String eqxPartnerStatus;
    @XmlElement(name = "EQXSweepInIndicator")
    protected String eqxSweepInIndicator;
    @XmlElement(name = "ListOfOrderEntry-LineItems", required = true)
    protected ListOfOrderEntryLineItems listOfOrderEntryLineItems;

    /**
     * Gets the value of the eqxAccountUCID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXAccountUCID() {
        return eqxAccountUCID;
    }

    /**
     * Sets the value of the eqxAccountUCID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXAccountUCID(String value) {
        this.eqxAccountUCID = value;
    }

    /**
     * Gets the value of the eqxSalesEngineer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXSalesEngineer() {
        return eqxSalesEngineer;
    }

    /**
     * Sets the value of the eqxSalesEngineer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXSalesEngineer(String value) {
        this.eqxSalesEngineer = value;
    }

    /**
     * Gets the value of the eqxBookedDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXBookedDate() {
        return eqxBookedDate;
    }

    /**
     * Sets the value of the eqxBookedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXBookedDate(String value) {
        this.eqxBookedDate = value;
    }

    /**
     * Gets the value of the eqxOrderNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXOrderNumber() {
        return eqxOrderNumber;
    }

    /**
     * Sets the value of the eqxOrderNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXOrderNumber(String value) {
        this.eqxOrderNumber = value;
    }

    /**
     * Gets the value of the eqxOrderSource property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXOrderSource() {
        return eqxOrderSource;
    }

    /**
     * Sets the value of the eqxOrderSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXOrderSource(String value) {
        this.eqxOrderSource = value;
    }

    /**
     * Gets the value of the orderStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderStatus() {
        return orderStatus;
    }

    /**
     * Sets the value of the orderStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderStatus(String value) {
        this.orderStatus = value;
    }

    /**
     * Gets the value of the orderDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderDate() {
        return orderDate;
    }

    /**
     * Sets the value of the orderDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderDate(String value) {
        this.orderDate = value;
    }

    /**
     * Gets the value of the orderType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * Sets the value of the orderType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderType(String value) {
        this.orderType = value;
    }

    /**
     * Gets the value of the eqxOrderSubType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXOrderSubType() {
        return eqxOrderSubType;
    }

    /**
     * Sets the value of the eqxOrderSubType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXOrderSubType(String value) {
        this.eqxOrderSubType = value;
    }

    /**
     * Gets the value of the eqxMigrationSubType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXMigrationSubType() {
        return eqxMigrationSubType;
    }

    /**
     * Sets the value of the eqxMigrationSubType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXMigrationSubType(String value) {
        this.eqxMigrationSubType = value;
    }

    /**
     * Gets the value of the eqxSalesOrderInitialTerm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXSalesOrderInitialTerm() {
        return eqxSalesOrderInitialTerm;
    }

    /**
     * Sets the value of the eqxSalesOrderInitialTerm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXSalesOrderInitialTerm(String value) {
        this.eqxSalesOrderInitialTerm = value;
    }

    /**
     * Gets the value of the eqxSalesOrderRenewalTerm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXSalesOrderRenewalTerm() {
        return eqxSalesOrderRenewalTerm;
    }

    /**
     * Sets the value of the eqxSalesOrderRenewalTerm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXSalesOrderRenewalTerm(String value) {
        this.eqxSalesOrderRenewalTerm = value;
    }

    /**
     * Gets the value of the eqxCustomerNoticePeriod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXCustomerNoticePeriod() {
        return eqxCustomerNoticePeriod;
    }

    /**
     * Sets the value of the eqxCustomerNoticePeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXCustomerNoticePeriod(String value) {
        this.eqxCustomerNoticePeriod = value;
    }

    /**
     * Gets the value of the eqxMaxPriceIncreasePercentage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXMaxPriceIncreasePercentage() {
        return eqxMaxPriceIncreasePercentage;
    }

    /**
     * Sets the value of the eqxMaxPriceIncreasePercentage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXMaxPriceIncreasePercentage(String value) {
        this.eqxMaxPriceIncreasePercentage = value;
    }

    /**
     * Gets the value of the eqxMaxPriceIncreaseValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXMaxPriceIncreaseValue() {
        return eqxMaxPriceIncreaseValue;
    }

    /**
     * Sets the value of the eqxMaxPriceIncreaseValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXMaxPriceIncreaseValue(String value) {
        this.eqxMaxPriceIncreaseValue = value;
    }

    /**
     * Gets the value of the eqxPriceIncreaseNoticePeriod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXPriceIncreaseNoticePeriod() {
        return eqxPriceIncreaseNoticePeriod;
    }

    /**
     * Sets the value of the eqxPriceIncreaseNoticePeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXPriceIncreaseNoticePeriod(String value) {
        this.eqxPriceIncreaseNoticePeriod = value;
    }

    /**
     * Gets the value of the eqxBillingAgreementNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXBillingAgreementNumber() {
        return eqxBillingAgreementNumber;
    }

    /**
     * Sets the value of the eqxBillingAgreementNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXBillingAgreementNumber(String value) {
        this.eqxBillingAgreementNumber = value;
    }

    /**
     * Gets the value of the contactFirstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContactFirstName() {
        return contactFirstName;
    }

    /**
     * Sets the value of the contactFirstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContactFirstName(String value) {
        this.contactFirstName = value;
    }

    /**
     * Gets the value of the contactLastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContactLastName() {
        return contactLastName;
    }

    /**
     * Sets the value of the contactLastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContactLastName(String value) {
        this.contactLastName = value;
    }

    /**
     * Gets the value of the requestOrigin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestOrigin() {
        return requestOrigin;
    }

    /**
     * Sets the value of the requestOrigin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestOrigin(String value) {
        this.requestOrigin = value;
    }

    /**
     * Gets the value of the eqxOrderComment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXOrderComment() {
        return eqxOrderComment;
    }

    /**
     * Sets the value of the eqxOrderComment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXOrderComment(String value) {
        this.eqxOrderComment = value;
    }

    /**
     * Gets the value of the eqxOriginalOrderNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXOriginalOrderNumber() {
        return eqxOriginalOrderNumber;
    }

    /**
     * Sets the value of the eqxOriginalOrderNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXOriginalOrderNumber(String value) {
        this.eqxOriginalOrderNumber = value;
    }

    /**
     * Gets the value of the eqxFirstPIAppAfter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXFirstPIAppAfter() {
        return eqxFirstPIAppAfter;
    }

    /**
     * Sets the value of the eqxFirstPIAppAfter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXFirstPIAppAfter(String value) {
        this.eqxFirstPIAppAfter = value;
    }

    /**
     * Gets the value of the eqxExclInterconnectionFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXExclInterconnectionFlag() {
        return eqxExclInterconnectionFlag;
    }

    /**
     * Sets the value of the eqxExclInterconnectionFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXExclInterconnectionFlag(String value) {
        this.eqxExclInterconnectionFlag = value;
    }

    /**
     * Gets the value of the eqxOneTimePIUpcharge property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXOneTimePIUpcharge() {
        return eqxOneTimePIUpcharge;
    }

    /**
     * Sets the value of the eqxOneTimePIUpcharge property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXOneTimePIUpcharge(String value) {
        this.eqxOneTimePIUpcharge = value;
    }

    /**
     * Gets the value of the eqxRegionalDefaultCalc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXRegionalDefaultCalc() {
        return eqxRegionalDefaultCalc;
    }

    /**
     * Sets the value of the eqxRegionalDefaultCalc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXRegionalDefaultCalc(String value) {
        this.eqxRegionalDefaultCalc = value;
    }

    /**
     * Gets the value of the eqxAllProducts property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXAllProducts() {
        return eqxAllProducts;
    }

    /**
     * Sets the value of the eqxAllProducts property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXAllProducts(String value) {
        this.eqxAllProducts = value;
    }

    /**
     * Gets the value of the eqxOtherProducts property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXOtherProducts() {
        return eqxOtherProducts;
    }

    /**
     * Sets the value of the eqxOtherProducts property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXOtherProducts(String value) {
        this.eqxOtherProducts = value;
    }

    /**
     * Gets the value of the eqxPower property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXPower() {
        return eqxPower;
    }

    /**
     * Sets the value of the eqxPower property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXPower(String value) {
        this.eqxPower = value;
    }

    /**
     * Gets the value of the eqxSmartHandsPlan property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXSmartHandsPlan() {
        return eqxSmartHandsPlan;
    }

    /**
     * Sets the value of the eqxSmartHandsPlan property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXSmartHandsPlan(String value) {
        this.eqxSmartHandsPlan = value;
    }

    /**
     * Gets the value of the eqxSpace property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXSpace() {
        return eqxSpace;
    }

    /**
     * Sets the value of the eqxSpace property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXSpace(String value) {
        this.eqxSpace = value;
    }

    /**
     * Gets the value of the eqxInterconnection property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXInterconnection() {
        return eqxInterconnection;
    }

    /**
     * Sets the value of the eqxInterconnection property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXInterconnection(String value) {
        this.eqxInterconnection = value;
    }

    /**
     * Gets the value of the eqxAllProductsRenewal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXAllProductsRenewal() {
        return eqxAllProductsRenewal;
    }

    /**
     * Sets the value of the eqxAllProductsRenewal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXAllProductsRenewal(String value) {
        this.eqxAllProductsRenewal = value;
    }

    /**
     * Gets the value of the eqxOtherProductsRenewal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXOtherProductsRenewal() {
        return eqxOtherProductsRenewal;
    }

    /**
     * Sets the value of the eqxOtherProductsRenewal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXOtherProductsRenewal(String value) {
        this.eqxOtherProductsRenewal = value;
    }

    /**
     * Gets the value of the eqxPowerRenewal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXPowerRenewal() {
        return eqxPowerRenewal;
    }

    /**
     * Sets the value of the eqxPowerRenewal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXPowerRenewal(String value) {
        this.eqxPowerRenewal = value;
    }

    /**
     * Gets the value of the eqxSmartHandsPlanRenewal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXSmartHandsPlanRenewal() {
        return eqxSmartHandsPlanRenewal;
    }

    /**
     * Sets the value of the eqxSmartHandsPlanRenewal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXSmartHandsPlanRenewal(String value) {
        this.eqxSmartHandsPlanRenewal = value;
    }

    /**
     * Gets the value of the eqxSpaceRenewal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXSpaceRenewal() {
        return eqxSpaceRenewal;
    }

    /**
     * Sets the value of the eqxSpaceRenewal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXSpaceRenewal(String value) {
        this.eqxSpaceRenewal = value;
    }

    /**
     * Gets the value of the eqxInterconnectionRenewal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXInterconnectionRenewal() {
        return eqxInterconnectionRenewal;
    }

    /**
     * Sets the value of the eqxInterconnectionRenewal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXInterconnectionRenewal(String value) {
        this.eqxInterconnectionRenewal = value;
    }

    /**
     * Gets the value of the eqxResale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXResale() {
        return eqxResale;
    }

    /**
     * Sets the value of the eqxResale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXResale(String value) {
        this.eqxResale = value;
    }

    /**
     * Gets the value of the eqxPartnerTier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXPartnerTier() {
        return eqxPartnerTier;
    }

    /**
     * Sets the value of the eqxPartnerTier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXPartnerTier(String value) {
        this.eqxPartnerTier = value;
    }

    /**
     * Gets the value of the eqxResellerDiscount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXResellerDiscount() {
        return eqxResellerDiscount;
    }

    /**
     * Sets the value of the eqxResellerDiscount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXResellerDiscount(String value) {
        this.eqxResellerDiscount = value;
    }

    /**
     * Gets the value of the eqxDiscountType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXDiscountType() {
        return eqxDiscountType;
    }

    /**
     * Sets the value of the eqxDiscountType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXDiscountType(String value) {
        this.eqxDiscountType = value;
    }

    /**
     * Gets the value of the eqxPartnerType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXPartnerType() {
        return eqxPartnerType;
    }

    /**
     * Sets the value of the eqxPartnerType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXPartnerType(String value) {
        this.eqxPartnerType = value;
    }

    /**
     * Gets the value of the eqxPartnerSubType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXPartnerSubType() {
        return eqxPartnerSubType;
    }

    /**
     * Sets the value of the eqxPartnerSubType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXPartnerSubType(String value) {
        this.eqxPartnerSubType = value;
    }

    /**
     * Gets the value of the eqxPartnerStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXPartnerStatus() {
        return eqxPartnerStatus;
    }

    /**
     * Sets the value of the eqxPartnerStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXPartnerStatus(String value) {
        this.eqxPartnerStatus = value;
    }

    /**
     * Gets the value of the eqxSweepInIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXSweepInIndicator() {
        return eqxSweepInIndicator;
    }

    /**
     * Sets the value of the eqxSweepInIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXSweepInIndicator(String value) {
        this.eqxSweepInIndicator = value;
    }

    /**
     * Gets the value of the listOfOrderEntryLineItems property.
     * 
     * @return
     *     possible object is
     *     {@link ListOfOrderEntryLineItems }
     *     
     */
    public ListOfOrderEntryLineItems getListOfOrderEntryLineItems() {
        return listOfOrderEntryLineItems;
    }

    /**
     * Sets the value of the listOfOrderEntryLineItems property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListOfOrderEntryLineItems }
     *     
     */
    public void setListOfOrderEntryLineItems(ListOfOrderEntryLineItems value) {
        this.listOfOrderEntryLineItems = value;
    }

}
