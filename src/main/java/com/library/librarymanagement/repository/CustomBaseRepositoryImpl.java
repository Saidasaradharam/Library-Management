package com.library.librarymanagement.repository;

import com.library.librarymanagement.common.Auditable;
import com.library.librarymanagement.security.SecurityUtils;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// CRITICAL: Extends SimpleJpaRepository and implements our CustomBaseRepository
public class CustomBaseRepositoryImpl<T extends Auditable, ID extends Serializable>
        extends SimpleJpaRepository<T, ID> implements CustomBaseRepository<T, ID> {

    public CustomBaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    // 1. OVERRIDE save()
    @Override
    @Transactional
    public <S extends T> S save(S entity) {
        String currentAuditor = SecurityUtils.getCurrentUserLogin();

        if (entity.getCreatedBy() == null) {
            // New Entity: Set CreatedBy
            entity.setCreatedBy(currentAuditor);
            entity.setCreatedAt(LocalDateTime.now()); // Set timestamp manually for safety
        }
        // Existing Entity: Set UpdatedBy
        entity.setUpdatedBy(currentAuditor);
        entity.setUpdatedAt(LocalDateTime.now()); // Set timestamp manually for safety

        return super.save(entity);
    }

    // 2. OVERRIDE saveAll()
    @Override
    @Transactional
    public <S extends T> List<S> saveAll(Iterable<S> entities) {
        List<S> result = new ArrayList<>();
        for (S entity : entities) {
            result.add(this.save(entity)); // Call the overridden save method
        }
        return result;
    }
}