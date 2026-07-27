
import type { Partita } from '../types'
 
function formattaData(dataOra?: string) {
  if (!dataOra) return 'Data da definire'
  return new Date(dataOra).toLocaleString('it-IT', {
    day: '2-digit', month: '2-digit', year: 'numeric',
    hour: '2-digit', minute: '2-digit',
  })
}
 
const statoBadge: Record<Partita['stato'], { label: string; color: string; bg: string }> = {
  PLAYED:    { label: 'Giocata',    color: '#2b8a3e', bg: '#d3f9d8' },
  SCHEDULED: { label: '⏳ Da giocare', color: '#495057', bg: '#e2e8f0' },
  CANCELED:  { label: '🚫 Annullata',  color: '#c92a2a', bg: '#ffe3e3' },
}
 
export function PartitaCard({ partita }: { partita: Partita }) {
  const badge = statoBadge[partita.stato]
 
  return (
    <li style={{
      display: 'flex', alignItems: 'center', gap: 12, flexWrap: 'wrap',
      padding: '14px 16px', marginBottom: 10,
      border: '1px solid #dee2e6', borderRadius: 8,
      background: '#f8f9fa', boxShadow: '0 2px 4px rgba(0,0,0,0.04)',
    }}>
      <span style={{ fontSize: '0.85em', fontWeight: 600, background: '#e9ecef', padding: '5px 10px', borderRadius: 6, color: '#495057' }}>
        {formattaData(partita.dataOra)}
      </span>
 
      <span style={{ fontWeight: 'bold', fontSize: '1.05em', color: '#212529' }}>
        {partita.squadraHome.nome}
      </span>
 
      <span style={{ fontWeight: 'bold', padding: '4px 12px', borderRadius: 4, color: badge.color, background: badge.bg }}>
        {partita.stato === 'PLAYED' ? `${partita.goalsHome} - ${partita.goalsAway}` : badge.label}
      </span>
 
      <span style={{ fontWeight: 'bold', fontSize: '1.05em', color: '#212529' }}>
        {partita.squadraAway.nome}
      </span>
 
      {partita.luogo && (
        <span style={{ marginLeft: 'auto', color: '#868e96', fontStyle: 'italic', fontSize: '0.9em' }}>
          📍 {partita.luogo}
        </span>
      )}
    </li>
  )
}
 