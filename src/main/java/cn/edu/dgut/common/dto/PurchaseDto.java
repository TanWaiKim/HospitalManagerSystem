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

    private Integer providerId;

    private String remark;
    
    private Integer drugId;

    private String drugName;
    
    private BigDecimal purchasePrice;

    private BigDecimal salePrice;
    
    private Integer quantity;
    
    private String produceTime;
    
    private String validTime;
    
    private String drugNo;
    
    private Integer drugAdminId;
    
    private Integer isStock;
    

	public Integer getProviderId() {
		return providerId;
	}

	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProduceTime() {
		return produceTime;
	}

	public void setProduceTime(String produceTime) {
		this.produceTime = produceTime;
	}

	public String getValidTime() {
		return validTime;
	}

	public void setValidTime(String validTime) {
		this.validTime = validTime;
	}

	public String getDrugNo() {
		return drugNo;
	}

	public void setDrugNo(String drugNo) {
		this.drugNo = drugNo;
	}

	public Integer getDrugAdminId() {
		return drugAdminId;
	}

	public void setDrugAdminId(Integer drugAdminId) {
		this.drugAdminId = drugAdminId;
	}

	public Integer getIsStock() {
		return isStock;
	}

	public void setIsStock(Integer isStock) {
		this.isStock = isStock;
	}
    
    
}
