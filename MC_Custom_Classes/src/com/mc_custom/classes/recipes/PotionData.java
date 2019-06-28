package com.mc_custom.classes.recipes;

public enum PotionData {
	WATER_BOTTLE((byte) 0),
	AWKWARD((byte) 16),
	THICK((byte) 32),
	MUNDANE((byte) 64),
	REGENERATION((byte) (8193)),
	SWIFTNESS((byte) 8194),
	FIRE_RESISTANCE((byte) 8195),
	POISON((byte) 8196),
	HEALING((byte) 8197),
	NIGHT_VISION((byte) 8198),
	WEAKNESS((byte) 8200),
	STRENGTH((byte) 8201),
	SLOWNESS((byte) 8202),
	HARMING((byte) 8204),
	WATER_BREATHING((byte) 8205),
	INVISIBILITY((byte) 8206),
	REGENERATION_II((byte) 8225),
	SWIFTNESS_II((byte) 8226),
	POISON_II((byte) 8228),
	HEALING_II((byte) 8229),
	STRENGTH_II((byte) 8233),
	HARMING_II((byte) 8236),
	REGENERATION_EXTENDED((byte) 8257),
	SWIFTNESS_EXTENDED((byte) 8258),
	FIRE_RESISTANCE_EXTENDED((byte) 8259),
	POISON_EXTENDED((byte) 8260),
	NIGHT_VISION_EXTENDED((byte) 8262),
	WEAKNESS_EXTENDED((byte) 8264),
	STRENGTH_EXTENDED((byte) 8265),
	SLOWNESS_EXTENDED((byte) 8266),
	WATER_BREATHING_EXTENDED((byte) 8269),
	INVISIBILITY_EXTENDED((byte) 8270),
	REGENERATION_II_EXTENDED((byte) 8289),
	SWIFTNESS_II_EXTENDED((byte) 8290),
	POISON_II_EXTENDED((byte) 8292),
	STRENGTH_II_EXTENDED((byte) 8297);

	private final byte id;

	PotionData(byte id) {
		this.id = id;
	}

	public static byte getVal(PotionData data) {
		return data.id;
	}

	public byte getId() {
		return id;
	}
}
