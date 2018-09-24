package cn.edu.dgut.common.dto;

/**
 * @author TanWaiKim
 * @time 2018年5月1日 下午7:10:56
 * @version 1.0
 */
public class StockDto {
	private Integer isStock;
	private Integer drugId;
	private Integer quantity;
	private String warehouseNo;
	private String purchaseNo;
	public Integer getIsStock() {
		return isStock;
	}
	public void setIsStock(Integer isStock) {
		this.isStock = isStock;
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
	public String getWarehouseNo() {
		return warehouseNo;
	}
	public void setWarehouseNo(String warehouseNo) {
		this.warehouseNo = warehouseNo;
	}
	public String getPurchaseNo() {
		return purchaseNo;
	}
	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}
	
}
