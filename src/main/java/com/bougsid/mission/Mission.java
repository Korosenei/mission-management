package com.bougsid.mission;

import com.bougsid.decompte.Decompte;
import com.bougsid.employe.Employe;
import com.bougsid.entreprise.Entreprise;
import com.bougsid.missiontype.MissionType;
import com.bougsid.transport.TransportType;
import com.bougsid.ville.Ville;
import org.springframework.context.annotation.Scope;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by ayoub on 6/23/2016.
 */
@Scope("prototype")
@Entity
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMission;
    private String objet;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    //    private String destination;
    @Enumerated(EnumType.STRING)
    private TransportType transportType;
    @OneToOne
    private Employe accompEmploye;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_employe", nullable = false)
    private Employe employe;
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "MISSION_MISSION_STATE",
//            joinColumns = {@JoinColumn(name = "MISSION_ID")},
//            inverseJoinColumns = {@JoinColumn(name = "MISSION_STATE_ID")})
    @OneToMany(mappedBy = "mission",fetch = FetchType.EAGER)
    private Set<MissionState> states = new HashSet<MissionState>();
    @Enumerated(EnumType.STRING)
    private MissionStateEnum currentState;
    @Enumerated(EnumType.STRING)
    private MissionStateEnum nextState;
    @OneToOne
    @JoinColumn(name = "id_type")
    private MissionType type;
    private String comment;
    private String uuid;
    private String secret;// to destinct role
    //    @OneToMany(fetch = FetchType.LAZY)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "MISSION_VILLE",
            joinColumns = {@JoinColumn(name = "MISSION_ID")},
            inverseJoinColumns = {@JoinColumn(name = "VILLE_ID")})
    private Set<Ville> villes = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "id_entreprise", nullable = true)
    private Entreprise entreprise;
    @OneToOne
    private Decompte decompte;

    public Mission() {
//        this.transport = new Transport();
        this.uuid = UUID.randomUUID().toString();
        this.secret = UUID.randomUUID().toString();
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public TransportType getTransportType() {
        return transportType;
    }

    public void setTransportType(TransportType transportType) {
        this.transportType = transportType;
    }

    public Employe getAccompEmploye() {
        return accompEmploye;
    }

    public void setAccompEmploye(Employe accompEmploye) {
        this.accompEmploye = accompEmploye;
    }
//
//    public Vehicule getServiceVehicule() {
//        return serviceVehicule;
//    }
//
//    public void setServiceVehicule(Vehicule serviceVehicule) {
//        this.serviceVehicule = serviceVehicule;
//    }

    //    public String getDestination() {
//        return destination;
//    }
//
//    public void setDestination(String destination) {
//        this.destination = destination;
//    }

//    public Transport getTransport() {
//        return transport;
//    }
//
//    public void setTransport(Transport transport) {
//        this.transport = transport;
//    }

    public Long getIdMission() {
        return idMission;
    }

    public void setIdMission(Long idMission) {
        this.idMission = idMission;
    }

    public Set<MissionState> getStates() {
        return states;
    }

    public void setStates(Set<MissionState> states) {
        this.states = states;
    }

    public void addState(MissionState state) {
        this.states.add(state);
    }

    public MissionStateEnum getCurrentState() {
        return currentState;
    }

    public void setCurrentState(MissionStateEnum currentState) {
        this.currentState = currentState;
    }

    public MissionStateEnum getNextState() {
        return nextState;
    }

    public void setNextState(MissionStateEnum nextState) {
        this.nextState = nextState;
    }

    public MissionType getType() {
        return type;
    }

    public void setType(MissionType type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Transient
    public MissionState getLastState() {
        return Collections.max(this.states, Comparator.comparing(MissionState::getStateDate));
    }

    public Set<Ville> getVilles() {
        System.out.println(" 3 Ville Size ="+villes.size());

        return villes;
    }
    public void setVilles(Set<Ville> villes) {
        this.villes = villes;
        System.out.println("2 Ville Size ="+villes.size());
    }

    @Transient
    public void addVille(Ville ville) {
        this.villes.add(ville);
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }


    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public Decompte getDecompte() {
        return decompte;
    }

    public void setDecompte(Decompte decompte) {
        this.decompte = decompte;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Transient
    public String getStringfyVille() {
        String stringfyVilles = "";
        System.out.println("Ville Size ="+villes.size());
        for (Ville ville : villes) {
            stringfyVilles += ville.getNom() + ",";
        }
        stringfyVilles = stringfyVilles.substring(0, stringfyVilles.length() - 1);
        return stringfyVilles;
    }

    @Override
    public String toString() {
        return "Mission{" +
                "idMission=" + idMission +
                ", objet='" + objet + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
//                ", transport=" + transport +
                ", employe=" + employe +
                '}';
    }
}
