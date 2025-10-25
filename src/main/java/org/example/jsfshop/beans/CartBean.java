package org.example.jsfshop.beans;

import org.example.jsfshop.util.JPAUtil;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import org.example.jsfshop.models.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Named
@SessionScoped
public class CartBean implements Serializable {

    @Inject
    private UserBean userBean;

    private Cart cart;

    public void addToCart(Long productId, int quantity) {
        if (userBean.getCurrentUser() == null) {
            // Rediriger vers login
            return;
        }

        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            User user = em.find(User.class, userBean.getCurrentUser().getId());
            Product product = em.find(Product.class, productId);

            if (user.getCart() == null) {
                user.setCart(new Cart(user));
            }

            CartItem newItem = new CartItem(user.getCart(), product, quantity);
            user.getCart().getItems().add(newItem);

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public void removeFromCart(Long cartItemId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            CartItem item = em.find(CartItem.class, cartItemId);
            if (item != null) {
                em.remove(item);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public List<CartItem> getCartItems() {
        if (userBean.getCurrentUser() == null) {
            return List.of();
        }

        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT ci FROM CartItem ci " +
                                    "JOIN FETCH ci.product " +  // ← IMPORTANT : Charger les produits
                                    "WHERE ci.cart.user.id = :userId", CartItem.class)
                    .setParameter("userId", userBean.getCurrentUser().getId())
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public BigDecimal getTotal() {
        return getCartItems().stream()
                .map(item -> item.getProduct().getPrix().multiply(BigDecimal.valueOf(item.getQuantite())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public int getItemCount() {
        if (userBean.getCurrentUser() == null) {
            return 0;
        }

        EntityManager em = JPAUtil.getEntityManager();
        try {
            Long count = em.createQuery(
                            "SELECT COUNT(ci) FROM CartItem ci " +
                                    "WHERE ci.cart.user.id = :userId", Long.class)
                    .setParameter("userId", userBean.getCurrentUser().getId())
                    .getSingleResult();
            return count.intValue();
        } finally {
            em.close();
        }
    }

    // Getters et Setters
    public Cart getCart() { return cart; }
    public void setCart(Cart cart) { this.cart = cart; }
}
