package com.zerobase.stockdividend.model;

import com.zerobase.stockdividend.persist.entity.MemberEntity;
import java.util.List;
import lombok.Data;

public class Auth {

    @Data
    public static class SignIn {
        private String username;
        private String password;
    }

    @Data
    public static class SignUp {
        private String username;
        private String password;
        private List<String> roles;

        public MemberEntity toEntity() {
            return MemberEntity.builder()
                .username(username)
                .password(password)
                .roles(roles)
                .build();
        }
    }
}
