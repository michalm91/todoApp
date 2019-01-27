package com.mmatuszewski.todo.web.service;

import com.mmatuszewski.todo.domain.model.User;
import com.mmatuszewski.todo.domain.repository.UserRepository;
import com.mmatuszewski.todo.security.jwt.JwtProvider;
import com.mmatuszewski.todo.web.dto.Login;
import com.mmatuszewski.todo.web.dto.Register;
import com.mmatuszewski.todo.web.dto.UserPrinciple;
import com.mmatuszewski.todo.web.response.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private AuthenticationManager authenticationManager;



    /**
     * Perform adding task for User.
     * If user doesn't exists - throw ResponseStatusException
     *
     * @param userRequest
     */
    public void addUser(Register userRequest)
    {
        if(isUserExist(userRequest))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot add user. User exists!!!");

        persistUser(userRequest);
    }


    /**
     * Perform authentication for User.
     * Authenticate User by given credential.
     * Set authorization for context.
     *
     * @param loginRequest
     * @return JwtResponse
     */
    public JwtResponse authenticateUser (Login loginRequest)
    {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword(),
                        Collections.emptyList()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        return new JwtResponse(jwt);
    }

    /**
     * Perform finding user by username.
     * If user doesn't exist - throw UsernameNotFoundException
     *
     * @param username
     * @return UserDetails
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User Not Found")
                );

       return UserPrinciple.build(user);
    }

    private boolean isUserExist(Register userRequest)
    {
        return userRepository.existsByUsername(userRequest.getUsername());
    }

    private void persistUser(Register userRequest)
    {
        User user = new User(userRequest.getUsername(), encoder.encode(userRequest.getPassword()), userRequest.getEmail());
        userRepository.save(user);
    }
}
