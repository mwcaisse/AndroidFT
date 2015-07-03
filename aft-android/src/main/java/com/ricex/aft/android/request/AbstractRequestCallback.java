package com.ricex.aft.android.request;

import com.ricex.aft.android.request.exception.RequestException;


public abstract class AbstractRequestCallback<T> implements RequestCallback<T> {

	public void onSuccess(T results) {}

	public void onFailure(RequestException e, AFTResponse<T> response) {}

	public void onError(Exception e) {}

}
