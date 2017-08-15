package edu.learn.beans.repository;

import edu.learn.beans.models.Auditorium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA. User: Dmytro_Babichev Date: 2/3/2016 Time: 11:09 AM
 */
@Repository
public interface AuditoriumRepository extends JpaRepository<Auditorium, Long> {

	Auditorium getByName(String name);
}
