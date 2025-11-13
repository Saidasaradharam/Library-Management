package com.library.librarymanagement.entity;

import com.library.librarymanagement.common.Auditable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Loan extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Links to the specific physical copy being borrowed
    @OneToOne
    @JoinColumn(name = "copy_id", nullable = false)
    private BookCopy bookCopy;

    // Links to the user (Member) who borrowed the book
    // NOTE: This links to the User entity (our app_user table)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User member;

    // Key tracking fields
    private LocalDate loanDate = LocalDate.now();
    private LocalDate dueDate = LocalDate.now().plusWeeks(2); // Default 2 weeks
    private LocalDate returnDate;

    private Double fineAmount = 0.0; // Foundation for Fine calculation

    @Enumerated(EnumType.STRING)
    private LoanStatus status = LoanStatus.BORROWED;

}