package com.ezace.board.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.ezace.board.domain.Board;
import com.ezace.board.dto.board.BoardRequest;
import com.ezace.board.dto.board.BoardResponse;
import com.ezace.board.exception.NotFoundException;
import com.ezace.board.service.BoardService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/board")
@Validated
@Tag(name = "Board", description = "게시판")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping
    public ResponseEntity<List<BoardResponse>> getBoardList(
        @RequestParam(name = "user_id",required = false) String user_id,
        @RequestParam(name = "subject",required = false) String subject,
        @RequestParam(name = "content",required = false) String content
    ) {
        // System.out.println(user_id);
        // System.out.println(subject);
        // System.out.println(content);
        // 조건에 맞게 필터링
        List<Board> boards = boardService.getBoardList(user_id, subject, content);

        // Board -> BoardResponse 변환 후 반환
        List<BoardResponse> boardResponses = boards.stream()
                .map(BoardResponse::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(boardResponses);
    }


    @GetMapping("/{id}")
    public ResponseEntity<BoardResponse> getBoardById(@PathVariable("id") Long id) {
        Optional<Board> board = boardService.getBoardById(id);
        return board.map(BoardResponse::from)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BoardResponse> createBoard(@Valid @RequestBody BoardRequest request) {
        System.out.println(request.toString());
        System.out.println(request.getSubject());
        System.out.println(request.getContent());
        Board createdBoard = boardService.createBoard(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(BoardResponse.from(createdBoard));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardResponse> updateBoard(
            @PathVariable("id") Long id, @Valid @RequestBody BoardRequest request) { //DTO로 매핑
        try {
            Board updatedBoard = boardService.updateBoard(id, request); // DTD -> 엔티티 객체로 변환후 서비스로 넘김
            return ResponseEntity.ok(BoardResponse.from(updatedBoard)); //서비스에서 추출한 최종 엔티티 객체를 가져와서 다시 DTD로 변환
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        try {
            boardService.deleteBoard(id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
