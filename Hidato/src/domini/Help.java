/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domini;

/**
 * LOW: Nivell baix d'ajuda
 * MEDIUM: Nivell mitja d'ajuda: No es poden colocar dos nombres consecutius en 
 * caselles no contigues
 * HIGH: Nivell alt d'ajuda: No es pot colocar un nombre si aixo fa que el 
 * hidato deixi de tenir solucio
 * @author Pau
 */
public enum Help {
    LOW, MEDIUM, HIGH;
}
