package com.SpringBootCountryServiceProject.api.controller;

import com.SpringBootCountryServiceProject.api.beans.Country;
import org.json.JSONException;
import org.junit.jupiter.api.*;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

//@TestMethodOrder((MethodOrderer.OrderAnnotation.class))
@SpringBootTest
class CountryControllerIntegrationTest {

    TestRestTemplate restTemplate = new TestRestTemplate();

    //First start the spring Boot server then run the test class

    @Test
    @Order(1)
    void getCountriesIntegrationTest() throws JSONException {

        String expected = "[\n" +
                "    {\n" +
                "        \"id\": 1,\n" +
                "        \"countryName\": \"India\",\n" +
                "        \"countryCapital\": \"Delhi\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 2,\n" +
                "        \"countryName\": \"USA\",\n" +
                "        \"countryCapital\": \"Washington\"\n" +
                "    }\n" +
                "]";

        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/getCountryies", String.class);

        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    @Order(2)
    void getCountryByIdIntegrationTest() throws JSONException {

        String expected = "{\n" +
                "    \"id\": 1,\n" +
                "    \"countryName\": \"India\",\n" +
                "    \"countryCapital\": \"Delhi\"\n" +
                "}";

        ResponseEntity<String> res = restTemplate.getForEntity("http://localhost:8080/getCountryies/1", String.class);
        System.out.println(res.getStatusCode());
        System.out.println(res.getBody());

        JSONAssert.assertEquals(expected, res.getBody(), false);
    }

    @Test
    @Order(3)
    void getCountryByNameIntegrationTest() throws JSONException {

        String expected = "{\n" +
                "    \"id\": 2,\n" +
                "    \"countryName\": \"USA\",\n" +
                "    \"countryCapital\": \"Washington\"\n" +
                "}";

        ResponseEntity<String> res = restTemplate.getForEntity("http://localhost:8080/getCountryies/countryName?countryName=usa", String.class);
        System.out.println(res.getStatusCode());
        System.out.println(res.getBody());

        JSONAssert.assertEquals(expected, res.getBody(), false);
    }

    @Test
    @Order(4)
    void addCountryIntegrationTest() throws JSONException {

        Country country = new Country(4, "Germany", "Berlin");

        String expected = "{\n" +
                "    \"id\": 4,\n" +
                "    \"countryName\": \"Germany\",\n" +
                "    \"countryCapital\": \"Berlin\"\n" +
                "}";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Country> request = new HttpEntity<>(country, httpHeaders);

        ResponseEntity<String> res = restTemplate.postForEntity("http://localhost:8080/addCountry", request, String.class);

        System.out.println(res.getStatusCode());
        System.out.println(res.getBody());

        Assertions.assertEquals(HttpStatus.CREATED, res.getStatusCode());
        JSONAssert.assertEquals(expected, res.getBody(), false);
    }

    @Test
    @Order(5)
    void updateCountryIntegrationTest() throws JSONException {

        Country country = new Country(4, "Japan", "Tokyo");

        String expected = "{\n" +
                "    \"id\": 4,\n" +
                "    \"countryName\": \"Japan\",\n" +
                "    \"countryCapital\": \"Tokyo\"\n" +
                "}";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Country> request = new HttpEntity<>(country, httpHeaders);

        ResponseEntity<String> res = restTemplate.exchange("http://localhost:8080/updateCountry/4", HttpMethod.PUT, request, String.class);

        System.out.println(res.getStatusCode());
        System.out.println(res.getBody());

        Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());
        JSONAssert.assertEquals(expected, res.getBody(), false);
    }

    @Test
    @Order(6)
    void deleteCountryIntegrationTest() throws JSONException {

        Country country = new Country(3, "Japan", "Tokyo");

        String expected = "{\n" +
                "    \"id\": 3,\n" +
                "    \"countryName\": \"Japan\",\n" +
                "    \"countryCapital\": \"Tokyo\"\n" +
                "}";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Country> request = new HttpEntity<>(country, httpHeaders);

        ResponseEntity<String> res = restTemplate.exchange("http://localhost:8080/deleteCountry/3", HttpMethod.DELETE, request, String.class);

        System.out.println(res.getStatusCode());
        System.out.println(res.getBody());

        Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());
        JSONAssert.assertEquals(expected, res.getBody(), false);

    }
}