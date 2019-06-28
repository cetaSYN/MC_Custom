package com.mc_custom.core.database;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * A model for dealing with columns in SQL
 */
public class Column<T> {

	protected List<T> list = new ArrayList<>();
	protected Object value;
	protected Class<T> clazz;
	private int index;

	/**
	 * Creates a new Column Object.
	 *
	 * @param clazz Specifies the type of Objects to return
	 */
	public Column(Class<T> clazz) {
		this.clazz = clazz;
	}

	/**
	 * Adds an Object to the list. Used when adding values from multiple rows<br>
	 * This is used internally by QueryBuilder and should not be referenced Directly
	 *
	 * @param o The Object to add
	 */
	protected void add(Object o) {
		if (o instanceof Long && !clazz.equals(Long.class)) {
			Integer i = ((Long) o).intValue();
			list.add(clazz.cast(i));
			return;
		}
		if(o instanceof Timestamp && !clazz.equals(Timestamp.class)) {
			Long time = ((Timestamp) o).getTime();
			list.add(clazz.cast(time));
			return;
		}
		list.add(clazz.cast(o));
	}

	/**
	 * Returns the item from the next row in the same Column
	 *
	 * @return An Object casted to the specified Type.
	 */
	public T getNext() {
		if (list.get(index) == null
				&& (clazz.equals(Integer.class)
				|| clazz.equals(Double.class)
				|| clazz.equals(Long.class))) {
			try {
				index++;
				Constructor con = clazz.getConstructor(int.class);
				return (T) con.newInstance(0);
			}
			catch (NoSuchMethodException
					| InvocationTargetException
					| IllegalAccessException
					| InstantiationException e) {
				e.printStackTrace();
			}
		}
		return list.get(index++);
	}

	/**
	 * Gets the number of rows this Column spans
	 *
	 * @return The size.
	 */
	public int size() {
		return list.size();
	}

	/**
	 * Returns a single value if it is set, or the next item in the list.
	 *
	 * @return An Object casted to the specified Type.
	 */
	public T getValue() {
		if (value == null) {
			if (list != null && list.size() > 0) {
				return getNext();
			}
			return null;
		}
		if (value instanceof Long && !clazz.equals(Long.class)) {
			if (clazz.equals(Integer.class)) {
				return clazz.cast(((Long) value).intValue());
			}
			if (clazz.equals(Short.class)) {
				return clazz.cast(((Long) value).shortValue());
			}
		}
		return clazz.cast(value);
	}
}