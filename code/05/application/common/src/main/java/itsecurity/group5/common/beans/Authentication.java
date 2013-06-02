package itsecurity.group5.common.beans;

import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.UUID;

public class Authentication implements Serializable {
    private Integer id;
    private X509Certificate certificate;
    private String text;
    private UUID uuid;
    private byte[] irisData;
    
    private byte[] signature;

    public byte[] calculateSignature(PrivateKey key) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, CertificateEncodingException {
        Signature sig = Signature.getInstance("SHA1WithRSA");
        sig.initSign(key);

        if (text != null && !text.equals("")) {
            sig.update(text.getBytes());
        }
        if (uuid != null) {
            sig.update(uuid.toString().getBytes());
        }
        if (irisData != null && irisData.length > 0) {
            sig.update(irisData);
        }
        sig.update(certificate.getEncoded());

        signature = sig.sign();
        return signature;
    }

    public boolean verifySignature(String content) {
        if (signature == null || signature.length == 0) {
            return false;
        }

        try {
            // Check certificate validity
            certificate.checkValidity();
            // TODO: Additionally check certificate chain - i.e. if the root is trusted

            Signature sig = Signature.getInstance("SHA1WithRSA");
            sig.initVerify(certificate.getPublicKey());

            if (text != null && !text.equals("")) {
                sig.update(text.getBytes());
            }
            if (uuid != null) {
                sig.update(uuid.toString().getBytes());
            }
            if (irisData != null && irisData.length > 0) {
                sig.update(irisData);
            }
            sig.update(certificate.getEncoded());

            if (sig.verify(signature)) {
                // Extract id from certificate
                String name = certificate.getSubjectDN().getName();
                id = Integer.parseInt(name.subSequence(3, name.indexOf(", OU=ESSE")).toString().split(" ")[1]);

                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public byte[] getSignature() {
        return signature;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getId() {
        return id;
    }

    public X509Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(X509Certificate certificate) {
        this.certificate = certificate;
    }

    public byte[] getIrisData() {
        return irisData;
    }

    public void setIrisData(byte[] irisData) {
        this.irisData = irisData;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
