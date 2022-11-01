package com.smb.springcloudgateway.service;

import com.smb.springcloudgateway.constant.Constants;
import com.smb.springcloudgateway.exceptions.UnauthorizedException;
import com.smb.coremodel.model.user.BearerToken;
import com.smb.coremodel.model.user.BearerTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriTemplate;
import reactor.core.publisher.Mono;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class GlobalFilterSecurityService {

    @Autowired
    BearerTokenRepository bearerTokenRepository;

    private static final Pattern authorizationPattern = Pattern.compile("^Bearer (?<token>[a-zA-Z0-9-._~+/]+)=*$", 2);
    private static final Pattern requestPattern = Pattern.compile("(?<token>[a-zA-Z0-9-._~+/]+)=*$");

    private AntPathMatcher pathMatcher;
    private UriTemplate rootFileGetUriTemplate;
    private UriTemplate tomcatFileGetUriTemplate;
    private UriTemplate rootFileGetByTypeUriTemplate;
    private UriTemplate tomcatFileGetByTypeUriTemplate;
    private UriTemplate rootSignatureGetTemplate;
    private UriTemplate tomcatSignatureGetTemplate;
    private UriTemplate rootIntgrSignatureTemplate;
    private UriTemplate tomcatIntgrSignatureTemplate;
    private UriTemplate rootIntgrPlatformSignatureTemplate;
    private UriTemplate tomcatIntgrPlatformSignatureTemplate;
    private UriTemplate rootIntgrMultiSignatureTemplate;
    private UriTemplate tomcatIntgrMultiSignatureTemplate;

    private void uriTemplateSet(){
        this.pathMatcher = new AntPathMatcher();
        this.rootFileGetUriTemplate = new UriTemplate("/file/{name}/{token}");
        this.tomcatFileGetUriTemplate = new UriTemplate("/{baseUri}/file/{name}/{token}");
        this.rootFileGetByTypeUriTemplate = new UriTemplate("/file/getByType/{appId}/{type}/{token}");
        this.tomcatFileGetByTypeUriTemplate = new UriTemplate("/{baseUri}/file/getByType/{appId}/{type}/{token}");
        this.rootSignatureGetTemplate = new UriTemplate("/signaturePhoto/{appId}/{type}/{token}");
        this.tomcatSignatureGetTemplate = new UriTemplate("/{baseUri}/signaturePhoto/{appId}/{type}/{token}");
        this.rootIntgrSignatureTemplate = new UriTemplate("/intgr/signature/{accountNo}/{token}");
        this.tomcatIntgrSignatureTemplate = new UriTemplate("/{baseUri}/intgr/signature/{accountNo}/{token}");
        this.rootIntgrPlatformSignatureTemplate = new UriTemplate("/intgr/signature/{platform}/{accountNo}/{token}");
        this.tomcatIntgrPlatformSignatureTemplate = new UriTemplate("/{baseUri}/intgr/signature/{platform}/{accountNo}/{token}");
        this.rootIntgrMultiSignatureTemplate = new UriTemplate("/intgr/signaturePart/{platform}/{id}/{token}");
        this.tomcatIntgrMultiSignatureTemplate = new UriTemplate("/{baseUri}/intgr/signaturePart/{platform}/{id}/{token}");
    }

    final Logger logger =
            LoggerFactory.getLogger(GlobalFilterSecurityService.class);

    public Mono<Void> filter(ServerWebExchange exchange,
                             GatewayFilterChain chain) {
        this.uriTemplateSet();
        logger.info("Pre-Filter executed");
        ServerHttpRequest httpRequest = exchange.getRequest();
        ServerHttpResponse httpResponse = exchange.getResponse();

        System.err.println("==========" + httpRequest.getURI().getPath() + "=================");
        System.err.println("==========" + httpRequest.getMethod().toString() + "=================");

        String uri = httpRequest.getURI().getPath();
        if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod().toString())) {
            httpResponse.setStatusCode(HttpStatus.OK);
            return Mono.empty();
        } else if (uri.contains("/logMeIn") || uri.contains("/forgetPass/") || uri.contains("/users/forceLogoutSelf/") || uri.contains("/external/api/getBearerToken")) {
            return lastPostGlobalFilter(exchange, chain);
        } else if (pathMatcher.match("/file/*/*", uri)) {
            Map<String, String> variables = rootFileGetUriTemplate.match(uri);
            setUriChecking(variables,httpRequest);
            return lastPostGlobalFilter(exchange,chain);
        } else if (pathMatcher.match("/*/file/*/*", uri)) {
            Map<String, String> variables = tomcatFileGetUriTemplate.match(uri);
            setUriChecking(variables,httpRequest);
            return lastPostGlobalFilter(exchange,chain);
        } else if (pathMatcher.match("/file/getByType/*/*/*", uri)) {
            Map<String, String> variables = rootFileGetByTypeUriTemplate.match(uri);
            setUriChecking(variables,httpRequest);
            return lastPostGlobalFilter(exchange,chain);
        } else if (pathMatcher.match("/*/file/getByType/*/*/*", uri)) {
            Map<String, String> variables = tomcatFileGetByTypeUriTemplate.match(uri);
            setUriChecking(variables,httpRequest);
            return lastPostGlobalFilter(exchange,chain);
        } else if (pathMatcher.match("/signaturePhoto/*/*/*", uri)) {
            Map<String, String> variables = rootSignatureGetTemplate.match(uri);
            setUriChecking(variables,httpRequest);
            return lastPostGlobalFilter(exchange,chain);
        } else if (pathMatcher.match("/*/signaturePhoto/*/*/*", uri)) {
            Map<String, String> variables = tomcatSignatureGetTemplate.match(uri);
            setUriChecking(variables,httpRequest);
            return lastPostGlobalFilter(exchange,chain);
        } else if (pathMatcher.match("/intgr/signature/*/*", uri)) {
            Map<String, String> variables = rootIntgrSignatureTemplate.match(uri);
            setUriChecking(variables,httpRequest);
            return lastPostGlobalFilter(exchange,chain);
        } else if (pathMatcher.match("/*/intgr/signature/*/*", uri)) {
            Map<String, String> variables = tomcatIntgrSignatureTemplate.match(uri);
            String token = variables.get("token");
            setUriChecking(variables,httpRequest);
            return lastPostGlobalFilter(exchange,chain);
        } else if (pathMatcher.match("/intgr/signature/*/*/*", uri)) {
            Map<String, String> variables = rootIntgrPlatformSignatureTemplate.match(uri);
            setUriChecking(variables,httpRequest);
            return lastPostGlobalFilter(exchange,chain);
        } else if (pathMatcher.match("/*/intgr/signature/*/*/*", uri)) {
            Map<String, String> variables = tomcatIntgrPlatformSignatureTemplate.match(uri);
            setUriChecking(variables,httpRequest);
            return lastPostGlobalFilter(exchange,chain);
        } else if (pathMatcher.match("/intgr/signaturePart/*/*/*", uri)) {
            Map<String, String> variables = rootIntgrMultiSignatureTemplate.match(uri);
            setUriChecking(variables,httpRequest);
            return lastPostGlobalFilter(exchange,chain);
        } else if (pathMatcher.match("/*/intgr/signaturePart/*/*/*", uri)) {
            Map<String, String> variables = tomcatIntgrMultiSignatureTemplate.match(uri);
            setUriChecking(variables,httpRequest);
            return lastPostGlobalFilter(exchange,chain);
        } else {
            String token = resolveFromAuthorizationHeader(httpRequest);
            validateToken(token, httpRequest);
            return lastPostGlobalFilter(exchange, chain);
        }
    }

    private void setUriChecking(Map<String, String> variables, ServerHttpRequest request){
        String token = variables.get("token");
        if (!validateToken(token, request))
            throw new UnauthorizedException("Invalid permission");
    }
    private Mono<Void> lastPostGlobalFilter(ServerWebExchange exchange,
                                            GatewayFilterChain chain) {
        return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {
                    logger.info("Last Post Global Filter");

                }));
    }

    @Transactional
    public boolean validateToken(String token, ServerHttpRequest request) {
//        String requestPath = request.getPath().toString();
//        log.info("Request path = " + requestPath);
//        System.out.println("========= Request Body =============");
//        HttpHeaders headers = request.getHeaders();
//        Set<String> headerNames = headers.keySet();
//
//        headerNames.forEach((header) -> {
//            log.info(header + " " + headers.get(header));
//        });

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
//            BearerToken bearerToken = bearerTokenOptional.get();
//                if (name.equals(bearerToken.getUsername()) && LocalDateTime.now().isBefore(bearerToken.getTimeout())){
//                    bearerToken.setTimeout(LocalDateTime.now().plusMinutes(Constants.sessionTimeoutMinute));
//                    return true;
//                } else {
//                    System.err.println("Token Time out");
//                    throw new UnauthorizedException("Token Time out");
//                }
            return true;

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

    @SneakyThrows
    private void getEncryptedMessageBytesRSA(String token, HttpServletRequest httpServletRequest){
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        KeyPair pair = generator.generateKeyPair();
        PublicKey publicKey = pair.getPublic();
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] secretMessageBytes = token.getBytes(StandardCharsets.UTF_8);
        byte[] tokenEncrypted = encryptCipher.doFinal(secretMessageBytes);
        httpServletRequest.setAttribute("tokenEncrypted",tokenEncrypted);
    }

    @SneakyThrows
    private Boolean getDecryptCipherMessageBytesRSA(byte[] tokenEncrypted, String token){
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        KeyPair pair = generator.generateKeyPair();
        PrivateKey privateKey = pair.getPrivate();
        Cipher decryptCipher = Cipher.getInstance("RSA");
        decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedMessageBytes = decryptCipher.doFinal(tokenEncrypted);
        String decryptedToken = new String(decryptedMessageBytes, StandardCharsets.UTF_8);
        return token == decryptedToken;
    }
}
