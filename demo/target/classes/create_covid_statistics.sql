create schema covid_statistics;
create table covid_statistics
(
    id               int auto_increment
        primary key,
    unique_id        varchar(50) null,
    current_value    float       null,
    current_currency varchar(10) null,
    target_currency  varchar(10) null,
    target_value     float       null,
    date             varchar(20) null,
    constraint transactions_unique_id_uindex
        unique (unique_id)
);