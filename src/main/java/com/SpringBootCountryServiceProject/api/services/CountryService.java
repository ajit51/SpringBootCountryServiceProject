package com.SpringBootCountryServiceProject.api.services;

import com.SpringBootCountryServiceProject.api.beans.Country;
import com.SpringBootCountryServiceProject.api.controllers.AddResponse;
import com.SpringBootCountryServiceProject.api.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepo;

    public List<Country> getAllCountries() {
        return countryRepo.findAll();
    }

    public Country getCountryById(Integer countryId) {
        List<Country> countries = countryRepo.findAll();
        Country country = null;
        for (Country con : countries){
            if (con.getId() == countryId)
                country = con;
        }
        return country;
    }

    public Country getCountryByName(String countryName) {
        List<Country> countries = countryRepo.findAll();
        Country country = null;
        for (Country c : countries) {
            if (countryName.equalsIgnoreCase(c.getCountryName())) {
                country = c;
            }
        }
        return country;
    }

    public Country addCountry(Country country) {;
        Country savedCountry = countryRepo.save(country);
        return savedCountry;
    }

    public Country updateCountry(Country country) {
        return countryRepo.save(country);
    }

    public AddResponse deleteCountry(Integer countryId) {
        countryRepo.deleteById(countryId);
        AddResponse response = new AddResponse();
        response.setId(countryId);
        response.setMsg("Country Deleted !!");
        return response;
    }

    public void deleteeCountry(Country country) {
        countryRepo.delete(country);
    }
}
