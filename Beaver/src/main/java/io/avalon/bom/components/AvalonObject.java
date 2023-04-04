/**
 * 
 */
package io.avalon.bom.components;

import javafx.beans.property.StringProperty;

/**
 * @author gte81
 *
 */
public abstract class AvalonObject implements GuiObject{

	/**
	 * @return
	 */
	public abstract StringProperty codeProperty();

	/**
	 * @return
	 */
	public abstract StringProperty nameProperty();

	/**
	 * @param newValue
	 * @return
	 */
	public abstract void setName(String newValue);

	/**
	 * @return
	 */
	public abstract String getName();

	/**
	 * @return
	 */
	public abstract String getCode();

	/**
	 * @param code
	 */
	public abstract void setCode(String code);

	/**
	 * @return
	 */
    public abstract AvalonObject createAvalonObject(String name, String code);
}
