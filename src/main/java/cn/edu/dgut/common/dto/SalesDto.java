package cn.edu.dgut.common.dto;

import java.math.BigDecimal;
import java.util.List;

import cn.edu.dgut.pojo.DrugData;

/**
 * @author TanWaiKim
 * @time 2018年2月24日 下午9:07:06
 * @version 1.0
 */
public class SalesDto {
	private Integer id;
	
	private String salesNo;
	
	private String patientId;
	
	private String patientName;
	
	private String operator;
	
	private String remarks;
	
	private Integer drugId;
	
	private String drugName;
	
	private BigDecimal salePrice;
	
	private Integer quantity;
	
	private String batchNo;
	
    private Integer totalQuantity;
    
    private Integer oldSalesItemQuantity;
    
    private BigDecimal totalPrice;
    
    private BigDecimal oldTotalQuantity;
    
    List<DrugData> drugDataList;

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getDrugId() {
		return drugId;
	}

	public void setDrugId(Integer drugId) {
		this.drugId = drugId;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getSalesNo() {
		return salesNo;
	}

	public void setSalesNo(String salesNo) {
		this.salesNo = salesNo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public Integer getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public Integer getOldSalesItemQuantity() {
		return oldSalesItemQuantity;
	}

	public void setOldSalesItemQuantity(Integer oldSalesItemQuantity) {
		this.oldSalesItemQuantity = oldSalesItemQuantity;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public BigDecimal getOldTotalQuantity() {
		return oldTotalQuantity;
	}

	public void setOldTotalQuantity(BigDecimal oldTotalQuantity) {
		this.oldTotalQuantity = oldTotalQuantity;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public List<DrugData> getDrugDataList() {
		return drugDataList;
	}

	public void setDrugDataList(List<DrugData> drugDataList) {
		this.drugDataList = drugDataList;
	}
	
	
	
	
	
}
