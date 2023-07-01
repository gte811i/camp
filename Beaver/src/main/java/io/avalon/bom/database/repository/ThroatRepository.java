package io.avalon.bom.database.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import io.avalon.bom.components.Finish;
import io.avalon.bom.components.Throat;


public interface ThroatRepository extends MongoRepository<Throat, String> {
	
	@Query("{name:'?0'}")
	Throat findItemByName(String name);
	
	@Query(value="{category:'?0'}", fields="{'name' : 1, 'quantity' : 1}")
	List<Throat> findAll(String category);
	
	@Override
	public long count();

}
