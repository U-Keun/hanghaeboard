package com.homework.hanghaeboard.entity;

import com.homework.hanghaeboard.dto.BoardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name ="BOARD")
@NoArgsConstructor
public class Board extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private Users user;

    public Board(Users user, String title, String contents) {
        this.user = user;
        this.title = title;
        this.contents = contents;
    }

    public Board(BoardRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

    public void update(BoardRequestDto boardRequestDto) {
        this.username = boardRequestDto.getUsername();
        this.contents = boardRequestDto.getContents();
    }
}