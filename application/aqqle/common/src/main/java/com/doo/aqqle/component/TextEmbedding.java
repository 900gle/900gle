package com.doo.aqqle.component;


import com.doo.aqqle.dto.TextEmbeddingDTO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Vector;

public class TextEmbedding {
    public static Vector<Double> getVector(TextEmbeddingDTO textEmbeddingDTO) throws IOException {

        String result = SendRestUtil.sendRest(textEmbeddingDTO.getTensorApiUrl(), "{\"keyword\" : \"" + textEmbeddingDTO.getKeyword() + "\"}");

        try {

            JSONParser parser = new JSONParser();
            JSONObject jsonObj = (JSONObject) parser.parse(result);
            JSONArray jsonArray = (JSONArray) parser.parse(jsonObj.get("vectors").toString());

            Vector<Double> vector = new Vector<>();
            jsonArray.stream().forEach(x -> {
                vector.add((Double) x);
            });
            return vector;
        } catch (ParseException p) {
            p.getStackTrace();
        }

        return null;
    }

}
