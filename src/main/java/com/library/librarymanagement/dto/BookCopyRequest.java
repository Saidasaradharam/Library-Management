package com.library.librarymanagement.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class BookCopyRequest {

    @NotNull(message = "Book ID is required to link the copy")
    private Long bookId;

    @NotBlank(message = "Inventory number (barcode) is required")
    private String inventoryNumber;

    // Status defaults to AVAILABLE in the entity, so we don't need it here.
}