package com.finmanager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.finmanager.model.Role;

import java.time.LocalDateTime;
import java.util.Objects;

public class UserDto {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String surname;
    private String phone;
    private Role role;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createdDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime updatedDate;

    public UserDto(Long id, String email, String password, String name, String surname, String phone, Role role, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.role = role;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public UserDto() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                ", role=" + role +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDto)) return false;
        UserDto userDto = (UserDto) o;
        return id.equals(userDto.id) &&
                email.equals(userDto.email) &&
                password.equals(userDto.password) &&
                name.equals(userDto.name) &&
                surname.equals(userDto.surname) &&
                phone.equals(userDto.phone) &&
                role == userDto.role &&
                createdDate.equals(userDto.createdDate) &&
                updatedDate.equals(userDto.updatedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, name, surname, phone, role, createdDate, updatedDate);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

