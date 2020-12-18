
package com.intecbilling.tresoap._2_0.afs_inbound;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListOfOrderItemRamp complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ListOfOrderItemRamp">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OrderItemRamp" type="{http://tresoap.intecbilling.com/2.0/AFS-Inbound}OrderItemRamp" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListOfOrderItemRamp", propOrder = {
    "orderItemRamp"
})
public class ListOfOrderItemRamp {

    @XmlElement(name = "OrderItemRamp")
    protected List<OrderItemRamp> orderItemRamp;

    /**
     * Gets the value of the orderItemRamp property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the orderItemRamp property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrderItemRamp().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrderItemRamp }
     * 
     * 
     */
    public List<OrderItemRamp> getOrderItemRamp() {
        if (orderItemRamp == null) {
            orderItemRamp = new ArrayList<OrderItemRamp>();
        }
        return this.orderItemRamp;
    }

}
