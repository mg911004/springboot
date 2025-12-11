package com.ezace.board.dto.notice;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class NoticeRequest {

    @NotBlank
    @Size(max = 255)
    private String subject;

    @NotBlank private String content;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public NoticeRequest() {}

    public NoticeRequest(
            String subject, String content, LocalDateTime startDate, LocalDateTime endDate) {
        this.subject = subject;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
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
