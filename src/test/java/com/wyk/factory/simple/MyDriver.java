package com.wyk.factory.simple;

public class MyDriver {
	
	public static void main(String[] args) {
		CarFactory factory = new CarFactory();
		// 不用考虑创建对象的具体细节，但是当业务种类增加时，需要在工厂类中添加新的种类。
		// 违背了“开闭原则（对外扩展是允许的，对内修改是不允许的）”
		Car baomaCar = factory.createCar("baoma");
		baomaCar.driver();
		Car aodiCar = factory.createCar("aodi");
		aodiCar.driver();
	}
	
}
