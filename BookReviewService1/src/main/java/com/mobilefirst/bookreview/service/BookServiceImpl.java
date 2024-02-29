package com.mobilefirst.bookreview.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilefirst.bookreview.model.Book;
import com.mobilefirst.bookreview.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService{

	@Autowired
	private BookRepository bookRepository;
	
	
	public BookServiceImpl(BookRepository bookRepository) {
		super();
		this.bookRepository = bookRepository;
	}

	@Override
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	@Override
	public Book getBookById(long id) {
		return bookRepository.findById(id).orElse(null);
	}

	@Override
	public Book createBook(Book book) {
 		return bookRepository.save(book);
	}

	@Override
	public Book updateBook(Long id, Book book) {
		Book existingBook = bookRepository.findById(id).orElse(null);

		if (existingBook != null) {
			existingBook.setTitle(book.getTitle());
			existingBook.setAuthor(book.getAuthor());
			existingBook.setPublicationYear(book.getPublicationYear());
			return bookRepository.save(existingBook);
		}
		return null;
	}

	@Override
	public void deleteBookById(long id) {
		bookRepository.deleteById(id);
		
	}

}
