package cn.edu.dgut.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class TbPurchase {
    private Integer id;

    private String purchaseNo;

    private Integer providerId;

    private Integer drugId;

    private Integer quantity;

    private BigDecimal totalPrice;

    private Integer isStock;

    private Integer drugAdminId;

    private String remark;

    private Date createTime;

    private Date updateTime;
    
    private TbProvider provider;
    
    private TbDrugAdmin drugAdmin;
    
    private TbDrug drug;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPurchaseNo() {
        return purchaseNo;
    }

    public void setPurchaseNo(String purchaseNo) {
        this.purchaseNo = purchaseNo == null ? null : purchaseNo.trim();
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public Integer getDrugId() {
        return drugId;
    }

    public void setDrugId(Integer drugId) {
        this.drugId = drugId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getIsStock() {
        return isStock;
    }

    public void setIsStock(Integer isStock) {
        this.isStock = isStock;
    }

    public Integer getDrugAdminId() {
        return drugAdminId;
    }

    public void setDrugAdminId(Integer drugAdminId) {
        this.drugAdminId = drugAdminId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public TbProvider getProvider() {
		return provider;
	}

	public void setProvider(TbProvider provider) {
		this.provider = provider;
	}

	public TbDrugAdmin getDrugAdmin() {
		return drugAdmin;
	}

	public void setDrugAdmin(TbDrugAdmin drugAdmin) {
		this.drugAdmin = drugAdmin;
	}

	public TbDrug getDrug() {
		return drug;
	}

	public void setDrug(TbDrug drug) {
		this.drug = drug;
	}
}