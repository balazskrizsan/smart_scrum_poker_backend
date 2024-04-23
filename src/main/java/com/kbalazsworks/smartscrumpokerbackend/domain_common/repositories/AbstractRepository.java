package com.kbalazsworks.smartscrumpokerbackend.domain_common.repositories;

import com.kbalazsworks.smartscrumpokerbackend.domain_common.services.JooqService;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
abstract public class AbstractRepository
{
    private JooqService jooqService;

    protected final com.kbalazsworks.smartscrumpokerbackend.db.tables.InsecureUser insecureUserTable =
        com.kbalazsworks.smartscrumpokerbackend.db.tables.InsecureUser.INSECURE_USER;
    protected final com.kbalazsworks.smartscrumpokerbackend.db.tables.InsecureUserSessions insecureUserSessionsTable =
        com.kbalazsworks.smartscrumpokerbackend.db.tables.InsecureUserSessions.INSECURE_USER_SESSIONS;

    @Autowired
    public void setJooqService(JooqService jooqService)
    {
        this.jooqService = jooqService;
    }

    protected DSLContext getDSLContext()
    {
        return jooqService.getDbContext();
    }
}
