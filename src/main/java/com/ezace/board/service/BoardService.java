package com.ezace.board.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezace.board.domain.Board;
import com.ezace.board.dto.board.BoardRequest;
import com.ezace.board.exception.NotFoundException;
import com.ezace.board.repository.BoardRepository;

@Service
@Transactional(readOnly = true)
public class BoardService {

    private static final String DEFAULT_USER_ID = "testUserId";
    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public List<Board> getBoardList(String userId, String subject, String content) {
        List<Board> boards = boardRepository.findAll();

        // 조건에 맞는 필터링
        boards = boards.stream()
                .filter(board -> (userId == null || board.getUserId().equals(userId)))  // userId 필터링
                .filter(board -> (subject == null || board.getSubject().contains(subject)))  // subject 필터링
                .filter(board -> (content == null || board.getContent().contains(content)))  // content 필터링
                .collect(Collectors.toList());

        return boards;
    }


    public Optional<Board> getBoardById(Long boardId) {
        return boardRepository.findById(boardId);
    }

    @Transactional
    public Board createBoard(BoardRequest request) {
        Board board = new Board(request.getSubject(), request.getContent() , DEFAULT_USER_ID);
        return boardRepository.save(board);
    }

    @Transactional
    public Board updateBoard(Long boardId, BoardRequest request) {
        Board board =
            boardRepository
                .findById(boardId)
                .orElseThrow(() -> new NotFoundException("Board not found with id: " + boardId));

        board.update(request.getSubject(), request.getContent());

        return boardRepository.save(board);
    }

    @Transactional
    public void deleteBoard(Long boardId) {
        Board board =
            boardRepository
                .findById(boardId)
                .orElseThrow(() -> new NotFoundException("Board not found with id: " + boardId));
        boardRepository.delete(board);
    }
}
