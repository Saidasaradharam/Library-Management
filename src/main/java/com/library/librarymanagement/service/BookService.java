package com.library.librarymanagement.service;

import com.library.librarymanagement.dto.BookRequest;
import com.library.librarymanagement.dto.BookResponse;
import com.library.librarymanagement.entity.Author;
import com.library.librarymanagement.entity.Book;
import com.library.librarymanagement.entity.Category;
import com.library.librarymanagement.repository.AuthorRepository;
import com.library.librarymanagement.repository.BookRepository;
import com.library.librarymanagement.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private CategoryRepository categoryRepository;

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

        // CRITICAL UPDATE: Find ONE Category
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found: " + request.getCategoryId()));

        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setIsbn(request.getIsbn());
        book.setAuthor(author);
        book.setCategory(category);

        return toResponse(bookRepository.save(book));
    }

    public Page<BookResponse> searchBooks(String title, String authorFirstName, Pageable pageable) {
        Page<Book> booksPage;

        if (title != null && !title.isEmpty()) {
            booksPage = bookRepository.findByTitleContainingIgnoreCase(title, pageable);
        } else if (authorFirstName != null && !authorFirstName.isEmpty()) {
            booksPage = bookRepository.findByAuthorFirstNameContainingIgnoreCase(authorFirstName, pageable);
        } else {
            // Default: List all books with pagination
            booksPage = bookRepository.findAll(pageable);
        }

        // Map Page<Book> to Page<BookResponse>
        return booksPage.map(this::toResponse);
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

    public BookResponse updateBook(Long id, BookRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found for update: " + id));

        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found: " + request.getAuthorId()));

        book.setTitle(request.getTitle());
        book.setIsbn(request.getIsbn());
        book.setAuthor(author);

        // Note: updatedAt field is handled automatically by JPA Auditing

        return toResponse(bookRepository.save(book));
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}