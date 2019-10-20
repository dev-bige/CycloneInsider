import edu.cyclone.insider.models.InsiderUser;
import edu.cyclone.insider.repos.UsersRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class InsiderUserServiceImplIntegrationTest {

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
        timmy.setFirstName("Lil");
        timmy.setLastName("Tim");
        String firstname="Lil";
        String lastname="Tim";

        assertThat(timmy.getUsername())
                .isEqualTo(username);

        assertThat(timmy.getLastName())
                .isEqualTo(lastname);


        assertThat(timmy.getFirstName())
                .isEqualTo(firstname);
    }
}
