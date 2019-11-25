package com.finmanager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.finmanager.dto.Transafer.ExistingRecord;
import com.finmanager.dto.Transafer.NewRecord;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;
import java.util.Objects;

public class TransactionDto {
    @Null(groups = {NewRecord.class},
            message = "This field must be empty due to auto generation")
    @NotNull(groups = {ExistingRecord.class},
            message = "This field can't be empty")
    private Long id;

    @NotNull(groups = {ExistingRecord.class, NewRecord.class},
            message = "This field can't be empty")
    private Long categoryId;

    @NotNull(groups = {ExistingRecord.class, NewRecord.class},
            message = "This field can't be empty")
    private Long operationId;

    @NotNull(groups = {ExistingRecord.class, NewRecord.class},
            message = "This field can't be empty")
    private Long userId;


    @NotNull(groups = {ExistingRecord.class, NewRecord.class},
            message = "This field can't be empty")
    private Double sum;

    @NotBlank(groups = {NewRecord.class, ExistingRecord.class},
            message = "Please, provide an description")
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createdDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime updatedDate;

    public TransactionDto() {
    }

    public TransactionDto(Long id, Long categoryId, Long operationId, Long userId, Double sum, String description, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.categoryId = categoryId;
        this.operationId = operationId;
        this.userId = userId;
        this.sum = sum;
        this.description = description;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransactionDto)) return false;
        TransactionDto that = (TransactionDto) o;
        return id.equals(that.id) &&
                categoryId.equals(that.categoryId) &&
                operationId.equals(that.operationId) &&
                userId.equals(that.userId) &&
                sum.equals(that.sum) &&
                description.equals(that.description) &&
                createdDate.equals(that.createdDate) &&
                updatedDate.equals(that.updatedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, categoryId, operationId, userId, sum, description, createdDate, updatedDate);
    }

    @Override
    public String toString() {
        return "TransactionDto{" +
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
