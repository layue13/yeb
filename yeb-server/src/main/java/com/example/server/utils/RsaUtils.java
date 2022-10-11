package com.example.server.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RsaUtils {

    public static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDKFcC0Y++FNtpu7RVvARWSmSUe\n" +
            "7dkEq5dPheZMcs2dMarSmhCiKwNrrKG7ZYeNz4+A2sh9DRGiAOuhZIT9pCSwuCNy\n" +
            "b5ECyimi++Y9hirO9+bjuRXHqSlJz6a/4eaFGV8j6x3UK+2xPXp0g2tkQKoHa8Yg\n" +
            "eRZu2/P44rkL+sDgswIDAQAB";

    public static String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAMoVwLRj74U22m7t\n" +
            "FW8BFZKZJR7t2QSrl0+F5kxyzZ0xqtKaEKIrA2usobtlh43Pj4DayH0NEaIA66Fk\n" +
            "hP2kJLC4I3JvkQLKKaL75j2GKs735uO5FcepKUnPpr/h5oUZXyPrHdQr7bE9enSD\n" +
            "a2RAqgdrxiB5Fm7b8/jiuQv6wOCzAgMBAAECgYAihFin9zHgNSBWNhSlIzBGG/Zl\n" +
            "NAcPbyenI53UjP9lzCf7VOlmaaZp69CsK4ldxYgjYDtCcMsEOtgEdyDoGfNSFhr5\n" +
            "6JbdPVLjtapsQW8BcTD7ZJ92esbpl3TdQ2LxnCZ5kGckfi1omCgCw6ENKi/Sl8Aq\n" +
            "4qYgTuP+sCOUGoOQQQJBAPRnO9KjycqaTWc3N9pPPTibMkxY8tLye9ToAuudTdU0\n" +
            "n60iWSz4c7t2u1xd1EWZQFZj9HbmqW1VYztwZ2qybMMCQQDTrHqXPMQtV2r5TlUI\n" +
            "CcqM4b+olxJZlvwfGNO79tz/pb5PcDso/oHxa57oHuEX0AoZwcwTXHtblc/3pAX7\n" +
            "9j1RAkAGiJv4zZgfanCXScqcc3HXiY6Wq+oR6ZmV3330fogOIxIBtuFbamjtogbw\n" +
            "OTHrimVF6fYnF5N/8MBuXlrlZ0+xAkA3UG1AtEp3ps53k7a4wQZDRoL1JXVQIBqG\n" +
            "bdB24A3kaIWQ8L0k+00tr3f5JIih1mWtUW1o6dP6nO2Zwy0lKLXhAkA8p11+Se2D\n" +
            "BsS9ppHFvqg4LlMojrbZbSbtrfx8oIByMjWQA5wDIBnrB+pzn+4aCpzR8Y+hqQ/7\n" +
            "gxz6gvCCQ04T";

    public static String signature(String plaintext) throws Exception {

        PrivateKey rsaPrivate = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(
                Base64.decodeBase64(privateKey)
        ));

        Signature signature = Signature.getInstance("NONEwithRSA");

        signature.initSign(rsaPrivate);

        signature.update(plaintext.getBytes());

        byte[] sign = signature.sign();

        return Base64.encodeBase64String(sign);
    }

    public static boolean verify(String plaintext, String sign) throws Exception {

        PublicKey rsaPublic = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decodeBase64(publicKey)));

        Signature signature = Signature.getInstance("NONEwithRSA");

        signature.initVerify(rsaPublic);

        signature.update(plaintext.getBytes());

        boolean verify = signature.verify(Base64.decodeBase64(sign));

        return verify;
    }

    public static void main(String[] args) throws Exception {

        String encoded = signature("{\"lastName\":\"Snow\",\"firstName\":\"Grpbby\"}");
        System.out.println(encoded);

        boolean success = verify("{\"lastName\":\"Snow\",\"firstName\":\"Grpbby\"}", encoded);
        System.out.println(success);
    }
}
