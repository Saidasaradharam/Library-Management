package com.library.entity;

import com.library.common.Auditable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

// Defines the status required by the PDF (AVAILABLE, RESERVED, BORROWED, LOST, MAINTENANCE)
enum CopyStatus {
    AVAILABLE, RESERVED, BORROWED, LOST, MAINTENANCE
}

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class BookCopy extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Unique identifier for the physical copy (barcode/QR requirement)
    private String inventoryNumber;

    @Enumerated(EnumType.STRING)
    private CopyStatus status = CopyStatus.AVAILABLE;

    // Many copies belong to one Book
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}