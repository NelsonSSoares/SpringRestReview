package br.com.nelsonssoares.springreview.domain.repositories;

import br.com.nelsonssoares.springreview.domain.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends JpaRepository<Book, Long> {
}
