package com.library.librarymanagement.service;

import com.library.librarymanagement.entity.*;
import com.library.librarymanagement.repository.*;
import com.library.librarymanagement.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    // Inject repositories for transaction management
    @Autowired private LoanRepository loanRepository;
    @Autowired private BookCopyRepository bookCopyRepository;
    @Autowired private UserRepository userRepository;

    private static final double FINE_RATE_PER_DAY = 0.50; // 0.50 per day

    // Helper method to find a User (Member or Librarian)
    private User findUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }

    // 1. RESERVE Workflow
    @Transactional
    public Loan reserveCopy(Long copyId) {
        BookCopy copy = bookCopyRepository.findById(copyId)
                .orElseThrow(() -> new RuntimeException("Copy not found: " + copyId));

        if (copy.getStatus() != CopyStatus.AVAILABLE) {
            throw new IllegalStateException("Copy is not available for reservation.");
        }

        User member = findUser(SecurityUtils.getCurrentUserLogin());

        // Update Copy Status
        copy.setStatus(CopyStatus.RESERVED);
        bookCopyRepository.save(copy);

        // Create Loan record with RESERVED status
        Loan loan = new Loan();
        loan.setBookCopy(copy);
        loan.setMember(member);
        loan.setStatus(LoanStatus.RESERVED);
        loan.setLoanDate(LocalDate.now()); // Loan date set at reservation time
        loan.setDueDate(LocalDate.now().plusDays(3)); // Short reserve window

        return loanRepository.save(loan);
    }

    // 2. BORROW Workflow (Librarian/System task to finalize checkout)
    @Transactional
    public Loan finalizeBorrow(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found: " + loanId));

        if (loan.getStatus() != LoanStatus.RESERVED) {
            throw new IllegalStateException("Loan is not in RESERVED status.");
        }

        // Update Copy Status (optional: ensure BookCopy status is updated if not already)
        loan.getBookCopy().setStatus(CopyStatus.BORROWED);
        bookCopyRepository.save(loan.getBookCopy());

        // Update Loan Status
        loan.setStatus(LoanStatus.BORROWED);
        loan.setDueDate(LocalDate.now().plusWeeks(2)); // Reset Due Date to 2 weeks from now

        // Update audit fields for the transaction finalization
        loan.setUpdatedBy(SecurityUtils.getCurrentUserLogin());

        return loanRepository.save(loan);
    }

    // 3. RETURN Workflow & Fine Calculation
    @Transactional
    public double processReturn(Long copyId) {
        BookCopy copy = bookCopyRepository.findById(copyId)
                .orElseThrow(() -> new RuntimeException("Copy not found: " + copyId));

        // Find the active loan for this copy
        Loan loan = loanRepository.findByBookCopyAndStatus(copy, LoanStatus.BORROWED)
                .or(() -> loanRepository.findByBookCopyAndStatus(copy, LoanStatus.OVERDUE))
                .orElseThrow(() -> new IllegalStateException("No active loan found for this copy."));

        // --- Fine Calculation ---
        double fine = calculateFine(loan.getDueDate());

        // Update Loan Record
        loan.setReturnDate(LocalDate.now());
        loan.setFineAmount(fine);
        loan.setStatus(LoanStatus.RETURNED);
        loan.setUpdatedBy(SecurityUtils.getCurrentUserLogin());
        loanRepository.save(loan);

        // Update Copy Status
        copy.setStatus(CopyStatus.AVAILABLE);
        bookCopyRepository.save(copy);

        return fine;
    }

    /** Fine calculation logic (Required Feature) */
    private double calculateFine(LocalDate dueDate) {
        if (dueDate.isBefore(LocalDate.now())) {
            long daysOverdue = ChronoUnit.DAYS.between(dueDate, LocalDate.now());
            return daysOverdue * FINE_RATE_PER_DAY;
        }
        return 0.0;
    }

    // Admin Reports
    public List<Loan> getCurrentActiveLoans() {
        List<LoanStatus> activeStatuses = List.of(
                LoanStatus.BORROWED,
                LoanStatus.RESERVED,
                LoanStatus.OVERDUE // Overdue is still an active loan
        );

        return loanRepository.findByStatusIn(activeStatuses);
    }

    public List<Loan> getOverdueLoansReport() {
        return loanRepository.findByStatusAndDueDateBefore(
                LoanStatus.BORROWED, // Find active BORROWED loans
                LocalDate.now()      // Where the due date is before today
        );
    }
}