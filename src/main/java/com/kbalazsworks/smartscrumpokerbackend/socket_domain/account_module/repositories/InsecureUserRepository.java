package com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.repositories;

import com.kbalazsworks.smartscrumpokerbackend.db.tables.records.InsecureUserRecord;
import com.kbalazsworks.smartscrumpokerbackend.domain_common.repositories.AbstractRepository;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.entities.InsecureUser;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.exceptions.AccountException;
import lombok.NonNull;
import org.jooq.Record;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class InsecureUserRepository extends AbstractRepository
{

    public InsecureUser create(@NonNull InsecureUser insecureUser) throws AccountException
    {
        InsecureUserRecord insecureUserRecord = getDSLContext().newRecord(insecureUserTable, insecureUser);
        insecureUserRecord.store();

        if (insecureUserRecord.getId() == null)
        {
            throw new AccountException("Insecure user creation failed.");
        }

        return insecureUserRecord.into(InsecureUser.class);
    }

    public InsecureUser findByIdSecure(@NonNull UUID idSecure) throws AccountException
    {
        InsecureUserRecord user = getDSLContext()
            .selectFrom(insecureUserTable)
            .where(insecureUserTable.ID_SECURE.eq(idSecure))
            .fetchOne();

        if (null == user)
        {
            throw new AccountException("User not found");
        }

        return user.into(InsecureUser.class);
    }

    public List<InsecureUser> findByIdSecureList(@NonNull List<UUID> idSecureList)
    {
        return getDSLContext()
            .selectFrom(insecureUserTable)
            .where(insecureUserTable.ID_SECURE.in(idSecureList))
            .fetchInto(InsecureUser.class);
    }

    public List<InsecureUser> searchUsersWithActiveSession(@NonNull List<UUID> idSecures)
    {
        return getDSLContext()
            .selectDistinct(insecureUserTable.fields())
            .from(insecureUserTable)
            .rightJoin(insecureUserSessionsTable)
            .on(insecureUserTable.ID_SECURE.eq(insecureUserSessionsTable.INSECURE_USER_ID_SECURE))
            .where(insecureUserSessionsTable.INSECURE_USER_ID_SECURE.in(idSecures))
            .fetchInto(InsecureUser.class);
    }
}
