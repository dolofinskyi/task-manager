package ua.dolofinskyi.features.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByOauth2Sub(String oauth2Sub);
}
