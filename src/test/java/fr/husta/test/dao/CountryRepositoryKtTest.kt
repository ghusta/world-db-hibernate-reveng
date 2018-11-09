package fr.husta.test.dao

import fr.husta.test.config.DatabaseConfiguration
import fr.husta.test.database.TestDatabase
import fr.husta.test.domain.Country
import org.assertj.core.api.Assertions.assertThat
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import kotlin.test.Test

@RunWith(SpringRunner::class)
@ContextConfiguration(classes = [DatabaseConfiguration::class])
@ActiveProfiles("TEST")
@TestDatabase  // includes @Transactional
@Rollback
class CountryRepositoryKtTest {

    @Autowired
    lateinit var countryRepository: CountryRepository

    @Test
    fun `find by name not null`() {
        val countries = countryRepository.findByName("France")
        assertThat<Country>(countries).isNotEmpty()
    }

}