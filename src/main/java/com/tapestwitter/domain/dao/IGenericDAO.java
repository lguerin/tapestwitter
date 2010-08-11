package com.tapestwitter.domain.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Contrat d'interface de DAO generique.
 * (cf. http://www.ibm.com/developerworks/java/library/j-genericdao.html)
 * Expose les methodes CRUD standards communes a tous les DAO.
 * Tous les DAO de l'application implementent cette interface.
 * 
 * @author lguerin
 * @param <T>
 * @param <PK>
 */
public interface IGenericDAO<T, PK extends Serializable>
{
    /**
     * Persiste l'instance en base
     * 
     * @param newInstance Instance a persister
     */
    void create(T newInstance);

    /**
     * Recupere un objet en base a partir de son identifiant unique.
     * 
     * @param id Identifiant de l'objet a recuperer
     * @return Objet resultat
     */
    T findById(PK id);

    /**
     * Sauvegarde en base les modifications apportees a l'objet
     * passe en parametre.
     * 
     * @param transientObject Objet a mettre a jour
     */
    void update(T transientObject);

    /**
     * Supprime un objet persistant de la base
     * 
     * @param persistentObject Objet a supprimer
     */
    void delete(T persistentObject);

    /**
     * Recupere toutes les entites du type T
     */
    List<T> listAll();
}
