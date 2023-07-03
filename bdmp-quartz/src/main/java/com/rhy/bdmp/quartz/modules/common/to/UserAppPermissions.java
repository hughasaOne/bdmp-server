package com.rhy.bdmp.quartz.modules.common.to;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserAppPermissions {
    private Set<String> permissionsIds;
    private String userId;
    private Integer isAdmin;
}
