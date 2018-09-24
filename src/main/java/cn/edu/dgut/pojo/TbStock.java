package cn.edu.dgut.pojo;

import java.util.Date;

public class TbStock {
    private Integer id;

    private String warehouseNo;

    private Integer drugId;

    private String drugname;

    private Integer stockQuantity;

    private Integer minQuantity;

    private Integer maxQuantity;

    private Date createTime;

    private Date updateTime;
    
    private TbWarehouse warehouse;
    
    private TbDrug drug;
    
    private String quantityWaring;

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
        this.warehouseNo = warehouseNo == null ? null : warehouseNo.trim();
    }

    public Integer getDrugId() {
        return drugId;
    }

    public void setDrugId(Integer drugId) {
        this.drugId = drugId;
    }

    public String getDrugname() {
        return drugname;
    }

    public void setDrugname(String drugname) {
        this.drugname = drugname == null ? null : drugname.trim();
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
}