package cn.edu.dgut.pojo;

import java.util.Date;

public class TbStock {
    private Integer id;

    private String warehouseNo;

    private Integer drugId;

    private Integer stockQuantity;

    private Integer minQuantity;

    private Integer maxQuantity;

    private String operator;

    private Date createTime;

    private Date updateTime;
    
    private TbWarehouse warehouse;
    
    private TbDrug drug;
    
    private String quantityWaring;
    
    private String validWaring;
    
    private String batchNo;
    
    private TbPurchaseItem purchaseItem;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWarehouseNo() {
        return warehouseNo;
    }

    public void setWarehouseNo(String warehouseNo) {
        this.warehouseNo = warehouseNo;
    }

    public Integer getDrugId() {
        return drugId;
    }

    public void setDrugId(Integer drugId) {
        this.drugId = drugId;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Integer getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(Integer minQuantity) {
        this.minQuantity = minQuantity;
    }

    public Integer getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(Integer maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
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

	public TbWarehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(TbWarehouse warehouse) {
		this.warehouse = warehouse;
	}

	public TbDrug getDrug() {
		return drug;
	}

	public void setDrug(TbDrug drug) {
		this.drug = drug;
	}

	public String getQuantityWaring() {
		return quantityWaring;
	}

	public void setQuantityWaring(String quantityWaring) {
		this.quantityWaring = quantityWaring;
	}

	public String getValidWaring() {
		return validWaring;
	}

	public void setValidWaring(String validWaring) {
		this.validWaring = validWaring;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public TbPurchaseItem getPurchaseItem() {
		return purchaseItem;
	}

	public void setPurchaseItem(TbPurchaseItem purchaseItem) {
		this.purchaseItem = purchaseItem;
	}
}