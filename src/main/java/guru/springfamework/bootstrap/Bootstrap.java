package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {
    private CategoryRepository categoryRepository;
    private CustomerRepository customerRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        /********************************
         ********* CATEGORIES **********
         *******************************/
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        // Save categories to repository
        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        System.out.println("Loaded: " + categoryRepository.count() + " categories.");

        /********************************
         ********** CUSTOMERS ***********
         *******************************/
        Customer client1 = new Customer();
        client1.setFirstName("Cli1");

        Customer client2 = new Customer();
        client2.setFirstName("Cli2");

        Customer client3 = new Customer();
        client3.setFirstName("Cli3");

        Customer client4 = new Customer();
        client4.setFirstName("Cli4");

        customerRepository.save(client1);
        customerRepository.save(client2);
        customerRepository.save(client3);
        customerRepository.save(client4);

        System.out.println("Loaded: " + customerRepository.count() + " customers.");
    }
}
