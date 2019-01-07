package cu.agg.machinegun.services

import cu.agg.machinegun.repository.EntityRepository
import org.bson.Document
import org.joda.time.DateTime
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.json.simple.parser.ParseException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

import javax.annotation.PostConstruct

@Service
class MachineGun {
    @Value('${collection}')
    private String collection
    @Value('${date_field}')
    private String dateField
    @Value('${year}')
    private String year
    @Value('${batch}')
    private int batch

    @Autowired
    EntityRepository entityRepository

    private static Logger logger = LoggerFactory.getLogger(MachineGun.class)

    @PostConstruct
    void shoot() {
        Document document
        String collection = getCollection()
        List<String> years = getYear()
        String documentString = getDocument()
        int batch = getBatch()
        DateTime date
        int intYear
        int bulk
        int percent
        for (String year : years) {
            intYear = year as int
            for (int i = 1; i<= 12; i++) {
                date = new DateTime().withYear(intYear).withMonthOfYear(i)
                bulk = 0
                percent = 10
                for(int j = 0; j <= batch; j++) {
                    document = Document.parse(documentString)
                    document.put(getDateField(), date.toDate())
                    entityRepository.insertOne(collection, document)
                    bulk++
                    if(bulk == batch/10 as int){
                        logger.info("==> " + percent + "% del insertados satisfactoriamente del mes "
                                + i + " del año " + year)
                        percent+=10
                        bulk = 0
                    }
                }
            }
        }
        logger.info("==> Inserción satisfactoria de " + getBatch()
                + " documentos en la colelection " + getCollection())
    }

    String getCollection() {
        return collection
    }

    static String getDocument() {
        JSONParser parser = new JSONParser()
        JSONObject jsonObject = new JSONObject()
        try {
            FileReader documentJSON = new FileReader("src/main/resources/document")
            jsonObject = (JSONObject) parser.parse(documentJSON)

        } catch (ParseException | IOException e) {
            e.printStackTrace()
        }

        return jsonObject.toJSONString()
    }

    int getBatch() {
        return batch
    }

    String getDateField() {
        return dateField
    }

    List<String> getYear() {
        return Arrays.asList(this.year.split(","))
    }
}
