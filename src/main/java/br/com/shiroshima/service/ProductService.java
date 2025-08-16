package br.com.shiroshima.service;

import br.com.shiroshima.exception.*;
import br.com.shiroshima.model.Product;
import br.com.shiroshima.repository.ProductDAO;

import java.util.List;

public class ProductService {

    private final ProductDAO dao = new ProductDAO();

    public void insert(Product product) {

        // null validation
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        // name validation
        if (product.getName() == null || product.getName().isBlank()) {
            throw new AttributeNotBlankException("Name cannot be blank");
        }
        if (product.getName().length() < 2 || product.getName().length() > 50) {
            throw new StringLenghtException("Name must have 2 - 50 letters");
        }
        if (dao.getByName(product.getName()) != null) {
            throw new DuplicateProductException("Product already exists with name: " + product.getName());
        }

        // desc validation
        if (product.getDescription().length() < 5 || product.getDescription().length() > 75) {
            throw new StringLenghtException("Description must have 5 - 75 letters");
        }

        // price validation
        if (product.getPrice() == null || product.getPrice() == 0.0) {
            throw new AttributeNotBlankException("Price cannot be blank");
        }
        if (product.getPrice() < 0.0) {
            throw new PriceNotNegativeException("Price cannot be negative");
        }

        dao.save(product);
    }

    public Product getById(int id) {
        return dao.getById(id);
    }

    public List<Product> searchPartial(String query) {
        return dao.getAllByPartialName(query.toLowerCase());
    }

    public List<Product> listOrderByPrice() {
        return dao.getAllOrderByPrice();
    }

    public Product updatePriceByPercent(int id, int percent) {
        if (percent < 0) {
            throw new PriceNotNegativeException("Percentage cannot be negative");
        }

        Product p = dao.getById(id);

        double newPrice = p.getPrice() + (p.getPrice() * percent / 100.0);

        if (newPrice < 0) {
            throw new PriceNotNegativeException("Price cannot be negative");
        }

        p.setPrice(newPrice);
        return dao.update(p);
    }

    public List<Product> updateAllPricesByPercent(int perchance) {
        if (perchance < 0) {
            throw new PriceNotNegativeException("Percentage cannot be negative");
        }

        List<Product> products = dao.getAll();

        for (Product p : products) {
            double newPrice = p.getPrice() + (p.getPrice() * perchance / 100.0);

            if (newPrice < 0) {
                throw new PriceNotNegativeException("Price cannot be negative");
            }

            p.setPrice(newPrice);
            dao.update(p);
        }

        return dao.getAll();
    }

    public void deleteById(int id) {
        dao.deleteById(id);
    }

}
