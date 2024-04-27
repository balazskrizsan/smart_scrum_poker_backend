package com.kbalazsworks.smartscrumpokerbackend.domain_common.services;

import com.kbalazsworks.smartscrumpokerbackend.config.ApplicationProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.TransactionalRunnable;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JooqService
{
    private final ApplicationProperties ap;
    private static DSLContext dslContext = null;

    public DSLContext getDbContext()
    {
        if (null == dslContext)
        {
            HikariConfig config = new HikariConfig();
            config.setTransactionIsolation("TRANSACTION_READ_UNCOMMITTED");
            config.setJdbcUrl(ap.getDataSourceUrl());
            config.setUsername(ap.getDataSourceUsername());
            config.setPassword(ap.getDataSourcePassword());
            config.setMaximumPoolSize(10);
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            this.dslContext = DSL.using(new HikariDataSource(config), SQLDialect.POSTGRES);
        }

        return dslContext;
    }

    void transaction(@NonNull TransactionalRunnable transactional)
    {
        getDbContext().transaction(transactional);
    }
}
