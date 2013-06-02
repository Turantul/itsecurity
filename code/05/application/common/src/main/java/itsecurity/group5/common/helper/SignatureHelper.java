package itsecurity.group5.common.helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class SignatureHelper {
    public static PrivateKey getPrivateKey() throws KeyStoreException, NoSuchAlgorithmException, CertificateException,
        IOException, UnrecoverableKeyException {
        return SignatureHelper.getPrivateKey(System.getProperty("javax.net.ssl.keyStore"));
    }

    public static PrivateKey getPrivateKey(String path) throws KeyStoreException, NoSuchAlgorithmException, CertificateException,
        IOException, UnrecoverableKeyException {
        return SignatureHelper.getPrivateKey(path, System.getProperty("javax.net.ssl.keyStoreType"), System.getProperty("javax.net.ssl.keyStorePassword"));
    }

    public static PrivateKey getPrivateKey(String path, String type, String password) throws KeyStoreException, NoSuchAlgorithmException, CertificateException,
        IOException, UnrecoverableKeyException {
        FileInputStream is = new FileInputStream(path);

        KeyStore keystore = KeyStore.getInstance(type);
        keystore.load(is, password.toCharArray());

        String alias = "1";

        Key key = keystore.getKey(alias, password.toCharArray());
        if (key instanceof PrivateKey) {
            return (PrivateKey) key;
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

        return (X509Certificate) keystore.getCertificateChain(alias)[0];
    }
}
