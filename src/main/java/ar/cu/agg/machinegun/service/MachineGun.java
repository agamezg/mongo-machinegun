package ar.cu.agg.machinegun.service;

import ar.cu.agg.machinegun.repository.EntityRepository;
import org.bson.Document;
import org.joda.time.DateTime;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.Arrays;
import java.util.List;

@Service
public class MachineGun {

    @Value("${collection}")
    private String collection;
    @Value("${date_field}")
    private String dateField;
    @Value("${year}")
    private String year;
    @Value("${batch}")
    private int batch;

    @Autowired
    EntityRepository entityRepository;

    private static Logger logger = LoggerFactory.getLogger(EntityRepository.class);

    @PostConstruct
    void shoot() {
        Document document;
        String collection = getCollection();
        List<String> years = getYear();
        String documentString = getDocument();
        int batch = getBatch();
        DateTime date;
        int intYear;
        int progress;
        for (String year : years) {
            intYear = Integer.parseInt(year);
            for (int i = 1; i<= 12; i++) {
                date = new DateTime().withYear(intYear).withMonthOfYear(i);
                progress = 0;
                for(int j = 0; j <= batch; j++) {
                    document = Document.parse(documentString);
                    document.put(getDateField(), date.toDate());
                    entityRepository.insertOne(collection, document);
                    progress ++;
                    if(progress == batch /10){
                        logger.info("==> Se han insertado satisfactoriamente "
                                + j + " documentos correspondientes al mes " + i + " del año " + year);
                        progress = 0;
                    }
                }
            }
        }
        logger.info("==> Inserción satisfactoria de " + getBatch()
                + " documentos en la colelection " + getCollection());
    }

    public String getCollection() {
        return collection;
    }

    public String getDocument() {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = new JSONObject();
        try {
            FileReader documentJSON = new FileReader("src/main/resources/document");
            jsonObject = (JSONObject) parser.parse(documentJSON);

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        return jsonObject.toJSONString();
    }

    public int getBatch() {
        return batch;
    }

    public String getDateField() {
        return dateField;
    }

    public List<String> getYear() {
        return Arrays.asList(this.year.split(","));
    }
}
