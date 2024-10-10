package org.example.expert.domain.todo.dto.response;

import lombok.Getter;

@Getter
public class TodoSearchResponse {

    private final String title;

    public TodoSearchResponse(String title) {
        this.title = title;

    }
}
