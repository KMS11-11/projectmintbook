package com.test.re;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.mo.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {

	List<Book> findByGenre(int genre);

	Book findByIsbn(String isbn);

	List<Book> findByBookNameLike(String title);

}
