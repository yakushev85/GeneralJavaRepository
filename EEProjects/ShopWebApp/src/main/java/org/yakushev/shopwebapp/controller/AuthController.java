package org.yakushev.shopwebapp.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.yakushev.shopwebapp.bean.AuthRequest;
import org.yakushev.shopwebapp.bean.AuthResponse;
import org.yakushev.shopwebapp.model.User;
import org.yakushev.shopwebapp.security.JwtTokenRepository;
import org.yakushev.shopwebapp.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/")
public class AuthController {
    @Autowired
    private Gson gson;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenRepository jwtTokenRepository;

    @Transactional
    @RequestMapping(path = "/login", method = RequestMethod.POST, produces = "application/json")
    public String loginUser(@RequestBody AuthRequest authRequest, HttpServletRequest request, HttpServletResponse response) {
        List<User> resolvedUserList = userService.findByUsernameOrderByIdDesc(authRequest.getUsername());

        if (resolvedUserList != null && !resolvedUserList.isEmpty()) {
            User resolvedUser = resolvedUserList.get(0);
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            if (!passwordEncoder.matches(authRequest.getPassword(), resolvedUser.getPassword())) {
                throw new IllegalArgumentException("Wrong password.");
            }

            request.setAttribute(User.class.getName(), resolvedUser.getUsername());
            CsrfToken csrfToken = jwtTokenRepository.generateToken(request);
            jwtTokenRepository.saveToken(csrfToken, request, response);

            AuthResponse authResponse = new AuthResponse();
            authResponse.setId(resolvedUser.getId());
            authResponse.setUsername(resolvedUser.getUsername());
            authResponse.setToken(csrfToken.getToken());

            return gson.toJson(authResponse);
        } else {
            throw new IllegalArgumentException("Wrong username.");
        }
    }

    @Transactional
    @RequestMapping(path = "/signup", method = RequestMethod.POST, produces = "application/json")
    public String signupUser(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) {
        List<User> resolvedUserList = userService.findByUsernameOrderByIdDesc(user.getUsername());

        if (resolvedUserList == null || resolvedUserList.isEmpty()) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User storedUser = userService.add(user);

            if (storedUser != null) {
                request.setAttribute(User.class.getName(), storedUser.getUsername());
                CsrfToken csrfToken = jwtTokenRepository.generateToken(request);
                jwtTokenRepository.saveToken(csrfToken, request, response);

                AuthResponse authResponse = new AuthResponse();
                authResponse.setId(storedUser.getId());
                authResponse.setUsername(storedUser.getUsername());
                authResponse.setToken(csrfToken.getToken());

                return gson.toJson(authResponse);
            }
        }

        throw new IllegalArgumentException("Username is already used.");
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET, produces = "application/json")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        String actualToken = request.getHeader("x-csrf-token");

        if (actualToken != null && !actualToken.isEmpty()) {
            jwtTokenRepository.clearToken(response);
        }

        return "{}";
    }
}