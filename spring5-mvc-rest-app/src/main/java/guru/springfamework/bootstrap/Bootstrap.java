package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {
    private CategoryRepository categoryRepository;
    private CustomerRepository customerRepository;
    private VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadMyCategories();
        loadMyCustomers();
        loadMyVendors();
    }

    private void loadMyVendors() {
        Vendor vendor1 = new Vendor();
        //vendor1.setId(1L);
        vendor1.setName("Amazon");

        Vendor vendor2 = new Vendor();
        //vendor2.setId(2L);
        vendor2.setName("ebay");

        vendorRepository.save(vendor1);
        vendorRepository.save(vendor2);

        System.out.println("Loaded: " + vendorRepository.count() + " vendors.");
    }

    private void loadMyCustomers() {
        Customer client1 = new Customer();
        client1.setId(1L);
        client1.setFirstName("Pol");
        client1.setLastName("Le Grand");

        Customer client2 = new Customer();
        client2.setId(2L);
        client2.setFirstName("Yves");
        client2.setLastName("Magnot");

        customerRepository.save(client1);
        customerRepository.save(client2);

        System.out.println("Loaded: " + customerRepository.count() + " customers.");
    }

    private void loadMyCategories() {
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
    }
}
