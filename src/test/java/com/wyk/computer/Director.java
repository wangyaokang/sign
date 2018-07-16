package com.wyk.computer;

/**
 * 
 * 负责展示的类和建造的类进行衔接
 *
 */
public class Director {
	
	public void construct(Builder builder){
		builder.buildCPU();
		builder.buildMainBoard();
		builder.buildHD();
	}
	
}
