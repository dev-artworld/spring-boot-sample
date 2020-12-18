package com.equinix.appops.dart.portal.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the XML_CONFIG database table.
 * 
 */
@Entity
@Table(name="XML_CONFIG",schema="EQX_DART")
@NamedQuery(name="XmlConfig.findAll", query="SELECT x FROM XmlConfig x")
public class XmlConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="XML_NAME")
	private String xmlName;

	@Lob
	@Column(name="XML_DATA")
	private String xmlData;

	@Column(name="XML_GROUP")
	private String xmlGroup;

	public XmlConfig() {
	}

	public String getXmlName() {
		return this.xmlName;
	}

	public void setXmlName(String xmlName) {
		this.xmlName = xmlName;
	}

	public String getXmlData() {
		return this.xmlData;
	}

	public void setXmlData(String xmlData) {
		this.xmlData = xmlData;
	}

	public String getXmlGroup() {
		return this.xmlGroup;
	}

	public void setXmlGroup(String xmlGroup) {
		this.xmlGroup = xmlGroup;
	}

}