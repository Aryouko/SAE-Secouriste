package model.data.service;
import model.data.persistence.Secouriste;
import model.data.persistence.Journee;

public class DisponibiliteManagement {

    /**
     * The rescuer
     */
    private Secouriste s;

    /**
     * The day
     */
    private Journee j;

    /**
     * Constructor
     * @param secouriste - the rescuer
     * @param journee - the day
     * @throws NullPointerException - throw an exception if one param are null
     */
    public DisponibiliteManagement(Secouriste secouriste, Journee journee) throws NullPointerException{
        if(secouriste == null || journee == null){
            throw new NullPointerException("Les paramètres ne doivent pas être nulls");
        } else {
            this.s = secouriste;
            this.j = journee;
        }
    }

    /**
     * Says if the rescuer is available that day
     * @param journee - the day we are looking for
     * @return true if the rescuer is available, false otherwise
     */
    public boolean estDispo(Journee journee){
        return this.j.equals(journee);
    }
}
