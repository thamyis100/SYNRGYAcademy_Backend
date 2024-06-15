package synrgy7thapmoch1.repository.oauth;


import org.springframework.data.repository.PagingAndSortingRepository;
import synrgy7thapmoch1.entity.oauth.Role;

import java.util.List;

public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {
    Role findOneByName(String name);

    List<Role> findByNameIn(String[] names);
}
