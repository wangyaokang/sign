package com.wyk.computer;

/**
 * 
 * 将结构中共有的部分提取出来
 *
 */
public abstract class Builder {
	
	public abstract void  buildCPU();
	
	public abstract void buildMainBoard();
	
	public abstract void buildHD();
	
	public abstract Computer getComputer();
}
