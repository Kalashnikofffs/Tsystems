package ru.kalashnikofffs.eCare.dao;

import ru.kalashnikofffs.eCare.model.Role;

import java.util.List;

public interface RoleDAO {
    List<Role> allRoles();
    Role getById(Long id);
}
