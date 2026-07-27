-- ==========================================
-- 1. UTENTE E CREDENZIALI ADMIN
-- ==========================================
INSERT INTO utente (id, nome, cognome, email) VALUES (999, 'Paolo', 'Merialdo', 'p.merialdo@uniroma3.it');

INSERT INTO credentials (id, username, password, role, utente_id) VALUES (999, 'paolo', '$2a$10$yWAIDyuEr78BBBFZ5cYh8.Nw4gUHFTRG5FwaWqNCGeOD8M4mh3.xy', 'ADMIN', 999);
-- =========================================================================
-- 2. INSERIMENTO TORNEI
-- =========================================================================
INSERT INTO torneo (id, nome, anno, descrizione) VALUES (nextval('torneo_seq'), 'Torneo Estivo Roma', 2026, 'Campionato amatoriale calciotto');
INSERT INTO torneo (id, nome, anno, descrizione) VALUES (nextval('torneo_seq'), 'Coppa dei Campioni SIW', 2026, 'Torneo elite facoltà ingegneria');

-- =========================================================================
-- 3. INSERIMENTO SQUADRE
-- =========================================================================
INSERT INTO squadra (id, nome, anno_di_fondazione, citta) VALUES (nextval('squadra_seq'), 'GLI SPORCHI', 2024, 'Roma');
INSERT INTO squadra (id, nome, anno_di_fondazione, citta) VALUES (nextval('squadra_seq'), 'Los Puercos', 2025, 'Roma');
INSERT INTO squadra (id, nome, anno_di_fondazione, citta) VALUES (nextval('squadra_seq'), 'AC Picchia', 2023, 'Milano');
INSERT INTO squadra (id, nome, anno_di_fondazione, citta) VALUES (nextval('squadra_seq'), 'Real Madrink', 2026, 'Roma');

-- =========================================================================
-- 4. COLLEGAMENTO TORNEO <-> SQUADRE (Tabella: squadra_torneo)
-- =========================================================================
INSERT INTO squadra_torneo (squadra_id, torneo_id) VALUES ((SELECT id FROM squadra WHERE nome = 'GLI SPORCHI' LIMIT 1), (SELECT id FROM torneo WHERE nome = 'Torneo Estivo Roma' LIMIT 1));
INSERT INTO squadra_torneo (squadra_id, torneo_id) VALUES ((SELECT id FROM squadra WHERE nome = 'Los Puercos' LIMIT 1), (SELECT id FROM torneo WHERE nome = 'Torneo Estivo Roma' LIMIT 1));
INSERT INTO squadra_torneo (squadra_id, torneo_id) VALUES ((SELECT id FROM squadra WHERE nome = 'Real Madrink' LIMIT 1), (SELECT id FROM torneo WHERE nome = 'Torneo Estivo Roma' LIMIT 1));
INSERT INTO squadra_torneo (squadra_id, torneo_id) VALUES ((SELECT id FROM squadra WHERE nome = 'AC Picchia' LIMIT 1), (SELECT id FROM torneo WHERE nome = 'Coppa dei Campioni SIW' LIMIT 1));
INSERT INTO squadra_torneo (squadra_id, torneo_id) VALUES ((SELECT id FROM squadra WHERE nome = 'GLI SPORCHI' LIMIT 1), (SELECT id FROM torneo WHERE nome = 'Coppa dei Campioni SIW' LIMIT 1));
INSERT INTO squadra_torneo (squadra_id, torneo_id) VALUES ((SELECT id FROM squadra WHERE nome = 'Los Puercos' LIMIT 1), (SELECT id FROM torneo WHERE nome = 'Coppa dei Campioni SIW' LIMIT 1));
INSERT INTO squadra_torneo (squadra_id, torneo_id) VALUES ((SELECT id FROM squadra WHERE nome = 'Real Madrink' LIMIT 1), (SELECT id FROM torneo WHERE nome = 'Coppa dei Campioni SIW' LIMIT 1));

-- =========================================================================
-- 5. INSERIMENTO PARTITE
-- =========================================================================
INSERT INTO partita (id, data_ora, luogo, goals_home, goals_away, stato, torneo_id, squadra_home_id, squadra_away_id) VALUES (nextval('partita_seq'), '2026-06-01 20:30:00', 'Campo Pro Roma', 3, 1, 'PLAYED', (SELECT id FROM torneo WHERE nome = 'Torneo Estivo Roma' LIMIT 1), (SELECT id FROM squadra WHERE nome = 'GLI SPORCHI' LIMIT 1), (SELECT id FROM squadra WHERE nome = 'Los Puercos' LIMIT 1));
INSERT INTO partita (id, data_ora, luogo, goals_home, goals_away, stato, torneo_id, squadra_home_id, squadra_away_id) VALUES (nextval('partita_seq'), '2026-06-08 21:45:00', 'Campo Pro Roma', 0, 0, 'PLAYED', (SELECT id FROM torneo WHERE nome = 'Coppa dei Campioni SIW' LIMIT 1), (SELECT id FROM squadra WHERE nome = 'AC Picchia' LIMIT 1), (SELECT id FROM squadra WHERE nome = 'Real Madrink' LIMIT 1));
INSERT INTO partita (id, data_ora, luogo, goals_home, goals_away, stato, torneo_id, squadra_home_id, squadra_away_id) VALUES (nextval('partita_seq'), '2026-06-15 20:00:00', 'Ostia Beach Arena', 2, 2, 'PLAYED', (SELECT id FROM torneo WHERE nome = 'Torneo Estivo Roma' LIMIT 1), (SELECT id FROM squadra WHERE nome = 'Real Madrink' LIMIT 1), (SELECT id FROM squadra WHERE nome = 'GLI SPORCHI' LIMIT 1));
INSERT INTO partita (id, data_ora, luogo, goals_home, goals_away, stato, torneo_id, squadra_home_id, squadra_away_id) VALUES (nextval('partita_seq'), '2026-11-15 19:00:00', 'Pala Green', 2, 4, 'PLAYED', (SELECT id FROM torneo WHERE nome = 'Coppa dei Campioni SIW' LIMIT 1), (SELECT id FROM squadra WHERE nome = 'Real Madrink' LIMIT 1), (SELECT id FROM squadra WHERE nome = 'Los Puercos' LIMIT 1));
INSERT INTO partita (id, data_ora, luogo, goals_home, goals_away, stato, torneo_id, squadra_home_id, squadra_away_id) VALUES (nextval('partita_seq'), '2026-11-22 21:00:00', 'Pala Green', 1, 0, 'PLAYED', (SELECT id FROM torneo WHERE nome = 'Coppa dei Campioni SIW' LIMIT 1), (SELECT id FROM squadra WHERE nome = 'AC Picchia' LIMIT 1), (SELECT id FROM squadra WHERE nome = 'GLI SPORCHI' LIMIT 1));
INSERT INTO partita (id, data_ora, luogo, goals_home, goals_away, stato, torneo_id, squadra_home_id, squadra_away_id) VALUES (nextval('partita_seq'), '2026-12-10 20:00:00', 'Campo Pro Roma', NULL, NULL, 'SCHEDULED', (SELECT id FROM torneo WHERE nome = 'Torneo Estivo Roma' LIMIT 1), (SELECT id FROM squadra WHERE nome = 'Los Puercos' LIMIT 1), (SELECT id FROM squadra WHERE nome = 'Real Madrink' LIMIT 1));

-- =========================================================================
-- 6. INSERIMENTO GIOCATORI
-- =========================================================================
INSERT INTO giocatore (id, nome, cognome, data_di_nascita, ruolo, squadra_id) VALUES (nextval('giocatore_seq'), 'Francesco', 'Totti', '1976-09-27', 'Attaccante', (SELECT id FROM squadra WHERE nome = 'GLI SPORCHI' LIMIT 1));
INSERT INTO giocatore (id, nome, cognome, data_di_nascita, ruolo, squadra_id) VALUES (nextval('giocatore_seq'), 'Daniele', 'De Rossi', '1983-07-24', 'Centrocampista', (SELECT id FROM squadra WHERE nome = 'GLI SPORCHI' LIMIT 1));
INSERT INTO giocatore (id, nome, cognome, data_di_nascita, ruolo, squadra_id) VALUES (nextval('giocatore_seq'), 'Alessandro', 'Del Piero', '1974-11-09', 'Attaccante', (SELECT id FROM squadra WHERE nome = 'Los Puercos' LIMIT 1));
INSERT INTO giocatore (id, nome, cognome, data_di_nascita, ruolo, squadra_id) VALUES (nextval('giocatore_seq'), 'Gianluigi', 'Buffon', '1978-01-28', 'Portiere', (SELECT id FROM squadra WHERE nome = 'Los Puercos' LIMIT 1));
INSERT INTO giocatore (id, nome, cognome, data_di_nascita, ruolo, squadra_id) VALUES (nextval('giocatore_seq'), 'Andrea', 'Pirlo', '1979-05-19', 'Centrocampista', (SELECT id FROM squadra WHERE nome = 'AC Picchia' LIMIT 1));
INSERT INTO giocatore (id, nome, cognome, data_di_nascita, ruolo, squadra_id) VALUES (nextval('giocatore_seq'), 'Filippo', 'Inzaghi', '1973-08-09', 'Attaccante', (SELECT id FROM squadra WHERE nome = 'AC Picchia' LIMIT 1));
INSERT INTO giocatore (id, nome, cognome, data_di_nascita, ruolo, squadra_id) VALUES (nextval('giocatore_seq'), 'Paolo', 'Maldini', '1968-06-26', 'Difensore', (SELECT id FROM squadra WHERE nome = 'Real Madrink' LIMIT 1));
INSERT INTO giocatore (id, nome, cognome, data_di_nascita, ruolo, squadra_id) VALUES (nextval('giocatore_seq'), 'Gennaro', 'Gattuso', '1978-01-09', 'Centrocampista', (SELECT id FROM squadra WHERE nome = 'Real Madrink' LIMIT 1));


INSERT INTO arbitro (id, nome, cognome, codice_arbitrale) VALUES (1001, 'Pierluigi', 'Collina', 'ARB-001'), (1002, 'Nicola', 'Rizzoli', 'ARB-002'), (1003, 'Gianluca', 'Rocchi', 'ARB-003'), (1004, 'Daniele', 'Orsato', 'ARB-004'), (1005, 'Paolo', 'Valeri', 'ARB-005'), (1006, 'Marco', 'Guida', 'ARB-006'), (1007, 'Fabio', 'Maresca', 'ARB-007'), (1008, 'Davide', 'Massa', 'ARB-008');