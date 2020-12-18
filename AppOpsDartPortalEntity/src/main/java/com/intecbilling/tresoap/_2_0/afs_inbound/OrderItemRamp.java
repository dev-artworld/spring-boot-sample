
package com.intecbilling.tresoap._2_0.afs_inbound;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrderItemRamp complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderItemRamp">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EQXStartDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="EQXEndDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="EQXAmount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="EQXKVA" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXDuration" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="EQXPercentage" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderItemRamp", propOrder = {
    "eqxStartDate",
    "eqxEndDate",
    "eqxAmount",
    "eqxkva",
    "eqxDuration",
    "eqxPercentage"
})
public class OrderItemRamp {

    @XmlElement(name = "EQXStartDate", required = true)
    protected String eqxStartDate;
    @XmlElement(name = "EQXEndDate", required = true)
    protected String eqxEndDate;
    @XmlElement(name = "EQXAmount", required = true)
    protected String eqxAmount;
    @XmlElement(name = "EQXKVA")
    protected String eqxkva;
    @XmlElement(name = "EQXDuration", required = true)
    protected String eqxDuration;
    @XmlElement(name = "EQXPercentage", required = true)
    protected String eqxPercentage;

    /**
     * Gets the value of the eqxStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXStartDate() {
        return eqxStartDate;
    }

    /**
     * Sets the value of the eqxStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXStartDate(String value) {
        this.eqxStartDate = value;
    }

    /**
     * Gets the value of the eqxEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXEndDate() {
        return eqxEndDate;
    }

    /**
     * Sets the value of the eqxEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXEndDate(String value) {
        this.eqxEndDate = value;
    }

    /**
     * Gets the value of the eqxAmount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXAmount() {
        return eqxAmount;
    }

    /**
     * Sets the value of the eqxAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXAmount(String value) {
        this.eqxAmount = value;
    }

    /**
     * Gets the value of the eqxkva property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXKVA() {
        return eqxkva;
    }

    /**
     * Sets the value of the eqxkva property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXKVA(String value) {
        this.eqxkva = value;
    }

    /**
     * Gets the value of the eqxDuration property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXDuration() {
        return eqxDuration;
    }

    /**
     * Sets the value of the eqxDuration property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXDuration(String value) {
        this.eqxDuration = value;
    }

    /**
     * Gets the value of the eqxPercentage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXPercentage() {
        return eqxPercentage;
    }

    /**
     * Sets the value of the eqxPercentage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXPercentage(String value) {
        this.eqxPercentage = value;
    }

}
