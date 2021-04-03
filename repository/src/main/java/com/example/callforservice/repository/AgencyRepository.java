package com.example.callforservice.repository;

import com.example.callforservice.repository.model.AgencyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgencyRepository extends JpaRepository<AgencyModel, String> {
}
