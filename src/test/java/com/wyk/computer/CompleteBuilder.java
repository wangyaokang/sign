package com.wyk.computer;

/**
 * 
 * 然后依据业务建造不同的对象
 *
 */
public class CompleteBuilder extends Builder{
	
	private Computer computer = new Computer();

	@Override
	public void buildCPU() {
		computer.Add("组装CPU");
	}

	@Override
	public void buildMainBoard() {
		computer.Add("组装主板");
	}

	@Override
	public void buildHD() {
		computer.Add("组装HD");
	}

	@Override
	public Computer getComputer() {
		return computer;
	}

}
