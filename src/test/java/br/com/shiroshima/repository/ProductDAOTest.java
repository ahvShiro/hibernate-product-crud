package br.com.shiroshima.repository;

import br.com.shiroshima.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class ProductDAOTest {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("testPersistencePU");

    Product productTest = new Product("Test Product", "Test Description", 10.0);
    Product anotherProductTest = new Product("Test Product", "Test Description", 10.0);

    @Test
    @DisplayName("Save with valid product should return valid product")
    public void save_withValidProduct_shouldReturnProduct() {
        Product product = productTest;

        ProductDAO dao = new ProductDAO();
        dao.save(product);

        Assertions.assertNotNull(product.getId());

        Product found = dao.getById(product.getId());

        Assertions.assertEquals("Test Product", found.getName());
        Assertions.assertEquals("Test Description", found.getDescription());
        Assertions.assertEquals(10.0, found.getPrice());
    }

    @Test
    @DisplayName("Get by id with existing id should return product")
    public void getById_withExistingId_shouldReturnProduct() {
        Product product = productTest;

        ProductDAO dao = new ProductDAO();
        dao.save(product);
        Product found = dao.getById(1);

        Assertions.assertEquals(1, found.getId());
        Assertions.assertEquals("Test Product", found.getName());
        Assertions.assertEquals("Test Description", found.getDescription());
        Assertions.assertEquals(10.0, found.getPrice());
    }

    @Test
    @DisplayName("Get by id with invalid id should throw NotFoundException")
    public void getById_withInvalidId_shouldThrowNotFoundException() {
        ProductDAO dao = new ProductDAO();
        Assertions.assertThrows(br.com.shiroshima.exception.NotFoundException.class, () -> dao.getById(999999));
    }

    @Test
    @DisplayName("Get all with existing products should return product list")
    public void getAll_withExistingProducts_shouldReturnProductList() {
        Product product = productTest;
        Product anotherProduct = anotherProductTest;

        ProductDAO dao = new ProductDAO();
        dao.save(product);
        dao.save(anotherProduct);

        List<Product> products = dao.getAll();
        Assertions.assertEquals(product.getName(), products.getFirst().getName());
        Assertions.assertEquals(product.getDescription(), products.getFirst().getDescription());
        Assertions.assertEquals(product.getPrice(), products.getFirst().getPrice());

        Assertions.assertEquals(anotherProduct.getName(), products.getLast().getName());
        Assertions.assertEquals(anotherProduct.getDescription(), products.getLast().getDescription());
        Assertions.assertEquals(anotherProduct.getPrice(), products.getLast().getPrice());
    }


    @Test
    @DisplayName("Get all with non existing products should throw NotFoundException")
    public void getAll_withNonExistingProducts_shouldThrowNotFoundException() {
        ProductDAO dao = new ProductDAO();
        Assertions.assertThrows(br.com.shiroshima.exception.NotFoundException.class, () -> dao.getById(999999));

    }

    @Test
    @DisplayName("Update with valid id and product should return Product")
    public void update_withValidIdAndValidProduct_shouldReturnProduct() {
        ProductDAO dao = new ProductDAO();
        Product product = productTest;
        dao.save(product);

        product.setName("Updated");
        product.setDescription("Updated Desc");
        product.setPrice(15.0);

        Product updated = dao.update(product);

        Assertions.assertEquals("Updated", updated.getName());
        Assertions.assertEquals("Updated Desc", updated.getDescription());
        Assertions.assertEquals(15.0, updated.getPrice());
    }


    @Test
    @DisplayName("Update with INVALID id and valid product should throw NotFoundException")
    public void update_withInvalidIdAndValidProduct_shouldThrowNotFoundException() {
        ProductDAO dao = new ProductDAO();
        Product product = new Product("Valid", "Valid Desc", 5.0);
        product.setId(999999);

        Assertions.assertThrows(br.com.shiroshima.exception.NotFoundException.class, () -> {
            Product found = dao.getById(product.getId());
            dao.update(found);
        });
    }

    @Test
    @DisplayName("Delete by id with valid id should return void")
    public void deleteById_withValidId_shouldReturnVoid() {
        ProductDAO dao = new ProductDAO();
        Product product = new Product("ToDelete", "Desc", 1.0);
        dao.save(product);

        Integer id = product.getId();
        dao.deleteById(id);

        Assertions.assertThrows(br.com.shiroshima.exception.NotFoundException.class, () -> dao.getById(id));
    }

    @Test
    @DisplayName("Delete by id with invalid id should throw NotFoundException")
    public void deleteById_withInvalidId_shouldThrowNotFoundException() {
        ProductDAO dao = new ProductDAO();
        Assertions.assertThrows(br.com.shiroshima.exception.NotFoundException.class, () -> dao.deleteById(999999));
    }
}