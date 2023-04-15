package com.homework.hanghaeboard.entity;

import com.homework.hanghaeboard.dto.BoardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

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
    private String userpwd;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String contents;

    public Board(String username, String userpwd, String title, String contents) {
        this.username = username;
        this.userpwd = userpwd;
        this.title = title;
        this.contents = contents;
    }

    public Board(BoardRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.userpwd = requestDto.getUserpwd();
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

    public void update(BoardRequestDto boardRequestDto) {
        this.username = boardRequestDto.getUsername();
        this.contents = boardRequestDto.getContents();
    }

}