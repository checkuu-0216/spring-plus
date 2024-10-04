package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;

import java.util.Optional;

public interface TodoFindRepository {
    Todo findByIdWithUserFromQueryDsl(long todoId);
}
