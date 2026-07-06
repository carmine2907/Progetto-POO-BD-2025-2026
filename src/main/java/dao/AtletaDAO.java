package dao;

import model.Atleta;

import java.util.List;

/**
 * The interface Atleta dao.
 */
public interface AtletaDAO {

	/**
	 * Salva.
	 *
	 * @param atleta the atleta
	 */
	void salva(Atleta atleta);

	/**
	 * Cerca per id atleta.
	 *
	 * @param id the id
	 * @return the atleta
	 */
	Atleta cercaPerId(int id);

	/**
	 * Trova tutti list.
	 *
	 * @return the list
	 */
	List<Atleta> trovaTutti();

	/**
	 * Gets atleti per squadra.
	 *
	 * @param idSquadra the id squadra
	 * @return the atleti per squadra
	 */
	List<Atleta> getAtletiPerSquadra(int idSquadra);

	/**
	 * Elimina.
	 *
	 * @param id the id
	 */
	void elimina(int id);
}