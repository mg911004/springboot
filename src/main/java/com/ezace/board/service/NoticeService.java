package com.ezace.board.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezace.board.domain.Notice;
import com.ezace.board.exception.NotFoundException;
import com.ezace.board.repository.NoticeRepository;

@Service
@Transactional
public class NoticeService {

    private final NoticeRepository noticeRepository;

    @Autowired
    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    @Transactional(readOnly = true)
    public List<Notice> getAllNotices() {
        return noticeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Notice> getNoticeById(Long noticeId) {
        return noticeRepository.findById(noticeId);
    }

    public Notice createNotice(Notice notice) {
        return noticeRepository.save(notice);
    }

    public Notice updateNotice(Long noticeId, Notice noticeDetails) {
        Notice notice =
                noticeRepository
                        .findById(noticeId)
                        .orElseThrow(
                                () ->
                                        new NotFoundException(
                                                "Notice not found with id: " + noticeId));

        notice.setSubject(noticeDetails.getSubject());
        notice.setContent(noticeDetails.getContent());
        notice.setStartDate(noticeDetails.getStartDate());
        notice.setEndDate(noticeDetails.getEndDate());

        return noticeRepository.save(notice);
    }

    public void deleteNotice(Long noticeId) {
        Notice notice =
                noticeRepository
                        .findById(noticeId)
                        .orElseThrow(
                                () ->
                                        new NotFoundException(
                                                "Notice not found with id: " + noticeId));
        noticeRepository.delete(notice);
    }
}
