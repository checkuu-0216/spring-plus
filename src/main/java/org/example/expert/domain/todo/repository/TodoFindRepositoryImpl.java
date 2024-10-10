package org.example.expert.domain.todo.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.dto.response.TodoProjectionDto;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public Page<TodoProjectionDto> findByIdFromProjection(Pageable pageable,
                                                          String title,
                                                          LocalDate startDate,
                                                          LocalDate endDate,
                                                          String nickName) {
        BooleanBuilder builder = new BooleanBuilder();

        LocalDateTime startDateTime = startDate != null ? startDate.atStartOfDay() : null;
        LocalDateTime endDateTime = endDate != null ? endDate.atTime(23, 59, 59) : null;

        if (title != null && !title.isEmpty()) {
            builder.and(todo.title.containsIgnoreCase(title));
        }

        // 생성일 범위 검색 조건
        if (startDateTime != null && endDateTime != null) {
            builder.and(todo.modifiedAt.between(startDateTime, endDateTime));
        } else if (startDateTime != null) {
            builder.and(todo.modifiedAt.goe(startDateTime));
        } else if (endDateTime != null) {
            builder.and(todo.modifiedAt.loe(endDateTime));
        }

        // 담당자 닉네임 검색 조건
        if (nickName != null && !nickName.isEmpty()) {
            builder.and(manager.user.nickname.containsIgnoreCase(nickName));
        }

        List<TodoProjectionDto> results = queryFactory
                .select(Projections.constructor(
                                TodoProjectionDto.class,
                                todo.title,
                                manager.countDistinct().intValue(),
                                comment.countDistinct().intValue()))
                .from(todo)
                .leftJoin(todo.managers, manager)
                .leftJoin(todo.comments, comment)
                .where(builder)
                .groupBy(todo.id, todo.title)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalCount = queryFactory
                .select(Wildcard.count) //select count(*)
                .from(todo)
                .fetchOne();

        return new PageImpl<>(results,pageable,totalCount);
    }

    private BooleanExpression todoIdEq(Long todoId) {
        return todoId != null ? todo.id.eq(todoId) :null ;
    }
}
