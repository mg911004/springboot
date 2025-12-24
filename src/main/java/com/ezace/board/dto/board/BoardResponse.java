package com.ezace.board.dto.board;

import java.time.LocalDateTime;

import com.ezace.board.domain.Board;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class BoardResponse {
    private final Long boardId;
    private final String userId;
    private final String subject;
    private final String content;
    private final LocalDateTime regDate;
    private final LocalDateTime updateDate;

    public static BoardResponse from(Board board) {
        // ⭐️ Builder 패턴을 사용하여 가독성 높게 객체 생성
        return BoardResponse.builder()
            .boardId(board.getBoardId())
            .userId(board.getUserId())
            .subject(board.getSubject())
            .content(board.getContent())
            .regDate(board.getRegDate())
            .updateDate(board.getUpdateDate())
            .build();
    }
}
