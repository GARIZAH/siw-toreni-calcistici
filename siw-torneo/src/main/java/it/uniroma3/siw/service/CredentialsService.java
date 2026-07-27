package it.uniroma3.siw.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.repository.CredentialsRepository;

@Service
public class CredentialsService {
	private CredentialsRepository credentialsRepository;
	private PasswordEncoder passwordEncoder; 

    public CredentialsService(CredentialsRepository credentialsRepository, 
                               PasswordEncoder passwordEncoder) { 
        this.credentialsRepository = credentialsRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional(readOnly = true)
	public Credentials getCredentials(Long id) {
		return this.credentialsRepository.findById(id).get() ;
	}
    @Transactional(readOnly = true)
	public Credentials getCredentials(String username) {
		return this.credentialsRepository.findByUsername(username).get();
	}
	@Transactional
	public Credentials saveCredentials(Credentials credentials) {
		credentials.setRole(Credentials.DEFAULT_ROLE); // imposta ruolo default
	    credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
		return this.credentialsRepository.save(credentials);
	}

}
