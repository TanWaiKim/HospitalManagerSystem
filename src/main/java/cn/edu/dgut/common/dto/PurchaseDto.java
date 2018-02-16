package cn.edu.dgut.common.dto;

import java.math.BigDecimal;

/**
 * @author TanWaiKim
 * @time 2018年2月2日 下午9:11:28
 * @version 1.0
 */
public class PurchaseDto {
	private Integer id;
	
	private String purchaseNo;
	
    private String warehouseNo;

    private Integer providerId;
    
    private String operator;

    private String remarks;
    
    private Integer drugId;

    private String drugName;
    
    private BigDecimal purchasePrice;

    private BigDecimal salePrice;
    
    private Integer quantity;
    
    private Integer totalQuantity;
    
    private Integer oldPurchaseItemQuantity;
    
    private BigDecimal totalPrice;
    
    private BigDecimal oldTotalQuantity;
    
    private Integer purchaseId;
    
	public String getWarehouseNo() {
		return warehouseNo;
	}

	public void setWarehouseNo(String warehouseNo) {
		this.warehouseNo = warehouseNo;
	}

	public Integer getProviderId() {
		return providerId;
	}

	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
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

	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getPurchaseNo() {
		return purchaseNo;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}

	public Integer getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOldPurchaseItemQuantity() {
		return oldPurchaseItemQuantity;
	}

	public void setOldPurchaseItemQuantity(Integer oldPurchaseItemQuantity) {
		this.oldPurchaseItemQuantity = oldPurchaseItemQuantity;
	}

	public BigDecimal getOldTotalQuantity() {
		return oldTotalQuantity;
	}

	public void setOldTotalQuantity(BigDecimal oldTotalQuantity) {
		this.oldTotalQuantity = oldTotalQuantity;
	}

	public Integer getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(Integer purchaseId) {
		this.purchaseId = purchaseId;
	}
    
    
}
