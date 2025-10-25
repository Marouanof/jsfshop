# 🛒 Projet E-Commerce JSF

## 🎯 Description du Projet
Ce projet est une application **e-commerce complète** développée avec **Java Server Faces (JSF)** et **PrimeFaces**.  
Il permet aux utilisateurs de parcourir des produits, gérer un panier d'achat, passer des commandes, et inclut un module d'administration pour gérer le catalogue.

---

## 🛠 Technologies Utilisées
**Backend :** Java EE, JSF 2.3  
**Frontend :** PrimeFaces, HTML, CSS  
**Base de données :** JPA / Hibernate  
**Serveur d'application :** Compatible Jakarta EE 9+  
**Gestion de dépendances :** Maven

---

## ✨ Fonctionnalités

### 👤 Module Utilisateur
- ✅ Inscription et connexion
- ✅ Navigation des produits avec recherche
- ✅ Gestion du panier d'achat
- ✅ Passer des commandes
- ✅ Historique des commandes

### ⚙️ Module Administration
- ✅ Ajout de produits
- ✅ Gestion des catégories
- ✅ Interface d'administration sécurisée

---

## 🚀 Installation et Démarrage

### 🔧 Prérequis
- JDK **11+**
- Serveur d'application **Jakarta EE 9+** (Payara, TomEE, WildFly…)
- **Maven 3.6+**

### 🧩 Étapes d'installation

1. **Cloner le projet**
   ```bash
   git clone [url-du-projet]
   cd jsfshop
2. **Configurer la base de données**

   **Modifier le fichier : src/main/resources/META-INF/persistence.xml
   et adapter le datasource selon votre environnement.

3. **Compiler le projet**
   ```bash
   mvn clean package
## 📁 Structure des Fichiers

### 🎨 Pages JSF Principales

| Fichier | Description |
|----------|--------------|
| `index.xhtml` | Page d'accueil |
| `products.xhtml` | Catalogue produits |
| `product-detail.xhtml` | Détail produit |
| `cart.xhtml` | Panier d'achat |
| `login.xhtml` | Connexion |
| `register.xhtml` | Inscription |
| `orders.xhtml` | Commandes utilisateur |
| `admin.xhtml` | Interface administrateur |
| `template.xhtml` | Template commun |

---

### ⚙️ Beans Managed

| Bean | Rôle |
|------|------|
| `UserBean` | Gestion des utilisateurs |
| `ProductBean` | Gestion des produits |
| `CartBean` | Gestion du panier |
| `OrderBean` | Gestion des commandes |
| `AdminBean` | Gestion administrative du catalogue |
