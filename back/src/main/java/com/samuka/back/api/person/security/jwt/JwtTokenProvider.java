/*
 * Copyright 2021 Samuka <samuelgenio28@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.samuka.back.api.person.security.jwt;

import com.samuka.back.api.person.exception.InvalidJwtAuthenticationException;
import com.samuka.back.api.person.exception.ResourceNotFoundException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 *
 * @author Samuka <samuelgenio28@gmail.com>
 */
@Service
public class JwtTokenProvider {
    
    @Value("${security.jwt.token.secret-key:secret}")
    private String secretKey;
    
    @Value("${security.jwt.token.expire-lenght:3600000}")
    private long validityInMs = 3600000;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @PostConstruct
    public void init() {
    
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        
    }
    
    public String createToken(String username, List<String> roles) {
    
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);
        
        Date now = new Date();
        Date validityDate = new Date(now.getTime() + validityInMs);
        
        
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validityDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        
    }
    
    public Authentication getAuthentication(String token) {
        
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUserName(token));
        
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getUserName(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    
    public String resolveRoken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        
        if (bearerToken != null && bearerToken.startsWith("Bearer:")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        
        return null;
    }
    
    public boolean validateToken (String token) throws InvalidJwtAuthenticationException {
        try {
            
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            
            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }
                    
            return true;
            
        } catch (Exception e) {
            //throw new ResourceNotFoundException("Expired or invalid token.");
            throw new InvalidJwtAuthenticationException("Expired or invalid token.");
        }
        
    }
    
}
