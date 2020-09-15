//import org.junit.Test;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import ru.kalashnikofffs.eCare.dao.ClientDAO;
//import ru.kalashnikofffs.eCare.dao.RoleDAO;
//import ru.kalashnikofffs.eCare.model.Client;
//import ru.kalashnikofffs.eCare.service.ClientService;
//import ru.kalashnikofffs.eCare.serviceImpl.ClientServiceImpl;
//
////@ExtendWith(MockitoExtension.class)
//public class ClentServiceTest {
//
//    @Mock
//    BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @Mock
//    private ClientDAO clientDAO;
//    @Mock
//    private RoleDAO roleDao;
//    @InjectMocks
//    private ClientServiceImpl clientService = new ClientServiceImpl();
//
//    @BeforeEach
//    void init_mocks() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void addUserTest () {
//        clientService.setClientDAO(clientDAO);
//        Client client =  new Client();
//        client.setId(1123L);
//        clientService.add(client);
//
//
//
//    }
//}
