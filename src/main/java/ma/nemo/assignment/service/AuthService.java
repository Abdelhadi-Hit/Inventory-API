package ma.nemo.assignment.service;

import ma.nemo.assignment.domain.User;
import ma.nemo.assignment.domain.util.Role;
import ma.nemo.assignment.repository.UserRepository;
import ma.nemo.assignment.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            AuthenticationManager authenticationManager, JwtService jwtService,
            UserDetailsService userDetailsService,
            UserRepository userRepository, PasswordEncoder passwordEncoder
    ){
        this.userDetailsService=userDetailsService;
        this.authenticationManager =authenticationManager;
        this.jwtService=jwtService;
        this.userRepository= userRepository;
        this.passwordEncoder=passwordEncoder;

    }



    public Map<String,String> register(String username,
                                       String password) throws Exception {

        User user =null ;
        try{
            LocalDateTime currentDateTime = LocalDateTime.now();
            user = User.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .roles(List.of(Role.USER))
                    .creationDate(currentDateTime)
                    .lastLoginDate(currentDateTime)
                    .build();

            System.out.println("USER HERE: " + user);

            var insertedUser= userRepository.save(user);


        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new  Exception("This username can't be used, already exists: try to another one");
        }
        return this.authenticate(username,password);
    }


    public Map<String,String> authenticate(
            String username, String password
    ) throws Exception {

        Map<String,String> result =new HashMap<>();
        String subject=null;
        String scope=null;
        Authentication authentication=null;

        try{
            authentication= authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username,
                            password
                    )
            );

        }catch (Exception e){
            System.out.println(e.getMessage());
            e.getStackTrace();
            throw new Exception("Bad credentials");
        }

        System.out.println(authentication.toString());
        subject =authentication.getName();

        scope =authentication.getAuthorities().stream()
                .map(item->item.getAuthority())
                .collect(Collectors.joining(" "));

        String accessToken=jwtService.generateToken(scope,subject);
        result.put("accessToken",accessToken);

        return result;
    }
}
