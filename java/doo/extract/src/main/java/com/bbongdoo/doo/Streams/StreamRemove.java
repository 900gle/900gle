package com.bbongdoo.doo.Streams;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StreamRemove {

    public static void main(String[] args) {

        List<Goods> goods = new ArrayList<>();
        goods.add(new Goods("나이키", "신발"));
        goods.add(new Goods("아디다스", "신발"));
        goods.add(new Goods("리복", "가방"));

        goods.stream().filter(x -> x.getType().equals("가방")).collect(Collectors.toList()).forEach(x ->
        {
            goods.remove(x);
        });

        goods.stream().forEach(
                x -> {
                    System.out.println(x.getName());
                }
        );
    }
}
