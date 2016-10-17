package ru.andrw.java.socialnw.util;

import java.io.InputStream;
import java.security.Timestamp;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by john on 10/16/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class X509Signer {

    private static final String CERT_FILE_NAME = "cert.cer";
    private static volatile X509Signer INSTANCE;
    private final CertPath cp;

    private X509Signer(CertPath certPath){
        cp = certPath;
    }

    public static X509Signer getInstance() throws Exception{
        X509Signer localInstance = INSTANCE;
        if (localInstance == null) {
            synchronized (X509Signer.class) {
                localInstance = INSTANCE;
                if (localInstance == null) {
                    INSTANCE = localInstance = new X509Signer(X509Initializer.init());
                }
            }
        }
        return localInstance;
    }

    private static class X509Initializer{
        private static CertPath init() throws Exception{
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            List<Certificate> list = new ArrayList<>();
            InputStream is = X509Signer.class.getClassLoader()
                    .getResourceAsStream(CERT_FILE_NAME);
            Certificate c = cf.generateCertificate(is);
            list.add(c);
            return cf.generateCertPath(list);
        }
    }



    public final Timestamp getTimestamp(){
        return new Timestamp(new Date(), cp);
    }

}
