package com.homework.hanghaeboard.service;

import com.homework.hanghaeboard.dto.BoardRequestDto;
import com.homework.hanghaeboard.dto.ResponseDto;
import com.homework.hanghaeboard.entity.Board;
import com.homework.hanghaeboard.jwt.JwtUtil;
import com.homework.hanghaeboard.repository.BoardRepository;
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
    private final JwtUtil jwtUtil;

    @Transactional
    public ResponseDto<?> createBoard(BoardRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);

        if(token == null) return ResponseDto.setFailed("토큰이 존재하지 않음", 401);

        if(!jwtUtil.validateToken(token)) return ResponseDto.setFailed("토큰이 유효하지 않음" ,403);

        Board board = new Board(requestDto);
        boardRepository.save(board);
        return ResponseDto.setSuccess("작성 완료!",board);
    }

    @Transactional
    public ResponseDto<?> getBoards() {
        List<Board> boardList= boardRepository.findAllByOrderByModifiedAtDesc();
        return ResponseDto.setSuccess("전체 게시물 조회 완료", boardList);
    }

    @Transactional
    public ResponseDto<?> getBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        return ResponseDto.setSuccess("게시물 조회 완료", board);
    }

    @Transactional
    public ResponseDto<?> update(Long id, BoardRequestDto requestDto, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        Claims claims;
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        if (token == null) return ResponseDto.setFailed("토큰이 존재하지 않음", 401);

        if (!jwtUtil.validateToken(token)) return ResponseDto.setFailed("토큰이 유효하지 않음",403);

        claims = jwtUtil.getUserInfoFromToken(token);
        if (board.getUser().getUsername().equals(claims.getSubject())) {
            board.update(requestDto);
        }
        return ResponseDto.setSuccess("수정 완료", board);
    }

    @Transactional
    public ResponseDto<?> deleteBoard(Long id, HttpServletRequest request) {

        String token = jwtUtil.resolveToken(request);
        Claims claims;
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        if (token == null) return ResponseDto.setFailed("토큰이 존재하지 않음", 401);

        if (!jwtUtil.validateToken(token)) return ResponseDto.setFailed("토큰이 유효하지 않음",403);

        claims = jwtUtil.getUserInfoFromToken(token);
        if (board.getUser().getUsername().equals(claims.getSubject())) {
            boardRepository.deleteById(id);
        }
        return ResponseDto.setSuccess("삭제 완료",null);
    }

}