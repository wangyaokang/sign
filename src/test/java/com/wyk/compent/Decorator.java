package com.wyk.compent;

public abstract class Decorator implements Compent{
	
	protected Compent compent;
	
	/**
	 * @return the compent
	 */
	public Compent getCompent() {
		return compent;
	}

	/**
	 * @param compent the compent to set
	 */
	public void setCompent(Compent compent) {
		this.compent = compent;
	}

	@Override
	public void run() {
		
	}
	
}
