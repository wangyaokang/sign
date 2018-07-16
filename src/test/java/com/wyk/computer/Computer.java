package com.wyk.computer;

import java.util.ArrayList;
import java.util.List;

public class Computer {

	private List<String> parts = new ArrayList<String>();

	// 用于将组件组装到电脑里
	public void Add(String part) {
		parts.add(part);
	}
	
	public void show(){
		for(String part : parts){
			System.out.println("组件" + part + "装好了");
		}
	}

}
