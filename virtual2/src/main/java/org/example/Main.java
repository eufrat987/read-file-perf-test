package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.Executors;

/**
 * Percentage of the requests served within a certain time (ms)
 *   50%   1561
 *   66%   1629
 *   75%   1675
 *   80%   1708
 *   90%   1796
 *   95%   1885
 *   98%   2046
 *   99%   2322
 *  100%   3050 (longest request)
 */
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> tomcatCustomizer() {
        return factory -> factory.addConnectorCustomizers(connector -> {
            connector.getProtocolHandler()
                    .setExecutor(Executors.newThreadPerTaskExecutor(Thread.ofVirtual().factory()));
        });
    }
}