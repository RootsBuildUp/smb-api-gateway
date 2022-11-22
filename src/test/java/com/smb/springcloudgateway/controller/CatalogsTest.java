//package com.smb.springcloudgateway.controller;
//
//import lombok.SneakyThrows;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
//import org.junit.jupiter.api.Order;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
//import javax.crypto.Cipher;
//import java.nio.charset.StandardCharsets;
//import java.security.*;
//import java.util.Base64;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * https://spring.io/guides/gs/testing-web/
// *
// * @author Rashedul Islam
// * @since 2021-02-16
// */
//@TestMethodOrder(OrderAnnotation.class)
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//class CatalogsTest {
//
//    @SneakyThrows
//    @Test
//    @Order(0)
//    public void contextLoads() {
//        Map<String,Object> map = new HashMap<>();
//        map.put("token","hello");
//        getDecryptCipherMessageBytesRSA(getEncryptedMessageBytesRSA(map), "hello");
//    }
//
//    @SneakyThrows
//    private Map getEncryptedMessageBytesRSA(Map map) {
//        KeyPairGenerator keyPairGenerator =
//                KeyPairGenerator.getInstance("RSA");
//        SecureRandom secureRandom = new SecureRandom();
//
//        keyPairGenerator.initialize(2048,secureRandom);
//
//        KeyPair pair = keyPairGenerator.generateKeyPair();
//
//        PublicKey publicKey = pair.getPublic();
//        map.put("publicKey",publicKey);
//
//        String publicKeyString =
//                Base64.getEncoder().encodeToString(publicKey.getEncoded());
//
//        System.out.println("public key = "+ publicKeyString);
//
//        PrivateKey privateKey = pair.getPrivate();
//
//        String privateKeyString =
//                Base64.getEncoder().encodeToString(privateKey.getEncoded());
//
//        System.out.println("private key = "+ privateKeyString);
//
//        //Encrypt Hello world message
//        Cipher encryptionCipher = Cipher.getInstance("RSA");
//        encryptionCipher.init(Cipher.ENCRYPT_MODE,privateKey);
//        String token = (String) map.get("token");
//        map.put("tokenEncrypted",encryptionCipher.doFinal(token.getBytes()));
//        return map;
//    }
//
//    @SneakyThrows
//    private Boolean getDecryptCipherMessageBytesRSA(Map map,String token) {
//        byte[] tokenEncrypted = (byte[]) map.get("tokenEncrypted");
//        PublicKey publicKey = (PublicKey) map.get("publicKey");
//
//        String encryption =
//                Base64.getEncoder().encodeToString(tokenEncrypted);
//        System.out.println("encrypted message = "+encryption);
//
//        //Decrypt Hello world message
//        Cipher decryptionCipher = Cipher.getInstance("RSA");
//        decryptionCipher.init(Cipher.DECRYPT_MODE,publicKey);
//        byte[] decryptedMessage =
//                decryptionCipher.doFinal(tokenEncrypted);
//        String decryption = new String(decryptedMessage);
//        System.out.println("decrypted message = "+decryption);
//        return token.equals(decryption);
//    }
//}
