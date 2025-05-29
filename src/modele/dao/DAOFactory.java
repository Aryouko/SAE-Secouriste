package modele.dao;

public class DAOFactory {

    private static SecouristeDAO secouristeDAO;
    private static DPSDAO dpsDAO;
    private static JourneeDAO journeeDAO;

    // Fournit une seule instance de chaque DAO (singleton basique)
    public static SecouristeDAO getSecouristeDAO() {
        if (secouristeDAO == null) {
            secouristeDAO = new SecouristeDAO();
        }
        return secouristeDAO;
    }

    public static DPSDAO getDPSDAO() {
        if (dpsDAO == null) {
            dpsDAO = new DPSDAO();
        }
        return dpsDAO;
    }

    public static JourneeDAO getJourneeDAO() {
        if (journeeDAO == null) {
            journeeDAO = new JourneeDAO();
        }
        return journeeDAO;
    }
}