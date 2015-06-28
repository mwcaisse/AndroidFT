package com.ricex.aft.android.requester;

public abstract class AbstractRequestCallback<T> implements RequestCallback<T> {

	public void onSuccess(T results) {}

	public void onFailure(AFTResponse<T> response) {}

	public void onError(Exception e) {}

}
