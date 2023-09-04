package repositories;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import models.Role;

@ApplicationScoped
public class RoleRepository implements PanacheRepository<Role> {
    public Role findByName(String name) {
        return find("name", name).firstResult();
    }
}
