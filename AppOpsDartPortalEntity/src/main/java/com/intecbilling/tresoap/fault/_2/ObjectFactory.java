
package com.intecbilling.tresoap.fault._2;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.intecbilling.tresoap.fault._2 package. 
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

    private final static QName _ConnectionFault_QNAME = new QName("http://tresoap.intecbilling.com/fault/2.0", "ConnectionFault");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.intecbilling.tresoap.fault._2
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ConnectionFaultDetail }
     * 
     */
    public ConnectionFaultDetail createConnectionFaultDetail() {
        return new ConnectionFaultDetail();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConnectionFaultDetail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tresoap.intecbilling.com/fault/2.0", name = "ConnectionFault")
    public JAXBElement<ConnectionFaultDetail> createConnectionFault(ConnectionFaultDetail value) {
        return new JAXBElement<ConnectionFaultDetail>(_ConnectionFault_QNAME, ConnectionFaultDetail.class, null, value);
    }

}
