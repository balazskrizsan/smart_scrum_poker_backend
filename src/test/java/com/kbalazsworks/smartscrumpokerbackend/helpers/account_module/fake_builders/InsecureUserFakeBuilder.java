package com.kbalazsworks.smartscrumpokerbackend.helpers.account_module.fake_builders;

import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.entities.InsecureUser;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Accessors(fluent = true)
@Getter
@Setter
public class InsecureUserFakeBuilder
{
    public static final long defaultId1 = 102001L;
    public static final long defaultId2 = 102002L;
    public static final UUID defaultIdSecure1 = UUID.fromString("10000000-0000-0000-0000-000000002001");
    public static final UUID defaultIdSecure2 = UUID.fromString("10000000-0000-0000-0000-000000002002");
    public static final UUID defaultIdSecure3 = UUID.fromString("10000000-0000-0000-0000-000000002003");
    public static final UUID defaultIdSecure4 = UUID.fromString("10000000-0000-0000-0000-000000002004");

    private long id = defaultId1;
    private long id2 = defaultId2;
    private UUID idSecure = defaultIdSecure1;
    private UUID idSecure2 = defaultIdSecure2;
    private String userName = "insecure user name";
    private String userName2 = "insecure user name 2";
    private LocalDateTime createdAt = LocalDateTime.of(2020, 11, 22, 11, 22, 33);

    public InsecureUser build()
    {
        return new InsecureUser(id, idSecure, userName, createdAt);
    }


    public List<InsecureUser> buildAsList()
    {
        return new ArrayList<>()
        {{
            add(new InsecureUser(id, idSecure, userName, createdAt));
        }};
    }

    public List<InsecureUser> build2AsList()
    {
        return new ArrayList<>()
        {{
            add(new InsecureUser(id2, idSecure2, userName2, createdAt));
        }};
    }
}
