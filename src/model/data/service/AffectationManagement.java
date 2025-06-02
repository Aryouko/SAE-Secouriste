package model.data.service;
import model.data.persistence.Affectation;
import java.util.ArrayList;

/**
 * Class allow to collect all affectation
 * @author L. Carré, G. Potay, C. Brocart, T.Brami--Coatual
 * @version 1.0
 */
public class AffectationManagement {
    private ArrayList<Affectation> listAffectation;

    /**
     * Constructor for AffectationManagement
     * @param affectations - an affectation list
     */
    public AffectationManagement(ArrayList<Affectation> affectations) {
        this.listAffectation = affectations;
    }

    /**
     * Method allow to add an affectation
     * @param affectation - affectation to add
     */
    public void addAffectation(Affectation affectation) {
        this.listAffectation.add(affectation);
    }

    /**
     * Method allow to remove an affectation
     * @param affectation - affectation to remove
     */
    public void removeAffectation(Affectation affectation) {
        this.listAffectation.remove(affectation);
    }
}
