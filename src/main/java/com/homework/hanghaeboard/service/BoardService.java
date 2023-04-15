package com.homework.hanghaeboard.service;

import com.homework.hanghaeboard.dto.BoardRequestDto;
import com.homework.hanghaeboard.entity.Board;
import com.homework.hanghaeboard.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Board createBoard(BoardRequestDto requestDto) {
        Board board = new Board(requestDto);
        boardRepository.save(board);
        return board;
    }

    @Transactional
    public List<Board> getBoard() {
        return boardRepository.findAllByOrderByModifiedAtDesc();
    }

    @Transactional
    public Long update(Long id, BoardRequestDto requestDto) {

        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );

        if (boardRepository.findByUserpwd(requestDto.getUserpwd()) != null) {
            board.update(requestDto);
            return board.getId();
        } else return null;
    }

    @Transactional
    public Long deleteBoard(Long id, BoardRequestDto requestDto) {

        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        if (boardRepository.findByUserpwd(requestDto.getUserpwd()) != null) {
            boardRepository.deleteById(id);
            return id;
        } else return null;
    }

}