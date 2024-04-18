package com.example.semestrovkafoodz.utils;

import com.example.semestrovkafoodz.dtos.JwtResponse;
import com.example.semestrovkafoodz.dtos.TokenDto;
import com.example.semestrovkafoodz.entities.TokenEntity;
import com.example.semestrovkafoodz.entities.UserEntity;
import com.example.semestrovkafoodz.repositories.TokenRepository;
import com.example.semestrovkafoodz.repositories.UserRepository;
import com.google.gson.Gson;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.ILoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtils {

    @Value("${jwt.access-secret}")
    private String accessSecret;

    @Value("${jwt.access-lifetime}")
    private Duration accessJwtLifetime;

    @Value("${jwt.refresh-secret}")
    private String refreshSecret;

    @Value("${jwt.refresh-lifetime}")
    private Duration refreshJwtLifetime;

    @Autowired
    public UserRepository userRepository;
    @Autowired
    public TokenRepository tokenRepository;


    public JwtResponse generateToken(UserDetails userDetails) {
        UserEntity userEntity = userRepository.findByUsername(userDetails.getUsername()).get();
        return createTokens(userEntity);
    }

    public JwtResponse createTokens(UserEntity userEntity) {
        Map<String, Object> claims = new HashMap<>();
        List<String> roles = new ArrayList<>();
        roles.add(userEntity.getRole());
        claims.put("roles", roles);

        Date issuedDate = new Date();
        Date accessExpiredDate = new Date(issuedDate.getTime() + accessJwtLifetime.toMillis());
        Date refreshExpiredDate = new Date(issuedDate.getTime() + refreshJwtLifetime.toMillis());

        JwtResponse jwtResponse = new JwtResponse(Jwts.builder()
                .setClaims(claims)
                .setSubject(userEntity.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(accessExpiredDate)
                .signWith(SignatureAlgorithm.HS256, accessSecret)
                .compact(),
                Jwts.builder()
                        .setClaims(claims)
                        .setSubject(userEntity.getUsername())
                        .setIssuedAt(issuedDate)
                        .setExpiration(refreshExpiredDate)
                        .signWith(SignatureAlgorithm.HS256, refreshSecret)
                        .compact());
        TokenEntity tokenEntity = TokenEntity.builder()
                .token(jwtResponse.getRefreshToken())
                .user(userEntity)
                .build();
        tokenRepository.save(tokenEntity);
        return jwtResponse;

    }


    public JwtResponse updateToken(TokenDto tokenDto) {
            Optional<UserEntity> userEntityOptional = userRepository.findByUsername(getUsernameRefresh(tokenDto.getRefreshToken()));
            Optional<TokenEntity> tokenEntityOptional = tokenRepository.findByToken(tokenDto.getRefreshToken());
        if (tokenEntityOptional.isPresent() && userEntityOptional.isPresent()) {
            tokenRepository.deleteById(tokenEntityOptional.get().getId());
            return createTokens(userEntityOptional.get());
        }
        return null;
    }

    public String getUsername(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public String getUsernameRefresh(String token) {
        return getAllClaimsFromRefreshToken(token).getSubject();
    }

    public List<String> getRoles(String token) {
        return getAllClaimsFromToken(token).get("roles", List.class);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().
                setSigningKey(accessSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Claims getAllClaimsFromRefreshToken(String token) {
        return Jwts.parserBuilder().
                setSigningKey(refreshSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private String extractTokenType(String token) {
        String[] parts = token.split("\\.");
        String body = parts[1];
        byte[] decodedBytes = Base64.getUrlDecoder().decode(body);
        String decodedBody = new String(decodedBytes);
        Gson gson = new Gson();
        PayloadModel payloadModel = gson.fromJson(decodedBody, PayloadModel.class);
        String type = payloadModel.getType();
        return type;
    }

    static class PayloadModel {
        private String type;

        public String getType() {
            return type;
        }
    }
}
