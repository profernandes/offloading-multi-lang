package com.example.anderson.benchimage.util;


/**
 * @author Philipp B. Costa
 */


public abstract class TaskResultAdapter<T> implements TaskResult<T> {
	@Override
	public void completedTask(T obj) {
	}

	@Override
	public void taskOnGoing(int completed) {
	}

	@Override
	public void taskOnGoing(int completed, String statusText) {
	}

	@Override
	public void taskOnGoing(int completed, String statusText, String execText) {
	}
}