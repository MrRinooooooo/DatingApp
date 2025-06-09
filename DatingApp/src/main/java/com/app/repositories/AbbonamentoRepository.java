package com.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entities.Abbonamento;

@Repository
public interface AbbonamentoRepository extends JpaRepository <Abbonamento, Long> {

}
