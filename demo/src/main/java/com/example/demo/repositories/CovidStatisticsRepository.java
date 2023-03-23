package com.example.demo.repositories;

import com.example.demo.entities.CovidStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CovidStatisticsRepository extends JpaRepository<CovidStatistics,String> {

    Optional<CovidStatistics> findFirstByCountryCodeOrderByTimeCreatedDesc(String countryCode);
}
