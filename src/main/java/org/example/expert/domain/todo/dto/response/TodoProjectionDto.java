package org.example.expert.domain.todo.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TodoProjectionDto {
    private final String title;
    private final int mangerCount;
    private final int commentCount;
}
