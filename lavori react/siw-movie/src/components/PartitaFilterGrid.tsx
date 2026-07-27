import { useState } from 'react'
import { PartitaCard } from './PartitaCard'
import type { Partita } from '../types'
 
export function PartitaFilterGrid({ partite }: { partite: Partita[] }) {
  const [squadraFilter, setSquadraFilter] = useState('')
  const [statoFilter, setStatoFilter] = useState('ALL')
 
  const filtered = partite.filter((p) => {
    const cercato = squadraFilter.toLowerCase()
    const matchSquadra = p.squadraHome.nome.toLowerCase().includes(cercato) ||
                         p.squadraAway.nome.toLowerCase().includes(cercato)
    const matchStato = statoFilter === 'ALL' || p.stato === statoFilter
    return matchSquadra && matchStato
  })
 
  return (
    <div>
      <div style={{ background: '#eef2f5', padding: 15, borderRadius: 6, marginBottom: 20, display: 'flex', gap: 15, flexWrap: 'wrap', alignItems: 'center' }}>
        <div>
          <label style={{ fontWeight: 'bold', display: 'block', marginBottom: 5, fontSize: '0.9em' }}>🔍 Cerca Squadra:</label>
          <input
            type="text"
            placeholder="Es: Roma, Lazio..."
            value={squadraFilter}
            onChange={(e) => setSquadraFilter(e.target.value)}
            style={{ padding: 8, borderRadius: 4, border: '1px solid #ccc', width: 200 }}
          />
        </div>
 
        <div>
          <label style={{ fontWeight: 'bold', display: 'block', marginBottom: 5, fontSize: '0.9em' }}>📊 Stato:</label>
          <select
            value={statoFilter}
            onChange={(e) => setStatoFilter(e.target.value)}
            style={{ padding: 8, borderRadius: 4, border: '1px solid #ccc', background: 'black' }}
          >
            <option value="ALL">⚽ Tutte</option>
            <option value="SCHEDULED">⏳ Da giocare</option>
            <option value="PLAYED">✅ Giocate</option>
            <option value="CANCELED">❌ Annullate</option>
          </select>
        </div>
 
        <span style={{ marginLeft: 'auto', fontSize: '0.9em', color: '#555' }}>
          Risultati: <strong>{filtered.length}</strong>
        </span>
      </div>
 
      <ul style={{ listStyle: 'none', padding: 0 }}>
        {filtered.length === 0 ? (
          <p style={{ color: 'gray', fontStyle: 'italic', textAlign: 'center', marginTop: 20 }}>
            Nessuna partita corrisponde ai criteri selezionati.
          </p>
        ) : (
          filtered.map((p) => <PartitaCard key={p.id} partita={p} />)
        )}
      </ul>
    </div>
  )
}