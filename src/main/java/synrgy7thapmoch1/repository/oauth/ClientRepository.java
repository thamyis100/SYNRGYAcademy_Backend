package synrgy7thapmoch1.repository.oauth;

import com.example.challenge6.entity.oauth.Client;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClientRepository extends PagingAndSortingRepository<Client, Long> {

    Client findOneByClientId(String clientId);
}
