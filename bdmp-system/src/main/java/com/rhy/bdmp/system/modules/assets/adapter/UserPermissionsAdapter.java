package com.rhy.bdmp.system.modules.assets.adapter;

import com.rhy.bdmp.system.modules.assets.permissions.PermissionsAbstract;

import java.util.Set;


public class UserPermissionsAdapter implements PermissionsAbstract {

    private final PermissionsAbstract permissionsAbstract;

    public UserPermissionsAdapter(PermissionsAbstract permissionsAbstract){
        this.permissionsAbstract = permissionsAbstract;
    }

    @Override
    public Set<String> getPermissions(String userId) {
        return permissionsAbstract.getPermissions(userId);
    }
}
