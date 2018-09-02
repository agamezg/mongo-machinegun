package ar.cu.agg.machinegun;

import ar.cu.agg.machinegun.service.MachineGun;
import jdk.nashorn.internal.parser.JSONParser;
import org.bson.Document;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.print.Doc;
import javax.xml.crypto.Data;
import java.util.Date;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MachineGunApplicationTests {

	@Autowired
	MachineGun machineGun;

	@Test
	public void contextLoads() {
	}

	@Test
	public void loadConfig(){
		String collection = machineGun.getCollection();
		String dateField = machineGun.getDateField();
		int batch = machineGun.getBatch();

		assertTrue(collection.equalsIgnoreCase("MensajesCleaner"));
		assertTrue(dateField.equalsIgnoreCase("fechaIngreso"));
		assertTrue(batch == 2000000);

	}

}
