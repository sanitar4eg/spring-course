package edu.learn.beans.repository;

import edu.learn.beans.models.Auditorium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditoriumRepository extends JpaRepository<Auditorium, Long> {

	Auditorium getByName(String name);
}
