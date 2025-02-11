package fr.softsf.sudokufx.configuration;

import fr.softsf.sudokufx.annotations.ExcludedFromCoverageReportGenerated;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import java.net.http.HttpClient;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;

/**
 * Configures a secure HttpClient bean with TLS 1.2, no redirects, and a 5-second timeout.
 */
@Configuration
@ExcludedFromCoverageReportGenerated
public class HttpClientConfig {

    /**
     * Provides a secure HttpClient with TLS 1.2.
     *
     * @return Configured HttpClient instance.
     * @throws NoSuchAlgorithmException if TLS 1.2 is unsupported.
     * @throws KeyManagementException   if SSL context initialization fails.
     */
    @Bean
    HttpClient httpClient() throws KeyManagementException, NoSuchAlgorithmException {
        final SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
        sslContext.init(null, null, null);
        return HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NEVER)
                .sslContext(sslContext)
                .connectTimeout(Duration.ofSeconds(5))
                .build();
    }
}

