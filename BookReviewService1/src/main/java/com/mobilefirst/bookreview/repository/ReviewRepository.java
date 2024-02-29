package com.mobilefirst.bookreview.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mobilefirst.bookreview.model.Book;
import com.mobilefirst.bookreview.model.Review;

@Repository
public interface ReviewRepository  extends JpaRepository<Review, Long>{
	List<Review> findByBookId(long bookId);

	List<Review> findByBook(Book book);

}
