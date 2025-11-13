package com.library.librarymanagement.entity;

import com.library.librarymanagement.common.Auditable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Book extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String isbn;

    // Simple many-to-one to Author (for now)
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    // One book can have many physical copies
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<BookCopy> copies;

    @ManyToOne // A book belongs to ONE category
    @JoinColumn(name = "category_id")
    private Category category;
}