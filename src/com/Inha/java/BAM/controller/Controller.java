package com.Inha.java.BAM.controller;

import com.Inha.java.BAM.dto.Member;

public abstract class Controller {
	public abstract void doAction(String command, String actionMethodName);
	
	public static Member loginedMember;

	public static boolean isLogined() {
		return loginedMember != null;
	}
	
	public abstract void makeTestData();
}