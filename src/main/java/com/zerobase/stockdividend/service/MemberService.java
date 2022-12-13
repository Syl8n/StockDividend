package com.zerobase.stockdividend.service;

import com.zerobase.stockdividend.model.Auth;
import com.zerobase.stockdividend.model.MemberEntity;
import com.zerobase.stockdividend.persist.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("couldn't find user -> " + username));
    }

    public MemberEntity register(Auth.SignUp member){
        if(memberRepository.existsByUsername(member.getUsername())){
            throw new RuntimeException("이미 사용 중인 아이디입니다.");
        }

        member.setPassword(passwordEncoder.encode(member.getPassword()));

        return memberRepository.save(member.toEntity());
    }

    public MemberEntity authenticate(Auth.SignIn member) {
        var user = memberRepository.findByUsername(member.getUsername())
            .orElseThrow(() -> new RuntimeException("존재하지 않는 ID 입니다"));

        if (!passwordEncoder.matches(member.getPassword(), user.getPassword())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
        return user;
    }
}
