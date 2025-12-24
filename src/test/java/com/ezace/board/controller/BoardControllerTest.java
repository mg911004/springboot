package com.ezace.board.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ezace.board.domain.Board;
import com.ezace.board.dto.board.BoardRequest;
import com.ezace.board.dto.board.BoardResponse;
import com.ezace.board.exception.NotFoundException;
import com.ezace.board.service.BoardService;

class BoardControllerTest {

    @Mock
    private BoardService boardService;

    @InjectMocks
    private BoardController boardController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(boardController).build();
    }

    @Test
    void testGetBoardList() throws Exception {
        Board board = new Board(); // 실제 Board 객체를 생성해줘야 함
        board.setBoardId(1L);
        board.setSubject("Test Subject");
        board.setContent("Test Content");
        BoardResponse boardResponse = BoardResponse.from(board);

        when(boardService.getBoardList(null, null, null)).thenReturn(Collections.singletonList(board));

        mockMvc.perform(get("/api/board"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].subject").value("Test Subject"))
                .andExpect(jsonPath("$[0].content").value("Test Content"));
    }

    @Test
    void testGetBoardById() throws Exception {
        Board board = new Board(); // 실제 Board 객체를 생성
        board.setBoardId(1L);
        board.setSubject("Test Subject");
        board.setContent("Test Content");
        BoardResponse boardResponse = BoardResponse.from(board);

        when(boardService.getBoardById(1L)).thenReturn(Optional.of(board));

        mockMvc.perform(get("/api/board/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subject").value("Test Subject"))
                .andExpect(jsonPath("$.content").value("Test Content"));
    }

    @Test
    void testGetBoardByIdNotFound() throws Exception {
        when(boardService.getBoardById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/board/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateBoard() throws Exception {
        BoardRequest boardRequest = new BoardRequest("Test Subject", "Test Content");
        Board board = new Board(); // 실제 Board 객체 생성
        board.setBoardId(1L);
        board.setSubject("Test Subject");
        board.setContent("Test Content");

        when(boardService.createBoard(boardRequest)).thenReturn(board);

        mockMvc.perform(post("/api/board")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"subject\": \"Test Subject\", \"content\": \"Test Content\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.subject").value("Test Subject"))
                .andExpect(jsonPath("$.content").value("Test Content"));
    }

    @Test
    void testUpdateBoard() throws Exception {
        BoardRequest boardRequest = new BoardRequest("Updated Subject", "Updated Content");
        Board board = new Board(); // 실제 Board 객체 생성
        board.setBoardId(1L);
        board.setSubject("Updated Subject");
        board.setContent("Updated Content");

        when(boardService.updateBoard(1L, boardRequest)).thenReturn(board);

        mockMvc.perform(put("/api/board/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"subject\": \"Updated Subject\", \"content\": \"Updated Content\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subject").value("Updated Subject"))
                .andExpect(jsonPath("$.content").value("Updated Content"));
    }

    @Test
    void testUpdateBoardNotFound() throws Exception {
        BoardRequest boardRequest = new BoardRequest("Updated Subject", "Updated Content");

        when(boardService.updateBoard(1L, boardRequest)).thenThrow(NotFoundException.class);

        mockMvc.perform(put("/api/board/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"subject\": \"Updated Subject\", \"content\": \"Updated Content\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteBoard() throws Exception {
        doNothing().when(boardService).deleteBoard(1L);

        mockMvc.perform(delete("/api/board/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteBoardNotFound() throws Exception {
        doThrow(NotFoundException.class).when(boardService).deleteBoard(1L);

        mockMvc.perform(delete("/api/board/1"))
                .andExpect(status().isNotFound());
    }
}
