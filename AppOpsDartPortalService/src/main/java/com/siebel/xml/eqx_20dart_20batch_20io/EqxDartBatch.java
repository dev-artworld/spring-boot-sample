
package com.siebel.xml.eqx_20dart_20batch_20io;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EqxDartBatch complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EqxDartBatch">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EQXDFRID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXLoginName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ListOfEqxDartBatchLines" type="{http://www.siebel.com/xml/EQX%20DART%20Batch%20IO}ListOfEqxDartBatchLines" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EqxDartBatch", propOrder = {
    "eqxdfrid",
    "eqxLoginName",
    "listOfEqxDartBatchLines"
})
public class EqxDartBatch {

    @XmlElement(name = "EQXDFRID")
    protected String eqxdfrid;
    @XmlElement(name = "EQXLoginName")
    protected String eqxLoginName;
    @XmlElement(name = "ListOfEqxDartBatchLines")
    protected ListOfEqxDartBatchLines listOfEqxDartBatchLines;

    /**
     * Gets the value of the eqxdfrid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXDFRID() {
        return eqxdfrid;
    }

    /**
     * Sets the value of the eqxdfrid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXDFRID(String value) {
        this.eqxdfrid = value;
    }

    /**
     * Gets the value of the eqxLoginName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXLoginName() {
        return eqxLoginName;
    }

    /**
     * Sets the value of the eqxLoginName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXLoginName(String value) {
        this.eqxLoginName = value;
    }

    /**
     * Gets the value of the listOfEqxDartBatchLines property.
     * 
     * @return
     *     possible object is
     *     {@link ListOfEqxDartBatchLines }
     *     
     */
    public ListOfEqxDartBatchLines getListOfEqxDartBatchLines() {
        return listOfEqxDartBatchLines;
    }

    /**
     * Sets the value of the listOfEqxDartBatchLines property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListOfEqxDartBatchLines }
     *     
     */
    public void setListOfEqxDartBatchLines(ListOfEqxDartBatchLines value) {
        this.listOfEqxDartBatchLines = value;
    }

}
