package com.library.librarymanagement.controller;

import com.library.librarymanagement.entity.Loan;
import com.library.librarymanagement.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired private LoanService loanService;

    @PreAuthorize("hasRole('MEMBER')")
    @PostMapping("/reserve/{copyId}")
    public ResponseEntity<Loan> reserveBook(@PathVariable Long copyId) {
        Loan loan = loanService.reserveCopy(copyId);
        return new ResponseEntity<>(loan, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @PutMapping("/borrow/{loanId}")
    public ResponseEntity<Loan> finalizeBorrow(@PathVariable Long loanId) {
        Loan loan = loanService.finalizeBorrow(loanId);
        return ResponseEntity.ok(loan);
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @PutMapping("/return/{copyId}")
    public ResponseEntity<String> processReturn(@PathVariable Long copyId) {
        double fine = loanService.processReturn(copyId);
        if (fine > 0) {
            return ResponseEntity.ok("Return processed. Fine accrued: €" + String.format("%.2f", fine));
        }
        return ResponseEntity.ok("Return processed. No fines.");
    }

    /** Admin Report 1: Get all current active loans (BORROWED, RESERVED, OVERDUE) */
    @PreAuthorize("hasRole('LIBRARIAN')")
    @GetMapping("/report/active")
    public ResponseEntity<List<Loan>> getCurrentLoansReport() {
        List<Loan> activeLoans = loanService.getCurrentActiveLoans();
        return ResponseEntity.ok(activeLoans);
    }

    /** Admin Report 2: Get all loans past their due date */
    @PreAuthorize("hasRole('LIBRARIAN')")
    @GetMapping("/report/overdue")
    public ResponseEntity<List<Loan>> getOverdueLoans() {
        List<Loan> overdueLoans = loanService.getOverdueLoansReport();
        return ResponseEntity.ok(overdueLoans);
    }
}