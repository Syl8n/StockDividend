package com.zerobase.stockdividend.web;

import com.zerobase.stockdividend.Security.TokenProvider;
import com.zerobase.stockdividend.model.Auth;
import com.zerobase.stockdividend.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final MemberService memberService;
    private final TokenProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Auth.SignUp request){
        return ResponseEntity.ok(memberService.register(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody Auth.SignIn request){
        // 패스워드 검증
        var member = memberService.authenticate(request);
        // 토큰 생성 & 반환
        var token = tokenProvider.generateToken(member.getUsername(), member.getRoles());

        return ResponseEntity.ok(token);
    }
}
