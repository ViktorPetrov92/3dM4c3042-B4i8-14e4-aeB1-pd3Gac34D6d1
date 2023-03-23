package com.example.demo.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "covid_statistics")
public class CovidStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "uuid")
    private String UUID;
    @Column(name = "country")
    private String country;
    @Column(name = "country_code")
    private String countryCode;
    @Column(name = "slug")
    private String slug;
    @Column(name = "new_confirmed")
    private int newConfirmed;
    @Column(name = "total_confirmed")
    private int totalConfirmed;
    @Column(name = "new_deaths")
    private int newDeaths;
    @Column(name = "total_deaths")
    private int totalDeaths;
    @Column(name = "new_recovered")
    private int newRecovered;
    @Column(name = "total_recovered")
    private int totalRecovered;
    @Column(name = "date")
    private String date;
    @Column(name = "time_created")
    private LocalDateTime timeCreated = LocalDateTime.now();
}
