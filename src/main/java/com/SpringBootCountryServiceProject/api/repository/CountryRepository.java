package com.SpringBootCountryServiceProject.api.repository;

import com.SpringBootCountryServiceProject.api.beans.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Integer> {

    Optional<Country> findByCountryName(String countryName);

}
