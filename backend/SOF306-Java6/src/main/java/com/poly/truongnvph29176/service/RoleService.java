package com.poly.truongnvph29176.service;

import com.poly.truongnvph29176.dto.request.RoleRequest;
import com.poly.truongnvph29176.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();
    Role createRole(RoleRequest roleRequest);
    Role updateRole(String id, RoleRequest roleRequest) throws Exception;
    void deleteRole(String id);
}
