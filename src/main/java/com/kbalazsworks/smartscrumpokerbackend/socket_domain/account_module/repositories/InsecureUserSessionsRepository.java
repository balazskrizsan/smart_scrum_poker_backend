package com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.repositories;

import com.kbalazsworks.smartscrumpokerbackend.domain_common.repositories.AbstractRepository;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.entities.InsecureUserSession;
import lombok.NonNull;
import org.jooq.Record1;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class InsecureUserSessionsRepository extends AbstractRepository
{
    private final com.kbalazsworks.smartscrumpokerbackend.db.tables.InsecureUserSessions insecureUserSessionsTable =
        com.kbalazsworks.smartscrumpokerbackend.db.tables.InsecureUserSessions.INSECURE_USER_SESSIONS;

    public boolean create(@NonNull InsecureUserSession insecureUserSession)
    {
        Record1<UUID> returning = getDSLContext()
            .insertInto(
                insecureUserSessionsTable,
                insecureUserSessionsTable.INSECURE_USER_ID_SECURE,
                insecureUserSessionsTable.SESSION_ID,
                insecureUserSessionsTable.CREATED_AT
            )
            .values(
                insecureUserSession.insecureUserIdSecure(),
                insecureUserSession.sessionId(),
                insecureUserSession.createdAt()
            )
            .onDuplicateKeyIgnore()
            .returningResult(insecureUserSessionsTable.SESSION_ID)
            .fetchOne();

        return null != returning;
    }

    public void removeBySessionId(UUID sessionId)
    {
        getDSLContext()
            .deleteFrom(insecureUserSessionsTable)
            .where(insecureUserSessionsTable.SESSION_ID.eq(sessionId))
            .execute();
    }
}
