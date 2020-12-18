
package com.intecbilling.tresoap._2_0.afs_inbound;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListOfEqxOrderEntryIo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ListOfEqxOrderEntryIo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OrderEntry-Orders" type="{http://tresoap.intecbilling.com/2.0/AFS-Inbound}OrderEntry-Orders"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListOfEqxOrderEntryIo", propOrder = {
    "orderEntryOrders"
})
public class ListOfEqxOrderEntryIo {

    @XmlElement(name = "OrderEntry-Orders", required = true)
    protected OrderEntryOrders orderEntryOrders;

    /**
     * Gets the value of the orderEntryOrders property.
     * 
     * @return
     *     possible object is
     *     {@link OrderEntryOrders }
     *     
     */
    public OrderEntryOrders getOrderEntryOrders() {
        return orderEntryOrders;
    }

    /**
     * Sets the value of the orderEntryOrders property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderEntryOrders }
     *     
     */
    public void setOrderEntryOrders(OrderEntryOrders value) {
        this.orderEntryOrders = value;
    }

}
