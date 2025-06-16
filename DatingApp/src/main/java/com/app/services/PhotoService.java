package com.app.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.dto.UtenteDiscoverDTO;
import com.app.entities.Utente;
import com.app.repositories.UtenteRepository;

@Service
public class PhotoService {
	
	//salvataggio delle foto profilo nella cartella
	@Value("${file.upload-dir}")
	private String uploadDir;
	
	@Autowired
	UtenteRepository utenteRepository;
	
	
	//METTERE QUI LA LOGICA DEL SALVATAGGIO E MANIPOLAZIONE DEL FILE
	
	public Utente addPhoto(String email, MultipartFile file) throws IOException {
		
		Utente utente = utenteRepository.findByUsername(email)
				.orElseThrow(() -> new RuntimeException("utente non trovato"));
		

		Path uploadPath = Paths.get(uploadDir);		//prendiamo la cartella
		Path userPath = Paths.get(uploadDir, utente.getId().toString());

		if (!Files.exists(uploadPath)) {	//se la cartella non esiste la creiamo
			Files.createDirectories(uploadPath);
		
		}
		
		if (!Files.exists(userPath)) {	//crea la sottocartella con l'id dell'utente corrispondente
			Files.createDirectories(userPath);
		}
		
		String fileName = file.getOriginalFilename();	//prende il nome originale del file caricato
		Path filePath = userPath.resolve(fileName);		//qui creo il percorso del file da salvare nella cartella
		
		Files.write(filePath, file.getBytes());		//getBytes restituisce il contenuto del file sotto forma di array di byte per salvarlo sul disco
		utente.setFotoProfilo(fileName);
		
		return utenteRepository.save(utente);
	}
	
	public byte[] getPhoto(String email) {
	    Utente utente = utenteRepository.findByUsername(email)
	            .orElseThrow(() -> new RuntimeException("Utente non trovato"));

	    String nomeFoto = utente.getFotoProfilo();

	    if (nomeFoto == null || nomeFoto.isEmpty()) {
	        throw new RuntimeException("Nessuna immagine trovata");
	    }

	    Path userPath = Paths.get("uploads",utente.getId().toString(), nomeFoto);

	    if (!Files.exists(userPath)) {
	        throw new RuntimeException("Nessuna immagine trovata nella cartella");
	    }

	    try {
	        return Files.readAllBytes(userPath);
	    } catch (IOException e) {
	        throw new RuntimeException("Errore nella lettura del file", e);
	    }
	}

	
	//elimina foto 
	
	public Utente deletePhoto(String email, String fileName) {
		Utente utente = utenteRepository.findByUsername(email)
				.orElseThrow(() -> new RuntimeException("utente non trovato"));
		
		try {
		
		Path userPath = Paths.get(uploadDir, utente.getId().toString());	//prendiamo il path della sottocartella
		Path filePath = userPath.resolve(fileName);
		
		
		Files.deleteIfExists(filePath) ;	//eliminiamo il file 
		// nel caso tutte le foto vengono eliminate verrà cancellata anche la cartella
		Files.deleteIfExists(userPath);		
		
		
		} catch(IOException e)  {
			throw new RuntimeException("errore durante l'eliminazione del file" + e.getMessage());
		}
				
		utente.setFotoProfilo(null);
		
		return utenteRepository.save(utente);
	}
}
