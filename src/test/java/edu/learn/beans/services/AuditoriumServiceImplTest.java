package edu.learn.beans.services;

import static org.junit.Assert.assertEquals;

import edu.learn.beans.TestConfiguration;
import edu.learn.beans.configuration.TestAuditoriumConfiguration;
import edu.learn.beans.models.Auditorium;
import edu.learn.beans.repository.AuditoriumRepository;
import java.util.List;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA. User: Dmytro_Babichev Date: 06/2/16 Time: 1:23 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfiguration.class, AuditoriumServiceImpl.class,
	TestAuditoriumConfiguration.class})
@Transactional
public class AuditoriumServiceImplTest {

	private static final int AUDITORIUMS_COUNT = 3;
	@Autowired
	private AuditoriumService auditoriumService;
	@Autowired
	private AuditoriumRepository auditoriumRepository;
	@Autowired
	private Auditorium testHall1;
	@Autowired
	private Auditorium testHall2;

	@After
	public void cleanup() {
		auditoriumRepository.deleteAll();
	}

	@Test
	public void testGetAuditoriums() throws Exception {
		List<Auditorium> auditoriums = auditoriumService.getAuditoriums();

		System.out.println(auditoriums);
		assertEquals("Auditoriums number should match", AUDITORIUMS_COUNT, auditoriums.size());
	}

	@Test
	public void testGetByName() throws Exception {
		checkTestHall(testHall1);
		checkTestHall(testHall2);
	}

	@Test(expected = RuntimeException.class)
	public void testGetByName_Exception() throws Exception {
		auditoriumService.getSeatsNumber("bla-bla-bla");
	}

	private void checkTestHall(Auditorium testHall) {
		int seatsNumber = auditoriumService.getSeatsNumber(testHall.getName());
		List<Integer> vipSeats = auditoriumService.getVipSeats(testHall.getName());
		assertEquals("Auditorium seats number should match. Auditorium: [" + testHall + "]", testHall.getSeatsNumber(),
			seatsNumber);
		assertEquals("Auditorium vip seats should match. Auditorium: [" + testHall + "]", testHall.getVipSeatsList(),
			vipSeats);
	}
}
