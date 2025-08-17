package br.com.shiroshima.controller;

import br.com.shiroshima.exception.NotFoundException;
import br.com.shiroshima.model.Product;
import br.com.shiroshima.service.ProductService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ProductController {

    Scanner scanner = new Scanner(System.in);
    ProductService service = new ProductService();

    public void start() {
        while (true) {
            //Inserir produtos
            //Buscar por nome (parcial)
            //Listar todos os produtos ordenados por preço
            //Atualizar preço de um produto por um percentual informado pelo usuário
            //Atualizar o preço de todos os produtos por um percentual informado pelo usuário
            //Deletar por ID
            System.out.println("== GESTÃO DE PRODUTOS ==");
            System.out.println("1. Criar produto");
            System.out.println("2. Listar produtos por preço");
            System.out.println("3. Consultar produtos por id");
            System.out.println("4. Consultar produtos por nome (parcial)");
            System.out.println("5. Atualizar preço de um produto por percentual");
            System.out.println("6. Atualizar preço de TODOS os produtos por percentual");
            System.out.println("7. Deletar produto");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> createProduct();
                case 2 -> listAllByPrice();
                case 3 -> getById();
                case 4 -> searchByName();
                case 5 -> updateSingleByPercent();
                case 6 -> updateAllByPercent();
                case 7 -> delete();
                case 0 -> {
                    System.out.println("Saindo...");
                    return;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    public void createProduct() {
        while (true) {
            System.out.println("== CRIAR PRODUTO ==");

            System.out.print("- Nome: ");
            scanner.nextLine();
            String name = scanner.nextLine().trim();

            System.out.print("- Description: ");
            String description = scanner.nextLine().trim();

            System.out.print("- Preço: ");
            Double price = scanner.nextDouble();

            try {
                Product product = new Product(name, description, price);
                service.insert(product);

                System.out.println("Produto cadastrado: ");
                System.out.println(product);

                break;

            } catch (RuntimeException ex) {
                System.out.println("[!] Erro: " + ex.getMessage());
            }
        }
    }

    public void listAllByPrice() {
        try {
            List<Product> products = service.listOrderByPrice();
            System.out.println("== PRODUTOS (ORDENADOS POR PREÇO) ==");
            for (Product p : products) {
                System.out.println(p);
            }
        } catch (RuntimeException ex) {
            System.out.println("[!] Erro: " + ex.getMessage());
        }
    }

    public void getById() {
        while (true) {
            try {
                System.out.print("- ID: ");
                int id = scanner.nextInt();
                System.out.println(service.getById(id));
                break;
            } catch (InputMismatchException ex) {
                System.out.println("[!] Erro: ID inválido");
                scanner.nextLine();

            } catch (NotFoundException ex) {
                System.out.println("[!] Erro: " + ex.getMessage());
            }
        }
    }

    public void searchByName() {
        while (true) {
            try {
                System.out.print("- Nome (parcial): ");
                scanner.nextLine();
                String query = scanner.nextLine().trim();

                if (query.isBlank()) {
                    System.out.println("[!] Erro: Nome não pode estar vazio");
                    continue;
                }

                List<Product> products = service.searchPartial(query);
                System.out.println("== PRODUTOS ENCONTRADOS ==");
                for (Product p : products) {
                    System.out.println(p);
                }
                break;

            } catch (NotFoundException ex) {
                System.out.println("[!] Erro: " + ex.getMessage());
                break;
            } catch (RuntimeException ex) {
                System.out.println("[!] Erro: " + ex.getMessage());
            }
        }
    }

    private void updateSingleByPercent() {
        while (true) {
            try {
                System.out.print("- ID do produto: ");
                int id = scanner.nextInt();

                System.out.print("- Percentual de aumento: ");
                int percent = scanner.nextInt();

                Product updated = service.updatePriceByPercent(id, percent);
                System.out.println("Preço atualizado: " + updated);
                break;

            } catch (InputMismatchException ex) {
                System.out.println("[!] Erro: Entrada inválida");
                scanner.nextLine();

            } catch (RuntimeException ex) {
                System.out.println("[!] Erro: " + ex.getMessage());
            }
        }
    }

    private void updateAllByPercent() {
        while (true) {
            try {
                System.out.print("- Percentual de aumento para todos os produtos: ");
                int percent = scanner.nextInt();

                List<Product> updated = service.updateAllPricesByPercent(percent);

                System.out.println("== PRODUTOS COM PREÇOS ATUALIZADOS ==");

                for (Product p : updated) {
                    System.out.println(p);
                }

                break;

            } catch (InputMismatchException ex) {
                System.out.println("[!] Erro: Entrada inválida");
                scanner.nextLine();
            } catch (RuntimeException ex) {
                System.out.println("[!] Erro: " + ex.getMessage());
            }
        }
    }

    public void delete() {
        while (true) {
            try {
                System.out.print("- ID do produto a deletar: ");
                int id = scanner.nextInt();

                Product product = service.getById(id);
                System.out.println("Produto a ser deletado: " + product);

                System.out.print("Confirma a exclusão? (s/n): ");
                scanner.nextLine();
                String confirmation = scanner.nextLine().trim().toLowerCase();

                if (confirmation.equals("s") || confirmation.equals("sim")) {
                    service.deleteById(id);
                    System.out.println("Produto deletado com sucesso!");
                } else {
                    System.out.println("Operação cancelada.");
                }
                break;

            } catch (InputMismatchException ex) {
                System.out.println("[!] Erro: ID inválido");
                scanner.nextLine();

            } catch (RuntimeException ex) {
                System.out.println("[!] Erro: " + ex.getMessage());
            }
        }
    }
}