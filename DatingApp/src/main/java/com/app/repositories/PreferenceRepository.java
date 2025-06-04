package com.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.Preferenze;

public interface PreferenceRepository extends JpaRepository<Preferenze, Long> {

}
