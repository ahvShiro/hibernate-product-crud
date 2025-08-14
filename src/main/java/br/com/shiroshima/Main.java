package br.com.shiroshima;

import br.com.shiroshima.model.Product;
import br.com.shiroshima.repository.ProductDAO;

public class Main {
    public static void main(String[] args) {
        Product abacaxi = new Product("abacaxi", "Fruta espilhos", 10.00);
        Product banana = new Product("banana", "Fruta amarela", 2.00);
        Product goiaba = new Product("goiaba", "Lá nas quebrada onde eu moro, mano tem um ponto de ônibus e um vendedor de goiaba, né e aí um dia eu tinha um", 1.50);


        ProductDAO dao = new ProductDAO();

        System.out.println("============ SAVE ============");

        dao.save(abacaxi);
        dao.save(banana);
        dao.save(goiaba);

        System.out.println("============ GET ALL ============");

        System.out.println(dao.getAll());

        System.out.println("============ GET BY ID ============");

        System.out.println(dao.getById(3));

        System.out.println("============ UPDATE ============");

        goiaba.setDescription("mano cê pegou minha goiaba");
        System.out.println(dao.update(goiaba));

        System.out.println("============ DELETE BY ID ============");

        System.out.println(dao.getById(3));
        dao.deleteById(3);
        System.out.println(dao.getById(3));

    }
}