
package com.siebel.xml.eqx_20dart_20batch_20io;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EqxDartBatchLines complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EqxDartBatchLines">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EQXCustUCMId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXDFRItemId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PricingChangeFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXGenAttrib10" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXGenAttrib2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXGenAttrib3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXGenAttrib4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DFRType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXGenAttrib6" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXGenAttrib7" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXGenAttrib8" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXGenAttrib9" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXGroupNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXOperation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXPOEName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXPOFAssetNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXPOFName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXUnqSpaceIdVal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXPOEAssetNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXIBX" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXCageUniqueSpaceId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EQXLineSequenceNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ListOfEqxDartBatchLineXa" type="{http://www.siebel.com/xml/EQX%20DART%20Batch%20IO}ListOfEqxDartBatchLineXa" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EqxDartBatchLines", propOrder = {
    "eqxCustUCMId",
    "eqxdfrItemId",
    "pricingChangeFlag",
    "eqxGenAttrib10",
    "eqxGenAttrib2",
    "eqxGenAttrib3",
    "eqxGenAttrib4",
    "dfrType",
    "eqxGenAttrib6",
    "eqxGenAttrib7",
    "eqxGenAttrib8",
    "eqxGenAttrib9",
    "eqxGroupNum",
    "eqxOperation",
    "eqxpoeName",
    "eqxpofAssetNum",
    "eqxpofName",
    "eqxUnqSpaceIdVal",
    "eqxpoeAssetNum",
    "eqxibx",
    "eqxCageUniqueSpaceId",
    "eqxLineSequenceNumber",
    "listOfEqxDartBatchLineXa"
})
public class EqxDartBatchLines {

    @XmlElement(name = "EQXCustUCMId")
    protected String eqxCustUCMId;
    @XmlElement(name = "EQXDFRItemId")
    protected String eqxdfrItemId;
    @XmlElement(name = "PricingChangeFlag")
    protected String pricingChangeFlag;
    @XmlElement(name = "EQXGenAttrib10")
    protected String eqxGenAttrib10;
    @XmlElement(name = "EQXGenAttrib2")
    protected String eqxGenAttrib2;
    @XmlElement(name = "EQXGenAttrib3")
    protected String eqxGenAttrib3;
    @XmlElement(name = "EQXGenAttrib4")
    protected String eqxGenAttrib4;
    @XmlElement(name = "DFRType")
    protected String dfrType;
    @XmlElement(name = "EQXGenAttrib6")
    protected String eqxGenAttrib6;
    @XmlElement(name = "EQXGenAttrib7")
    protected String eqxGenAttrib7;
    @XmlElement(name = "EQXGenAttrib8")
    protected String eqxGenAttrib8;
    @XmlElement(name = "EQXGenAttrib9")
    protected String eqxGenAttrib9;
    @XmlElement(name = "EQXGroupNum")
    protected String eqxGroupNum;
    @XmlElement(name = "EQXOperation")
    protected String eqxOperation;
    @XmlElement(name = "EQXPOEName")
    protected String eqxpoeName;
    @XmlElement(name = "EQXPOFAssetNum")
    protected String eqxpofAssetNum;
    @XmlElement(name = "EQXPOFName")
    protected String eqxpofName;
    @XmlElement(name = "EQXUnqSpaceIdVal")
    protected String eqxUnqSpaceIdVal;
    @XmlElement(name = "EQXPOEAssetNum")
    protected String eqxpoeAssetNum;
    @XmlElement(name = "EQXIBX")
    protected String eqxibx;
    @XmlElement(name = "EQXCageUniqueSpaceId")
    protected String eqxCageUniqueSpaceId;
    @XmlElement(name = "EQXLineSequenceNumber")
    protected String eqxLineSequenceNumber;
    @XmlElement(name = "ListOfEqxDartBatchLineXa")
    protected ListOfEqxDartBatchLineXa listOfEqxDartBatchLineXa;

    /**
     * Gets the value of the eqxCustUCMId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXCustUCMId() {
        return eqxCustUCMId;
    }

    /**
     * Sets the value of the eqxCustUCMId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXCustUCMId(String value) {
        this.eqxCustUCMId = value;
    }

    /**
     * Gets the value of the eqxdfrItemId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXDFRItemId() {
        return eqxdfrItemId;
    }

    /**
     * Sets the value of the eqxdfrItemId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXDFRItemId(String value) {
        this.eqxdfrItemId = value;
    }

    /**
     * Gets the value of the pricingChangeFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPricingChangeFlag() {
        return pricingChangeFlag;
    }

    /**
     * Sets the value of the pricingChangeFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPricingChangeFlag(String value) {
        this.pricingChangeFlag = value;
    }

    /**
     * Gets the value of the eqxGenAttrib10 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXGenAttrib10() {
        return eqxGenAttrib10;
    }

    /**
     * Sets the value of the eqxGenAttrib10 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXGenAttrib10(String value) {
        this.eqxGenAttrib10 = value;
    }

    /**
     * Gets the value of the eqxGenAttrib2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXGenAttrib2() {
        return eqxGenAttrib2;
    }

    /**
     * Sets the value of the eqxGenAttrib2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXGenAttrib2(String value) {
        this.eqxGenAttrib2 = value;
    }

    /**
     * Gets the value of the eqxGenAttrib3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXGenAttrib3() {
        return eqxGenAttrib3;
    }

    /**
     * Sets the value of the eqxGenAttrib3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXGenAttrib3(String value) {
        this.eqxGenAttrib3 = value;
    }

    /**
     * Gets the value of the eqxGenAttrib4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXGenAttrib4() {
        return eqxGenAttrib4;
    }

    /**
     * Sets the value of the eqxGenAttrib4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXGenAttrib4(String value) {
        this.eqxGenAttrib4 = value;
    }

    /**
     * Gets the value of the dfrType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDFRType() {
        return dfrType;
    }

    /**
     * Sets the value of the dfrType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDFRType(String value) {
        this.dfrType = value;
    }

    /**
     * Gets the value of the eqxGenAttrib6 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXGenAttrib6() {
        return eqxGenAttrib6;
    }

    /**
     * Sets the value of the eqxGenAttrib6 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXGenAttrib6(String value) {
        this.eqxGenAttrib6 = value;
    }

    /**
     * Gets the value of the eqxGenAttrib7 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXGenAttrib7() {
        return eqxGenAttrib7;
    }

    /**
     * Sets the value of the eqxGenAttrib7 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXGenAttrib7(String value) {
        this.eqxGenAttrib7 = value;
    }

    /**
     * Gets the value of the eqxGenAttrib8 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXGenAttrib8() {
        return eqxGenAttrib8;
    }

    /**
     * Sets the value of the eqxGenAttrib8 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXGenAttrib8(String value) {
        this.eqxGenAttrib8 = value;
    }

    /**
     * Gets the value of the eqxGenAttrib9 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXGenAttrib9() {
        return eqxGenAttrib9;
    }

    /**
     * Sets the value of the eqxGenAttrib9 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXGenAttrib9(String value) {
        this.eqxGenAttrib9 = value;
    }

    /**
     * Gets the value of the eqxGroupNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXGroupNum() {
        return eqxGroupNum;
    }

    /**
     * Sets the value of the eqxGroupNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXGroupNum(String value) {
        this.eqxGroupNum = value;
    }

    /**
     * Gets the value of the eqxOperation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXOperation() {
        return eqxOperation;
    }

    /**
     * Sets the value of the eqxOperation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXOperation(String value) {
        this.eqxOperation = value;
    }

    /**
     * Gets the value of the eqxpoeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXPOEName() {
        return eqxpoeName;
    }

    /**
     * Sets the value of the eqxpoeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXPOEName(String value) {
        this.eqxpoeName = value;
    }

    /**
     * Gets the value of the eqxpofAssetNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXPOFAssetNum() {
        return eqxpofAssetNum;
    }

    /**
     * Sets the value of the eqxpofAssetNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXPOFAssetNum(String value) {
        this.eqxpofAssetNum = value;
    }

    /**
     * Gets the value of the eqxpofName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXPOFName() {
        return eqxpofName;
    }

    /**
     * Sets the value of the eqxpofName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXPOFName(String value) {
        this.eqxpofName = value;
    }

    /**
     * Gets the value of the eqxUnqSpaceIdVal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXUnqSpaceIdVal() {
        return eqxUnqSpaceIdVal;
    }

    /**
     * Sets the value of the eqxUnqSpaceIdVal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXUnqSpaceIdVal(String value) {
        this.eqxUnqSpaceIdVal = value;
    }

    /**
     * Gets the value of the eqxpoeAssetNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXPOEAssetNum() {
        return eqxpoeAssetNum;
    }

    /**
     * Sets the value of the eqxpoeAssetNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXPOEAssetNum(String value) {
        this.eqxpoeAssetNum = value;
    }

    /**
     * Gets the value of the eqxibx property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXIBX() {
        return eqxibx;
    }

    /**
     * Sets the value of the eqxibx property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXIBX(String value) {
        this.eqxibx = value;
    }

    /**
     * Gets the value of the eqxCageUniqueSpaceId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXCageUniqueSpaceId() {
        return eqxCageUniqueSpaceId;
    }

    /**
     * Sets the value of the eqxCageUniqueSpaceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXCageUniqueSpaceId(String value) {
        this.eqxCageUniqueSpaceId = value;
    }

    /**
     * Gets the value of the eqxLineSequenceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEQXLineSequenceNumber() {
        return eqxLineSequenceNumber;
    }

    /**
     * Sets the value of the eqxLineSequenceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEQXLineSequenceNumber(String value) {
        this.eqxLineSequenceNumber = value;
    }

    /**
     * Gets the value of the listOfEqxDartBatchLineXa property.
     * 
     * @return
     *     possible object is
     *     {@link ListOfEqxDartBatchLineXa }
     *     
     */
    public ListOfEqxDartBatchLineXa getListOfEqxDartBatchLineXa() {
        return listOfEqxDartBatchLineXa;
    }

    /**
     * Sets the value of the listOfEqxDartBatchLineXa property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListOfEqxDartBatchLineXa }
     *     
     */
    public void setListOfEqxDartBatchLineXa(ListOfEqxDartBatchLineXa value) {
        this.listOfEqxDartBatchLineXa = value;
    }

}
