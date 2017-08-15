package edu.learn.beans.aspects.mocks;

import edu.learn.beans.aspects.LuckyWinnerAspect;

public class LuckyWinnerAspectMock extends LuckyWinnerAspect {

	public LuckyWinnerAspectMock(int luckyPercentage) {
		super(luckyPercentage);
	}

	public static void cleanup() {
		luckyUsers.clear();
	}
}
