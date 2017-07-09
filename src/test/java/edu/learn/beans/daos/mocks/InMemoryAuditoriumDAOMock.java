package edu.learn.beans.daos.mocks;

import edu.learn.beans.daos.inmemory.InMemoryAuditoriumDAO;
import edu.learn.beans.models.Auditorium;
import java.util.List;

/**
 * Created with IntelliJ IDEA. User: Dmytro_Babichev Date: 06/2/16 Time: 1:27 PM
 */
public class InMemoryAuditoriumDAOMock extends InMemoryAuditoriumDAO {

	public InMemoryAuditoriumDAOMock(List<Auditorium> auditoriums) {
		super(auditoriums);
	}
}
