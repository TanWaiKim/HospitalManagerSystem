package cn.edu.dgut.common.vo;

import java.math.BigDecimal;

/**
 * @author TanWaiKim
 * @time 2018年5月10日 上午3:10:30
 * @version 1.0
 */
public class PriceCount {
	private String time;
	private BigDecimal price;
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
}
