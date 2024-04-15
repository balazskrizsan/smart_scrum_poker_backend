package com.kbalazsworks.smartscrumpokerbackend.helpers.account_module.fake_builders;

import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.entities.InsecureUserSession;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Accessors(fluent = true)
@Getter
@Setter
public class InsecureUserSessionFakeBuilder
{
    public static final UUID defaultInsecureUserIdSecure = InsecureUserFakeBuilder.defaultIdSecure1;
    public static final UUID defaultInsecureUserIdSecure2 = InsecureUserFakeBuilder.defaultIdSecure1;
    public static final UUID defaultInsecureUserIdSecure3 = InsecureUserFakeBuilder.defaultIdSecure2;
    public static final UUID defaultSessionId = UUID.fromString("10000000-0000-0000-0000-000000005001");
    public static final UUID defaultSessionId2 = UUID.fromString("10000000-0000-0000-0000-000000005002");
    public static final UUID defaultSessionId3 = UUID.fromString("10000000-0000-0000-0000-000000005003");

    private UUID insecureUserIdSecure = defaultInsecureUserIdSecure;
    private UUID insecureUserIdSecure3 = defaultInsecureUserIdSecure3;
    private UUID sessionId = defaultSessionId;
    private UUID sessionId3 = defaultSessionId3;
    private LocalDateTime createdAt = LocalDateTime.of(2020, 11, 22, 11, 22, 33);

    public InsecureUserSession build()
    {
        return new InsecureUserSession(insecureUserIdSecure, sessionId, createdAt);
    }

    public InsecureUserSession build3()
    {
        return new InsecureUserSession(insecureUserIdSecure3, sessionId3, createdAt);
    }

    public List<InsecureUserSession> buildAsList()
    {
        return List.of(new InsecureUserSession(insecureUserIdSecure, sessionId, createdAt));
    }
}