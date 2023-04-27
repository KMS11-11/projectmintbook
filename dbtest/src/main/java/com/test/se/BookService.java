package com.test.se;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.mo.Book;
import com.test.re.BookRepository;

@Service
public class BookService {
	@Autowired
	private BookRepository bookR;

	public void save(Book book) {
		bookR.save(book);
	}

	public Book findById(int id) {
		return bookR.findById(id).get();
	}

	public List<Book> findByGenre(int genre) {
		return bookR.findByGenre(genre);
	}

	public Book findByIsbn(String isbn) {
		return bookR.findByIsbn(isbn);
	}

	public void deleteById(int id) {
		bookR.deleteById(id);
	}

	public List<Book> findByBookNameLike(String title) {
		return bookR.findByBookNameLike("%"+title+"%");
	}
}
