package com.SpringBootCountryServiceProject.api.controller;

import com.SpringBootCountryServiceProject.api.beans.Country;
import com.SpringBootCountryServiceProject.api.controllers.CountryController;
import com.SpringBootCountryServiceProject.api.services.CountryService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {CountryControllerTest.class})
public class CountryControllerTest {

    @Mock
    private CountryService countryService;

    @InjectMocks
    private CountryController countryController;

    List<Country> myCountries;
    Country country;

    @Test
    @Order(1)
    public void getCountriesTest() {

        myCountries = new ArrayList<>();
        myCountries.add(new Country(1, "India", "Delhi"));
        myCountries.add(new Country(1, "USA", "Washington"));

        Mockito.when(countryService.getAllCountries()).thenReturn(myCountries);

        ResponseEntity<List<Country>> res = countryController.getCountries();

        Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());
        Assertions.assertEquals(2, res.getBody().size());

    }

    @Test
    @Order(2)
    public void getCountryByIdTest(){
        country = new Country(2, "India", "Delhi");
        int countryId = 2;

        Mockito.when(countryService.getCountryById(countryId)).thenReturn(country);
        ResponseEntity<Country> res = countryController.getCountryById(countryId);

        Assertions.assertEquals(HttpStatus.FOUND, res.getStatusCode());
        Assertions.assertEquals(countryId, res.getBody().getId());
    }

    @Test
    @Order(3)
    public void getCountryByNameTest(){
        country = new Country(2, "USA", "Washington");
        String countryName = "USA";

        Mockito.when(countryService.getCountryByName(countryName)).thenReturn(country);
        ResponseEntity<Country> response = countryController.getCountryByName(countryName);

        Assertions.assertEquals(HttpStatus.FOUND, response.getStatusCode());
        Assertions.assertEquals(country.getCountryCapital(), response.getBody().getCountryCapital());
    }

    @Test
    @Order(4)
    public void addCountryTest(){
        country = new Country(3, "Germany", "Berlin");

        Mockito.when(countryService.addCountry(country)).thenReturn(country);
        ResponseEntity<Country> response = countryController.addCountry(country);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(country, response.getBody());
    }

    @Test
    @Order(5)
    public void updateCountryTest(){
        country = new Country(3, "Japan", "Tokyo");
        int countryId = 3;

        Mockito.when(countryService.getCountryById(countryId)).thenReturn(country);
        Mockito.when(countryService.updateCountry(country)).thenReturn(country);
        ResponseEntity<Country> response = countryController.updateCountry(country, countryId);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(3, response.getBody().getId());
        Assertions.assertEquals("Japan", response.getBody().getCountryName());
        Assertions.assertEquals("Tokyo", response.getBody().getCountryCapital());
    }

    @Test
    @Order(6)
    public void deleteCountryTest(){
        country = new Country(3, "Germany", "Berlin");
        int countryId = 3;

        Mockito.when(countryService.getCountryById(countryId)).thenReturn(country);
        ResponseEntity<Country> response = countryController.deleteCountry(countryId);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(country, response.getBody());
    }
}
