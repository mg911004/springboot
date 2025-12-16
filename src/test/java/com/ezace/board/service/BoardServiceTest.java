package com.ezace.board.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ezace.board.domain.Board;
import com.ezace.board.dto.board.BoardRequest;
import com.ezace.board.repository.BoardRepository;

// JUnit 5를 사용하는 표준 방식. (JUnit 4에서는 @RunWith(MockitoJUnitRunner.class)를 사용합니다)
@ExtendWith(MockitoExtension.class)
public class BoardServiceTest {

    // 1. BoardRepository 가짜 객체 생성 (DB 접근 안 함)
    @Mock
    private BoardRepository boardRepository;

    // 2. BoardService에 생성한 가짜 객체 주입
    @InjectMocks
    private BoardService boardService;

    @Test
    void getBoardById_정상_조회() {
        // Given (준비)
        Long boardId = 1L;
        Board mockBoard = new Board("제목", "내용", "userA");

        // Mocking: boardRepository.findById(1L)이 호출되면 Optional.of(mockBoard)를 반환하도록 가짜 동작 정의
        when(boardRepository.findById(boardId)).thenReturn(Optional.of(mockBoard));

        // When (실행)
        Optional<Board> result = boardService.getBoardById(boardId);

        // Then (검증)
        assertTrue(result.isPresent());
        assertEquals("제목", result.get().getSubject());

        // verify: findById(1L) 메서드가 실제로 1번 호출되었는지 검증
        verify(boardRepository, times(1)).findById(boardId);
    }


    @Test
    void createBoard_정상_생성() {
        // Given (준비 단계)
        // 1. Service가 기대하는 타입인 BoardRequest DTO를 생성합니다.
        BoardRequest request = new BoardRequest("새 글 제목", "새 글 내용");

        // 2. Repository가 반환할 Mock 결과를 만듭니다. (Service가 DTO를 변환하여 저장할 Board 엔티티)
        // 실제 Board 객체의 생성자 시그니처에 맞게 데이터를 채워줍니다.
        Board mockSavedBoard = new Board(request.getSubject(), request.getContent(), "tester");

        // Mocking: save가 어떤 Board 객체로 호출되든 mockSavedBoard를 반환하도록 설정
        when(boardRepository.save(any(Board.class))).thenReturn(mockSavedBoard);

        // When (실행 단계)
        // 3. createBoard 메서드에 BoardRequest 객체를 전달하여 호출합니다.
        Board created = boardService.createBoard(request);

        // Then (검증 단계)
        assertNotNull(created);
        assertEquals("새 글 제목", created.getSubject()); // Subject 필드 검증

        // Optional: Repository의 save가 1번 호출되었는지 검증
        verify(boardRepository, times(1)).save(any(Board.class));
    }

    @AfterAll
    static void tesat(){
        System.out.println("종료");
    }
}
