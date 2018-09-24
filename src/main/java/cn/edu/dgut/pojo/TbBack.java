package cn.edu.dgut.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class TbBack {
    private Integer id;

    private String backNo;

    private Integer providerId;
    
//    private String providerName;

    private Integer drugId;
    
    private String drugNo;
    
    private String drugName;

    private Integer quantity;

    private BigDecimal totalPrice;

    private String reason;

    private Date createTime;

    private Date updateTime;
    
    private TbDrug drug;
    
    private TbProvider provider;

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
        this.backNo = backNo == null ? null : backNo.trim();
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
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

	public TbDrug getDrug() {
		return drug;
	}

	public void setDrug(TbDrug drug) {
		this.drug = drug;
	}

	public String getDrugNo() {
		return drugNo;
	}

	public void setDrugNo(String drugNo) {
		this.drugNo = drugNo;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public TbProvider getProvider() {
		return provider;
	}

	public void setProvider(TbProvider provider) {
		this.provider = provider;
	}

//	public String getProviderName() {
//		return providerName;
//	}
//
//	public void setProviderName(String providerName) {
//		this.providerName = providerName;
//	}
}