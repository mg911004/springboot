package com.ezace.board.service;

import java.util.ArrayList;
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
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Transactional(readOnly = true)
    public List<Board> getAllBoards(String userId, String subject, String content) {
        List<Board> boards = boardRepository.findAll();

        // 조건에 맞는 필터링
        boards = boards.stream()
                .filter(board -> (userId == null || board.getUserId().equals(userId)))  // userId 필터링
                .filter(board -> (subject == null || board.getSubject().contains(subject)))  // subject 필터링
                .filter(board -> (content == null || board.getContent().contains(content)))  // content 필터링
                .collect(Collectors.toList());
    
        return boards;
    }

    
    @Transactional(readOnly = true)
    public Optional<Board> getBoardById(Long boardId) {
        return boardRepository.findById(boardId);
    }

        
    public Board createBoard(BoardRequest request) {
        System.out.println(request);
        Board board = new Board(request.getSubject(), request.getContent() , "testUserId");
        return boardRepository.save(board);
    }

    public Board updateBoard(Long boardId, BoardRequest request) {
        Board board =
            boardRepository
                .findById(boardId)
                .orElseThrow(() -> new NotFoundException("Board not found with id: " + boardId));

        board.update(request.getSubject(), request.getContent());

        return boardRepository.save(board);
    }

    public void deleteBoard(Long boardId) {
        Board board =
            boardRepository
                .findById(boardId)
                .orElseThrow(() -> new NotFoundException("Board not found with id: " + boardId));
        boardRepository.delete(board);
    }
}
