package com.library.service;

import com.library.dto.BookRequest;
import com.library.dto.BookResponse;
import com.library.entity.Author;
import com.library.entity.Book;
import com.library.repository.AuthorRepository;
import com.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    private BookResponse toResponse(Book book) {
        BookResponse response = new BookResponse();
        response.setId(book.getId());
        response.setTitle(book.getTitle());
        response.setIsbn(book.getIsbn());

        if (book.getAuthor() != null) {
            response.setAuthorId(book.getAuthor().getId());
            response.setAuthorName(book.getAuthor().getFirstName() + " " + book.getAuthor().getLastName());
        }

        response.setCreatedAt(book.getCreatedAt());
        response.setCreatedBy(book.getCreatedBy());
        return response;
    }

    // CREATE
    public BookResponse createBook(BookRequest request) {
        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found: " + request.getAuthorId()));

        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setIsbn(request.getIsbn());
        book.setAuthor(author);

        return toResponse(bookRepository.save(book));
    }

    // READ ALL
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // READ BY ID
    public BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with ID: " + id));
        return toResponse(book);
    }

    // DELETE
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}