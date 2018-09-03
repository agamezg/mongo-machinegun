package cu.agg.machinegun

import cu.agg.machinegun.services.MachineGun
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import static org.junit.Assert.assertTrue

@RunWith(SpringRunner)
@SpringBootTest
class MachinegunApplicationTests {

	@Autowired
	MachineGun machineGun

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
		assertTrue(batch == 2000000)

	}

}
