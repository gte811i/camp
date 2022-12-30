package io.avalon.bom.database;

public interface CustomItemRepository {
	
	void updateItemQuantity(String itemName, float newQuantity);

}
