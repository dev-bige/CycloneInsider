import edu.cyclone.insider.models.InsiderUser;
import edu.cyclone.insider.repos.UsersRepository;
import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
public class ExampleTest {
    @RunWith(SpringRunner.class)
    public static class InsiderUserServiceImplIntegrationTest {

        @TestConfiguration
        static class InsiderUserServiceImplTestContextConfiguration {

            @Bean
            public InsiderUser insiderUser() {
                return new InsiderUser();
            }
        }

        @Autowired
        private InsiderUser insiderUser;

        @MockBean
        private UsersRepository usersRepository;
        @Before
        public void setUp() {
            InsiderUser timmy = new InsiderUser();
           timmy.setUsername("LilTimTim");

            Mockito.when(usersRepository.findUserByUsername(timmy.getUsername()))
                    .thenReturn(timmy);
        }
        @Test
        public void whenValidName_thenUserShouldBeFound() {
            InsiderUser timmy= new InsiderUser() ;
            timmy.setUsername("LilTimTim");
            String username = "LilTimTim";

            assertThat(timmy.getUsername())
                    .isEqualTo(username);
        }
    }
}