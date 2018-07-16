package com.wyk.future;

public class CommonCook {
	public static void main(String[] args) throws InterruptedException {
		long startTime = System.currentTimeMillis();
		OnlineShopping thread = new OnlineShopping();
		thread.start();
		thread.join(); 
		Thread.sleep(2000); 
		Shicai shicai = new Shicai();
		System.out.println("第二步：食材到位");
		System.out.println("第三步：开始展现厨艺");
		cook(thread.chuju, shicai);

		System.out.println("总共用时" + (System.currentTimeMillis() - startTime) + "ms");
	}

	// 网购厨具线程
	static class OnlineShopping extends Thread {
		private Chuju chuju;
		
		@Override
		public void run() {
			System.out.println("第一步：下单");
			System.out.println("第一步：等待送货");
			try {
				Thread.sleep(5000); 
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("第一步：快递送到");
			chuju = new Chuju();
		}
	}

	// 用厨具烹饪食材
	static void cook(Chuju chuju, Shicai shicai) {
	}

	// 厨具类
	static class Chuju {
	}

	// 食材类
	static class Shicai {
	}
}
