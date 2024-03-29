package com.kbalazsworks.smartscrumpokerbackend.helpers.account_module.fake_builders;

import com.kbalazsworks.smartscrumpokerbackend.socket_domain.account_module.entities.InsecureUser;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.UUID;

@Accessors(fluent = true)
@Getter
@Setter
public class InsecureUserFakeBuilder
{
    public static final long defaultId1 = 102001L;
    public static final UUID defaultIdSecure1 = UUID.fromString("10000000-0000-0000-0000-000000002001");

    private long id = defaultId1;
    private UUID idSecure = defaultIdSecure1;
    private String userName = "insecure user name";
    private LocalDateTime createdAt = LocalDateTime.of(2020, 11, 22, 11, 22, 33);

    public InsecureUser build()
    {
        return new InsecureUser(id, idSecure, userName, createdAt);
    }
}
