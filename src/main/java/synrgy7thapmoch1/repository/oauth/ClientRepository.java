package synrgy7thapmoch1.repository.oauth;


import org.springframework.data.repository.PagingAndSortingRepository;
import synrgy7thapmoch1.entity.oauth.Client;

public interface ClientRepository extends PagingAndSortingRepository<Client, Long> {

    Client findOneByClientId(String clientId);
}
