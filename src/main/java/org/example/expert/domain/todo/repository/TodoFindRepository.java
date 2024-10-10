package org.example.expert.domain.todo.repository;

import com.querydsl.core.types.dsl.Wildcard;
import org.example.expert.domain.comment.dto.response.CommentResponse;
import org.example.expert.domain.manager.dto.response.ManagerResponse;
import org.example.expert.domain.todo.dto.response.TodoProjectionDto;
import org.example.expert.domain.todo.dto.response.TodoResponse;
import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TodoFindRepository {
    Todo findByIdWithUserFromQueryDsl(long todoId);

    Page<TodoProjectionDto> findByIdFromProjection(Pageable pageable,
                                                   String title,
                                                   LocalDate startDate,
                                                   LocalDate endDate,
                                                   String nickName);
}
