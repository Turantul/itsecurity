package itsecurity.group5.common.beans;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.X509Certificate;

public class Authentication implements Serializable {
    private Integer id;
    private byte[] signature;
    private X509Certificate certificate;
    private String text;
    private byte[] irisData;

    public byte[] calculateSignature(PrivateKey key) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException,
        SignatureException {
        if (text == null || text.equals("")) {
            return null;
        }
        text.replaceAll(" ", "");

        String name = certificate.getSubjectDN().getName();
        
        if (irisData != null && irisData.length > 0) {
            text += " " + new String(irisData);
        }
        
        text += " " + name.subSequence(3, name.indexOf(", OU=ESSE"));
        byte[] data = text.getBytes("UTF8");

        Signature sig = Signature.getInstance("SHA1WithRSA");
        sig.initSign(key);
        sig.update(data);

        signature = sig.sign();
        return signature;
    }

    public boolean verifySignature(String content) {
        if (text == null || text.equals("") || signature == null || signature.length == 0) {
            return false;
        }

        try {
            //Check certificate validity
            certificate.checkValidity();
            //TODO: Additionally check certificate chain - i.e. if the root is trusted

            //Check content of the text
            String name = certificate.getSubjectDN().getName();
            if (content != null && !content.equals("") && !text.startsWith(content)) {
                return false;
            }
            if (!text.endsWith(name.subSequence(3, name.indexOf(", OU=ESSE")).toString())) {
                return false;
            }
            
            //Check signature
            byte[] data = text.getBytes("UTF8");

            Signature sig = Signature.getInstance("SHA1WithRSA");
            sig.initVerify(certificate.getPublicKey());
            sig.update(data);

            if (sig.verify(signature)) {
                //Extract id from certificate
                id = Integer.parseInt(name.subSequence(3, name.indexOf(", OU=ESSE")).toString().split(" ")[1]);
                
                //Extract irisData if available
                if (text.split(" ").length > 3) {
                    irisData = text.split(" ")[1].getBytes();
                }
                
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
}
