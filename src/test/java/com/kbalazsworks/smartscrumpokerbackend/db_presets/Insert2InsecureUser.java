package com.kbalazsworks.smartscrumpokerbackend.db_presets;

import com.kbalazsworks.smartscrumpokerbackend.db.tables.InsecureUser;
import com.kbalazsworks.smartscrumpokerbackend.helpers.account_module.fake_builders.InsecureUserFakeBuilder;
import lombok.NonNull;
import org.jooq.DSLContext;

public class Insert2InsecureUser implements IInsert
{
    @Override
    public void runParent()
    {
    }

    @Override
    public void run(@NonNull DSLContext dslContext)
    {
        dslContext.newRecord(InsecureUser.INSECURE_USER, new InsecureUserFakeBuilder().build()).store();
        dslContext.newRecord(InsecureUser.INSECURE_USER, new InsecureUserFakeBuilder().build2()).store();
    }
}
