package com.example.semestrovkafoodz.utils;

import com.example.semestrovkafoodz.dtos.JwtResponse;
import com.example.semestrovkafoodz.repositories.UserRepository;
import com.google.gson.Gson;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
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


    public JwtResponse generateToken(UserDetails userDetails) {
        Map<String, Object> claimsAccess = new HashMap<>();
        Map<String, Object> claimsRefresh = new HashMap<>();
        List<String> roles = new ArrayList<>();
        roles.add(userRepository.findByUsername(userDetails.getUsername()).get().getRole());
        userRepository.findByUsername(userDetails.getUsername()).get().getRole();
        claimsAccess.put("roles", roles);
        claimsAccess.put("type", "access");
        claimsRefresh.put("type", "refresh");
        claimsRefresh.put("roles", roles);

        Date issuedDate = new Date();
        Date accessExpiredDate = new Date(issuedDate.getTime() + accessJwtLifetime.toMillis());
        Date refreshExpiredDate = new Date(issuedDate.getTime() + refreshJwtLifetime.toMillis());

        return new JwtResponse
                (Jwts.builder()
                        .setClaims(claimsAccess)
                        .setSubject(userDetails.getUsername())
                        .setIssuedAt(issuedDate)
                        .setHeaderParam("typ", "JWT")
                        .setExpiration(accessExpiredDate)
                        .signWith(SignatureAlgorithm.HS256, accessSecret)
                        .compact(),
                        Jwts.builder()
                                .setClaims(claimsRefresh)
                                .setSubject(userDetails.getUsername())
                                .setIssuedAt(issuedDate)
                                .setExpiration(refreshExpiredDate)
                                .signWith(SignatureAlgorithm.HS256, refreshSecret)
                                .compact()
                );
    }

    public String getUsername(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public List<String> getRoles(String token) {
        return getAllClaimsFromToken(token).get("roles", List.class);
    }

    private Claims getAllClaimsFromToken(String token) {
        String type = extractTokenType(token);
        if (type.equals("access")) {
            return Jwts.parserBuilder().
                    setSigningKey(accessSecret)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } else {
            return Jwts.parserBuilder().
                    setSigningKey(refreshSecret)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }
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
