package com.library.librarymanagement.service;

import com.library.librarymanagement.dto.BookCopyRequest;
import com.library.librarymanagement.dto.BookCopyResponse;
import com.library.librarymanagement.entity.Book;
import com.library.librarymanagement.entity.BookCopy;
import com.library.librarymanagement.repository.BookCopyRepository;
import com.library.librarymanagement.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookCopyService {

    @Autowired
    private BookCopyRepository bookCopyRepository;

    @Autowired
    private BookRepository bookRepository;

    private BookCopyResponse toResponse(BookCopy copy) {
        BookCopyResponse response = new BookCopyResponse();
        response.setId(copy.getId());
        response.setInventoryNumber(copy.getInventoryNumber());
        response.setStatus(copy.getStatus());
        response.setCreatedAt(copy.getCreatedAt());

        if (copy.getBook() != null) {
            response.setBookId(copy.getBook().getId());
            response.setBookTitle(copy.getBook().getTitle());
        }
        return response;
    }

    // CREATE BookCopy (Librarian task)
    public BookCopyResponse createCopy(BookCopyRequest request) {
        // 1. Find the parent Book (must exist)
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found with ID: " + request.getBookId()));

        // 2. Create the new copy entity
        BookCopy copy = new BookCopy();
        copy.setBook(book);
        copy.setInventoryNumber(request.getInventoryNumber());
        // Status defaults to AVAILABLE in the entity

        // 3. Save and return
        return toResponse(bookCopyRepository.save(copy));
    }
}