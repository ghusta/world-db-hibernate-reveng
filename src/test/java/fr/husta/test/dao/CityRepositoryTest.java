package fr.husta.test.dao;

import fr.husta.test.config.DatabaseConfiguration;
import fr.husta.test.database.TestDatabase;
import fr.husta.test.domain.City;
import fr.husta.test.domain.Country;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DatabaseConfiguration.class})
@ActiveProfiles("TEST")
@TestDatabase // includes @Transactional
@Rollback
public class CityRepositoryTest
{

    @Autowired
    private CityRepository cityRepository;

    @Before
    public void setUp() throws Exception
    {
        assertThat(cityRepository).isNotNull();
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void findById() throws Exception
    {
        Optional<City> city1 = cityRepository.findById(1);
        assertThat(city1).isPresent();
        assertThat(city1.get().getCountry()).isNotNull();
    }

    @Test
    public void existsById() throws Exception
    {
        boolean exists = cityRepository.existsById(1);
        assertThat(exists).isTrue();
    }

    @Test
    public void findParis_ignoreCase() throws Exception
    {
        City paris = cityRepository.findByNameIgnoreCase("PaRiS");
        assertThat(paris).isNotNull();
    }

    @Test
    public void findParis_andCountry() throws Exception
    {
        City paris = cityRepository.findByName("Paris");
        assertThat(paris).isNotNull();

        Country france = paris.getCountry();
        assertThat(france).isNotNull();
        assertThat(france.getName()).isEqualToIgnoringCase("France");
    }

    @Test(expected = IncorrectResultSizeDataAccessException.class)
    public void findCity_hasDuplicate() throws Exception
    {
        City dupCity = cityRepository.findByName("Alexandria");
        // javax.persistence.NonUniqueResultException
        // -> org.springframework.dao.IncorrectResultSizeDataAccessException
    }

}
