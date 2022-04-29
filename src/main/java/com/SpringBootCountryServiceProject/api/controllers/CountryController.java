package com.SpringBootCountryServiceProject.api.controllers;

import com.SpringBootCountryServiceProject.api.beans.Country;
import com.SpringBootCountryServiceProject.api.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
public class CountryController {

    @Autowired
    private CountryService countryService;

    @GetMapping("/getCountryies")
    public ResponseEntity<List<Country>> getCountries() {
        List<Country> countries = countryService.getAllCountries();
        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    @GetMapping("/getCountryies/{countryId}")
    public ResponseEntity<Country> getCountryById(@PathVariable(value = "countryId") Integer countryId) {
        return new ResponseEntity<>(countryService.getCountryById(countryId), HttpStatus.OK);
    }

    @GetMapping("/getCountryies/countryName")
    public ResponseEntity<Country> getCountryByName(@PathParam(value = "countryName") String countryName) {
        return new ResponseEntity<>(countryService.getCountryByName(countryName), HttpStatus.OK);
    }

    @PostMapping("/addCountry")
    public ResponseEntity<Country> addCountry(@RequestBody Country country) {
        return new ResponseEntity<>(countryService.addCountry(country), HttpStatus.OK);
    }

    @PutMapping("/updateCountry/{countryId}")
    public ResponseEntity<Country> updateCountry(@RequestBody Country country, @PathVariable Integer countryId) {
        Country existingCountry = countryService.getCountryById(countryId);
        existingCountry.setCountryName(country.getCountryName());
        existingCountry.setCountryCapital(country.getCountryCapital());
        Country updateCountry = countryService.updateCountry(existingCountry);
        return new ResponseEntity<>(updateCountry, HttpStatus.OK);
    }

    @DeleteMapping("/deleteCountry/{countryId}")
    public ResponseEntity<AddResponse> addCountry(@PathVariable Integer countryId) {
        return new ResponseEntity<>(countryService.deleteCountry(countryId), HttpStatus.OK);
    }


}
