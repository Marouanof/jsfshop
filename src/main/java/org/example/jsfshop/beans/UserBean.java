package org.example.jsfshop.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import org.example.jsfshop.models.*;
import org.example.jsfshop.util.JPAUtil;

import java.io.Serializable;

@Named
@SessionScoped
public class UserBean implements Serializable {
    private User currentUser;
    private String email;
    private String password;
    private String nom;

    public String login() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            User user = em.createQuery("SELECT u FROM User u WHERE u.email = :email AND u.password = :password", User.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .getSingleResult();
            this.currentUser = user;
            return "products?faces-redirect=true";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur de connexion", "Email ou mot de passe incorrect"));
            return null;
        } finally {
            em.close();
        }
    }

    public String register() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            User newUser = new User(nom, email, password);
            em.persist(newUser);
            em.getTransaction().commit();
            this.currentUser = newUser;
            return "products?faces-redirect=true";
        } catch (Exception e) {
            em.getTransaction().rollback();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur d'inscription", "Cet email existe déjà"));
            return null;
        } finally {
            em.close();
        }
    }

    public String logout() {
        this.currentUser = null;
        return "index?faces-redirect=true";
    }

    // Getters et Setters
    public User getCurrentUser() { return currentUser; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
}
