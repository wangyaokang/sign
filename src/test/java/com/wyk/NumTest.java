package com.wyk;

import com.wyk.computer.Builder;
import com.wyk.computer.CompleteBuilder;
import com.wyk.computer.Computer;
import com.wyk.computer.Director;

public class NumTest {
	
	public static void main(String[] args) {
		Director director = new Director();
		Builder builder = new CompleteBuilder();
		director.construct(builder);
		Computer computer = builder.getComputer();
		computer.show();
	}
	
}
