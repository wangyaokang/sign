package com.wyk.signten;

/**
 * 
 * 单例模式之懒汉模式
 *
 */
public class SingleTonLazy {
	
	private SingleTonLazy single = new SingleTonLazy();
	
	// 构造方式私有化
	private SingleTonLazy(){
		
	}
	
	// 对外提供获取实例的方法
	public SingleTonLazy getInstance(){
		return single;
	}

}
