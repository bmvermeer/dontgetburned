package nl.brianvermeer.workshop.coffee.util;

import nl.brianvermeer.workshop.coffee.domain.Product;
import nl.brianvermeer.workshop.coffee.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.sun.row



import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Locale;

@Component
public class Util {


    @Autowired
    EntityManager em;

    @Autowired
    DataSource dataSource;

    @Autowired
    private PersonService personService;

    public List<Product> searchProduct(String input) {
        var lowerInput = input.toLowerCase(Locale.ROOT);
        String query = "Select * from Product where lower(description) like '%" + lowerInput + "%' OR lower(product_name) like '%" + lowerInput + "%'";
        var resultList = (List<Product>) em.createNativeQuery(query, Product.class).getResultList();
        return resultList;
    }

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";
    public String uploadImage(Model model, @RequestParam("image") MultipartFile file, Principal principal) throws IOException {
        var name = file.getOriginalFilename().replace(" ", "_");
        var fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, name);
        Files.write(fileNameAndPath, file.getBytes());
        model.addAttribute("msg", "Uploaded images: " + name);

        if (principal == null) {
            model.addAttribute("message", "ERROR");
            return "person/upload";
        }

        var user = principal.getName();
        var person = personService.findByUsername(user);

        person.setProfilePic(name);
        personService.savePerson(person);
        return "person/upload";
    }


}
