package dao;

import model.Atleta;

import java.util.List;

public interface AtletaDAO {

	void salva(Atleta atleta);

	Atleta cercaPerId(int id);

	List<Atleta> trovaTutti();
	List<Atleta> getAtletiPerSquadra(int idSquadra);
	void elimina(int id);
}