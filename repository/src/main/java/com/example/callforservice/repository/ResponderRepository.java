package com.example.callforservice.repository;

import com.example.callforservice.repository.model.ResponderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponderRepository extends JpaRepository<ResponderModel, Long> {

  @Query("SELECT res FROM ResponderModel res WHERE res.responderCode = :responderCode")
  ResponderModel findByResponderCode(final String responderCode);
}
