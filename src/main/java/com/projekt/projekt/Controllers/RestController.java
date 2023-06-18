package com.projekt.projekt.Controllers;

import com.projekt.projekt.Notes.Category;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @GetMapping("/category")
    public List<Category> getsCategory(){
        throw new IllegalArgumentException("Not implemented yet!");
    }

}
