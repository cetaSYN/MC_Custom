package com.mc_custom.core.utils;

public enum ChatColor {
	BLACK("§0"),
	DARK_BLUE("§1"),
	DARK_GREEN("§2"),
	DARK_AQUA("§3"),
	DARK_RED("§4"),
	DARK_PURPLE("§5"),
	GOLD("§6"),
	GRAY("§7"),
	DARK_GRAY("§8"),
	BLUE("§9"),
	GREEN("§a"),
	AQUA("§b"),
	RED("§c"),
	LIGHT_PURPLE("§d"),
	YELLOW("§e"),
	WHITE("§f"),
	//MAGIC("§k"),
	BOLD("§l"),
	STRIKETHROUGH("§m"),
	UNDERLINE("§n"),
	ITALIC("§o"),
	RESET("§r");

	public static final char COLOR_CHAR = '§';
	private final String code;

	ChatColor(String code) {
		this.code = code;
	}

	/**
	 * Replaces ampersand character with corresponding valid Minecraft section-notation color code.
	 * If string does not contain ampersand char, original string will be returned.
	 *
	 * @param text String to convert.
	 * @return Converted string
	 */
	public static String convertColor(String text) {
		if (text == null) {
			return null;
		}
		char[] b = text.toCharArray();

		for (int i = 0; i < b.length - 1; ++i) {
			if (b[i] == '&' && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[i + 1]) > -1) {
				b[i] = 167;
				b[i + 1] = Character.toLowerCase(b[i + 1]);
			}
		}

		return new String(b);
	}

	@Override
	public String toString() {
		return code;
	}

	/**
	 * Strips colors from passed String.
	 * Works for both Minecraft section-notation and ampersand-notation.
	 *
	 * @param message The message to remove color codes from
	 * @return The clean message
	 */
	public static String removeColor(String message) {
		if (message == null) {
			return null;
		}
		message = convertColor(message);
		for (ChatColor color : ChatColor.values()) {
			message = message.replace(color.toString(), "");
		}
		return message;
	}
}