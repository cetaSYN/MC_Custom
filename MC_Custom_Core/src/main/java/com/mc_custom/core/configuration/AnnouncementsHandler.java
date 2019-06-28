package com.mc_custom.core.configuration;

import com.mc_custom.core.MC_Custom_Core;
import com.mc_custom.core.timers.CoreTimer;

import java.util.LinkedList;

public class AnnouncementsHandler {

	LinkedList<CoreTimer> announcements = new LinkedList<>();

	public AnnouncementsHandler() {
	}

	public void addAnnouncement(final Announcement announcement) {
		CoreTimer timer = new CoreTimer(announcement.getRepeatTime()) {
			@Override
			public void run() {
				announcement.announce();
			}
		};
		announcements.add(timer);
		MC_Custom_Core.getTimerHandler().submitTask(timer);
	}

	public void clearAnnouncements() {
		for (CoreTimer timer : announcements) {
			MC_Custom_Core.getTimerHandler().cancelTask(timer);
		}
		announcements.clear();
	}
}
