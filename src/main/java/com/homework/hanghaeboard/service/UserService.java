package com.homework.hanghaeboard.service;

import com.homework.hanghaeboard.dto.LoginRequestDto;
import com.homework.hanghaeboard.dto.ResponseDto;
import com.homework.hanghaeboard.dto.SignupRequestDto;
import com.homework.hanghaeboard.entity.User;
import com.homework.hanghaeboard.jwt.JwtUtil;
import com.homework.hanghaeboard.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional
    public ResponseDto<?> signup(SignupRequestDto signupRequestDto){
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();

        if (!(Pattern.matches("^[a-z0-9]{4,10}$", username) && Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9]{8,15}$", password))){
            return ResponseDto.setFailed("아이디, 비밀번호가 유효하지 않습니다.",400);
        }
        User user = new User(signupRequestDto);
        userRepository.save(user);
        return ResponseDto.setSuccess("회원가입 완료",null);
    }

    @Transactional(readOnly = true)
    public ResponseDto<?> login(LoginRequestDto loginRequestDto, HttpServletResponse response){
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        if (user.getPassword().equals(password)) {
            response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
            return ResponseDto.setSuccess("로그인 완료", null);
        } else return ResponseDto.setFailed("비밀번호가 틀렸습니다", 401);
    }
}
