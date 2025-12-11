package com.ezace.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ezace.board.domain.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {}
