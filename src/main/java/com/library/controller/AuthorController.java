package com.library.controller;

import com.library.dto.AuthorRequest;
import com.library.dto.AuthorResponse;
import com.library.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    // POST /api/authors
    @PostMapping
    public ResponseEntity<AuthorResponse> createAuthor(@Valid @RequestBody AuthorRequest request) {
        AuthorResponse response = authorService.createAuthor(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // GET /api/authors
    @GetMapping
    public ResponseEntity<List<AuthorResponse>> getAllAuthors() {
        List<AuthorResponse> authors = authorService.getAllAuthors();
        return ResponseEntity.ok(authors);
    }

    // GET /api/authors/{id}
    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponse> getAuthorById(@PathVariable Long id) {
        AuthorResponse author = authorService.getAuthorById(id);
        return ResponseEntity.ok(author);
    }

    // PUT /api/authors/{id}
    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponse> updateAuthor(
            @PathVariable Long id,
            @Valid @RequestBody AuthorRequest request) {
        AuthorResponse updatedAuthor = authorService.updateAuthor(id, request);
        return ResponseEntity.ok(updatedAuthor);
    }

    // DELETE /api/authors/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}