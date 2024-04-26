package com.kbalazsworks.smartscrumpokerbackend.helpers;

import com.kbalazsworks.smartscrumpokerbackend.SmartScrumPokerApplication;
import com.kbalazsworks.smartscrumpokerbackend.config.ApplicationProperties;
import com.kbalazsworks.smartscrumpokerbackend.helpers.exceptions.ServiceFactoryException;
import lombok.NonNull;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "classpath:application.properties")
@ContextConfiguration(classes = SmartScrumPokerApplication.class)
@EnableAspectJAutoProxy
public abstract class AbstractTest
{
    @Autowired
    protected ApplicationProperties applicationProperties;
    @Autowired
    private ServiceFactory serviceFactory;

    @AfterEach
    public void after()
    {
        serviceFactory.resetMockContainer();
    }

    protected <T> T createInstance(@NonNull Class<T> clazz, @NonNull List<Object> mocks) throws ServiceFactoryException
    {
        mocks.forEach(m -> setOneTimeMock(clazz, m));

        return createInstance(clazz);
    }

    protected <T> T createInstance(@NonNull Class<T> clazz, @NonNull Object mock) throws ServiceFactoryException
    {
        setOneTimeMock(clazz, mock);

        return createInstance(clazz);
    }

    protected <T> T createInstance(@NonNull Class<T> clazz) throws ServiceFactoryException
    {
        return serviceFactory.createInstance(clazz);
    }

    protected void setOneTimeMock(@NonNull Class<?> newClass, @NonNull Object mock)
    {
        serviceFactory.setOneTimeMock(newClass, mock);
    }
}
