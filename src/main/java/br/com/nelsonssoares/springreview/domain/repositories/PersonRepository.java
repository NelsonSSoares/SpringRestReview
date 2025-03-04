package br.com.nelsonssoares.springreview.domain.repositories;

import br.com.nelsonssoares.springreview.domain.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}
