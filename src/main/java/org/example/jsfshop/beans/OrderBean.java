package org.example.jsfshop.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import org.example.jsfshop.models.*;
import org.example.jsfshop.util.JPAUtil;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class OrderBean implements Serializable {

    @Inject
    private UserBean userBean;

    @Inject
    private CartBean cartBean;

    public String createOrder() {
        if (userBean.getCurrentUser() == null || cartBean.getCartItems().isEmpty()) {
            return "cart?faces-redirect=true";
        }

        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            User user = em.find(User.class, userBean.getCurrentUser().getId());
            Order order = new Order(user);

            for (CartItem cartItem : cartBean.getCartItems()) {
                OrderItem orderItem = new OrderItem(
                        order,
                        cartItem.getProduct(),
                        cartItem.getQuantite(),
                        cartItem.getProduct().getPrix()
                );
                order.getItems().add(orderItem);
            }

            em.persist(order);

            // Vider le panier
            user.getCart().getItems().clear();

            em.getTransaction().commit();
            return "order-confirmation?faces-redirect=true&orderId=" + order.getId();
        } catch (Exception e) {
            em.getTransaction().rollback();
            return "cart?faces-redirect=true&error=true";
        } finally {
            em.close();
        }
    }

    public List<Order> getUserOrders() {
        if (userBean.getCurrentUser() == null) {
            return List.of();
        }

        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT o FROM Order o WHERE o.user.id = :userId ORDER BY o.date DESC", Order.class)
                    .setParameter("userId", userBean.getCurrentUser().getId())
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Order getOrderById(Long orderId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Order.class, orderId);
        } finally {
            em.close();
        }
    }
}
