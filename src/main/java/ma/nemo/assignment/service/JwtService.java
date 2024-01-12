package ma.nemo.assignment.service;

import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class JwtService {
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    public JwtService(JwtEncoder jwtEncoder , JwtDecoder jwtDecoder
    ){
        this.jwtEncoder=jwtEncoder;
        this.jwtDecoder=jwtDecoder;
    }

    public String generateToken(String scope, String subject){

        Instant instant =Instant.now();
        JwtClaimsSet jwtClaimsSet=null;
        jwtClaimsSet = JwtClaimsSet.builder()
                .subject(subject)
                .issuer("nemo-app")
                .issuedAt(instant)
                .expiresAt(instant.plus(24, ChronoUnit.HOURS))
                .claim("scope",scope)
                .build();


        return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();

    }

    public String getSubject(String token){
        Jwt decodedToken = jwtDecoder.decode(token);
        return decodedToken.getSubject();
    }

}
