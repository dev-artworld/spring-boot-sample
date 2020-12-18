
package com.siebel.eqxprocessdartbatches;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import com.siebel.xml.eqx_20dart_20batch_20io.ListOfEqxDartBatchIo;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SetMinimalReturns" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BusObjCacheSize" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MessageId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ObjectLevelTransactions" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element ref="{http://www.siebel.com/xml/EQX%20DART%20Batch%20IO}ListOfEqxDartBatchIo" minOccurs="0"/>
 *         &lt;element name="StatusObject" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "setMinimalReturns",
    "busObjCacheSize",
    "messageId",
    "objectLevelTransactions",
    "listOfEqxDartBatchIo",
    "statusObject"
})
@XmlRootElement(name = "Upsert_spcDART_spcBatch_Input")
public class UpsertSpcDARTSpcBatchInput {

    @XmlElement(name = "SetMinimalReturns")
    protected String setMinimalReturns;
    @XmlElement(name = "BusObjCacheSize")
    protected String busObjCacheSize;
    @XmlElement(name = "MessageId")
    protected String messageId;
    @XmlElement(name = "ObjectLevelTransactions")
    protected String objectLevelTransactions;
    @XmlElement(name = "ListOfEqxDartBatchIo", namespace = "http://www.siebel.com/xml/EQX%20DART%20Batch%20IO")
    protected ListOfEqxDartBatchIo listOfEqxDartBatchIo;
    @XmlElement(name = "StatusObject")
    protected String statusObject;

    /**
     * Gets the value of the setMinimalReturns property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSetMinimalReturns() {
        return setMinimalReturns;
    }

    /**
     * Sets the value of the setMinimalReturns property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSetMinimalReturns(String value) {
        this.setMinimalReturns = value;
    }

    /**
     * Gets the value of the busObjCacheSize property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusObjCacheSize() {
        return busObjCacheSize;
    }

    /**
     * Sets the value of the busObjCacheSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusObjCacheSize(String value) {
        this.busObjCacheSize = value;
    }

    /**
     * Gets the value of the messageId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * Sets the value of the messageId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageId(String value) {
        this.messageId = value;
    }

    /**
     * Gets the value of the objectLevelTransactions property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObjectLevelTransactions() {
        return objectLevelTransactions;
    }

    /**
     * Sets the value of the objectLevelTransactions property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObjectLevelTransactions(String value) {
        this.objectLevelTransactions = value;
    }

    /**
     * Gets the value of the listOfEqxDartBatchIo property.
     * 
     * @return
     *     possible object is
     *     {@link ListOfEqxDartBatchIo }
     *     
     */
    public ListOfEqxDartBatchIo getListOfEqxDartBatchIo() {
        return listOfEqxDartBatchIo;
    }

    /**
     * Sets the value of the listOfEqxDartBatchIo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListOfEqxDartBatchIo }
     *     
     */
    public void setListOfEqxDartBatchIo(ListOfEqxDartBatchIo value) {
        this.listOfEqxDartBatchIo = value;
    }

    /**
     * Gets the value of the statusObject property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusObject() {
        return statusObject;
    }

    /**
     * Sets the value of the statusObject property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusObject(String value) {
        this.statusObject = value;
    }

}
