package com.ezace.board.dto.notice;

import java.time.LocalDateTime;

import com.ezace.board.domain.Notice;

public class NoticeResponse {

    private Long noticeId;
    private String subject;
    private String content;
    private LocalDateTime regDate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public NoticeResponse() {}

    public NoticeResponse(
            Long noticeId,
            String subject,
            String content,
            LocalDateTime regDate,
            LocalDateTime startDate,
            LocalDateTime endDate) {
        this.noticeId = noticeId;
        this.subject = subject;
        this.content = content;
        this.regDate = regDate;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static NoticeResponse from(Notice notice) {
        return new NoticeResponse(
                notice.getNoticeId(),
                notice.getSubject(),
                notice.getContent(),
                notice.getRegDate(),
                notice.getStartDate(),
                notice.getEndDate());
    }

    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDateTime regDate) {
        this.regDate = regDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
