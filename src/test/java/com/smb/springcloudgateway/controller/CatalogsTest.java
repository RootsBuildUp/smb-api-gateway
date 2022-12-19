package com.smb.springcloudgateway.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * https://spring.io/guides/gs/testing-web/
 *
 * @author Rashedul Islam
 * @since 2021-02-16
 */
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CatalogsTest {

    @SneakyThrows
    @Test
    @Order(0)
    public void contextLoads() {

        getToken(setToken("hello"), "hello");
    }

    @SneakyThrows
    private Map getEncryptedMessageBytesRSA(String token) {
        Map<String,Object> map = new HashMap<>();
        KeyPairGenerator keyPairGenerator =
                KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom();

        keyPairGenerator.initialize(2048,secureRandom);

        KeyPair pair = keyPairGenerator.generateKeyPair();

        PublicKey publicKey = pair.getPublic();
        map.put("publicKey",publicKey);

        String publicKeyString =
                Base64.getEncoder().encodeToString(publicKey.getEncoded());

        System.out.println("public key = "+ publicKeyString);

        PrivateKey privateKey = pair.getPrivate();

        String privateKeyString =
                Base64.getEncoder().encodeToString(privateKey.getEncoded());

        System.out.println("private key = "+ privateKeyString);

        //Encrypt Hello world message
        Cipher encryptionCipher = Cipher.getInstance("RSA");
        encryptionCipher.init(Cipher.ENCRYPT_MODE,privateKey);
        map.put("tokenEncrypted",encryptionCipher.doFinal(token.getBytes()));
        return map;
    }

    @SneakyThrows
    private Boolean getDecryptCipherMessageBytesRSA(Map map,String token) {
        byte[] tokenEncrypted = (byte[]) map.get("tokenEncrypted");
        PublicKey publicKey = (PublicKey) map.get("publicKey");

        String encryption =
                Base64.getEncoder().encodeToString(tokenEncrypted);
        System.out.println("encrypted message = "+encryption);

        //Decrypt Hello world message
        Cipher decryptionCipher = Cipher.getInstance("RSA");
        decryptionCipher.init(Cipher.DECRYPT_MODE,publicKey);
        byte[] decryptedMessage =
                decryptionCipher.doFinal(tokenEncrypted);
        String decryption = new String(decryptedMessage);
        System.out.println("decrypted message = "+decryption);
        return token.equals(decryption);
    }

    @SneakyThrows
    private Boolean getToken(Map map,String token) {
        String tokenEncrypted = (String) map.get("tokenEncrypted");
        String publicKey = (String) map.get("publicKey");

        System.out.println("encrypted message = "+tokenEncrypted);

        //Decrypt Hello world message
        Cipher decryptionCipher = Cipher.getInstance("RSA");
        decryptionCipher.init(Cipher.DECRYPT_MODE,getPublicKey(publicKey));
        byte[] decryptedMessage =
                decryptionCipher.doFinal(Base64.getDecoder().decode(tokenEncrypted));
        String decryption = new String(decryptedMessage);
        System.out.println("decrypted message = "+decryption);
        return token.equals(decryption);
    }
    @SneakyThrows
    public Map<String, String> setToken(String token){
        Map<String,String> map = new HashMap<>();
        KeyPairGenerator keyPairGenerator =
                KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom();

        keyPairGenerator.initialize(2048,secureRandom);

        KeyPair pair = keyPairGenerator.generateKeyPair();

        PublicKey publicKey = pair.getPublic();

        String publicKeyString =
                Base64.getEncoder().encodeToString(publicKey.getEncoded());

        System.out.println("public key = "+ publicKeyString);
        map.put("publicKey",publicKeyString);

        PrivateKey privateKey = pair.getPrivate();

        String privateKeyString =
                Base64.getEncoder().encodeToString(privateKey.getEncoded());

        System.out.println("private key = "+ privateKeyString);

        //Encrypt Hello world message
        Cipher encryptionCipher = Cipher.getInstance("RSA");
        encryptionCipher.init(Cipher.ENCRYPT_MODE,privateKey);
        map.put("tokenEncrypted",Base64.getEncoder().encodeToString(encryptionCipher.doFinal(token.getBytes())));
        return map;
    }

    @SneakyThrows
    public PublicKey getPublicKey(String publicKey){
        byte[] publicBytes = org.apache.commons.codec.binary.Base64.decodeBase64(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        return pubKey;
    }
    @SneakyThrows
    public void getPrivateKey(){
        byte[] publicBytes = org.apache.commons.codec.binary.Base64.decodeBase64("publicK");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
    }
}
