package com.mobilefirst.bookreview.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobilefirst.bookreview.exception.BookNotFoundException;
import com.mobilefirst.bookreview.model.Book;
import com.mobilefirst.bookreview.model.Review;
import com.mobilefirst.bookreview.repository.BookRepository;
import com.mobilefirst.bookreview.repository.ReviewRepository;
import com.netflix.discovery.converters.Auto;
@Service
public class ReviewServiceImpl implements ReviewService {
	@Autowired
	private ReviewRepository reviewRepository;
	@Autowired
	private BookService bookService;
	
	@Autowired
	private BookRepository bookrepository;
	
	public ReviewServiceImpl(ReviewRepository reviewRepository, BookService bookService) {
		super();
		this.reviewRepository = reviewRepository;
		this.bookService = bookService;
	}

	@Override
	public List<Review> getallReviews() {
		return reviewRepository.findAll();
	}

	@Override
	public Review getReviewById(Long id) {
		return reviewRepository.findById(id).orElse(null);
	}

	@Override
	public Review createReview(Review review) throws BookNotFoundException {
		Book book = bookService.getBookById(review.getBook().getId());
		if (book != null) {
			review.setBook(book);
			return reviewRepository.save(review);
		} else {
			throw new BookNotFoundException("Book with ID " + review.getBook().getId() + " not found.");
		}
	}

	@Override
	public Review updateReview(Long id, Review review) {
		Review existingReview = reviewRepository.findById(id).orElse(null);

		if (existingReview != null) {
			existingReview.setRating(review.getRating());
			existingReview.setText(review.getText());

			return reviewRepository.save(existingReview);

		}
		return null;
	}

	@Override
	public void deleteReviewById(Long id) {
		reviewRepository.deleteById(id);
		
	}

	@Override
	public List<Review> getReviewsByBookId(long bookId) {
 		return reviewRepository.findByBookId(bookId);
	}

	@Override
	public double calculateAverageRatingForBook(Long bookId) {
      Book book = bookrepository.findById(bookId).orElse(null);
      if(book ==null) {
    	  return 0.0;
      }
      List<Review> reviews=reviewRepository.findByBook(book);
      if(reviews.isEmpty()) {
    	  return 0.0;
      }

      int totalRating = reviews.stream().mapToInt(Review::getRating).sum();
      return (double) totalRating / reviews.size();
	}

}
