
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
 *         &lt;element name="PrimaryRowId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element ref="{http://www.siebel.com/xml/EQX%20DART%20Batch%20IO}ListOfEqxDartBatchIo" minOccurs="0"/>
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
    "primaryRowId",
    "listOfEqxDartBatchIo"
})
@XmlRootElement(name = "Upsert_spcDART_spcBatch_Output")
public class UpsertSpcDARTSpcBatchOutput {

    @XmlElement(name = "PrimaryRowId")
    protected String primaryRowId;
    @XmlElement(name = "ListOfEqxDartBatchIo", namespace = "http://www.siebel.com/xml/EQX%20DART%20Batch%20IO")
    protected ListOfEqxDartBatchIo listOfEqxDartBatchIo;

    /**
     * Gets the value of the primaryRowId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrimaryRowId() {
        return primaryRowId;
    }

    /**
     * Sets the value of the primaryRowId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrimaryRowId(String value) {
        this.primaryRowId = value;
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

}
