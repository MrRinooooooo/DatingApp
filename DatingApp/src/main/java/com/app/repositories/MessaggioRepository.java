package com.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.Messaggio;

public interface MessaggioRepository extends JpaRepository<Messaggio, Long>{

}
