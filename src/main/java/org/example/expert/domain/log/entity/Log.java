package org.example.expert.domain.log.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "log")
public class Log {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String action;  // 로그의 액션 내용 (예: "SAVE_MANAGER")
    private String status;  // 로그 상태 (예: "SUCCESS", "FAILURE")
    private String message; // 상세 메시지
    private LocalDateTime createdDate;

    public Log(String action,String status,String message){
        this.action = action;
        this.status = status;
        this.message = message;
        this.createdDate = LocalDateTime.now();
    }
}
