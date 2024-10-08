package org.example.devkorchat.chat;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "chat")
public class ChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "USERNAME", nullable = false)
    private String username;

    @Column(name = "MESSAGE", nullable = false)
    private String message;

    @Column(name = "ROOM_NUMBER", nullable = false)
    private int roomNumber;

    @Column(name = "CREATED_AT", nullable = false)
        private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public ChatEntity(String username, String message, int roomNumber) {
        this.username = username;
        this.message = message;
        this.roomNumber = roomNumber;
    }

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        LocalDateTime now = LocalDateTime.now();
        this.updatedAt = now;
    }

}
