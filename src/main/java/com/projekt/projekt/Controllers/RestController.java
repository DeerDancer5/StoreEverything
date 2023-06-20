package com.projekt.projekt.Controllers;

import com.projekt.projekt.Notes.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/category")
public class RestController {
    @GetMapping("/categoryList")
    public List<Category> getsCategory(){
        List<Category> CatList = new ArrayList<>();
        CatList.add(new Category("sport"));
        CatList.add(new Category("lotnictwo"));
        CatList.add(new Category("muzyka"));
        CatList.add(new Category("plywanie"));
        CatList.add(new Category("motoryzacja"));
        CatList.add(new Category("spotkanie"));
        CatList.add(new Category("linux"));
        CatList.add(new Category("komputery"));
        CatList.add(new Category("technologie"));
        CatList.add(new Category("podrozowanie"));
        CatList.add(new Category("przypomnienie"));
        CatList.add(new Category("uczelnia"));
        CatList.add(new Category("dom"));
        CatList.add(new Category("gry"));
        CatList.add(new Category("jedzenie"));
        CatList.add(new Category("egzamin"));
        CatList.add(new Category("programowanie"));
        CatList.add(new Category("filmy"));
        CatList.add(new Category("ksiazki"));
        CatList.add(new Category("sztuka"));

        return CatList;
    }

}
