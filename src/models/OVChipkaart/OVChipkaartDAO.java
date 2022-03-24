package models.OVChipkaart;

import models.Reiziger.ReizigerModel;

import java.util.List;

public interface OVChipkaartDAO {
    boolean save(OVChipkaartModel ovChipkaartModel);
    boolean update(OVChipkaartModel ovChipkaartModel);
    boolean delete(OVChipkaartModel ovChipkaartModel);
    List<OVChipkaartModel> findByReiziger(ReizigerModel reiziger);
    List<OVChipkaartModel> findAll();
    OVChipkaartModel findByKaartNummer(int kaartNummer);
}