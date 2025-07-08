package nl.brianvermeer.workshop.coffee.controller;

import nl.brianvermeer.workshop.coffee.repository.ProductRepository;
import nl.brianvermeer.workshop.coffee.repository.SearchRepository;
import nl.brianvermeer.workshop.coffee.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private ProductService productService;
    private SearchRepository searchRepository;
    private final ProductRepository productRepository;

    public HomeController(ProductService productService, SearchRepository searchRepository, ProductRepository productRepository) {
        this.productService = productService;
        this.searchRepository = searchRepository;
        this.productRepository = productRepository;
    }

    @GetMapping({"/", "/index", "/home"})
    public String homePage(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "index";
    }

    @PostMapping("/")
    public String searchProducts(Model model, @RequestParam String input) {
        model.addAttribute("products", searchRepository.searchProduct(input));
//        model.addAttribute("products", productRepository.findProductByProductNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(input, input));
        return "index";
    }
}
