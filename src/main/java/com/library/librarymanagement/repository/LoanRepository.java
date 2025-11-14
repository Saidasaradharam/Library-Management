package com.library.librarymanagement.repository;

import com.library.librarymanagement.entity.BookCopy;
import com.library.librarymanagement.entity.Loan;
import com.library.librarymanagement.entity.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    // Required for Return Workflow
    Optional<Loan> findByBookCopyAndStatus(BookCopy bookCopy, LoanStatus status);

    // Required for Admin Report: Current Loans (BORROWED status)
    List<Loan> findByStatusIn(List<LoanStatus> statuses);

    // Required for Admin Report: Overdue List (OVERDUE status)
    List<Loan> findByStatusAndDueDateBefore(LoanStatus status, LocalDate dueDate);
}