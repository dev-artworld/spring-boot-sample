
package com.intecbilling.tresoap._2_0.afs_inbound;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProcessOrderRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProcessOrderRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ListOfEqxOrderEntryIo" type="{http://tresoap.intecbilling.com/2.0/AFS-Inbound}ListOfEqxOrderEntryIo"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProcessOrderRequest", propOrder = {
    "listOfEqxOrderEntryIo"
})
public class ProcessOrderRequest {

    @XmlElement(name = "ListOfEqxOrderEntryIo", required = true)
    protected ListOfEqxOrderEntryIo listOfEqxOrderEntryIo;

    /**
     * Gets the value of the listOfEqxOrderEntryIo property.
     * 
     * @return
     *     possible object is
     *     {@link ListOfEqxOrderEntryIo }
     *     
     */
    public ListOfEqxOrderEntryIo getListOfEqxOrderEntryIo() {
        return listOfEqxOrderEntryIo;
    }

    /**
     * Sets the value of the listOfEqxOrderEntryIo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListOfEqxOrderEntryIo }
     *     
     */
    public void setListOfEqxOrderEntryIo(ListOfEqxOrderEntryIo value) {
        this.listOfEqxOrderEntryIo = value;
    }

}
