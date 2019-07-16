package com.finmanager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Objects;

public class TransactionDto {
    private Long id;
    private Long categoryId;
    private Long operationId;
    private Double sum;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createdDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime updatedDate;

    public TransactionDto() {
    }

    public TransactionDto(Long id, Long categoryId, Long operationId, Double sum, String description, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.categoryId = categoryId;
        this.operationId = operationId;
        this.sum = sum;
        this.description = description;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getOperationId() {
        return operationId;
    }

    public void setOperationId(Long operationId) {
        this.operationId = operationId;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransactionDto)) return false;
        TransactionDto that = (TransactionDto) o;
        return id.equals(that.id) &&
                Objects.equals(categoryId, that.categoryId) &&
                Objects.equals(operationId, that.operationId) &&
                Objects.equals(sum, that.sum) &&
                Objects.equals(description, that.description) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(updatedDate, that.updatedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, categoryId, operationId, sum, description, createdDate, updatedDate);
    }

    @Override
    public String toString() {
        return "TransactionDto{" +
                "id=" + id +
                ", categoryId=" + categoryId +
                ", operationId=" + operationId +
                ", sum=" + sum +
                ", description='" + description + '\'' +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
