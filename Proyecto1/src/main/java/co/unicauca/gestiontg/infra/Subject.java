/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.unicauca.gestiontg.infra;

import java.util.ArrayList;

/**
 *
 * @author Nicolas
 */
public class Subject {
    ArrayList<Observer> observers;
    
    public void addObserver(Observer obs) {
        if (observers == null) {
            observers = new ArrayList<>();
        }
        observers.add(obs);
    }
    
    public void notifyAllObserves() {
        System.out.println("Notificando a observadores...");
        for (Observer each : observers) {
            each.update(this);
        }
    }
}
