package com.kbalazsworks.smartscrumpokerbackend.config;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Log4j2
public class ApplicationProperties
{
    public void testOverride() throws Exception
    {
        if (getServerEnv().equalsIgnoreCase("prod"))
        {
            log.fatal("Not a good idea");

            throw new Exception("Not a good idea");
        }

    }

    @Value("${server.env}")
    private String serverEnv;

    @Value("${site.domain}")
    private String siteDomain;

    @Value("${site.frontend.host}")
    private String siteFrontendHost;

    @Value("${server.port}")
    private String serverPort;

    @Value("${spring.datasource.url}")
    private String dataSourceUrl;

    @Value("${spring.datasource.username}")
    private String dataSourceUsername;

    @Value("${spring.datasource.password}")
    private String dataSourcePassword;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.hikari.maximum-pool-size}")
    private int dataSourceHikariMaximumPoolSize;

    @Value("${spring.datasource.hikari.minimum-idle}")
    private int dataSourceHikariMinimumIdle;


    public String getStackJudgeSdkCertP12KeystoreFilePath()
    {
        return "classpath:keystore/v2/sjdev.p12";
    }

    public String getStackJudgeSdkCertP12KeystoreFilePassword()
    {
        return "password";
    }
}
