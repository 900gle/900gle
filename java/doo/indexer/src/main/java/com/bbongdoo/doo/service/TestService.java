package com.bbongdoo.doo.service;


import com.bbongdoo.doo.domain.Products;
import com.bbongdoo.doo.domain.ProductsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestService {

    private final ProductsRepository productsRepository;



    public void staticIndex() {

        JSONObject obj = new JSONObject();

        List<Products> productsList = productsRepository.findAll();
        try {

            FileWriter file = new FileWriter("./products.json");

            productsList.forEach(x -> {
                obj.put("id", x.getId());
                obj.put("name", x.getName());
                obj.put("price", x.getPrice());
                obj.put("category", x.getCategory());
                try {
                    file.write(obj.toJSONString() +"\r\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });

            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print(obj);
    }
}
