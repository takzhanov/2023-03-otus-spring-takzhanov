package io.github.takzhanov.otus.spring.lesson07.repository;

import java.util.List;

public interface Repository<ID, E> {
    List<E> findAll();

    E findById(ID id);

    E create(E entity);

    int update(E entity);

    int delete(ID id);
}
