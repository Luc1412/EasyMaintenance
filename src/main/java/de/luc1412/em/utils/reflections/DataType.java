package de.luc1412.em.utils.reflections;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Luc1412 on 16.10.2017 at 18:38
 */
public enum DataType {

	BYTE(byte.class, Byte.class),
	SHORT(short.class, Short.class),
	INTEGER(int.class, Integer.class),
	LONG(long.class, Long.class),
	CHARACTER(char.class, Character.class),
	FLOAT(float.class, Float.class),
	DOUBLE(double.class, Double.class),
	BOOLEAN(boolean.class, Boolean.class);

	private static final Map<Class<?>, DataType> CLASS_MAP = new HashMap<>();

	static {
		for (DataType type : values()) {
			CLASS_MAP.put(type.primitive, type);
			CLASS_MAP.put(type.reference, type);
		}
	}

	private final Class<?> primitive;
	private final Class<?> reference;

	DataType(Class<?> primitive, Class<?> reference) {
		this.primitive = primitive;
		this.reference = reference;
	}

	public static DataType fromClass(Class<?> clazz) {
		return CLASS_MAP.get(clazz);
	}

	public static Class<?> getPrimitive(Class<?> clazz) {
		DataType type = fromClass(clazz);
		return type == null ? clazz : type.getPrimitive();
	}

	public static Class<?> getReference(Class<?> clazz) {
		DataType type = fromClass(clazz);
		return type == null ? clazz : type.getReference();
	}

	public static Class<?>[] getPrimitive(Class<?>[] classes) {
		int length = classes == null ? 0 : classes.length;
		Class<?>[] types = new Class<?>[length];
		for (int index = 0; index < length; index++) {
			types[index] = getPrimitive(classes[index]);
		}
		return types;
	}

	public static Class<?>[] getReference(Class<?>[] classes) {
		int length = classes == null ? 0 : classes.length;
		Class<?>[] types = new Class<?>[length];
		for (int index = 0; index < length; index++) {
			types[index] = getReference(classes[index]);
		}
		return types;
	}

	public static Class<?>[] getPrimitive(Object[] objects) {
		int length = objects == null ? 0 : objects.length;
		Class<?>[] types = new Class<?>[length];
		for (int index = 0; index < length; index++) {
			if (objects[index] != null)
				types[index] = getPrimitive(objects[index].getClass());
		}
		return types;
	}

	public static Class<?>[] getReference(Object[] objects) {
		int length = objects == null ? 0 : objects.length;
		Class<?>[] types = new Class<?>[length];
		for (int index = 0; index < length; index++) {
			if (objects[index] != null)
				types[index] = getReference(objects[index].getClass());
		}
		return types;
	}

	public static boolean compare(Class<?>[] primary, Class<?>[] secondary) {
		if (primary == null || secondary == null || primary.length != secondary.length) {
			return false;
		}
		for (int index = 0; index < primary.length; index++) {
			Class<?> primaryClass = primary[index];
			Class<?> secondaryClass = secondary[index];
			if (secondaryClass != null && primaryClass != null)
				if (primaryClass.equals(secondaryClass) || primaryClass.isAssignableFrom(secondaryClass)) {
					continue;
				}
			return false;
		}
		return true;
	}

	public Class<?> getPrimitive() {
		return primitive;
	}

	public Class<?> getReference() {
		return reference;
	}
}
