package com.kbalazsworks.smartscrumpokerbackend.db_presets;

import com.kbalazsworks.smartscrumpokerbackend.db.tables.InsecureUserSessions;
import com.kbalazsworks.smartscrumpokerbackend.helpers.account_module.fake_builders.InsecureUserSessionFakeBuilder;
import lombok.NonNull;
import org.jooq.DSLContext;

public class Insert3SessionsFor2Users implements IInsert
{
    @Override
    public void runParent()
    {
    }

    @Override
    public void run(@NonNull DSLContext dslContext)
    {
        dslContext.newRecord(InsecureUserSessions.INSECURE_USER_SESSIONS, new InsecureUserSessionFakeBuilder().build()).store();
        dslContext.newRecord(InsecureUserSessions.INSECURE_USER_SESSIONS, new InsecureUserSessionFakeBuilder().build2()).store();
        dslContext.newRecord(InsecureUserSessions.INSECURE_USER_SESSIONS, new InsecureUserSessionFakeBuilder().build3()).store();
    }
}
