package model.dao;

public class DAOFactory {

    private static SecouristeDAO secouristeDAO;
    private static DPSDAO dpsDAO;
    private static JourneeDAO journeeDAO;
    private static UserDAO userDAO;

    /**
     * Returns a singleton instance of SecouristeDAO.
     * This method ensures that only one instance of SecouristeDAO is created and used throughout the application.
     *
     * @return SecouristeDAO instance
     */
    public static SecouristeDAO getSecouristeDAO() {
        if (secouristeDAO == null) {
            secouristeDAO = new SecouristeDAO();
        }
        return secouristeDAO;
    }

    /**
     * Returns a singleton instance of SecouristeDAO.
     * This method ensures that only one instance of SecouristeDAO is created and used throughout the application.
     *
     * @return SecouristeDAO instance
     */
    public static DPSDAO getDPSDAO() {
        if (dpsDAO == null) {
            dpsDAO = new DPSDAO();
        }
        return dpsDAO;
    }

    /**
     * Returns a singleton instance of JourneeDAO.
     * This method ensures that only one instance of JourneeDAO is created and used throughout the application.
     *
     * @return JourneeDAO instance
     */
    public static JourneeDAO getJourneeDAO() {
        if (journeeDAO == null) {
            journeeDAO = new JourneeDAO();
        }
        return journeeDAO;
    }

    /**
     * Returns a singleton instance of UserDAO.
     * This method ensures that only one instance of UserDAO is created and used throughout the application.
     *
     * @return UserDAO instance
     */
    public static UserDAO getUserDAO() {
        if (userDAO == null) {
            userDAO = new UserDAO();
        }
        return userDAO;
    }
}