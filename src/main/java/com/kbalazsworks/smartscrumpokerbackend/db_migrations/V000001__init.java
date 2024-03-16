package com.kbalazsworks.smartscrumpokerbackend.db_migrations;

import lombok.val;
import org.flywaydb.core.api.migration.Context;
import org.springframework.stereotype.Component;

import static org.jooq.impl.DSL.constraint;
import static org.jooq.impl.SQLDataType.*;

@Component
public class V000001__init extends AbstractBaseJooqMigration
{
    @Override
    public void migrate(Context context)
    {
        val qB = getQueryBuilder(context);

        qB.createTable("pokers")
            .column("id", BIGINT.nullable(false).identity(true))
            .column("id_secure", UUID.nullable(false))
            .column("name", VARCHAR.nullable(false))
            .column("created_at", TIMESTAMP.nullable(false))
            .column("created_by", VARCHAR.nullable(true))
            .constraint(constraint("pokers_pk").primaryKey("id"))
            .execute();
    }
}
