package edu.learn.beans.aspects.mocks;

import edu.learn.beans.aspects.CounterAspect;

public class CountAspectMock extends CounterAspect {

	public static void cleanup() {
		accessByNameCounter.clear();
		getPriceByNameCounter.clear();
		bookByNameCounter.clear();
	}
}
