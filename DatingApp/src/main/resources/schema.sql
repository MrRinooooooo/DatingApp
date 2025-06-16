CREATE DATABASE datingapp;

CREATE TABLE preferenze(
	id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	distanza_massima DOUBLE,
	genere_preferito VARCHAR(255),
	eta_massima INT,
	eta_minima INT,
);

CREATE TABLE utente (
	id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	bio TEXT,
	data_nascita DATE,
	data_registrazione DATE,
	foto_profilo VARCHAR(255),
	genere VARCHAR(255),
	interessi TEXT,
	nome VARCHAR(255),
	password VARCHAR(60) NOT NULL,
	-- campi dell'Embedded posizione, 
	-- nella classe utente includiamo manualmente i 3 campi 
	-- hibernate gestirà questi 3 campi direttamente in questa tabella
	citta VARCHAR(255),
	latitudine DOUBLE,
	longitudine DOUBLE,
	tipo_account VARCHAR(255),
	username VARCHAR(255) UNIQUE NOT NULL,
	preferenze_id BIGINT,
	FOREIGN KEY (preferenze_id) REFERENCES preferenze(id)
);

CREATE TABLE swipe (
	id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	timestamp DATETIME NOT NULL,
	tipo VARCHAR(255) NOT NULL,
	utente_id BIGINT,
	utente_target_id BIGINT)
);

CREATE TABLE report(
	id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	motivo TEXT,
	segnalante_id BIGINT,
	segnalato_id BIGINT,
	timestamp DATETIME)
);



CREATE TABLE notifica(
	id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	contenuto VARCHAR(255),
	letta boolean,
	timestamp DATETIME,
	tipo VARCHAR(100)
	utente_id BIGINT
);

CREATE TABLE messaggio(
	id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	contenuto VARCHAR(255),
	match_id BIGINT,
	mittende_id BIGINT,
	timestamp DATETIME,
	stato VARCHAR(255)
);

CREATE TABLE matches(
	id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	timestamp DATETIME,
	utente1_id BIGINT,
	utente2_id BIGINT)
)

CREATE TABLE abbonamento(
   id bigint(20) NOT NULL,
  attivo bit(1) NOT NULL,
  data_fine date NOT NULL,
  data_inizio date NOT NULL,
  metodo_pagamento varchar(255) DEFAULT NULL,
  stripe_subscription_id bigint(30) DEFAULT NULL,
  tipo varchar(255) DEFAULT NULL,
  utente_id bigint(11) DEFAULT NULL
)
