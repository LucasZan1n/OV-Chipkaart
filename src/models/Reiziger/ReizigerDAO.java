package models.Reiziger;

import java.util.List;

public interface ReizigerDAO {
    boolean save(ReizigerModel reiziger);
    boolean update(ReizigerModel reiziger);
    boolean delete(ReizigerModel reiziger);
    ReizigerModel findById(int id);
    List<ReizigerModel> findByGbdatum(String datum);
    List<ReizigerModel> findAll();
}
