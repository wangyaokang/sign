package com.wyk.signten;

/**
 * 
 * 单例模式之饿汉模式
 *
 */
public class SingleTonHungry {

	private SingleTonHungry single = null;

	// 构造方法私有化
	private SingleTonHungry() {
	}

	// 对外公开获取实例的方法
	public SingleTonHungry getInstance() {
		if (single == null) {
			return new SingleTonHungry();
		}
		return single;
	}

}
