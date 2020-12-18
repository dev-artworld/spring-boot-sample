package com.equinix.appops.dart.portal.common;

import java.util.ArrayList;

public class ResponseDTO<V> {

	String msg;
	
	Status status;
	
	V data;
	
	Integer totalRecords=new Integer(0);
	
	Integer records=new Integer(0);
	
	MetaData _meta;
	
	String uuid;
	
	public ResponseDTO(V data)
	{
		this.data=data;
	}
	
	public ResponseDTO()
	{
	}
	
	public ResponseDTO(String msg,Status status,V data)
	{
		this.msg=msg;
		this.status=status;
		this.data=data;
		if(data != null)
		{
			this.totalRecords=1;
			this.records=1;
		}
	}
	
	public ResponseDTO(String msg,V data)
	{
		this.msg=msg;
		this.status=Status.PASS;
		this.data=data;
		if(data != null)
		{
			if(this.data instanceof ArrayList ){
				this.totalRecords=((ArrayList) this.data).size();
			} else {
				this.totalRecords = 1;
			}
			this.records=1;
		}
	}
	
	public ResponseDTO(String msg)
	{
		this.msg=msg;
		this.status=Status.PASS;
	}
	
	public ResponseDTO(String msg,V data,Integer totalRecords,Integer records)
	{
		this.msg=msg;
		this.status=Status.PASS;
		this.data=data;
		this.totalRecords=totalRecords;
		this.records=records;
	}
	
	public ResponseDTO(String msg,V data,Integer totalRecords,Integer records,Integer pageNumber,MetaData metaData)
	{
		this.msg=msg;
		this.status=Status.PASS;
		this.data=data;
		this._meta = metaData;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public V getData() {
		return data;
	}

	public void setData(V data) {
		this.data = data;
	}

	public Integer getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}

	public Integer getRecords() {
		return records;
	}

	public void setRecords(Integer records) {
		this.records = records;
	}

	public MetaData get_meta() {
		return _meta;
	}

	public void set_meta(MetaData _meta) {
		this._meta = _meta;
	}
	
	
	
	/*public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}*/

	public ResponseDTO(String msg,Status status,String uuid)
	{
		this.msg=msg;
		this.status=status;
		//this.uuid=uuid;
	}
	
	
	
}