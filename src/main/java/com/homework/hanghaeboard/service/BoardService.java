package com.homework.hanghaeboard.service;

import com.homework.hanghaeboard.dto.BoardRequestDto;
import com.homework.hanghaeboard.dto.ResponseDto;
import com.homework.hanghaeboard.entity.Board;
import com.homework.hanghaeboard.entity.Users;
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
    public ResponseDto<Board> createBoard(BoardRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);

        if(token == null) return ResponseDto.setFailed("토큰이 존재하지 않음", 401);

        try {
            jwtUtil.validateToken(token);
        } catch (Exception e) {
            return ResponseDto.setFailed("토큰이 유효하지 않음", 403);
        }

        Users user = userRepository.findByUsername(requestDto.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );

        Board board = new Board(user, requestDto);
        boardRepository.save(board);
        return ResponseDto.setSuccess("작성 완료!", board);
    }

    @Transactional
    public ResponseDto<List<Board>> getBoards() {
        List<Board> boardList= boardRepository.findAllByOrderByModifiedAtDesc();
        return ResponseDto.setSuccess("전체 게시물 조회 완료", boardList);
    }

    @Transactional
    public ResponseDto<Board> getBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시물이 존재하지 않습니다.")
        );
        return ResponseDto.setSuccess("게시물 조회 완료", board);
    }

    @Transactional
    public ResponseDto<Board> update(Long id, BoardRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        if (token == null) return ResponseDto.setFailed("토큰이 존재하지 않음", 401);

        try {
            jwtUtil.validateToken(token);
        } catch (Exception e) {
            return ResponseDto.setFailed("토큰이 유효하지 않음", 403);
        }

        claims = jwtUtil.getUserInfoFromToken(token);
        if (board.getUser().getUsername().equals(claims.getSubject())) {
            board.update(requestDto);
        } else return ResponseDto.setFailed("수정할 권한이 없음", 403);

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

        try {
            jwtUtil.validateToken(token);
        } catch (Exception e) {
            return ResponseDto.setFailed("토큰이 유효하지 않음", 403);
        }

        claims = jwtUtil.getUserInfoFromToken(token);
        if (board.getUser().getUsername().equals(claims.getSubject())) {
            boardRepository.deleteById(id);
        } else return ResponseDto.setFailed("삭제할 권한이 없음", 403);

        return ResponseDto.setSuccess("삭제 완료",null);
    }

}