//package br.com.devdojo.util;
//
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.JsonDeserializer;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.node.ArrayNode;
//import org.springframework.data.domain.Sort;
//
//import java.io.IOException;

/**
 * @author yvesguilherme on 09/07/2020.
 * @project spring-boot-essentials
 */

/**
 * ESSA CLASSE NÃO É MAIS NECESSÁRIA,
 * PORQUE O org.springframework.data.domain.Sort
 * NÃO IMPLEMENTA MAIS O ITERABLE E SIM STREAMABLE.
 */
//public class CustomSortDeserializer extends JsonDeserializer<Sort> {
//
//    @Override
//    public Sort deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
//        ArrayNode node = jsonParser.getCodec().readTree(jsonParser);
//        Sort.Order[] orders = new Sort.Order[node.size()];
//
//        int i = 0;
//        for (JsonNode json : node) {
//            orders[i] = new Sort.Order(Sort.Direction.valueOf(json.get("direction").asText()), json.get("property").asText());
//            i++;
//        }
//        return new Sort.by(orders);
//    }
//}
