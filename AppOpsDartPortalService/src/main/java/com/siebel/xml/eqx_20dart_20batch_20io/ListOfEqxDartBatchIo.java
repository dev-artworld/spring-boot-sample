
package com.siebel.xml.eqx_20dart_20batch_20io;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListOfEqxDartBatchIo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ListOfEqxDartBatchIo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EqxDartBatch" type="{http://www.siebel.com/xml/EQX%20DART%20Batch%20IO}EqxDartBatch" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListOfEqxDartBatchIo", propOrder = {
    "eqxDartBatch"
})
public class ListOfEqxDartBatchIo {

    @XmlElement(name = "EqxDartBatch")
    protected List<EqxDartBatch> eqxDartBatch;

    /**
     * Gets the value of the eqxDartBatch property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the eqxDartBatch property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEqxDartBatch().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EqxDartBatch }
     * 
     * 
     */
    public List<EqxDartBatch> getEqxDartBatch() {
        if (eqxDartBatch == null) {
            eqxDartBatch = new ArrayList<EqxDartBatch>();
        }
        return this.eqxDartBatch;
    }

}
