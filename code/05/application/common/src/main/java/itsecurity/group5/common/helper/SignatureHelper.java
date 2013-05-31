package itsecurity.group5.common.helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

public class SignatureHelper {
    public static KeyPair getKeyPair(String path, String type, String password) throws KeyStoreException, NoSuchAlgorithmException, CertificateException,
        IOException, UnrecoverableKeyException {
        FileInputStream is = new FileInputStream(path);

        KeyStore keystore = KeyStore.getInstance(type);
        keystore.load(is, password.toCharArray());

        String alias = "1";

        Key key = keystore.getKey(alias, password.toCharArray());
        if (key instanceof PrivateKey) {
            Certificate cert = keystore.getCertificate(alias);
            PublicKey publicKey = cert.getPublicKey();
            return new KeyPair(publicKey, (PrivateKey) key);
        }
        return null;
    }

    public static byte[] calculateSignature(String text, PrivateKey key) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException,
        SignatureException {
        byte[] data = text.getBytes("UTF8");

        Signature sig = Signature.getInstance("SHA1WithRSA");
        sig.initSign(key);
        sig.update(data);
        return sig.sign();
    }

    public static boolean verifySignature(byte[] signature, String text, PublicKey key) throws NoSuchAlgorithmException, UnsupportedEncodingException,
        InvalidKeyException, SignatureException {
        byte[] data = text.getBytes("UTF8");

        Signature sig = Signature.getInstance("SHA1WithRSA");
        sig.initVerify(key);
        sig.update(data);

        return sig.verify(signature);
    }

    public static void demo() throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException,
        InvalidKeyException, SignatureException {
        KeyPair keyPair =
            SignatureHelper.getKeyPair(System.getProperty("javax.net.ssl.keyStore"), System.getProperty("javax.net.ssl.keyStoreType"),
                System.getProperty("javax.net.ssl.keyStorePassword"));

        byte[] signature = calculateSignature("affe", keyPair.getPrivate());

        boolean result = verifySignature(signature, "affe", keyPair.getPublic());
        System.out.println(result);
    }
}
