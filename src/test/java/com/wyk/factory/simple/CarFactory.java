package com.wyk.factory.simple;

public class CarFactory {
	
	public Car createCar(String carType){
		if(carType.equals("baoma")){
			return new BaomaCar();
		}else if(carType.equals("aodi")){
			return new AodiCar();
		}
		return null;
	}
	
}
