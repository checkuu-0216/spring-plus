package org.example.expert.domain.todo.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.stereotype.Repository;

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

    private BooleanExpression todoIdEq(Long todoId) {
        return todoId != null ? todo.id.eq(todoId) :null ;
    }
}
