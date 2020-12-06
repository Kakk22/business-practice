package com.cyf.Controller;

import com.cyf.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author by cyf
 * @date 2020/12/6.
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    @Resource
    private ProductService productService;


    @GetMapping("/decrease")
    public String decrease(int id, Integer count) {
        return productService.decrease(id, count);
    }
}
