/**
 * 
 */
package io.avalon.bom.database.repository;

import java.util.Collection;

import io.avalon.bom.components.AvalonObject;
import javafx.beans.property.ListProperty;

/**
 * @author gte81
 *
 */
public class AvalonListProperty<T extends ListProperty<T>> {

    private T item;

    public AvalonListProperty(T item) {
        this.item = item;
    }

    public void copy(Collection<? extends T> src, Collection<? super T> dest) {
        for (T product : src) {
            dest.add(product);
        }
    }

    public T getItem() {
        return item;
    }

    public static <S extends ListProperty<S>> void copyStatic(Collection<S> src,
            Collection<? super S> dest) {
        for (S product : src) {
            dest.add(product);
        }
    }

}