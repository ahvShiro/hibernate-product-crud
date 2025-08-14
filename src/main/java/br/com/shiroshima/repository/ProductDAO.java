package br.com.shiroshima.repository;

import br.com.shiroshima.exception.NotFoundException;
import br.com.shiroshima.model.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;

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

    public List<Product> getAll() {
        EntityManager em = emf.createEntityManager();

        List<Product> products = em.createQuery("Select product from Product product", Product.class).getResultList();

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
