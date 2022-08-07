package com.smb.springcloudgateway.service;

import com.smb.springcloudgateway.constant.Constants;
import com.smb.springcloudgateway.exceptions.UnauthorizedException;
import com.babl.smbummodel.model.user.BearerToken;
import com.babl.smbummodel.model.user.BearerTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class GlobalFilterSecurityService {

    @Autowired
    BearerTokenRepository bearerTokenRepository;

    private static final Pattern authorizationPattern = Pattern.compile("^Bearer (?<token>[a-zA-Z0-9-._~+/]+)=*$", 2);
    private static final Pattern requestPattern = Pattern.compile("(?<token>[a-zA-Z0-9-._~+/]+)=*$");


    @Transactional
    public boolean validateToken(ServerHttpRequest request, ServerHttpResponse response) {
//        String requestPath = request.getPath().toString();
//        log.info("Request path = " + requestPath);
//        System.out.println("========= Request Body =============");
//        HttpHeaders headers = request.getHeaders();
//        Set<String> headerNames = headers.keySet();
//
//        headerNames.forEach((header) -> {
//            log.info(header + " " + headers.get(header));
//        });

        String token = resolveFromAuthorizationHeader(request);
        System.err.println(token);
        if (token == null) {
            System.err.println("Bearer Token Not Found or Invalid");
            throw new UnauthorizedException("Bearer Token Not Found or Invalid");
        }

        token = token.trim();

        Optional<BearerToken> bearerTokenOptional = bearerTokenRepository.findByToken(token);
        if (bearerTokenOptional.isPresent()) {
                byte[] key = Constants.tokenSecretKey.getBytes();
                Jws<Claims> claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
                String name = claims.getBody().getSubject();
                if (name == null) {
                    System.err.println("Invalid permission");
                    throw new UnauthorizedException("Invalid permission");
                }
                BearerToken bearerToken = bearerTokenOptional.get();
                if (name.equals(bearerToken.getUsername()) && LocalDateTime.now().isBefore(bearerToken.getTimeout())){
                //    bearerToken.setTimeout(LocalDateTime.now().plusMinutes(Constants.sessionTimeoutMinute));
                    return true;
                } else {
                    System.err.println("Token Time out");
                    throw new UnauthorizedException("Token Time out");
                }

        } else {
            System.err.println("Bearer Token not found");
            throw new UnauthorizedException("Bearer Token not found");
        }
    }

    public static String resolveFromAuthorizationHeader(ServerHttpRequest request) {
        if (request == null) {
            System.err.println("Request null do not Acceptable");
            return null;
        }

        List<String> obj = request.getHeaders().get("Authorization");
        if (obj != null) {
            String authorization = obj.get(0);
            System.out.println(authorization);
            if (StringUtils.startsWithIgnoreCase(authorization, "bearer")) {
                Matcher matcher = authorizationPattern.matcher(authorization);
                if (!matcher.matches()) {
                    return null;
                } else {
                    return matcher.group("token");
                }
            } else {
                System.err.println("Bearer Token not Found in request");
                return null;
            }
        }
        return null;
    }

    private String resolveFromRequestParam(HttpServletRequest httpServletRequest) {
        Enumeration<String> paramNames = httpServletRequest.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            if (paramName.equalsIgnoreCase("access_token")) {
                String[] accessTokenArray = httpServletRequest.getParameterValues(paramName);
                if (accessTokenArray.length == 0)
                    return null;
                Matcher matcher = requestPattern.matcher(accessTokenArray[0]);
                if (matcher.matches())
                    return accessTokenArray[0];
                return null;
            }
        }
        return null;
    }
}
