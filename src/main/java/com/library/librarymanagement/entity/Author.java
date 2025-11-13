package com.library.librarymanagement.entity;

import com.library.librarymanagement.common.Auditable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false) // Lombok annotation, essential when extending Auditable
public class Author extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Uses PostgreSQL sequence for IDs
    private Long id;

    private String firstName;
    private String lastName;
    private String bio;
}