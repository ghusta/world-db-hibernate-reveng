package fr.husta.test.dto;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CityDTOTest {

    @Test
    public void test_getter() throws Exception {
        CityDTO cityDTO = new CityDTO();

        // getter should exists / test should compile
        String name = cityDTO.getName();
    }

    @Test
    public void test_toString() throws Exception {
        CityDTO cityDTO = new CityDTO();
        cityDTO.setName("Test");

        String output = cityDTO.toString();
        assertThat(output).contains("name=Test");
    }

}
