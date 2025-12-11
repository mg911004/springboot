package com.ezace.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ezace.board.domain.Notice;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {}
