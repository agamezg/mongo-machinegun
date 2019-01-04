package ar.cu.agg.machinegun;

import ar.cu.agg.machinegun.service.MachineGun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MachineGunApplication {
    private final
    MachineGun machineGun;

    @Autowired
    public MachineGunApplication(MachineGun machineGun) {
        this.machineGun = machineGun;
    }

    public static void main(String[] args) {
		SpringApplication.run(MachineGunApplication.class, args);
	}
}
