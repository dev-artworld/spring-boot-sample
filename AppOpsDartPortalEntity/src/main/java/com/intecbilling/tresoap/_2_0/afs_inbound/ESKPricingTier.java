
package com.intecbilling.tresoap._2_0.afs_inbound;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ESKPricingTier complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ESKPricingTier">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EQXLowerTier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXUpperTier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXTierPrice" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ESKPricingTier", propOrder = {
    "eqxLowerTier",
    "eqxUpperTier",
    "eqxTierPrice"
})
public class ESKPricingTier {

    @XmlElement(name = "EQXLowerTier")
    protected String eqxLowerTier;
    @XmlElement(name = "EQXUpperTier")
    protected String eqxUpperTier;
    @XmlElement(name = "EQXTierPrice")
    protected String eqxTierPrice;

    /**
     * Gets the value of the eqxLowerTier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXLowerTier() {
        return eqxLowerTier;
    }

    /**
     * Sets the value of the eqxLowerTier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXLowerTier(String value) {
        this.eqxLowerTier = value;
    }

    /**
     * Gets the value of the eqxUpperTier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXUpperTier() {
        return eqxUpperTier;
    }

    /**
     * Sets the value of the eqxUpperTier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXUpperTier(String value) {
        this.eqxUpperTier = value;
    }

    /**
     * Gets the value of the eqxTierPrice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXTierPrice() {
        return eqxTierPrice;
    }

    /**
     * Sets the value of the eqxTierPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXTierPrice(String value) {
        this.eqxTierPrice = value;
    }

}
