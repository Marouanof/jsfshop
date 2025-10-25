package org.example.jsfshop.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import org.example.jsfshop.models.Product;
import org.example.jsfshop.util.JPAUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class ProductBean {
    private List<Product> products;
    private String searchTerm;

    public List<Product> getProducts() {
        if (products == null) {
            loadProducts();
        }
        return products;
    }

    private void loadProducts() {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManager();
            String query = "SELECT p FROM Product p";
            if (searchTerm != null && !searchTerm.isEmpty()) {
                query += " WHERE p.nom LIKE :term OR p.description LIKE :term";
            }

            var typedQuery = em.createQuery(query, Product.class);
            if (searchTerm != null && !searchTerm.isEmpty()) {
                typedQuery.setParameter("term", "%" + searchTerm + "%");
            }

            products = typedQuery.getResultList();
            System.out.println("✅ " + products.size() + " produits chargés");

        } catch (Exception e) {
            System.err.println("❌ Erreur chargement produits: " + e.getMessage());
            products = new ArrayList<>();
            // Données de test
            if (products.isEmpty()) {
                Product testProduct = new Product();
                testProduct.setId(1L);
                testProduct.setNom("Produit Test");
                testProduct.setDescription("Description test");
                testProduct.setPrix(BigDecimal.valueOf(9));
                testProduct.setStock(10);
                products.add(testProduct);
            }
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public String getSearchTerm() { return searchTerm; }
    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
        this.products = null; // Force le rechargement
    }
}
