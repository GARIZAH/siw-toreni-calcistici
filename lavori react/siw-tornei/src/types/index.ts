export interface Squadra {
  id: number
  nome: string
}
 
export interface Partita {
  id: number
  squadraHome: Squadra
  squadraAway: Squadra
  goalsHome?: number
  goalsAway?: number
  dataOra?: string
  stato: 'SCHEDULED' | 'PLAYED' | 'CANCELED'
  luogo?: string
}
 
export interface Torneo {
  id: number
  nome: string
  anno: number
}
 