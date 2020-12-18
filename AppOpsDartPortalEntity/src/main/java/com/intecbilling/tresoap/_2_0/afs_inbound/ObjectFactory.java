
package com.intecbilling.tresoap._2_0.afs_inbound;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.intecbilling.tresoap._2_0.afs_inbound package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {
	
	Logger logger = LoggerFactory.getLogger(ObjectFactory.class);
	
    private final static QName _ProcessOrderResponse_QNAME = new QName("http://tresoap.intecbilling.com/2.0/AFS-Inbound", "ProcessOrderResponse");
    private final static QName _ProcessOrderRequest_QNAME = new QName("http://tresoap.intecbilling.com/2.0/AFS-Inbound", "ProcessOrderRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.intecbilling.tresoap._2_0.afs_inbound
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ProcessOrderResponse }
     * 
     */
    public ProcessOrderResponse createProcessOrderResponse() {
        return new ProcessOrderResponse();
    }

    /**
     * Create an instance of {@link ProcessOrderRequest }
     * 
     */
    public ProcessOrderRequest createProcessOrderRequest() {
        return new ProcessOrderRequest();
    }

    /**
     * Create an instance of {@link ListOfOrderEntryLineItems }
     * 
     */
    public ListOfOrderEntryLineItems createListOfOrderEntryLineItems() {
        return new ListOfOrderEntryLineItems();
    }

    /**
     * Create an instance of {@link OrderEntryLineItems }
     * 
     */
    public OrderEntryLineItems createOrderEntryLineItems() {
        return new OrderEntryLineItems();
    }

    /**
     * Create an instance of {@link ListOfESKPricingTier }
     * 
     */
    public ListOfESKPricingTier createListOfESKPricingTier() {
        return new ListOfESKPricingTier();
    }

    /**
     * Create an instance of {@link ListOfEqxOrderEntryIo }
     * 
     */
    public ListOfEqxOrderEntryIo createListOfEqxOrderEntryIo() {
        return new ListOfEqxOrderEntryIo();
    }

    /**
     * Create an instance of {@link ListOfOrderItemXa }
     * 
     */
    public ListOfOrderItemXa createListOfOrderItemXa() {
        return new ListOfOrderItemXa();
    }

    /**
     * Create an instance of {@link OrderItemXa }
     * 
     */
    public OrderItemXa createOrderItemXa() {
        return new OrderItemXa();
    }

    /**
     * Create an instance of {@link ListOfOrderItemRamp }
     * 
     */
    public ListOfOrderItemRamp createListOfOrderItemRamp() {
        return new ListOfOrderItemRamp();
    }

    /**
     * Create an instance of {@link ESKPricingTier }
     * 
     */
    public ESKPricingTier createESKPricingTier() {
        return new ESKPricingTier();
    }

    /**
     * Create an instance of {@link OrderEntryOrders }
     * 
     */
    public OrderEntryOrders createOrderEntryOrders() {
        return new OrderEntryOrders();
    }

    /**
     * Create an instance of {@link OrderItemRamp }
     * 
     */
    public OrderItemRamp createOrderItemRamp() {
        return new OrderItemRamp();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProcessOrderResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tresoap.intecbilling.com/2.0/AFS-Inbound", name = "ProcessOrderResponse")
    public JAXBElement<ProcessOrderResponse> createProcessOrderResponse(ProcessOrderResponse value) {
        return new JAXBElement<ProcessOrderResponse>(_ProcessOrderResponse_QNAME, ProcessOrderResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProcessOrderRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tresoap.intecbilling.com/2.0/AFS-Inbound", name = "ProcessOrderRequest")
    public JAXBElement<ProcessOrderRequest> createProcessOrderRequest(ProcessOrderRequest value) {
    	return new JAXBElement<ProcessOrderRequest>(_ProcessOrderRequest_QNAME, ProcessOrderRequest.class, null, value);
    }

}
