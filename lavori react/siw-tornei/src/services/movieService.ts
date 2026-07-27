/*import api from './api';
import type { Movie } from '../types';

export async function getMovies(): Promise<Movie[]> {
  try {
    const response = await api.get<Movie[]>('/rest/movies');
    return response.data;
  } catch (error) {
    throw new Error('Errore nel caricamento dei film');
  }
}

export async function createMovie(data: Omit<Movie, 'id'>): Promise<Movie> {
  try {
    const response = await api.post<Movie>('/rest/movies', data);
    return response.data;
  } catch (error) {
    throw new Error('Errore nella creazione del film');
  }
}*/