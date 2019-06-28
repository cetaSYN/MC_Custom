package com.mc_custom.classes.events;

import com.mc_custom.classes.classes.MCCustomClass;
import com.mc_custom.classes.players.MCCustomPlayer;
import com.mc_custom.core.events.BaseEvent;

public class ClassChangeEvent extends BaseEvent {
	private MCCustomPlayer player;
	private MCCustomClass old_class;
	private MCCustomClass new_class;
	private boolean update_lastchange;

	public ClassChangeEvent(MCCustomPlayer player, MCCustomClass old_class, MCCustomClass new_class) {
		this.player = player;
		this.old_class = old_class;
		this.new_class = new_class;
		this.update_lastchange = true;
	}

	public MCCustomPlayer getPlayer() {
		return player;
	}

	public MCCustomClass getOldClass() {
		return old_class;
	}

	public MCCustomClass getNewClass() {
		return new_class;
	}

	public boolean isLastChangeUpdate() {
		return update_lastchange;
	}
}