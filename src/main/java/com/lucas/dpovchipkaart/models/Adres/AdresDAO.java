package com.lucas.dpovchipkaart.models.Adres;

import com.lucas.dpovchipkaart.models.Reiziger.ReizigerModel;

import java.util.List;

public interface AdresDAO {
    boolean save(AdresModel adres);
    boolean update(AdresModel adres);
    boolean delete(AdresModel adres);
    AdresModel findByReiziger(ReizigerModel reiziger);
    List<AdresModel> findAll();
}
