package com.rms.model;

public enum StatutCommande {
    EN_COURS("En cours"),
    Payer("Payer");
    
    private final String statut;
    StatutCommande(String statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return statut;
    }
}
