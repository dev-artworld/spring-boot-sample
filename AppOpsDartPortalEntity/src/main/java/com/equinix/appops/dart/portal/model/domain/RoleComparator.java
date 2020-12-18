package com.equinix.appops.dart.portal.model.domain;

import java.util.Comparator;

import com.equinix.appops.dart.portal.entity.Role;



public class RoleComparator implements Comparator<Role>
{
    public int compare(Role r1, Role r2)
    {
        return r1.getPriority() - r2.getPriority();
    }
}