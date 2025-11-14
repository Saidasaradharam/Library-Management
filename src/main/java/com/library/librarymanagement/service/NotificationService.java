package com.library.librarymanagement.service;

import com.library.librarymanagement.entity.Loan;
import com.library.librarymanagement.entity.LoanStatus;
import com.library.librarymanagement.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private LoanRepository loanRepository;

    /**
     * Scheduled job to check for 'Due Soon' and 'Overdue' items.
     * Runs every day at 3:00 AM (or every hour for testing: cron = "0 0 * * * *").
     */
    @Scheduled(cron = "0 0 3 * * *") // Runs every day at 3:00 AM
    public void runOverdueChecks() {
        System.out.println("--- Starting Scheduled Overdue Check ---");

        // 1. Check for Items DUE SOON (1 day early reminder)
        LocalDate dueTomorrow = LocalDate.now().plusDays(1);

        List<Loan> dueSoonLoans = loanRepository.findByStatusAndDueDateBefore(
                LoanStatus.BORROWED,
                dueTomorrow
        );

        // Simulate sending a notification
        dueSoonLoans.forEach(loan -> {
            System.out.println(String.format("NOTIFICATION (DUE SOON): Member %s's book copy %d is due tomorrow (%s).",
                    loan.getMember().getUsername(),
                    loan.getBookCopy().getId(),
                    loan.getDueDate()));
        });

        // For Items OVERDUE (Passed the due date)
        LocalDate today = LocalDate.now();

        List<Loan> overdueLoans = loanRepository.findByStatusAndDueDateBefore(
                LoanStatus.BORROWED,
                today
        );

        // Update Loan Status to OVERDUE and simulate notification
        overdueLoans.forEach(loan -> {
            loan.setStatus(LoanStatus.OVERDUE);
            // This would normally be saved back to the DB: loanRepository.save(loan);

            System.out.println(String.format("NOTIFICATION (OVERDUE ALERT): Member %s's loan %d is now OVERDUE!",
                    loan.getMember().getUsername(),
                    loan.getId()));
        });

        System.out.println("--- Scheduled Overdue Check Complete ---");
    }
}