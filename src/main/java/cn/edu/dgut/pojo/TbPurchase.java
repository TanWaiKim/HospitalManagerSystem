package cn.edu.dgut.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class TbPurchase {
    private Integer id;

    private String purchaseNo;

    private String warehouseNo;

    private Integer providerId;

    private Integer totalQuantity;

    private BigDecimal totalPrice;

    private String operator;

    private String remarks;

    private Date createTime;

    private Date updateTime;
    
    private TbProvider provider;
    
    private TbWarehouse warehouse;

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
        this.purchaseNo = purchaseNo;
    }

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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
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

	public TbWarehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(TbWarehouse warehouse) {
		this.warehouse = warehouse;
	}
}