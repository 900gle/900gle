package com.bbongdoo.doo.component;

import com.bbongdoo.doo.dto.TextEmbeddingDTO;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

public class TextEmbedding {

    public static Vector<Double> getVector(TextEmbeddingDTO textEmbeddingDTO) throws IOException {

        String result = SendRestUtil.sendRest(textEmbeddingDTO.getTensorApiUrl(), "{\"keyword\" : \"" + textEmbeddingDTO.getKeyword() + "\"}");
        try {

            JSONParser parser = new JSONParser();
            Object obj = parser.parse(result);
            JSONObject jsonObj = (JSONObject) obj;

            String step1 = jsonObj.get("vectors").toString().replace("[", "");
            String step2 = step1.replace("]", "");

            Vector<Double> vector = new Vector<>();
            Arrays.stream(step2.split(",")).forEach(x -> {
                vector.add(Double.valueOf(x));
            });

            return vector;

        } catch (ParseException p) {
            p.getStackTrace();
        }

        return null;
    }

}
