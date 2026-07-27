import api from './api'
import type { Partita } from '../types'
 
export async function getCalendarioTorneo(torneoId: number): Promise<Partita[]> {
  const response = await api.get<Partita[]>(`/rest/tornei/${torneoId}/calendario`)
  return response.data
}
 