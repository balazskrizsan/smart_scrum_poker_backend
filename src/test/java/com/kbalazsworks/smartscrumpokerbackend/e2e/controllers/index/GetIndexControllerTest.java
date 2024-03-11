package com.kbalazsworks.smartscrumpokerbackend.e2e.controllers.index;

import com.kbalazsworks.smartscrumpokerbackend.SmartScrumPokerApplication;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application.properties")
@ContextConfiguration(classes = SmartScrumPokerApplication.class)
public class GetIndexControllerTest
{
    @Autowired
    protected WebApplicationContext wac;

    public MockMvc getMockMvc()
    {
        return MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void HelloTest() throws Exception
    {
        // Arrange
        String testedUri = "/";

        // Act
        ResultActions result = getMockMvc().perform(
            MockMvcRequestBuilders.get(testedUri)
        );

        // Assert
        result.andExpect(content().bytes("hello".getBytes()));
    }
}
