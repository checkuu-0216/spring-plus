package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long>,TodoFindRepository {

    @Query("SELECT t FROM Todo t LEFT JOIN FETCH t.user u ORDER BY t.modifiedAt DESC")
    Page<Todo> findAllByOrderByModifiedAtDesc(Pageable pageable);

    @Query("SELECT t FROM Todo t WHERE t.weather in :weather ORDER BY t.modifiedAt DESC" )
    Page<Todo> findAllByWeather(Pageable pageable,@Param("weather") String weather);

    @Query("SELECT t from Todo t where (t.weather in :weather) AND (t.modifiedAt BETWEEN :startDate and :endDate)")
    Page<Todo> findAllByWeatherAndDate(Pageable pageable,
                                       @Param("weather") String weather,
                                       @Param("startDate") LocalDateTime startDateTime,
                                       @Param("endDate") LocalDateTime endDateTime);

    @Query("select t from Todo t where t.modifiedAt BETWEEN :startDate and :endDate ")
    Page<Todo> findAllByDate(Pageable pageable, @Param("startDate") LocalDateTime startDateTime,@Param("endDate") LocalDateTime endDateTime);

}
