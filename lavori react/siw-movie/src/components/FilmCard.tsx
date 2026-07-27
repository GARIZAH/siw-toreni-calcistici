/*export interface FilmCardProps {
  title: string
  year: number
  director: string
  rating?: number // prop opzionale (BONUS)
}

export function FilmCard({ title, year, director, rating }: FilmCardProps) {
  return (
    <div
      style={{
        border: '1px solid #e0e0e0',
        borderRadius: '8px',
        padding: '16px',
        margin: '12px 0',
        backgroundColor: '#ffffff',
        boxShadow: '0 2px 4px rgba(0,0,0,0.05)',
        fontFamily: 'sans-serif',
      }}
    >
      <h3 style={{ margin: '0 0 8px 0', color: '#1a73e8' }}>
        {title}
      </h3>

      <p style={{ margin: '4px 0', color: '#5f6368' }}>
        <strong>Anno:</strong> {year}
      </p>

      <p style={{ margin: '4px 0', color: '#5f6368' }}>
        <strong>Regista:</strong> {director}
      </p>

      
      {rating !== undefined && (
        <p
          style={{
            margin: '8px 0 0 0',
            color: '#f4b400',
            fontWeight: 'bold',
          }}
        >
          Valutazione: ⭐ {rating}/5
        </p>
      )}
    </div>
  )
}*/