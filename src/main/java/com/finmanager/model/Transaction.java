package com.finmanager.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Objects;

public class Transaction {
    private Long id;
    private Long categoryId;
    private Long operationId;
    private Long userId;
    private Double sum;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createdDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime updatedDate;

    public Transaction() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        Transaction that = (Transaction) o;
        return id.equals(that.id) &&
                categoryId.equals(that.categoryId) &&
                operationId.equals(that.operationId) &&
                userId.equals(that.userId) &&
                Objects.equals(sum, that.sum) &&
                Objects.equals(description, that.description) &&
                createdDate.equals(that.createdDate) &&
                updatedDate.equals(that.updatedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, categoryId, operationId, userId, sum, description, createdDate, updatedDate);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", categoryId=" + categoryId +
                ", operationId=" + operationId +
                ", userId=" + userId +
                ", sum=" + sum +
                ", description='" + description + '\'' +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }

    public Transaction(Long id, Long categoryId, Long operationId, Long userId, Double sum, String description, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.categoryId = categoryId;
        this.operationId = operationId;
        this.userId = userId;
        this.sum = sum;
        this.description = description;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

}
