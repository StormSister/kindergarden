package com.monika.kindergarden.repository;

import com.monika.kindergarden.model.Holiday;

import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;




@Repository
public interface HolidaysRepository extends CrudRepository<Holiday, String> {


}

