
package com.siebel.xml.eqx_20dart_20batch_20io;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EqxDartBatchLineXa complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EqxDartBatchLineXa">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EQXAttributeName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXAttributeType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXAttributeValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXSyncToAsset" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXSyncToPOF" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXXASequenceNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EqxDartBatchLineXa", propOrder = {
    "eqxAttributeName",
    "eqxAttributeType",
    "eqxAttributeValue",
    "eqxSyncToAsset",
    "eqxSyncToPOF",
    "eqxxaSequenceNumber"
})
public class EqxDartBatchLineXa {

    @XmlElement(name = "EQXAttributeName")
    protected String eqxAttributeName;
    @XmlElement(name = "EQXAttributeType")
    protected String eqxAttributeType;
    @XmlElement(name = "EQXAttributeValue")
    protected String eqxAttributeValue;
    @XmlElement(name = "EQXSyncToAsset")
    protected String eqxSyncToAsset;
    @XmlElement(name = "EQXSyncToPOF")
    protected String eqxSyncToPOF;
    @XmlElement(name = "EQXXASequenceNumber")
    protected String eqxxaSequenceNumber;

    /**
     * Gets the value of the eqxAttributeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXAttributeName() {
        return eqxAttributeName;
    }

    /**
     * Sets the value of the eqxAttributeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXAttributeName(String value) {
        this.eqxAttributeName = value;
    }

    /**
     * Gets the value of the eqxAttributeType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXAttributeType() {
        return eqxAttributeType;
    }

    /**
     * Sets the value of the eqxAttributeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXAttributeType(String value) {
        this.eqxAttributeType = value;
    }

    /**
     * Gets the value of the eqxAttributeValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXAttributeValue() {
        return eqxAttributeValue;
    }

    /**
     * Sets the value of the eqxAttributeValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXAttributeValue(String value) {
        this.eqxAttributeValue = value;
    }

    /**
     * Gets the value of the eqxSyncToAsset property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXSyncToAsset() {
        return eqxSyncToAsset;
    }

    /**
     * Sets the value of the eqxSyncToAsset property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXSyncToAsset(String value) {
        this.eqxSyncToAsset = value;
    }

    /**
     * Gets the value of the eqxSyncToPOF property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXSyncToPOF() {
        return eqxSyncToPOF;
    }

    /**
     * Sets the value of the eqxSyncToPOF property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXSyncToPOF(String value) {
        this.eqxSyncToPOF = value;
    }

    /**
     * Gets the value of the eqxxaSequenceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXXASequenceNumber() {
        return eqxxaSequenceNumber;
    }

    /**
     * Sets the value of the eqxxaSequenceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXXASequenceNumber(String value) {
        this.eqxxaSequenceNumber = value;
    }

}
