package io.avalon.bom.database;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import io.avalon.bom.components.Finish;


public interface ItemRepository extends MongoRepository<Finish, String> {
	
	@Query("{name:'?0'}")
	Finish findItemByName(String name);
	
	@Query(value="{category:'?0'}", fields="{'name' : 1, 'quantity' : 1}")
	List<Finish> findAll(String category);
	
	public long count();

}
