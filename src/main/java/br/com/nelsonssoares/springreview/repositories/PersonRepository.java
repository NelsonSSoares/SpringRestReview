package br.com.nelsonssoares.springreview.repositories;

import br.com.nelsonssoares.springreview.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}
