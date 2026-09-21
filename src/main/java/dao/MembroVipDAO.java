package dao;

import model.MembroVip;


import java.util.List;

public interface MembroVipDAO {
    public void salva(MembroVip membroVip );
    MembroVip cercaPerUsername(String username);
    List<MembroVip> trovaTutti();
    void aggiornaMembro(MembroVip membroVip);
}
