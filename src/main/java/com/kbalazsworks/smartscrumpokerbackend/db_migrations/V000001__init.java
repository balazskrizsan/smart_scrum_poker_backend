package com.kbalazsworks.smartscrumpokerbackend.db_migrations;

import org.flywaydb.core.api.migration.Context;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

import static org.jooq.impl.DSL.constraint;
import static org.jooq.impl.SQLDataType.*;

@Component
public class V000001__init extends AbstractBaseJooqMigration
{
    @Override
    public void migrate(Context context)
    {
        DSLContext dslContext = getDslContext(context);

        dslContext.createTable("poker")
            .column("id", BIGINT.nullable(false).identity(true))
            .column("id_secure", UUID.nullable(false))
            .column("name", VARCHAR.nullable(false))
            .column("created_at", TIMESTAMP.nullable(false))
            .column("created_by", VARCHAR.nullable(true))
            .constraints(
                constraint("poker_pk").primaryKey("id"),
                constraint("id_secure_unique").unique("id_secure")
            )
            .execute();

        dslContext.createTable("ticket")
            .column("id", BIGINT.nullable(false).identity(true))
            .column("poker_id", BIGINT.nullable(false))
            .column("name", VARCHAR.nullable(false))
            .column("active", BOOLEAN.nullable(false).defaultValue(false))
            .constraints(
                constraint("ticket_pk").primaryKey("id"),
                constraint("fk___ticket__poker_id___poker__id___on_delete_cascade")
                    .foreignKey("poker_id")
                    .references("poker", "id")
                    .onDeleteCascade()
            )
            .execute();

        dslContext.createTable("vote")
            .column("id", BIGINT.nullable(false).identity(true))
            .column("uncertainty", SMALLINT.nullable(false))
            .column("complexity", SMALLINT.nullable(false))
            .column("effort", SMALLINT.nullable(false))
            .column("calculated_point", SMALLINT.nullable(false))
            .column("created_at", TIMESTAMP.nullable(false))
            .column("created_by", VARCHAR.nullable(true))
            .constraints(
                constraint("vote_pk").primaryKey("id")
            )
            .execute();

        dslContext.createTable("insecure_user")
            .column("id", BIGINT.nullable(false).identity(true))
            .column("id_secure", UUID.nullable(false))
            .column("user_name", VARCHAR.nullable(false))
            .column("created_at", TIMESTAMP.nullable(false))
            .constraints(
                constraint("insecure_user_pk").primaryKey("id"),
                constraint("insecure_user_unique").unique("id_secure")
            )
            .execute();
    }
}
