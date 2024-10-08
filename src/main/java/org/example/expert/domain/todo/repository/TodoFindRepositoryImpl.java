package org.example.expert.domain.todo.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.comment.dto.response.CommentResponse;
import org.example.expert.domain.manager.dto.response.ManagerResponse;
import org.example.expert.domain.todo.dto.response.TodoResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.example.expert.domain.comment.entity.QComment.comment;
import static org.example.expert.domain.manager.entity.QManager.manager;
import static org.example.expert.domain.todo.entity.QTodo.todo;
import static org.example.expert.domain.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class TodoFindRepositoryImpl implements TodoFindRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public Todo findByIdWithUserFromQueryDsl(long todoId){
        return queryFactory
                .select(todo)
                .from(todo)
                .leftJoin(todo.user,user)
                .where(todoIdEq(todoId))
                .fetchOne();
    }
    @Override
    public Page<TodoResponse> search(Pageable pageable, Long managerId) {
        List<Todo> results = queryFactory
                .select(todo)
                .distinct()
                .from(todo)
                .leftJoin(todo.managers, manager).fetchJoin()
                .leftJoin(todo.comments, comment)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalCount = queryFactory
                .select(Wildcard.count)
                .from(todo)
                .fetchOne();

        List<TodoResponse> content = results.stream()
                .map(todo ->
                        new TodoResponse(
                                todo.getId(),
                                todo.getTitle(),
                                todo.getContents(),
                                todo.getComments().stream().map(comment -> new CommentResponse(comment.getId(), comment.getContents())).toList(),
                                todo.getManagers().stream().map(manager -> new ManagerResponse(manager.getId(), manager.getUser())).toList()
                        )
                ).toList();

        return new PageImpl<>(content, pageable, totalCount);
    }

    private BooleanExpression todoIdEq(Long todoId) {
        return todoId != null ? todo.id.eq(todoId) :null ;
    }
}
