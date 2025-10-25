package org.example.jsfshop.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import org.example.jsfshop.models.*;
import org.example.jsfshop.util.JPAUtil;

import java.math.BigDecimal;

@Named
@RequestScoped
public class AdminBean {
    private Product newProduct = new Product();
    private Category newCategory = new Category();
    private Long selectedCategoryId;

    public void addProduct() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            if (selectedCategoryId != null) {
                Category category = em.find(Category.class, selectedCategoryId);
                newProduct.setCategory(category);
            }

            em.persist(newProduct);
            em.getTransaction().commit();

            newProduct = new Product(); // Reset form
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public void addCategory() {
        EntityManager em = JPAUtil.getEntityManager(); // ← SUPPRIMER la ligne JPAUtil = null
        try {
            em.getTransaction().begin();
            em.persist(newCategory);
            em.getTransaction().commit();
            newCategory = new Category();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
        }
    }

    // Getters et Setters
    public Product getNewProduct() { return newProduct; }
    public void setNewProduct(Product newProduct) { this.newProduct = newProduct; }
    public Category getNewCategory() { return newCategory; }
    public void setNewCategory(Category newCategory) { this.newCategory = newCategory; }
    public Long getSelectedCategoryId() { return selectedCategoryId; }
    public void setSelectedCategoryId(Long selectedCategoryId) { this.selectedCategoryId = selectedCategoryId; }
}
