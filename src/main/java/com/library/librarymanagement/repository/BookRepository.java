package com.library.librarymanagement.repository;

import com.library.librarymanagement.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Finds books where the title contains the search term, ignoring case.
     * Used for the public catalog search feature.
     * @param title The partial title to search for.
     * @param pageable Contains pagination (page, size) and sorting information.
     * @return A Page of Book entities.
     */
    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    /**
     * Finds books by searching for the author's last name.
     * Used for the public catalog search feature.
     * @param firstName The partial last name of the author.
     * @param pageable Contains pagination and sorting information.
     * @return A Page of Book entities.
     */
    Page<Book> findByAuthorFirstNameContainingIgnoreCase(String firstName, Pageable pageable);

    // Note: The Page<Book> findAll(Pageable pageable) method is inherited from JpaRepository,
    // which handles the default list-all-with-pagination case.
}