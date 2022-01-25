package com.example.anderson.benchimage.util;



/**
 * This interface maybe be used inside <AsyncTask>, for get some intermediaries 
 * results.
 * 
 */

public interface TaskResult<T> {
	void completedTask(T obj);

	void taskOnGoing(int completed);

	void taskOnGoing(int completed, String statusText);
	
	void taskOnGoing(int completed, String statusText, String execText);
}