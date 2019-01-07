package cu.agg.machinegun

import cu.agg.machinegun.services.MachineGun
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.test.context.junit4.SpringRunner
import static org.junit.Assert.assertTrue

@RunWith(SpringRunner)
@SpringBootTest
@PropertySource("classpath:mongo.properties")
class MachinegunApplicationTests {

	@Autowired
	Environment environment

	@Autowired
	MachineGun machineGun
	@Autowired
	MongoTemplate mongoTemplate

	@Test
	void contextLoads() {
	}

	@Test
	void loadConfig(){
		String collection = machineGun.getCollection()
		String dateField = machineGun.getDateField()
		int batch = machineGun.getBatch()
		assertTrue(collection.equalsIgnoreCase("MensajesCleaner"))
		assertTrue(dateField.equalsIgnoreCase("fechaIngreso"))
		assertTrue(batch == 100)

	}

	@Test
	void insertOneDocument(){

	}



}
