package com.homework.hanghaeboard.entity;

import com.homework.hanghaeboard.dto.BoardRequestDto;
import com.homework.hanghaeboard.repository.BoardRepository;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name ="BOARD")
@NoArgsConstructor
public class Board extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long boardId;
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

    public Board(Users user, BoardRequestDto requestDto) {
        this.user = user;
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

    public void update(BoardRequestDto boardRequestDto) {
        this.contents = boardRequestDto.getContents();
    }
}