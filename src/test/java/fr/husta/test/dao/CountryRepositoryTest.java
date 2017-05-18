package fr.husta.test.dao;

import fr.husta.test.config.DatabaseConfiguration;
import fr.husta.test.domain.Country;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DatabaseConfiguration.class})
@ActiveProfiles("TEST")
@Transactional
@Rollback
public class CountryRepositoryTest
{

    @Autowired
    private CountryRepository countryRepository;

    @Before
    public void setUp() throws Exception
    {
        assertThat(countryRepository).isNotNull();
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void findByName() throws Exception
    {
        String name = "France";
        List<Country> countries = countryRepository.findByName(name);
        assertThat(countries).isNotEmpty();
    }

    @Test
    public void findByName_pagination() throws Exception
    {
        String name = "France";
        Pageable page = new PageRequest(0, 50);
        Page<Country> countries = countryRepository.findByName(name, page);
        assertThat(countries).isNotEmpty();
    }

    @Test
    public void findByNameLike() throws Exception
    {
        String name = "France%";
        List<Country> countries = countryRepository.findByNameLike(name);
        assertThat(countries).isNotEmpty();
    }

    @Test
    public void findByNameLike_pagination() throws Exception
    {
        String name = "A%";
        Pageable page = new PageRequest(0, 50);
        Page<Country> countries = countryRepository.findByNameLike(name, page);
        assertThat(countries).isNotEmpty();
        assertThat(countries.getContent()).isNotEmpty();
        assertThat(countries.getTotalElements()).isGreaterThan(10);
    }

    @Test
    @Transactional(readOnly = true)
    public void streamAll() throws Exception
    {
        Stream<Country> countryStream = countryRepository.streamAll();
        try (Stream<Country> stream = countryStream)
        {
            stream
                    .filter(country -> country.getContinent() != null && country.getContinent().equals("Europe"))
                    .sorted(Comparator.comparing(Country::getName))
                    .forEach(country -> System.out.println(String.format("%s on %s", country.getName(), country.getContinent())));
        }
    }


}