package com.mc_custom.core.timers;

import java.util.ArrayList;

public class TimerHandler implements Runnable{

	private final ArrayList<CoreTimer> tasks = new ArrayList<>();
	private final ArrayList<CoreTimer> to_remove = new ArrayList<>();
	private final ArrayList<CoreTimer> to_add = new ArrayList<>();
	private long time_compare = System.currentTimeMillis();
	
	@Override public synchronized void run() {
		// Prevent concurrent modification
		tasks.addAll(to_add);
		tasks.removeAll(to_remove);
		to_add.clear();
		to_remove.clear();
		for(CoreTimer task : tasks){
			if(task.getTimePassed()>=task.getRepeatDelay()){
				task.run();
				task.resetTime();
			}
			task.passTime((System.currentTimeMillis() - time_compare));
		}
		time_compare = System.currentTimeMillis();
	}
	
	public synchronized void submitTask(CoreTimer task){
		to_add.add(task);
	}
	
	public synchronized void cancelTask(CoreTimer task){
		to_remove.add(task);
	}
}