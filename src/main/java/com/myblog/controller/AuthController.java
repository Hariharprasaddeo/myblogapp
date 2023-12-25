package com.myblog.controller;

import com.myblog.dto.JWTAuthResponse;
import com.myblog.dto.LoginDto;
import com.myblog.dto.SignUpDto;
import com.myblog.entity.Role;
import com.myblog.entity.User;
import com.myblog.repository.RoleRepository;
import com.myblog.repository.UserRepository;
import com.myblog.secuirity.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    //-------------------signUp Endpoint------------------------------------------------
    @PostMapping("/signUp")
    public ResponseEntity<?> createUser(@RequestBody SignUpDto signUpDto) {

        if (userRepository.existsByUsername(signUpDto.getUsername())) {
            return new ResponseEntity<>("User is already exist", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            return ResponseEntity.badRequest().body("Email is already exist");
        }

        User user = new User();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode((signUpDto.getPassword())));

        Role role = roleRepository.findByRole("ROLE_ADMIN");   // only accessed by admin with ADMIN ROLE
    //  Role role = roleRepository.findByRole("ROLE_USER");    // only accessed by user with USER ROLE
    //  user.setRoles(Collections.singleton(role));

    // ---------------------------------OR---------------------------------------------------------------

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        User savedUser=userRepository.save(user);

        SignUpDto dto = new SignUpDto();
        dto.setEmail(savedUser.getEmail());
        dto.setUsername(savedUser.getUsername());
        dto.setName(savedUser.getName());
        return new ResponseEntity<>(dto, HttpStatus.CREATED);

    }
// --------------------------------signIn Endpoint---------------------------------------------------------------------
    @PostMapping("/signIn")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDto loginDto){
       Authentication authentication = authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
       // return new ResponseEntity<>("User Sign-In successfully", HttpStatus.OK);

        // get token from tokenProvider   ( 25/10/23 )
        String token = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JWTAuthResponse(token));

    }
// --------------------------logout Endpoint----------------------------------------------------------------------------
//    @PostMapping("/logout")
//    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null) {
//            new SecurityContextLogoutHandler().logout(request, response, authentication);
//        }
//        return new ResponseEntity<>("Logout successful", HttpStatus.OK);
//    }
//
//// -------------------Add a new endpoint for logout success-------------------------------------------------------------
//    @GetMapping("/logout-success")
//    public ResponseEntity<String> logoutSuccess() {
//        return new ResponseEntity<>("Logout was successful. You have been logged out.", HttpStatus.OK);
//    }


}
