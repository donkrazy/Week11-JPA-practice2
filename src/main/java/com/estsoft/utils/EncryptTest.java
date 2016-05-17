package com.estsoft.utils;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class EncryptTest {
	public static void main(String[] args) {
	    StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
	    encryptor.setPassword("encKey"); //암호화키
	    String url = encryptor.encrypt("jdbc:mysql://donkrazy.cylaafjfqiq0.ap-northeast-2.rds.amazonaws.com:3306/jpadb?characterEncoding=utf8");
	    String username = encryptor.encrypt("zdzf");
	    String password = encryptor.encrypt("zdfzdfzdf");
	    System.out.println("jdbc.url="+url);
	    System.out.println("jdbc.username="+username);
	    System.out.println("jdbc.password="+password);
	}
}
