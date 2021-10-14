package com.example.mess.firebase;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.PemReader;
import com.google.api.client.util.SecurityUtils;
import com.google.auth.http.HttpTransportFactory;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;


@Service
public class FirebaseInitialization {

    @PostConstruct
    public void initialization() {
        FileInputStream serviceAccount = null;
        try {
            // serviceAccount = new FileInputStream("./serviceAccountKey.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(getGoogleCredentials())
                    .setDatabaseUrl("https://mensagensdodia-be802-default-rtdb.firebaseio.com")
                    .build();

            FirebaseApp.initializeApp(options);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private GoogleCredentials getGoogleCredentials() throws IOException {
        String clientId = "117061515785118865311";
        String clientEmail = "firebase-adminsdk-ywk1z@mensagensdodia-be802.iam.gserviceaccount.com";
        String privateKeyPkcs8 = "-----BEGIN PRIVATE KEY-----\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCtEPJFFqzlWeuM\n5SZBPP2AzOttRWaIxIbE965ZBn4kCeQqJhx+BDDNWp3aUBxdWS2wmBJ/OixBmBFa\nYMeZbysUoVO/NpDGo3s1BpHrxWtXmxwWHTcYsGKx1BqvJDEv6VmDrfqc7ufm3gyq\nrlW+YchdV4jxI4EOI59tjQcEa0EBhGDsp782lg2DrTpu6A0xjZGpYW82D2+hnAr/\nvF+5s+8iikD1O1OhrfGSWY2Yrxqskgv1oeGql/bRSxWYbj3vMoFfT5APfKiWMiNQ\nD/tzGSRLaedSxpsA0TChyyDFMKEqfsVAu9R+RsKQy28+/hZJKPPPR3oeJXT+wS5x\njGk+A/RXAgMBAAECggEADPg0z7dP+a1wGOegovRJIyU4N2t4sE3aCQTEDL6PJsWU\nUxGhbP2EvWmfDsSWJkC6oH36VOKCfTVCMlMHt5iR0pf3HgtJdOQSJaUlEQAtzSuT\nQpep5gXB6lsKLOSPcxcPsu3k1sHooG/fjyLwILZUkk+EXwiEHA/QsMsIIu1Oun2W\nQ0ocrzuy+NTmZSnIKcFM0CnBgTd5bIokIOctoE+0pORDItHVZwHIkOX270UI8T6e\noo5FeJOuoEAhA56RlyTCbWRBlJxTdXgPXC5riYzueP/3wHocq+KI91dLHuPRIixP\nGXegWp3YMqC8qJnzIQ17im7dGZt8O45m33zlOapYIQKBgQDcnSuvqGyMTcUVGH8D\n1lDRbjjhznLk2VXCTEvQ8UTuukrlfQUfnf3mnMUQznmSx7Lt4OEFAsjsS68Q2Cd9\nfCH2TUfJH821yNk8FZGdcs6bQzBhtHAgg/GeUzQdn6fOMDIjYUpMrgPXtmm0pa6t\nym2DnfeCepv/G2ttSxCJrWVKGQKBgQDI01/YcSBR9VBma5BE6BIMf+XrhnzRLyfp\nrQwRtg0Y1vtZ2XW/oQqWcXgj+aTSl0xyJgLrYT3mJIO7cU/3gf0bGXdFwwAVxVhf\nn4Wk/vshnvrmGEf2eXCSYgk+qb4PKhvqpmqXBWr51xnwUkj+WjaYMZqiPA6YKpLA\nmZ8JlSjf7wKBgQCylhiTLcgRG7N6jEXlmUv6/TrWsASZ9MVTb+e/0E944R1YAIhx\nO7gZQrbUEBCiHT5fCjedks+LWQjyncf58M6dT5nY2gHwMhKIbP1fc1yEmzNDJInQ\nCH9DJx99C40PgDxWH5/ByuwZZvTS43wv+zoD3ZBK823saQnhg25zxQXFoQKBgQCK\nzp7myXpfa8HAqQQ0vHsNLLHMk05ef8uElllLPJvc2Wst9UKPy024IoG5y+mCrZTn\nKSLHcpUIJ6DtxCpTlwNM4dMw4tGCOr8UiPwkySDnYi1lMM88qz+FzkE5kLWPYbmd\n/Jyb+eCxA14oX5eYRt6JX2/URtKHlpWMjiIAy5pO/QKBgEQDTE8BlPWoKKulBCEv\nSgDMEp2LJGIROSw1jCH7/JsF4KzUg7fR2Y0CAzm4HlK21+Wc44+y7Tr5HUYE3xNU\nCVQ4WDSYFatBQ6PHRgstyt0CqNBVhQCWIIZ3Uzc/DGOaq2EndvUuQ0Vpk/o9yeLU\nFbqfqoNiACn0Rzn5RjJr4EzP\n-----END PRIVATE KEY-----\n";
        String privateKeyId = "9cb4c07b4b5e906d05014bc03f12a6f0ccc6fc1b";
        String projectId = "mensagensdodia-be802";
        String tokenServerUriStringFromCreds = "https://oauth2.googleapis.com/token";
        URI tokenServerUriFromCreds = null;

        try {
            tokenServerUriFromCreds = new URI(tokenServerUriStringFromCreds);
        } catch (URISyntaxException e) {
            throw new IOException("Token server URI specified in 'token_uri' could not be parsed.");
        }

        return ServiceAccountCredentials.newBuilder()
                .setClientId(clientId)
                .setClientEmail(clientEmail)
                .setPrivateKey(privateKeyFromPkcs8(privateKeyPkcs8))
                .setPrivateKeyId(privateKeyId)
                .setHttpTransportFactory(new DefaultHttpTransportFactory())
                .setTokenServerUri(tokenServerUriFromCreds)
                .setProjectId(projectId)
                .build();
    }

    static class DefaultHttpTransportFactory implements HttpTransportFactory {
        final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
        public HttpTransport create() {
            return HTTP_TRANSPORT;
        }
    }

    static PrivateKey privateKeyFromPkcs8(String privateKeyPkcs8) throws IOException {
        Reader reader = new StringReader(privateKeyPkcs8);
        PemReader.Section section = PemReader.readFirstSectionAndClose(reader, "PRIVATE KEY");
        if (section == null) {
            throw new IOException("Invalid PKCS#8 data.");
        }
        byte[] bytes = section.getBase64DecodedBytes();
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
        Exception unexpectedException;
        try {
            KeyFactory keyFactory = SecurityUtils.getRsaKeyFactory();
            return keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException exception) {
            unexpectedException = exception;
        }
        throw new IOException("Unexpected exception reading PKCS#8 data", unexpectedException);
    }

}
