import { useState, useEffect } from 'react'
import { PartitaFilterGrid } from './components/PartitaFilterGrid'
import { getCalendarioTorneo } from './services/torneoService'
import type { Partita } from './types'
 
export default function App() {
  const [partite, setPartite] = useState<Partita[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)
 
  const torneoId = Number(new URLSearchParams(window.location.search).get('id')) || 1
 
  useEffect(() => {
    getCalendarioTorneo(torneoId)
      .then(setPartite)
      .catch(() => setError('Impossibile connettersi al server.'))
      .finally(() => setLoading(false))
  }, [torneoId])
 
  if (loading) return <p style={{ padding: 40, textAlign: 'center' }}>⏳ Caricamento...</p>
  if (error)   return <p style={{ padding: 40, textAlign: 'center', color: '#e74c3c' }}>❌ {error}</p>
 
  return (
    <div style={{ backgroundColor: '#f1f3f5', minHeight: '100vh', fontFamily: 'Segoe UI, sans-serif', padding: '40px 20px' }}>
      <div style={{ maxWidth: 800, margin: '0 auto', backgroundColor: '#fff', padding: 30, borderRadius: 12, boxShadow: '0 4px 12px rgba(0,0,0,0.08)' }}>
        
        <h1 style={{ color: '#2c3e50', borderBottom: '2px solid #34495e', paddingBottom: 12, marginTop: 0 }}>
          ⚽ Calendario — Torneo #{torneoId}
        </h1>
 
        <PartitaFilterGrid partite={partite} />
 
        <div style={{ marginTop: 30, borderTop: '1px solid #e9ecef', paddingTop: 15 }}>
          <a href={`http://localhost:8080/tornei/${torneoId}/calendario`} style={{ color: '#495057', textDecoration: 'none', fontWeight: 'bold' }}>
            ⬅️ Torna alla visualizzazione standard
          </a>
        </div>
      </div>
    </div>
  )
}
 
        
      
 