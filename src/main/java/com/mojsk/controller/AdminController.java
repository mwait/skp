package com.mojsk.controller;

import com.mojsk.dao.ProductDao;
import com.mojsk.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by mc on 2016-02-25.
 */
@Controller
public class AdminController {

    private Path path;

    @Autowired
    private ProductDao productDao;

    @RequestMapping("/admin")
    public String adminPage() {
        return "admin";
    }

    @RequestMapping("/admin/productInventory")
    public String productInventory(Model model) {
        List<Product> products = productDao.getAllProduct();
        model.addAttribute("products", products);
        return "productInventory";
    }

    @RequestMapping("/admin/productInventory/addProduct")
    public String addProduct(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "addProduct";
    }

    @RequestMapping(value="/admin/productInventory/addProduct", method = RequestMethod.POST)
    public String addProductPost(@Valid @ModelAttribute("product") Product product, BindingResult result, HttpServletRequest request) {
        if(result.hasErrors()){
            return "addProduct";
        }
        productDao.addProduct(product);

        MultipartFile productImage = product.getProductImage();
        String rootDirectory = request.getSession().getServletContext().getRealPath("/");
        path= Paths.get(rootDirectory+"\\resources\\images\\"+product.getProductId()+".png");
        if(productImage!=null && !productImage.isEmpty()){
            try {
                productImage.transferTo(new File(path.toString()));
            }
            catch (Exception e){
                throw new RuntimeException("Product image saving failed");
            }
        }

        return "redirect:/admin/productInventory";
    }


    @RequestMapping("/admin/productInventory/deleteProduct/{id}")
    public String deleteProduct(@PathVariable String id, HttpServletRequest request){
        String rootDirectory = request.getSession().getServletContext().getRealPath("/");
        path= Paths.get(rootDirectory+"\\resources\\images\\"+id+".png");
        productDao.deleteProduct(id);
        if(Files.exists(path)){
            try {
                Files.delete(path);
            }
            catch (IOException e){
            }
        }
        return "redirect:/admin/productInventory";
    }

    @RequestMapping("/admin/productInventory/editProduct/{id}")
    public String editProduct(@PathVariable("id") String id, Model model){
        Product product = productDao.getProductById(id);

        model.addAttribute(product);
        return "editProduct";
    }

    @RequestMapping(value="/admin/productInventory/editProduct", method= RequestMethod.POST)
    public String editProduct(@Valid @ModelAttribute("product") Product product, BindingResult result, Model model, HttpServletRequest request){
        if(result.hasErrors()){
            return "editProduct";
        }

        MultipartFile productImage = product.getProductImage();
        String rootDirectory = request.getSession().getServletContext().getRealPath("/");
        path = Paths.get(rootDirectory+"\\WEB-INF\\resources\\images\\"+product.getProductId()+".png");

        if(productImage!=null && !productImage.isEmpty()){
            try {
                productImage.transferTo(new File(path.toString()));
            } catch (Exception e){
                throw new RuntimeException("Product image saving failed",e);
            }
        }
        productDao.editProduct(product);
        return "redirect:/admin/productInventory";
    }
}
