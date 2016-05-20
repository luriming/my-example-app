package com.nqsky.meap.utils.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/***
Callable 和 Runnable 的使用方法大同小异， 区别在于：
1.Callable 使用 call（） 方法， Runnable 使用 run() 方法
2.call() 可以返回值， 而 run()方法不能返回。
3.call() 可以抛出受检查的异常，比如ClassNotFoundException， 而run()不能抛出受检查的异常。
注意ExecutorService 在Callable中使用的是submit()， 在Runnable中使用的是 execute() 
 * @author lrm
 *
 */
public class RunnableTest implements Runnable{

	protected int countDown = 10;
	private static int taskCount = 0;
	private final int id = taskCount++;

	public RunnableTest() {

	}

	public RunnableTest(int countDown) {
		this.countDown = countDown;
	}

	public String status() {
		return "#" + id + "(" + (countDown > 0 ? countDown : "LiftOff! ") + ")";
	}

	public void run() {
		while (countDown-- > 0) {
			System.out.print(status());
			Thread.yield();
		}
		System.out.println();
	}

	public static void main(String[] args) {
		ExecutorService exec = Executors.newFixedThreadPool(1);
		for (int i = 0; i < 5; i++) {
			exec.execute(new RunnableTest());
		}
		exec.shutdown();
	}

}
