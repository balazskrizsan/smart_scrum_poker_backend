package com.kbalazsworks.smartscrumpokerbackend.helpers;

import com.kbalazsworks.smartscrumpokerbackend.SmartScrumPokerApplication;
import com.kbalazsworks.smartscrumpokerbackend.config.ApplicationProperties;
import lombok.NonNull;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application.properties")
@ContextConfiguration(classes = SmartScrumPokerApplication.class)
public abstract class AbstractTest
{
    @Autowired
    protected ApplicationProperties applicationProperties;
    @Autowired
    private ServiceFactory serviceFactory;

    protected <T> T createInstance(@NonNull Class<T> clazz)
    {
        return serviceFactory.createInstance(clazz);
    }

    protected void setOneTimeMock(
        @NonNull Class<?> newClass,
        @NonNull Class<?> dependencyClass,
        @NonNull Object defaultMock
    )
    {
        serviceFactory.setOneTimeMock(newClass, dependencyClass, defaultMock);
    }
}
