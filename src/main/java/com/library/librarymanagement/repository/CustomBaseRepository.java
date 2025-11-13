package com.library.librarymanagement.repository;

import com.library.librarymanagement.common.Auditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface CustomBaseRepository<T extends Auditable, ID extends Serializable> extends JpaRepository<T, ID> {

    // We will override this method in the implementation
    <S extends T> S save(S entity);

    // We also override saveAll for bulk operations if needed
    <S extends T> List<S> saveAll(Iterable<S> entities);
}