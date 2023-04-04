/**
 * 
 */
package io.avalon.bom.database;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.avalon.bom.components.Finish;
import io.avalon.bom.database.repository.FinishRepository;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
@Log4j2
@Data
@Component
public class DatabaseAccessSetup {
	@Autowired
	FinishRepository finishItemRepo;
	@Autowired
	CustomItemRepository customRepo;

	public void setup() {
		log.debug("Data creation started...");
		finishItemRepo.save(new Finish(Integer.valueOf(1), "Clear Anodized"));
		finishItemRepo.save(new Finish(2, "Black Anodized"));
		finishItemRepo.save(new Finish(3, "A2"));
		log.debug("Data creation Finished...");
	}
}
