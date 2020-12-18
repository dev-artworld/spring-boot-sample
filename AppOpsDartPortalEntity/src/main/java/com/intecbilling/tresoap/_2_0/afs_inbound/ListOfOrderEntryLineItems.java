
package com.intecbilling.tresoap._2_0.afs_inbound;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListOfOrderEntry-LineItems complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ListOfOrderEntry-LineItems">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OrderEntry-LineItems" type="{http://tresoap.intecbilling.com/2.0/AFS-Inbound}OrderEntry-LineItems" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListOfOrderEntry-LineItems", propOrder = {
    "orderEntryLineItems"
})
public class ListOfOrderEntryLineItems {

    @XmlElement(name = "OrderEntry-LineItems")
    protected List<OrderEntryLineItems> orderEntryLineItems;

    /**
     * Gets the value of the orderEntryLineItems property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the orderEntryLineItems property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrderEntryLineItems().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrderEntryLineItems }
     * 
     * 
     */
    public List<OrderEntryLineItems> getOrderEntryLineItems() {
        if (orderEntryLineItems == null) {
            orderEntryLineItems = new ArrayList<OrderEntryLineItems>();
        }
        return this.orderEntryLineItems;
    }

}
