package com.library.librarymanagement.service;

import com.library.librarymanagement.dto.AuthorRequest;
import com.library.librarymanagement.dto.AuthorResponse;
import com.library.librarymanagement.entity.Author;
import com.library.librarymanagement.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    private AuthorResponse toResponse(Author author) {
        AuthorResponse response = new AuthorResponse();
        response.setId(author.getId());
        response.setFirstName(author.getFirstName());
        response.setLastName(author.getLastName());
        response.setBio(author.getBio());
        response.setCreatedAt(author.getCreatedAt());
        response.setCreatedBy(author.getCreatedBy());
        return response;
    }

    // CREATE
    public AuthorResponse createAuthor(AuthorRequest request) {
        Author author = new Author();
        author.setFirstName(request.getFirstName());
        author.setLastName(request.getLastName());
        author.setBio(request.getBio());
        return toResponse(authorRepository.save(author));
    }

    // READ ALL
    public List<AuthorResponse> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // READ BY ID
    public AuthorResponse getAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found: " + id));
        return toResponse(author);
    }

    // UPDATE
    public AuthorResponse updateAuthor(Long id, AuthorRequest request) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found for update: " + id));

        author.setFirstName(request.getFirstName());
        author.setLastName(request.getLastName());
        author.setBio(request.getBio());
        return toResponse(authorRepository.save(author));
    }

    // DELETE
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}