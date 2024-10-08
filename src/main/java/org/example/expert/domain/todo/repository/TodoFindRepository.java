package org.example.expert.domain.todo.repository;

import com.querydsl.core.types.dsl.Wildcard;
import org.example.expert.domain.comment.dto.response.CommentResponse;
import org.example.expert.domain.manager.dto.response.ManagerResponse;
import org.example.expert.domain.todo.dto.response.TodoResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TodoFindRepository {
    Todo findByIdWithUserFromQueryDsl(long todoId);

    Page<TodoResponse> search(Pageable pageable, Long managerId);

}
