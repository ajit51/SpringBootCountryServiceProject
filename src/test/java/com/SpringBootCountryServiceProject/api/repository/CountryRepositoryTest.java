package com.SpringBootCountryServiceProject.api.repository;

import org.assertj.core.api.Assertions;
import com.SpringBootCountryServiceProject.api.beans.Country;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
public class CountryRepositoryTest {

    @Autowired
    private CountryRepository countryRepository;

    @Test
    @Order(1)
    @Transactional
    @Rollback(value = false) // Getting Roll Back error
    public void saveCountryTest() {

        Country country = Country.builder()
                .id(6)
                .countryName("India")
                .countryCapital("Delhi")
                .build();
        countryRepository.save(country);

        Assertions.assertThat(country.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getCountryTest() {
        Country country = countryRepository.findById(6).get();
        Assertions.assertThat(country.getId()).isEqualTo(6);
    }

    @Test
    @Order(3)
    public void getListOfCountryTest() {
        List<Country> countries = countryRepository.findAll();

        Assertions.assertThat(countries.size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    @Transactional
    @Rollback(value = false)
    public void updateCountryTest() {

        Country country = countryRepository.findById(5).get();

        country.setCountryCapital("Patna");

        Country updatedCountry = countryRepository.save(country);

        Assertions.assertThat(updatedCountry.getCountryCapital()).isEqualTo("Patna");
    }

    @Test
    @Order(5)
    @Transactional
    @Rollback(value = false)
    public void deleteCountryTest() {

        Country country = countryRepository.findById(6).get();
        countryRepository.delete(country);

        //countryRepository.deleteById(5);

        Country country1 = null;
        Optional<Country> optionalCountry = countryRepository.findByCountryName("India");
        if (optionalCountry.isPresent()) {
            country1 = optionalCountry.get();
        }

        Assertions.assertThat(country1).isNull();
    }
}
