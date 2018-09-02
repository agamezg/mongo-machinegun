package ar.cu.agg.machinegun.service;

import ar.cu.agg.machinegun.repository.EntityRepository;
import org.bson.Document;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class MachineGun {

    @Value("${collection}")
    private String collection;
    @Value("${fechaIngreso}")
    private String dateField;
    @Value("${document}")
    private String document;
    @Value("${batch}")
    private int batch;

    @Autowired
    EntityRepository entityRepository;

    private static Logger logger = LoggerFactory.getLogger(EntityRepository.class);

    public void shoot(){
        Document document = getDocument();
        Date date = new Date();
        document.put(getDateField(), date);
        for (int i = 0; i < getBatch(); i++) {
            entityRepository.insertOne();
        }
    }

    public String getCollection() {
        return collection;
    }

    public Document getDocument(){
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = (JSONObject) parser.parse(this.document);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return Document.parse(jsonObject.toJSONString());
    }

    public int getBatch() {
        return batch;
    }

    public String getDateField() {
        return dateField;
    }
}
