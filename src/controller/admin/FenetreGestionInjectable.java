package controller.admin;

/**
 * This interface is used to inject the FenetreGestionController into classes that need to interact
 * with the main window controller FenetreGestionController.
 * @author L. Carré, G. Potay, C. Brocart, T.Brami-Coatual
 * @version 1.0
 */
public interface FenetreGestionInjectable {

    /**
     * Sets the FenetreGestionController instance that this class will use to interact with the main window.
     *
     * @param controller the FenetreGestionController instance to set
     */
    void setFenetreGestionController(FenetreGestionController controller);
}

