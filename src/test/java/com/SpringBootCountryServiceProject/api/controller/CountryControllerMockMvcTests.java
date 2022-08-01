package com.SpringBootCountryServiceProject.api.controller;

import com.SpringBootCountryServiceProject.api.beans.Country;
import com.SpringBootCountryServiceProject.api.controllers.CountryController;
import com.SpringBootCountryServiceProject.api.services.CountryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ComponentScan(basePackages = "com.restservices.demo")
@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest(classes = CountryControllerMockMvcTests.class)
public class CountryControllerMockMvcTests {

    @Autowired
    MockMvc mockMvc;

    @Mock
    CountryService countryService;

    @InjectMocks
    CountryController countryController;


    List<Country> myCountries;
    Country country;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(countryController).build();
    }

    @Test
    @Order(1)
    public void getCountriesTest() throws Exception {

        myCountries = new ArrayList<>();
        myCountries.add(new Country(1, "India", "Delhi"));
        myCountries.add(new Country(2, "USA", "Washington"));

        when(countryService.getAllCountries()).thenReturn(myCountries); //mocking

        this.mockMvc.perform(MockMvcRequestBuilders.get("/getCountryies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Order(2)
    public void getCountryByIdTest() throws Exception {
        country = new Country(2, "USA", "Washington");
        int countryId = 2;

        when(countryService.getCountryById(countryId)).thenReturn(country);

        mockMvc.perform(MockMvcRequestBuilders.get("/getCountryies/{countryId}", countryId))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath(".id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("USA"))
                .andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("Washington"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Order(3)
    public void getCountryByNameTest() throws Exception {
        country = new Country(2, "USA", "Washington");
        String countryName = "USA";

        when(countryService.getCountryByName(countryName)).thenReturn(country);

        mockMvc.perform(MockMvcRequestBuilders.get("/getCountryies/countryName").param("countryName", countryName))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath(".id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("USA"))
                .andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("Washington"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Order(4)
    public void addCountryTest() throws Exception {
        country = new Country(3, "Germany", "Berlin");

        when(countryService.addCountry(country)).thenReturn(country);

        ObjectMapper mapper = new ObjectMapper();
        String jsonBody = mapper.writeValueAsString(country);

        mockMvc.perform(MockMvcRequestBuilders.post("/addCountry")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Order(5)
    public void updateCountryTest() throws Exception {
        country = new Country(3, "Germany", "Berlin");
        int countryId = 3;

        when(countryService.getCountryById(countryId)).thenReturn(country); //mocking
        when(countryService.updateCountry(country)).thenReturn(country); //mocking

        ObjectMapper mapper = new ObjectMapper();
        String jsonBody = mapper.writeValueAsString(country);

        mockMvc.perform(MockMvcRequestBuilders.put("/updateCountry/{countryId}", countryId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".id").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("Germany"))
                .andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("Berlin"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Order(6)
    public void deleteCountryTest() throws Exception {
        country = new Country(3, "Germany", "Berlin");
        int countryId = 3;

        when(countryService.getCountryById(countryId)).thenReturn(country); //mocking

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/deleteCountry/{countryId}", countryId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}