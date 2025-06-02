package model.data.persistence;
/**
 * This class stores the number of someone that we need for DPS
 * @author L. Carré, G. Potay, C. Brocart, T.Brami--Coatual
 * @version 1.0
*/
public class Besoin {

    /**
     * Private variable containing the number
     */
    int nombre; 

    /**
     * Get the attribute "nombre"
     * @return the value in "nombre"
     */
    public int getNombre() {
        return this.nombre;
    }

    /**
     * Set the attribute "nombre"
     * @param nombre the new value to "nombre"
     */
    public void setNombre(int nombre) {
        this.nombre = nombre;
    }
}
