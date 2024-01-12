package ma.nemo.assignment.web;
import ma.nemo.assignment.dto.AuthUserDTO;
import ma.nemo.assignment.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    AuthService authService;

    AuthController(AuthService authService) {
        this.authService=authService;
    }


    @PostMapping("/register")
    ResponseEntity<Map> register(@RequestBody AuthUserDTO userDto){

        try{
            Map<String,String> result = authService.register(userDto.username(), userDto.password());
            return new ResponseEntity<>(result, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(Map.of("message",e.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/login")
    ResponseEntity<Map> login(@RequestBody AuthUserDTO userDTO){

        try{
            Map<String,String> result = authService.authenticate(userDTO.username(), userDTO.password());
            return new ResponseEntity<>(result,HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(Map.of("message",e.getMessage()),HttpStatus.BAD_REQUEST);
        }
    }

}
