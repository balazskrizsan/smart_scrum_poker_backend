package com.kbalazsworks.smartscrumpokerbackend.helpers;

import com.kbalazsworks.smartscrumpokerbackend.db.tables.Poker;
import com.kbalazsworks.smartscrumpokerbackend.domain_common.services.JooqService;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractIntegrationTest extends AbstractTest
{
    @Autowired
    private JooqService jooqService;

    protected final Poker pokerTable = Poker.POKER;

    protected DSLContext getDslContext()
    {
        return jooqService.getDbContext();
    }
}
