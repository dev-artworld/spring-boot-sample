
package com.siebel.xml.eqx_20dart_20batch_20io;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListOfEqxDartBatchIoTopElmt complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ListOfEqxDartBatchIoTopElmt">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ListOfEqxDartBatchIo" type="{http://www.siebel.com/xml/EQX%20DART%20Batch%20IO}ListOfEqxDartBatchIo"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListOfEqxDartBatchIoTopElmt", propOrder = {
    "listOfEqxDartBatchIo"
})
public class ListOfEqxDartBatchIoTopElmt {

    @XmlElement(name = "ListOfEqxDartBatchIo", required = true)
    protected ListOfEqxDartBatchIo listOfEqxDartBatchIo;

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
