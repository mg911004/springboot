package com.ezace.board.domain;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "board")
@NoArgsConstructor
@Getter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id" , nullable = false)
    private Long boardId;

    @Column(name = "user_id" , nullable = false)
    private String userId;

    @Column(name = "subject", nullable = false, length = 255)
    private String subject;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "reg_date" , nullable = false, updatable = false)
    private LocalDateTime regDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    // @PrePersist는 JPA 엔티티 생명주기 콜백 중 하나입니다.
    //의미: 엔티티가 처음 데이터베이스에 저장되기 직전에 호출되는 메서드
    //주로 생성일(createdDate) 초기화, 기본값 설정 등에 사용됩니다.
    @PrePersist
    protected void onCreate() {
        regDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateDate = LocalDateTime.now();
    }

    // 생성자
    public Board(String subject, String content, String userId) {
        this.subject = subject;
        this.content = content;
        this.userId = userId;
    }

    public void update(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }
}
