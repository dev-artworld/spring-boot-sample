
package com.equinix.appops.dart.portal.model.search.product;

import java.io.Serializable;
import java.util.Base64;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "pageNumber", "keyword", "filters", "error", "snapfilter", "searchdropbox", "dfrid",
		"isErrorCodeGlobalFilterKeyword", "applications", "orderBy", "column", "header", "size", "searchValue",
		"selectedAttribute", "attributeFlag", "products", "showAll", "range", "kpiFilterType", "isErrorClassification" })
public class ProductFilter implements Serializable {
	@JsonProperty("pageNumber")
	private Long pageNumber;

	@JsonProperty("keyword")
	private String keyword;
	@JsonProperty("filters")
	private List<PFilter> filters = null;

	@JsonProperty("error")
	private List<String> error = null;
	private final static long serialVersionUID = 4398707025391144250L;
	@JsonProperty("dfrid")
	private String dfrid;

	@JsonProperty("snapfilter")
	private ProductFilter snapfilter = null;

	@JsonProperty("searchdropbox")
	private SearchDropBox searchdropbox = null;

	@JsonProperty("applications")
	private String applications;

	@JsonProperty("orderBy")
	private String orderBy;

	@JsonProperty("column")
	private String column;

	@JsonProperty("header")
	private String header;

	@JsonProperty("size")
	private String size;

	@JsonProperty("searchValue")
	private String searchValue;

	@JsonProperty("selectedAttribute")
	private List<String> selectedAttribute;

	@JsonProperty("attributeFlag")
	private String attributeFlag;

	@JsonProperty("products")
	private String products;

	@JsonProperty("showAll")
	private String showAll;

	@JsonProperty("range")
	private int range;

	@JsonProperty("kpiFilterType")
	private String kpiFilterType;

	@JsonProperty("isErrorClassification")
	private boolean isErrorClassification;

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	@JsonProperty("keyword")
	public String getKeyword() {
		if (StringUtils.isNotEmpty(this.keyword)) {
			/*if (this.keyword.contains(":")) {
				this.keyword = this.keyword;
			} else {
				this.keyword = this.keyword.toUpperCase();
			}*/
			this.keyword = this.keyword;
		}
		return keyword;
	}

	@JsonProperty("keyword")
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@JsonProperty("filters")
	public List<PFilter> getFilters() {
		return filters;
	}

	@JsonProperty("filters")
	public void setFilters(List<PFilter> filters) {
		this.filters = filters;
	}

	@JsonProperty("error")
	public List<String> getError() {
		return error;
	}

	@JsonProperty("error")
	public void setError(List<String> error) {
		this.error = error;
	}

	@JsonProperty("snapfilter")
	public ProductFilter getSnapfilter() {
		return snapfilter;
	}

	@JsonProperty("snapfilter")
	public void setSnapfilter(ProductFilter snapfilter) {
		this.snapfilter = snapfilter;
	}

	@JsonProperty("dfrid")
	public String getDfrId() {
		return dfrid;
	}

	@JsonProperty("dfrid")
	public void setDfrId(String dfrId) {
		this.dfrid = dfrId;
	}

	private String cacheKey;
	private String decodedKey;

	private long totalRows;

	public String genrateCacheKey() {
		StringBuffer sb = new StringBuffer(this.keyword);
		if (CollectionUtils.isNotEmpty(this.getFilters())) {
			for (PFilter f : this.getFilters()) {
				sb.append(f.getValue());
			}
		}
		this.decodedKey = sb.toString();
		this.cacheKey = Base64.getEncoder().encodeToString(sb.toString().getBytes());
		return cacheKey;
	}

	public String getCacheKey() {
		return this.cacheKey;
	}

	public String getDecodedKey() {
		return decodedKey;
	}

	@JsonProperty("searchdropbox")
	public SearchDropBox getSearchDropBox() {
		return searchdropbox;
	}

	@JsonProperty("searchdropbox")
	public void setSearchDropBox(SearchDropBox searchDropBox) {
		this.searchdropbox = searchDropBox;
	}

	@JsonProperty("isErrorCodeGlobalFilterKeyword")
	public boolean isErrorCodeGlobalFilterKeyword() {
		return (StringUtils.isNotEmpty(this.keyword) && this.keyword.length() == 7
				&& this.keyword.toUpperCase().startsWith("VAL"));
	}

	@JsonProperty("pageNumber")
	public Long getPageNumber() {
		return pageNumber;
	}

	@JsonProperty("pageNumber")
	public void setPageNumber(Long pageNumber) {
		this.pageNumber = pageNumber;
	}

	public long getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(long totalRows) {
		this.totalRows = totalRows;
	}

	@JsonProperty("applications")
	public String getApplications() {
		return applications;
	}

	@JsonProperty("applications")
	public void setApplications(String applications) {
		this.applications = applications;
	}

	@JsonProperty("orderBy")
	public String getOrderBy() {
		return orderBy;
	}

	@JsonProperty("orderBy")
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	@JsonProperty("column")
	public String getColumn() {
		return column;
	}

	@JsonProperty("column")
	public void setColumn(String column) {
		this.column = column;
	}

	@JsonProperty("header")
	public String getHeader() {
		return header;
	}

	@JsonProperty("header")
	public void setHeader(String header) {
		this.header = header;
	}

	@JsonProperty("size")
	public String getSize() {
		return size;
	}

	@JsonProperty("size")
	public void setSize(String size) {
		this.size = size;
	}

	@JsonProperty("searchValue")
	public String getSearchValue() {
		return searchValue;
	}

	@JsonProperty("searchValue")
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	@JsonProperty("selectedAttribute")
	public List<String> getSelectedAttribute() {
		return selectedAttribute;
	}

	@JsonProperty("selectedAttribute")
	public void setSelectedAttribute(List<String> selectedAttribute) {
		this.selectedAttribute = selectedAttribute;
	}

	@JsonProperty("attributeFlag")
	public void setAttributeFlag(String attributeFlag) {
		this.attributeFlag = attributeFlag;
	}

	@JsonProperty("attributeFlag")
	public String getAttributeFlag() {
		return attributeFlag;
	}

	@JsonProperty("products")
	public String getProducts() {
		return products;
	}

	@JsonProperty("products")
	public void setProducts(String products) {
		this.products = products;
	}

	@JsonProperty("showAll")
	public String getShowAll() {
		return showAll;
	}

	@JsonProperty("showAll")
	public void setShowAll(String showAll) {
		this.showAll = showAll;
	}

	@JsonProperty("kpiFilterType")
	public String getKpiFilterType() {
		return kpiFilterType;
	}

	@JsonProperty("kpiFilterType")
	public void setKpiFilterType(String kpiFilterType) {
		this.kpiFilterType = kpiFilterType;
	}

	public boolean isErrorClassification() {
		return isErrorClassification;
	}

	public void setErrorClassification(boolean isErrorClassification) {
		this.isErrorClassification = isErrorClassification;
	}
}
