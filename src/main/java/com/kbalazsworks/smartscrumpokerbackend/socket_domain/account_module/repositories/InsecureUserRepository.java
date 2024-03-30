package com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.repositories;

import com.kbalazsworks.smartscrumpokerbackend.db.tables.records.InsecureUserRecord;
import com.kbalazsworks.smartscrumpokerbackend.domain_common.repositories.AbstractRepository;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.entities.InsecureUser;
import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.exceptions.AccountException;
import org.jooq.Record;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class InsecureUserRepository extends AbstractRepository
{
    private final com.kbalazsworks.smartscrumpokerbackend.db.tables.InsecureUser insecureUserTable =
        com.kbalazsworks.smartscrumpokerbackend.db.tables.InsecureUser.INSECURE_USER;

    public InsecureUser create(InsecureUser insecureUser) throws AccountException
    {
        Record newInsecureUser = getDSLContext()
            .insertInto(
                insecureUserTable,
                insecureUserTable.ID_SECURE,
                insecureUserTable.USER_NAME,
                insecureUserTable.CREATED_AT
            )
            .values(
                insecureUser.idSecure(),
                insecureUser.userName(),
                insecureUser.createdAt()
            )
            .returningResult(insecureUserTable.fields())
            .fetchOne();

        if (null == newInsecureUser)
        {
            throw new AccountException("Insecure user creation failed.");
        }

        return newInsecureUser.into(InsecureUser.class);
    }

    public InsecureUser findByIdSecure(String idSecure) throws AccountException
    {
        InsecureUserRecord user = getDSLContext()
            .selectFrom(insecureUserTable)
            .where(insecureUserTable.ID_SECURE.eq(UUID.fromString(idSecure)))
            .fetchOne();

        if (null == user)
        {
            throw new AccountException("User not found");
        }

        return user.into(InsecureUser.class);
    }
}
