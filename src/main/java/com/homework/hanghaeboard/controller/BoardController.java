package com.homework.hanghaeboard.controller;

import com.homework.hanghaeboard.dto.BoardRequestDto;
import com.homework.hanghaeboard.dto.ResponseDto;
import com.homework.hanghaeboard.entity.Board;
import com.homework.hanghaeboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("index");
    }

    @PostMapping("/api/board")
    public ResponseDto<?> createBoard(@RequestBody BoardRequestDto requestDto) {
        return boardService.createBoard(requestDto);
    }

    @GetMapping("/api/board")
    public ResponseDto<?> getBoards() {
        return boardService.getBoards();
    }

    @GetMapping("/api/board/{id}")
    public ResponseDto<?> getBoard(@PathVariable Long id) { return boardService.getBoard(id); }

    @PutMapping("/api/board/{id}")
    public ResponseDto<?> updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto) {
        return boardService.update(id, requestDto);
    }

    @DeleteMapping("/api/board/{id}")
    public ResponseDto<?> deleteBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto) {
        return boardService.deleteBoard(id, requestDto);
    }
}
