package com.nqsky.meap.utils;

import java.util.concurrent.atomic.AtomicInteger;
/***
 * AtomicInteger，一个提供原子操作的Integer的类。
 * 在Java语言中，++i和i++操作并不是线程安全的，在使用的时候，不可避免的会用到synchronized关键字。
 * 而AtomicInteger则通过一种线程安全的加减操作接口
 * @author lrm
 *
 */
public class AtomicIntegerDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AtomicInteger ai = new AtomicInteger(0);
		int i1 = ai.get();
		printlnV(i1);
		int i2 = ai.getAndSet(5);
		printlnV(i2);
		int i3 = ai.get();
		printlnV(i3);
		int i4 = ai.getAndIncrement();
		printlnV(i4);
		printlnV(ai.get());

	}

	static void printlnV(int i) {
		System.out.println("i : " + i);
	}

}

/***

来看AtomicInteger提供的接口。
//获取当前的值
public final int get()
 
//取当前的值，并设置新的值
public final int getAndSet(int newValue)
 
//获取当前的值，并自增
public final int getAndIncrement() 
 
//获取当前的值，并自减
public final int getAndDecrement()
 
//获取当前的值，并加上预期的值
public final int getAndAdd(int delta) 
*/
