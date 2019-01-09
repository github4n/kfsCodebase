package com.newcore.bmp.utils;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.newcore.bmp.models.authority.models.Clerk;



@Service("passwordHelper")
public class PasswordHelper {

    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    @Value("${password.algorithmName:md5}")
    private String algorithmName = "md5";
    @Value("${password.hashIterations:2}")
    private int hashIterations = 2;

    public void setRandomNumberGenerator(RandomNumberGenerator randomNumberGenerator) {
        this.randomNumberGenerator = randomNumberGenerator;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public void setHashIterations(int hashIterations) {
        this.hashIterations = hashIterations;
    }

    public void encryptPassword(Clerk clerk) {

        if (null == clerk.getPasswordSalt() || "".equals(clerk.getPasswordSalt())) {
            clerk.setPasswordSalt(randomNumberGenerator.nextBytes().toHex());
        }

        String newPassword = new SimpleHash(algorithmName, clerk.getPassword(),
                ByteSource.Util.bytes(clerk.getCredentialsSalt()), hashIterations).toHex();

        clerk.setPassword(newPassword);
    }

    public String encryptPassword(String password, String salt) {

        return new SimpleHash(algorithmName, password, ByteSource.Util.bytes(salt), hashIterations).toHex();

    }
}
