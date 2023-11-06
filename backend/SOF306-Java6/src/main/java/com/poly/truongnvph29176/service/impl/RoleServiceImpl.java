package com.poly.truongnvph29176.service.impl;

import com.poly.truongnvph29176.dto.request.RoleRequest;
import com.poly.truongnvph29176.entity.Role;
import com.poly.truongnvph29176.exception.DataNotFoundException;
import com.poly.truongnvph29176.repository.RoleRepository;
import com.poly.truongnvph29176.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role createRole(RoleRequest roleRequest) {
        Role role = Role.builder()
                .id(roleRequest.getId())
                .name(roleRequest.getName())
                .build();
        return roleRepository.save(role);
    }

    @Override
    @Transactional
    public Role updateRole(String id, RoleRequest roleRequest) throws Exception {
        Role roleId = roleRepository.findById(id).orElseThrow(() ->
                    new DataNotFoundException("Cannot find with id = " + id)
                );
        roleId.setName(roleRequest.getName());
        roleRepository.save(roleId);
        return roleId;
    }

    @Override
    @Transactional
    public void deleteRole(String id) {
        Optional<Role> opsRole = roleRepository.findById(id);
        if(opsRole.isPresent()) {
            roleRepository.deleteById(id);
        }
    }
}
