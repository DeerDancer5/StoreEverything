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
    public List<String> getsCategory(){
        List<String> CatList = new ArrayList<>();
        CatList.add(new String("sport"));
        CatList.add(new String("lotnictwo"));
        CatList.add(new String("muzyka"));
        CatList.add(new String("plywanie"));
        CatList.add(new String("motoryzacja"));
        CatList.add(new String("spotkanie"));
        CatList.add(new String("linux"));
        CatList.add(new String("komputery"));
        CatList.add(new String("technologie"));
        CatList.add(new String("podrozowanie"));
        CatList.add(new String("przypomnienie"));
        CatList.add(new String("uczelnia"));
        CatList.add(new String("dom"));
        CatList.add(new String("gry"));
        CatList.add(new String("jedzenie"));
        CatList.add(new String("egzamin"));
        CatList.add(new String("programowanie"));
        CatList.add(new String("filmy"));
        CatList.add(new String("ksiazki"));
        CatList.add(new String("sztuka"));

        return CatList;
    }

}
