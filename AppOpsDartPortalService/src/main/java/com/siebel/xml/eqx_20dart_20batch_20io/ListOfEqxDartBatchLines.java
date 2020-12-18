
package com.siebel.xml.eqx_20dart_20batch_20io;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListOfEqxDartBatchLines complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ListOfEqxDartBatchLines">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EqxDartBatchLines" type="{http://www.siebel.com/xml/EQX%20DART%20Batch%20IO}EqxDartBatchLines" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListOfEqxDartBatchLines", propOrder = {
    "eqxDartBatchLines"
})
public class ListOfEqxDartBatchLines {

    @XmlElement(name = "EqxDartBatchLines")
    protected List<EqxDartBatchLines> eqxDartBatchLines;

    /**
     * Gets the value of the eqxDartBatchLines property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the eqxDartBatchLines property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEqxDartBatchLines().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EqxDartBatchLines }
     * 
     * 
     */
    public List<EqxDartBatchLines> getEqxDartBatchLines() {
        if (eqxDartBatchLines == null) {
            eqxDartBatchLines = new ArrayList<EqxDartBatchLines>();
        }
        return this.eqxDartBatchLines;
    }

}
