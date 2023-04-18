package com.homework.hanghaeboard.controller;

import com.homework.hanghaeboard.dto.LoginRequestDto;
import com.homework.hanghaeboard.dto.SignupRequestDto;
import com.homework.hanghaeboard.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;

    @GetMapping("/api/user/signup")
    public ModelAndView signupPage() {
        return new ModelAndView("signup");
    }

    @GetMapping("/api/user/login")
    public ModelAndView loginPage() {
        return new ModelAndView("login");
    }

    @PostMapping("/api/user/signup")
    public String signUp(@RequestBody SignupRequestDto signupRequestDto) {
        if (userService.signup(signupRequestDto).isSuccess()) {
            return "redirect:/api/user/login";
        } else return null;
    }

    @PostMapping("/api/user/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);
    }
}
