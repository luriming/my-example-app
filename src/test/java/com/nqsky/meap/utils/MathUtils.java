package com.nqsky.meap.utils;

public class MathUtils {

	/***
	 * java虚拟机可用的处理器个数
	 */
	public static final int JVM_AVAILABLE_PROCESSORS = Runtime.getRuntime()
			.availableProcessors();

	/***
	 * Math.min(int a,int b) 取a,b中的最小值
	 */
	public static final int AVAILABLE_PROCESSORS = Math.min(
			JVM_AVAILABLE_PROCESSORS, 3);

	public static void main(String[] argv) {
		System.out.println("JVM_AVAILABLE_PROCESSORS："+JVM_AVAILABLE_PROCESSORS+";AVAILABLE_PROCESSORS:"+AVAILABLE_PROCESSORS);

	}
}
