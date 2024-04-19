package com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.repositories;

import com.kbalazsworks.smartscrumpokerbackend.db.tables.records.InsecureUserSessionsRecord;
import com.kbalazsworks.smartscrumpokerbackend.domain_common.repositories.AbstractRepository;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.entities.InsecureUserSession;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.exceptions.SessionException;
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

    public int countByIdSecure(UUID uuidInsecureUserIdSecure)
    {
        var ctx = getDSLContext();

        return ctx.fetchCount(
            ctx.selectFrom(insecureUserSessionsTable)
                .where(insecureUserSessionsTable.INSECURE_USER_ID_SECURE.eq(uuidInsecureUserIdSecure))
        );
    }

    public void removeBySessionId(UUID sessionId)
    {
        getDSLContext()
            .deleteFrom(insecureUserSessionsTable)
            .where(insecureUserSessionsTable.SESSION_ID.eq(sessionId))
            .execute();
    }

    public InsecureUserSession getInsecureUserSession(UUID sessionId) throws SessionException
    {
        InsecureUserSessionsRecord insecureUserSessionRecord = getDSLContext()
            .selectFrom(insecureUserSessionsTable)
            .where(insecureUserSessionsTable.SESSION_ID.eq(sessionId))
            .fetchOne();

        // @todo: test
        if (null == insecureUserSessionRecord)
        {
            throw new SessionException(STR."Session not found: session_id#\{sessionId}");
        }

        return insecureUserSessionRecord.into(InsecureUserSession.class);
    }
}
