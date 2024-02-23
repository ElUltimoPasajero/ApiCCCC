package com.example.apicccc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class SecurityPostService {


        public Integer generateToken() {


            return ThreadLocalRandom.current().nextInt(100000, 999999);


        }


        public boolean validateGeneratedToken(Integer providedToken, Integer expectedToken) {
            return providedToken.equals(expectedToken);
        }
    }