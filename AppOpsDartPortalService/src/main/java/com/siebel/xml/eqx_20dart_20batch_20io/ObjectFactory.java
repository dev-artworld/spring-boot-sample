
package com.siebel.xml.eqx_20dart_20batch_20io;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.siebel.xml.eqx_20dart_20batch_20io package. 
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

    private final static QName _ListOfEqxDartBatchIo_QNAME = new QName("http://www.siebel.com/xml/EQX%20DART%20Batch%20IO", "ListOfEqxDartBatchIo");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.siebel.xml.eqx_20dart_20batch_20io
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ListOfEqxDartBatchIo }
     * 
     */
    public ListOfEqxDartBatchIo createListOfEqxDartBatchIo() {
        return new ListOfEqxDartBatchIo();
    }

    /**
     * Create an instance of {@link ListOfEqxDartBatchIoTopElmt }
     * 
     */
    public ListOfEqxDartBatchIoTopElmt createListOfEqxDartBatchIoTopElmt() {
        return new ListOfEqxDartBatchIoTopElmt();
    }

    /**
     * Create an instance of {@link ListOfEqxDartBatchLines }
     * 
     */
    public ListOfEqxDartBatchLines createListOfEqxDartBatchLines() {
        return new ListOfEqxDartBatchLines();
    }

    /**
     * Create an instance of {@link EqxDartBatch }
     * 
     */
    public EqxDartBatch createEqxDartBatch() {
        return new EqxDartBatch();
    }

    /**
     * Create an instance of {@link EqxDartBatchLineXa }
     * 
     */
    public EqxDartBatchLineXa createEqxDartBatchLineXa() {
        return new EqxDartBatchLineXa();
    }

    /**
     * Create an instance of {@link EqxDartBatchLines }
     * 
     */
    public EqxDartBatchLines createEqxDartBatchLines() {
        return new EqxDartBatchLines();
    }

    /**
     * Create an instance of {@link ListOfEqxDartBatchLineXa }
     * 
     */
    public ListOfEqxDartBatchLineXa createListOfEqxDartBatchLineXa() {
        return new ListOfEqxDartBatchLineXa();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListOfEqxDartBatchIo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.siebel.com/xml/EQX%20DART%20Batch%20IO", name = "ListOfEqxDartBatchIo")
    public JAXBElement<ListOfEqxDartBatchIo> createListOfEqxDartBatchIo(ListOfEqxDartBatchIo value) {
        return new JAXBElement<ListOfEqxDartBatchIo>(_ListOfEqxDartBatchIo_QNAME, ListOfEqxDartBatchIo.class, null, value);
    }

}
