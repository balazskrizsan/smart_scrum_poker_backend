package com.kbalazsworks.smartscrumpokerbackend.db_presets;

import org.jooq.DSLContext;

public interface IInsert
{
    void runParent();

    void run(DSLContext dslContext);
}
