package com.nqsky.meap.utils;

import java.util.UUID;

import com.nqsky.meap.core.util.HashUtil;

public class SsoTicketDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String ssoTicket = UUID.randomUUID().toString();
		ssoTicket += System.currentTimeMillis();
		ssoTicket = HashUtil.MD5Hashing(ssoTicket);
		String userId = com.nqsky.meap.persist.Strings.concat(new Object[] { ssoTicket, "MEAP" });
		System.out.println("userId="+userId+";deviceId="+ssoTicket);
	}

}
