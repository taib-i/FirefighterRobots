package Robot;

import java.util.NoSuchElementException;
import Simulation.DonneesSimulation;
import Carte.*;
import Exception.*;

public abstract class Robot
{
    protected Case position;
    protected int volumeEau;
    protected double vitesse;

    protected DonneesSimulation donnees;

    public Robot(Case position, int volumeEau, double vitesse, DonneesSimulation donnees)
    {
        this.position = position;
        this.volumeEau = volumeEau;
        this.vitesse = vitesse;
        this.donnees = donnees;
    }

    
    /** 
     * Renvoie un Robot du {@link TypeRobot} donnée, à la {@link Case} position, 
     * et avec la vitesse donnée. <p>
     * Throws: <p>
     * {@link VitesseIncorrectException} si,
     * pour un type donnée, la vitesse est incorrecte. <p>
     * {@link NoSuchElementException} si le type du robot est incorrect.
     * @param type
     * @param position
     * @param vitesse
     * @param donnees
     * @return Robot
     * @throws NoSuchElementException
     * @throws VitesseIncorrectException
     */
    public static Robot newRobot(TypeRobot type, Case position, double vitesse, DonneesSimulation donnees) 
        throws NoSuchElementException, VitesseIncorrectException
    {
        switch(type)
        {
            case CHENILLES:
            /* Volume d'eau temporairement à 0, changé dans le constructeur */
                return new Chenilles(position, 0, vitesse, donnees);
            case DRONE:
            //appeller le constructeur du robot drone
                return new Drone(position, 0, vitesse, donnees);
            case ROUES:
            //appeller le constructeur du robot roues
                return new Roues(position, 0, vitesse, donnees);
            case PATTES:
            //appeller le constructeur du robot pattes ( pas un copié-collé)
                return new Pattes(position, donnees);                
            default:
                //SI TYPE EST NULL CA VA BUG ici
                //todo
                throw new NoSuchElementException("Le  type robot " + type.toString() + " n'existe pas!");
        }
    }

    
    /** 
     * Renvoie la position du robot.
     * @return Case
     */
    public Case getPosition() {
        return this.position;
    }
    
    /** 
     * Change la position du Robot.
     * @param positionCase
     * @throws TerrainIncorrectException
     */
    private void setPosition(Case positionCase) throws TerrainIncorrectException
    {
        if (this.getVitesse((positionCase).getNature()) == 0)
        {
            System.out.println("Le robot ne peut pas se déplacer sur ce terrain");
        }
        else
        {
            //A changer pue le seum cette partie du code
            this.position = positionCase;
        }
    }
    
    
    /** 
     * Renvoie true si le Robot est deplacé à la {@link Case} destination, sinon false.
     * @param destination
     * @return boolean
     */
    public boolean moveRobot(Case destination)
    {
        try
        {
            this.setPosition(destination);
            return true;
        } catch (TerrainIncorrectException e)
        {
            return false;
        }
    }
    
    /** 
     * Renvoie true si le Robot est deplacé à la case dans la {@link Direction} dir, sinon false.
     * @param direction
     * @return boolean
     */
    public boolean moveRobotDirection(Direction direction)
    {
        Carte carte = this.donnees.getCarte();
        if (carte.voisinExiste(this.position, direction)) return moveRobot(carte.getVoisin(this.position, direction));
        else return false;
    }

    
    /** 
     * Renvoie le volume d'eau du Robot.
     * @return int
     */
    public int getVolumeEau()
    {
        return this.volumeEau;
    }

    
    /** 
     * Set le volume d'eau du Robot.
     * @param volumeEau
     */
    public void setVolumeEau(int volumeEau)
    {
        this.volumeEau = volumeEau;
    }

    
    /** 
     * Renvoie la vitesse du Robot.
     * @return double
     */
    public double getVitesse()
    {
        return this.vitesse;
    }

    
    /** 
     * Set la vitesse du Robot.
     * @param vitesse
     */
    public void setVitesse(double vitesse)
    {
        this.vitesse = vitesse;
    }

    //ME TAPEZ PAS 
    //NOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
    // public DonneesSimulation getDonnees(){
    //     return this.donnees;
    // }
    public abstract double getVitesse(NatureTerrain nature) throws TerrainIncorrectException;
    public abstract boolean peutRemplir();
    public abstract void deverserEau(int vol) throws VolumeEauIncorrectException;
    public abstract void remplirReservoir() throws TerrainIncorrectException;
}