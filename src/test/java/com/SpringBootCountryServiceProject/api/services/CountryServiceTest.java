package com.SpringBootCountryServiceProject.api.services;

import com.SpringBootCountryServiceProject.api.beans.Country;
import com.SpringBootCountryServiceProject.api.controllers.AddResponse;
import com.SpringBootCountryServiceProject.api.repository.CountryRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CountryServiceTest {

    @Mock
    private CountryRepository countryRepo;

    @InjectMocks
    private CountryService countryService;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Order(1)
    public void getAllCountriesTest(){
        List<Country> myCountries = new ArrayList<>();
        myCountries.add(new Country(1, "Russia ", "Moscow "));
        myCountries.add(new Country(2, "Pakistan", "Islamabad "));

        when(countryRepo.findAll()).thenReturn(myCountries);//mocking

        assertEquals(2, countryService.getAllCountries().size());
    }

    @Test
    @Order(2)
    public void getCountryByIdTest(){
        List<Country> myCountries = new ArrayList<>();
        myCountries.add(new Country(1, "Russia ", "Moscow "));
        myCountries.add(new Country(2, "Pakistan", "Islamabad "));

        int countryId = 1;

        when(countryRepo.findAll()).thenReturn(myCountries);//mocking
        assertEquals(1, countryService.getCountryById(countryId).getId());
    }

    @Test
    @Order(3)
    public void getCountryByNameTest(){
        List<Country> myCountries = new ArrayList<>();
        myCountries.add(new Country(1, "Russia ", "Moscow "));
        myCountries.add(new Country(2, "Pakistan", "Islamabad "));

        String countryName = "Pakistan";

        when(countryRepo.findAll()).thenReturn(myCountries);//mocking
        assertEquals(2, countryService.getCountryByName(countryName).getId());
    }

    @Test
    @Order(4)
    public void addCountryTest(){
        Country country = new Country(1, "Germany", "Berlin");
        when(countryRepo.save(country)).thenReturn(country);
        assertEquals(country, countryService.addCountry(country));
    }

    @Test
    @Order(5)
    public void updateCountryTest(){
        Country country = new Country(1, "Germany", "Berlin");
        when(countryRepo.save(country)).thenReturn(country);
        assertEquals(country, countryService.updateCountry(country));
    }

    @Test
    @Order(6)
    public void deleteeCountryTest(){
        Country country = new Country(1, "Germany", "Berlin");
        countryService.deleteeCountry(country);
        verify(countryRepo, times(1)).delete(country);

    }
}
