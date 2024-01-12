package ma.nemo.assignment.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
@ConfigurationProperties(prefix = "rsa.test")
@Data
public class RsaKeysConfig {
    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;
}
