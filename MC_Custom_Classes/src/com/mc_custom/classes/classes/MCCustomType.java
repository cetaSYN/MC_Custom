package com.mc_custom.classes.classes;

public enum MCCustomType {
	NONE("None"),
	CUSTOM7("Custom7"),
	CUSTOM9("Custom9"),
	CUSTOM5("Custom5"),
	CUSTOM4("Custom4"),
	CUSTOM8("Custom8"),
	CUSTOM3("Custom3"),
	CUSTOM6("Custom6"),
	CUSTOM2("Custom2"),
	CUSTOM1("Custom1"),
	CUSTOM10("Custom10");

	private final String name;

	MCCustomType(String name) {
		this.name = name;
	}

	public static String getTypeName(MCCustomType type) {
		return type.name;
	}

	public static MCCustomClass getMCCustomClass(String class_name) {
		if (class_name == null || class_name.equalsIgnoreCase(NONE.name)) {
			return new None();
		}
		else if (class_name.equalsIgnoreCase(CUSTOM7.name)) {
			return new Custom7();
		}
		else if (class_name.equalsIgnoreCase(CUSTOM9.name)) {
			return new Custom9();
		}
		else if (class_name.equalsIgnoreCase(CUSTOM5.name)) {
			return new Custom5();
		}
		else if (class_name.equalsIgnoreCase(CUSTOM4.name)) {
			return new Custom4();
		}
		else if (class_name.equalsIgnoreCase(CUSTOM8.name)) {
			return new Custom8();
		}
		else if (class_name.equalsIgnoreCase(CUSTOM3.name)) {
			return new Custom3();
		}
		else if (class_name.equalsIgnoreCase(CUSTOM6.name)) {
			return new Custom6();
		}
		else if (class_name.equalsIgnoreCase(CUSTOM2.name)) {
			return new Custom2();
		}
		else if (class_name.equalsIgnoreCase(CUSTOM1.name)) {
			return new Custom1();
		}
		else if (class_name.equalsIgnoreCase(CUSTOM10.name)) {
			return new Custom10();
		}
		else {
			return null;
		}
	}

	public static boolean isMCCustomType(String check_type) {
		for (MCCustomType type : MCCustomType.values()) {
			if (getTypeName(type).equalsIgnoreCase(check_type)) {
				return true;
			}
		}
		return false;
	}

	//Should not need recalculating each time.
	public static String[] listNames() {
		String types[] = new String[MCCustomType.values().length];
		int i = 0;
		for (MCCustomType type : MCCustomType.values()) {
			types[i] = type.name;
			i++;
		}
		return types;
	}
}
