package com.kbalazsworks.smartscrumpokerbackend.helpers.account_module.fake_builders;

import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.entities.InsecureUser;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Accessors(fluent = true)
@Getter
@Setter
public class InsecureUserFakeBuilder
{
    public static final long defaultId1 = 102001L;
    public static final long defaultId2 = 102002L;
    public static final long defaultId3 = 102003L;
    public static final UUID defaultIdSecure1 = UUID.fromString("10000000-0000-0000-0000-000000002001");
    public static final UUID defaultIdSecure2 = UUID.fromString("10000000-0000-0000-0000-000000002002");
    public static final UUID defaultIdSecure3 = UUID.fromString("10000000-0000-0000-0000-000000002003");
    public static final UUID defaultIdSecure4 = UUID.fromString("10000000-0000-0000-0000-000000002004");

    private long id = defaultId1;
    private long id2 = defaultId2;
    private long id3 = defaultId3;
    private UUID idSecure = defaultIdSecure1;
    private UUID idSecure2 = defaultIdSecure2;
    private UUID idSecure3 = defaultIdSecure3;
    private String userName = "insecure user name";
    private String userName2 = "insecure user name 2";
    private String userName3 = "insecure user name 3";
    private LocalDateTime createdAt = LocalDateTime.of(2020, 11, 22, 11, 22, 33);

    public InsecureUser build()
    {
        return new InsecureUser(id, idSecure, userName, createdAt);
    }

    public InsecureUser build2()
    {
        return new InsecureUser(id2, idSecure2, userName2, createdAt);
    }

    public InsecureUser build3()
    {
        return new InsecureUser(id3, idSecure3, userName3, createdAt);
    }

    public List<InsecureUser> buildAsList()
    {
        return List.of(build());
    }

    public List<InsecureUser> build2AsList()
    {
        return List.of(build2());
    }

    public List<InsecureUser> build1and2AsList()
    {
        return List.of(build(), build2());
    }
}
