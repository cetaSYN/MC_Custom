package com.mc_custom.core.utils.chatbuilders;

import com.mc_custom.core.exceptions.NoPermissionException;
import com.mc_custom.core.players.BasePlayer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sun.istack.internal.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public abstract class ReflectionBuilder<T> implements ChatBuilder {

	protected final Gson gson;
	protected final Class<T> clazz;
	protected final BasePlayer player;
	protected T target;

	private final String PERMISSION_FIELD_GET = "core.build.reflect.field.get";
	private final String PERMISSION_FIELD_SET = "core.build.reflect.field.set";
	private final String PERMISSION_FIELD_LIST = "core.build.reflect.field.list";

	protected ReflectionBuilder(BasePlayer player, @Nullable T target, boolean instantiate_if_null) {
		this(player,
				target,
				instantiate_if_null,
				new GsonBuilder().serializeNulls().disableHtmlEscaping().enableComplexMapKeySerialization().create()
		);
	}

	protected ReflectionBuilder(BasePlayer player, @Nullable T target, boolean instantiate_if_null, Gson gson) {
		TypeToken<T> token = new TypeToken<T>() {
		};
		clazz = (Class<T>) token.getRawType();
		if (target == null && instantiate_if_null) {
			try {
				target = clazz.newInstance();
			}
			catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		this.target = target;
		this.player = player;
		this.gson = gson;
		player.setChatBuilder(this);
	}

	protected void setField(String field_name, String json_value) throws NoSuchFieldException,
			IllegalAccessException, NoPermissionException {
		if (!player.hasPermission(PERMISSION_FIELD_SET)) {
			throw new NoPermissionException();
		}
		Field field = clazz.getDeclaredField(field_name);
		field.setAccessible(true);
		Type type = field.getGenericType();
		Object value = gson.toJson(json_value, type);
		field.set(target, value);
	}

	protected String getField(String field_name) throws NoSuchFieldException, IllegalAccessException,
			NoPermissionException {
		if (!player.hasPermission(PERMISSION_FIELD_GET)) {
			throw new NoPermissionException();
		}
		Field field = clazz.getDeclaredField(field_name);
		field.setAccessible(true);
		Type type = field.getGenericType();
		Object value = field.get(target);
		return gson.toJson(value, type);
	}

	public String[] listFields() throws NoPermissionException {
		if (!player.hasPermission(PERMISSION_FIELD_LIST)) {
			throw new NoPermissionException();
		}
		List<String> fields = new ArrayList<>();
		for (Field field : clazz.getDeclaredFields()) {
			fields.add(field.getName() + ", " + field.getType().getSimpleName());
		}
		return fields.toArray(new String[fields.size()]);
	}

	protected String[] getFields(boolean format) {
		ArrayList<String> fields = new ArrayList<>();
		for (Field field : target.getClass().getDeclaredFields()) {
			if (!Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers())) {
				fields.add(field.getName() + (format ? ", " + field.getType().getSimpleName() : ""));
			}
		}
		return fields.toArray(new String[fields.size()]);
	}
}
