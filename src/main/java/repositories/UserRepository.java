package repositories;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import models.User;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    public User findByIdentity(String identity) {
        return find("identity", identity).firstResult();
    }

    public User findByEmail(String email) {
        return find("email", email).firstResult();
    }

    public User findByUsername(String username) {
        return find("username", username).firstResult();
    }
}
