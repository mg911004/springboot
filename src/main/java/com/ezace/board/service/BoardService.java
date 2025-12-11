package com.ezace.board.service;

import java.util.List;
import java.util.Optional;

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
    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Board> getBoardById(Long boardId) {
        return boardRepository.findById(boardId);
    }

    public Board createBoard(BoardRequest request) {
        Board board = new Board(request.getSubject(), request.getContent());
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
