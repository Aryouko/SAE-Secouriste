package controller.admin;

/**
 * This interface is used to inject the FenetreGestionController into classes that need to interact
 * with the main window controller FenetreGestionController.
 */
public interface FenetreGestionInjectable {
    void setFenetreGestionController(FenetreGestionController controller);
}

