package com.SpringBootCountryServiceProject.api.controllers;

import com.SpringBootCountryServiceProject.api.beans.Country;
import com.SpringBootCountryServiceProject.api.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.NoSuchElementException;

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
        return new ResponseEntity<>(countryService.getCountryById(countryId), HttpStatus.FOUND);
    }

    @GetMapping("/getCountryies/countryName")
    public ResponseEntity<Country> getCountryByName(@PathParam(value = "countryName") String countryName) {
        try {
            Country country = countryService.getCountryByName(countryName);
            return new ResponseEntity<Country>(country, HttpStatus.FOUND);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Country>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addCountry")
    public ResponseEntity<Country> addCountry(@RequestBody Country country) {
        try {
            Country country1 = countryService.addCountry(country);
            return new ResponseEntity<>(country1, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Country>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/updateCountry/{countryId}")
    public ResponseEntity<Country> updateCountry(@RequestBody Country country, @PathVariable Integer countryId) {
       try {
           Country existingCountry = countryService.getCountryById(countryId);
           existingCountry.setCountryName(country.getCountryName());
           existingCountry.setCountryCapital(country.getCountryCapital());
           Country updateCountry = countryService.updateCountry(existingCountry);
           return new ResponseEntity<>(updateCountry, HttpStatus.OK);
       }catch (NoSuchElementException e){
           return new ResponseEntity<Country>(HttpStatus.CONFLICT);
       }
    }

  /*  @DeleteMapping("/deleteCountry/{countryId}")
    public ResponseEntity<AddResponse> deleteCountry(@PathVariable Integer countryId) {
        countryService.deleteCountry(countryId);
        return new ResponseEntity<>(countryService.deleteCountry(countryId), HttpStatus.OK);
    }*/

    @DeleteMapping("/deleteCountry/{countryId}")
    public ResponseEntity<Country> deleteCountry(@PathVariable Integer countryId) {
        Country country = null;
        try {
            country = countryService.getCountryById(countryId);
            countryService.deleteeCountry(country);
        }catch (NoSuchElementException e){
            return new ResponseEntity<Country>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(country, HttpStatus.OK);
    }


}
