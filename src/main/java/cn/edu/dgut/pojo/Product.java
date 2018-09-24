package cn.edu.dgut.pojo;

/**
 * @author TanWaiKim
 * @time 2018年5月9日 上午8:49:40
 * @version 1.0
 */
public class Product {
	 private String name;    //类别名称
	    private int num;        //销量
	    
	    private double price;
	    
	    public Product(String name, int num,double price) {
	        this.name = name;
	        this.num = num;
	        this.price = price;
	    }
	    
	    public String getName() {
	        return name;
	    }
	    public void setName(String name) {
	        this.name = name;
	    }
	    public int getNum() {
	        return num;
	    }
	    public void setNum(int num) {
	        this.num = num;
	    }

		public double getPrice() {
			return price;
		}

		public void setPrice(double price) {
			this.price = price;
		}
}
