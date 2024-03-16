package com.kbalazsworks.smartscrumpokerbackend.db_migrations;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.jooq.DSLContext;

import static org.jooq.impl.DSL.using;

abstract class AbstractBaseJooqMigration extends BaseJavaMigration
{
    protected DSLContext getDslContext(Context context)
    {
        return using(context.getConnection());
    }
}
