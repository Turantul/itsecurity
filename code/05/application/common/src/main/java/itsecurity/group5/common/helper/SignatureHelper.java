package itsecurity.group5.common.helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class SignatureHelper {
    public static KeyPair getKeyPair() throws KeyStoreException, NoSuchAlgorithmException, CertificateException,
        IOException, UnrecoverableKeyException {
        return SignatureHelper.getKeyPair(System.getProperty("javax.net.ssl.keyStore"));
    }

    public static KeyPair getKeyPair(String path) throws KeyStoreException, NoSuchAlgorithmException, CertificateException,
        IOException, UnrecoverableKeyException {
        return SignatureHelper.getKeyPair(path, System.getProperty("javax.net.ssl.keyStoreType"), System.getProperty("javax.net.ssl.keyStorePassword"));
    }

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

    public static X509Certificate getCertificate() throws KeyStoreException, NoSuchAlgorithmException, CertificateException,
        IOException, UnrecoverableKeyException {
        return SignatureHelper.getCertificate(System.getProperty("javax.net.ssl.keyStore"));
    }

    public static X509Certificate getCertificate(String path) throws KeyStoreException, NoSuchAlgorithmException, CertificateException,
        IOException, UnrecoverableKeyException {
        return SignatureHelper.getCertificate(path, System.getProperty("javax.net.ssl.keyStoreType"), System.getProperty("javax.net.ssl.keyStorePassword"));
    }

    public static X509Certificate getCertificate(String path, String type, String password) throws KeyStoreException, NoSuchAlgorithmException,
        CertificateException, IOException, UnrecoverableKeyException {
        FileInputStream is = new FileInputStream(path);

        KeyStore keystore = KeyStore.getInstance(type);
        keystore.load(is, password.toCharArray());

        String alias = "1";

        Key key = keystore.getKey(alias, password.toCharArray());
        if (key instanceof PrivateKey) {
            return (X509Certificate) keystore.getCertificate(alias);
        }
        return null;
    }
}
