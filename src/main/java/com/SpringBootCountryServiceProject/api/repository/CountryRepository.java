package com.SpringBootCountryServiceProject.api.repository;

import com.SpringBootCountryServiceProject.api.beans.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Integer> {

    public Country findByCountryName(String countryName);
}