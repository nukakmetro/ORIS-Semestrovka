package com.example.semestrovkafoodz.service;

import com.example.semestrovkafoodz.service.details.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.example.semestrovkafoodz.dtos.RegistrationUserDto;
import com.example.semestrovkafoodz.entities.UserEntity;
import com.example.semestrovkafoodz.repositories.UserRepository;

import java.util.Optional;

@Component("customUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("Пользователь '%s' не найден", username)
        ));
        return new UserDetailsImpl(userEntity);
    }

    public UserEntity createNewUser(RegistrationUserDto registrationUserDto) {
        UserEntity newUserEntity = UserEntity.builder()
                .username(registrationUserDto.getUsername())
                .password(passwordEncoder.encode(registrationUserDto.getPassword()))
                .role(UserEntity.Role.USER)
                .state(UserEntity.State.ACTIVE)
                .build();
        if (userRepository.findByUsername(newUserEntity.getUsername()).isPresent()) {
            throw new IllegalArgumentException("User with this email already exist");
        }
        return userRepository.save(newUserEntity);
    }
}
