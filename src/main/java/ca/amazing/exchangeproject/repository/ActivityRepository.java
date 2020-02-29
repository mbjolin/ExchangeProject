package ca.amazing.exchangeproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ca.amazing.exchangeproject.domain.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
