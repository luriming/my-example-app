package com.nqsky.meap.utils;

public class SystemMethodTest {

	public static void main(String[] args) {
		byte ai[] = { 1, 2, 3, 4 };
		byte ai1[] = { 'a', 'b', 'c' };
		byte re[] = SystemMethodTest.append(ai, ai1, false);
	}

	/**
	 * 添加int数组到原有数组里面
	 * 
	 * @param ai  原数组
	 * @param ai1  新数组
	 * @param first 是否first
	 * @return
	 */
	public static byte[] append(byte ai[], byte ai1[], boolean first) {
		if (ai == null)
			return ai1;
		if (ai1 == null || ai1.length == 0)
			return ai;
		byte ai2[] = new byte[ai.length + ai1.length];
		if (!first) {
			/**
			 * 把src源数组从srcPos开发，拷贝到dest目标数组从destPos,length被复制的长度
			 */
			System.arraycopy(ai, 0, ai2, 0, ai.length);
			System.arraycopy(ai1, 0, ai2, ai.length, ai1.length);
		} else {
			System.arraycopy(ai1, 0, ai2, 0, ai1.length);
			System.arraycopy(ai, 0, ai2, ai1.length, ai.length);
		}
		return ai2;
	}
}
