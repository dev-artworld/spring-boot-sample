package com.equinix.appops.dart.portal.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the DART_APP_CONFIG database table.
 * 
 */
@Entity
@Table(name="DART_APP_CONFIG", schema="EQX_DART")
@NamedQuery(name="Config.findAll", query="SELECT d FROM Config d")
public class Config implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CONFIG_KEY")
	private String key;

	@Column(name="CONFIG_VALUE")
	private String value;

	public Config() {
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	

}