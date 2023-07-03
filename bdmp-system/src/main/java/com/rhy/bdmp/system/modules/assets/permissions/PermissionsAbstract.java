package com.rhy.bdmp.system.modules.assets.permissions;

import java.util.Set;

public interface PermissionsAbstract {
    Set<String> getPermissions(String userId);
}
