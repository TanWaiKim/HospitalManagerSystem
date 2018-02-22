package cn.edu.dgut.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class TbDrug {
    private Integer id;

    private Integer drugtypeId;

    private String drugName;

    private String drugNo;

    private String purpose;

    private String spec;

    private String unit;

    private String howuse;

//    private String producedTime;
//
//    private String validTime;

    private Date createTime;

    private Date updateTime;
    
    private TbDrugtype drugtype;
    
//    private BigDecimal purchasePrice;

    private BigDecimal salePrice;
    
    private String remark;
    
    private String uneffect;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDrugtypeId() {
        return drugtypeId;
    }

    public void setDrugtypeId(Integer drugtypeId) {
        this.drugtypeId = drugtypeId;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName == null ? null : drugName.trim();
    }

    public String getDrugNo() {
        return drugNo;
    }

    public void setDrugNo(String drugNo) {
        this.drugNo = drugNo;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose == null ? null : purpose.trim();
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getHowuse() {
        return howuse;
    }

    public void setHowuse(String howuse) {
        this.howuse = howuse == null ? null : howuse.trim();
    }

//    public String getProducedTime() {
//        return producedTime;
//    }
//
//    public void setProducedTime(String producedTime) {
//        this.producedTime = producedTime;
//    }
//
//    public String getValidTime() {
//        return validTime;
//    }
//
//    public void setValidTime(String validTime) {
//        this.validTime = validTime;
//    }

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

	public TbDrugtype getDrugtype() {
		return drugtype;
	}

	public void setDrugtype(TbDrugtype drugtype) {
		this.drugtype = drugtype;
	}

//	public BigDecimal getPurchasePrice() {
//		return purchasePrice;
//	}
//
//	public void setPurchasePrice(BigDecimal purchasePrice) {
//		this.purchasePrice = purchasePrice;
//	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUneffect() {
		return uneffect;
	}

	public void setUneffect(String uneffect) {
		this.uneffect = uneffect;
	}

}