package com.library.librarymanagement.controller;

import com.library.librarymanagement.dto.BookCopyRequest;
import com.library.librarymanagement.dto.BookCopyResponse;
import com.library.librarymanagement.service.BookCopyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Used for RBAC
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/copies")
public class BookCopyController {

    @Autowired
    private BookCopyService bookCopyService;

    // POST /api/copies: Secured because managing inventory is a LIBRARIAN task.
    @PreAuthorize("hasRole('LIBRARIAN')")
    @PostMapping
    public ResponseEntity<BookCopyResponse> createBookCopy(@Valid @RequestBody BookCopyRequest request) {
        BookCopyResponse response = bookCopyService.createCopy(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // (Future: GET /api/copies/{id} to check status)
}