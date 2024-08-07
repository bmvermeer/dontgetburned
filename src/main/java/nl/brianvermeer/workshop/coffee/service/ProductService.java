package nl.brianvermeer.workshop.coffee.service;

import nl.brianvermeer.workshop.coffee.domain.Product;
import nl.brianvermeer.workshop.coffee.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void delete(Long productId) {
        productRepository.deleteById(productId);
    }

    public Product getProduct(Long productId) {
        return productRepository.findById(productId).get();
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductByName(String name) { return productRepository.findProductByProductName(name); }

}
