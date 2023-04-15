package com.isep.acme.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.isep.acme.domain.model.User;
import com.isep.acme.domain.repository.UserRepository;
import com.isep.acme.dto.UserView;
import com.isep.acme.dto.mapper.UserViewMapper;

import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepo;
    @Autowired
    private final UserViewMapper userViewMapper;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User with username - %s, not found", username)));
    }

    public UserView getUser(final UUID userId){
        return userViewMapper.toUserView(userRepo.getById(userId));
    }

    public Optional<User> getUserId(UUID user) {
        return userRepo.findById(user);
    }
}
