package br.com.shiroshima.repository;

import br.com.shiroshima.exception.NotFoundException;
import br.com.shiroshima.model.Product;

import javax.persistence.*;
import java.util.List;

public class ProductDAO {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencePU");

    public void save(Product product) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        em.persist(product);

        em.getTransaction().commit();
    }

    public Product getById(Integer id) {
        EntityManager em = emf.createEntityManager();

        Product product = em.find(Product.class, id);

        if (product == null) {
            throw new NotFoundException("Not found with ID: " + id);
        }

        em.close();
        return product;
    }

    public Product getByName(String name) {
        EntityManager em = emf.createEntityManager();

        String jpql = "SELECT p FROM Product p WHERE p.name = :name";
        TypedQuery<Product> query = em.createQuery(jpql, Product.class);
        query.setParameter("name", name);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<Product> getAllByPartialName(String search) {
        EntityManager em = emf.createEntityManager();

        String jpql = "SELECT p FROM Product p WHERE p.name LIKE :pattern";
        TypedQuery<Product> query = em.createQuery(jpql, Product.class);
        query.setParameter("pattern", "%" + search + "%");

        List<Product> products = query.getResultList();

        if (products.isEmpty()) {
            throw new NotFoundException("Not found!");
        }

        return products;
    }

    public List<Product> getAll() {
        EntityManager em = emf.createEntityManager();

        List<Product> products = em.createQuery("Select product from Product product", Product.class).getResultList();

        if (products.isEmpty()) {
            throw new NotFoundException("Not found!");
        }

        return products;
    }

    public List<Product> getAllOrderByPrice() {
        EntityManager em = emf.createEntityManager();

        List<Product> products = em.createQuery("Select product from Product product order by product.price", Product.class).getResultList();

        if (products.isEmpty()) {
            throw new NotFoundException("Not found!");
        }

        return products;
    }

    public Product update(Product product) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        em.merge(product);

        em.getTransaction().commit();

        return product;
    }


    public void deleteById(Integer id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Product foundProduct = em.find(Product.class, id);

        if (foundProduct == null) {
            throw new NotFoundException("Not found with ID: " + id);
        }

        em.remove(foundProduct);

        em.getTransaction().commit();
    }

}
