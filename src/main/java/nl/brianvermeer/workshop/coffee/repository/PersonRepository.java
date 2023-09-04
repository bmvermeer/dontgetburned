package nl.brianvermeer.workshop.coffee.repository;


import nl.brianvermeer.workshop.coffee.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    public Person findByUsername(String username);

    public List<Person> findByEmail(String email);


}
