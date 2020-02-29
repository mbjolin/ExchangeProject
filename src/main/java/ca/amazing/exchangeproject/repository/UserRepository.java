package ca.amazing.exchangeproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ca.amazing.exchangeproject.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
