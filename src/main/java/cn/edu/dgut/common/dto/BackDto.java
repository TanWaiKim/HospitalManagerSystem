package cn.edu.dgut.common.dto;

import java.math.BigDecimal;

/**
 * @author TanWaiKim
 * @time 2018年5月11日 上午1:12:05
 * @version 1.0
 */
public class BackDto {
	private Integer id;
	
	private String backNo;
	
	private Integer providerId;
	
	private String providerName;
	
	private String reason;
	
	private Integer drugId;
	
	private String drugName;
	
	private BigDecimal purchasePrice;
	
	private Integer quantity;
	
	private String batchNo;
	
    private Integer totalQuantity;
    
    private Integer oldBackItemQuantity;
    
    private BigDecimal totalPrice;
    
    private BigDecimal oldTotalQuantity;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBackNo() {
		return backNo;
	}

	public void setBackNo(String backNo) {
		this.backNo = backNo;
	}

	public Integer getProviderId() {
		return providerId;
	}

	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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

	public Integer getOldBackItemQuantity() {
		return oldBackItemQuantity;
	}

	public void setOldBackItemQuantity(Integer oldBackItemQuantity) {
		this.oldBackItemQuantity = oldBackItemQuantity;
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
}
