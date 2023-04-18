package com.homework.hanghaeboard.service;

import com.homework.hanghaeboard.dto.BoardRequestDto;
import com.homework.hanghaeboard.dto.ResponseDto;
import com.homework.hanghaeboard.entity.Board;
import com.homework.hanghaeboard.entity.User;
import com.homework.hanghaeboard.jwt.JwtUtil;
import com.homework.hanghaeboard.repository.BoardRepository;
import com.homework.hanghaeboard.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public ResponseDto<?> createBoard(BoardRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            Board board = new Board(requestDto);
            boardRepository.save(board);
            return ResponseDto.setSuccess(board);
        } else return ResponseDto.setFailed();
    }

    @Transactional
    public ResponseDto<?> getBoards() {
        List<Board> boardList= boardRepository.findAllByOrderByModifiedAtDesc();
        return ResponseDto.setSuccess(boardList);
    }

    @Transactional
    public ResponseDto<?> getBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        return ResponseDto.setSuccess(board);
    }

    @Transactional
    public ResponseDto<?> update(Long id, BoardRequestDto requestDto) {

        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );

        if (board.getPassword().equals(requestDto.getPassword()) ) {
            board.update(requestDto);
            return ResponseDto.setSuccess(board);
        } else return ResponseDto.setFailed();
    }

    @Transactional
    public ResponseDto<?> deleteBoard(Long id, BoardRequestDto requestDto) {

        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        if (board.getPassword().equals(requestDto.getPassword()) ) {
            boardRepository.deleteById(id);
            return ResponseDto.setSuccess(null);
        } else return ResponseDto.setFailed();
    }

}