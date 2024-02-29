package com.mobilefirst.bookreview.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mobilefirst.bookreview.exception.BookNotFoundException;
import com.mobilefirst.bookreview.model.Book;
import com.mobilefirst.bookreview.model.Review;
import com.mobilefirst.bookreview.service.BookService;
import com.mobilefirst.bookreview.service.ReviewService;

@RestController
@RequestMapping("/reviewApi")
public class ReviewController {

	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private BookService bookService;
	@GetMapping("/AllReviews")
	public List<Review> getallReviews() {
 		return reviewService.getallReviews();
	}

	@GetMapping("/books/{bookId}")
	public ResponseEntity<List<Review>> getReviewsByBookId(@PathVariable long bookId) {
  		List<Review> reviews = reviewService.getReviewsByBookId(bookId);
		if (!reviews.isEmpty()) {
			return ResponseEntity.ok(reviews);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Review> getReviewById(@PathVariable long id) {
  		Review reviewById = reviewService.getReviewById(id);
		if (reviewById != null) {
			return ResponseEntity.ok(reviewById);
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("/createReview")
	public ResponseEntity<Review> createReview(@RequestBody Review review) throws BookNotFoundException {
 		Review createReviews = reviewService.createReview(review);
		return ResponseEntity.status(HttpStatus.CREATED).body(createReviews);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Review> updateReviews(@PathVariable long id, @RequestBody Review review) {
 		Review updateReview = reviewService.updateReview(id, review);
		if (updateReview != null) {
			return ResponseEntity.ok(updateReview);
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteReviewById(@PathVariable long id) {
 		reviewService.deleteReviewById(id);
		return ResponseEntity.ok("Review with id:" + id + " has been deleted successfully");
	}

	@GetMapping("/averageRating/{bookId}")
    public ResponseEntity<Double> getAverageRatingForBook(@PathVariable Long bookId) {
        Book book = bookService.getBookById(bookId);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        double averageRating = reviewService.calculateAverageRatingForBook(bookId);
        		return ResponseEntity.ok(averageRating);
    }
}
