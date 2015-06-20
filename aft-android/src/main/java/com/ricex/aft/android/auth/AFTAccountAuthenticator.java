package com.ricex.aft.android.auth;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.ricex.aft.android.requester.InvalidCredentialsException;
import com.ricex.aft.android.requester.UserRequester;

/** The AccountAuthenticator to use with AccountManager for PushFile
 * 
 * @author Mitchell Caisse
 *
 */
public class AFTAccountAuthenticator extends AbstractAccountAuthenticator {

	/** The context for the Authenticator to use */
	private Context context;	
	
	/** The user requester to make account based requests to the server */
	private UserRequester userRequester;
	
	/** Creates a new AFTAccountAuthenticator with the specified context
	 * 
	 * @param context The context
	 */
	public AFTAccountAuthenticator(Context context) {
		super(context);
		this.context = context;
		userRequester = new UserRequester(context);
	}

	@Override
	public Bundle editProperties(AccountAuthenticatorResponse response,
			String accountType) {
		return null;
	}

	@Override
	public Bundle addAccount(AccountAuthenticatorResponse response,	String accountType, String authTokenType,
							 String[] requiredFeatures, Bundle options)	throws NetworkErrorException {
		
		//create the intent for the account manager to launch to create an account
		Intent intent = new Intent(context, AccountActivity.class);
		intent.putExtra(AccountActivity.ARG_ACCOUNT_TYPE, accountType);
		intent.putExtra(AccountActivity.ARG_AUTH_TYPE, authTokenType);
		intent.putExtra(AccountActivity.ARG_IS_ADDING_NEW_ACCOUNT, true);
		intent.putExtra(AccountActivity.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
		
		//create the bundle to contain the intent
		Bundle bundle = new Bundle();
		bundle.putParcelable(AccountManager.KEY_INTENT, intent);
		
		//return the bundle
		return bundle;
	}

	@Override
	public Bundle confirmCredentials(AccountAuthenticatorResponse response,
			Account account, Bundle options) throws NetworkErrorException {
		return null;
	}

	@Override
	public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options)
								throws NetworkErrorException {

		AccountManager accountManager = AccountManager.get(context);
		
		String authToken = accountManager.peekAuthToken(account, authTokenType);
		
		//If we do not have a cached auth token, request one from the server with the stored credentials
		// if they exist
		if (TextUtils.isEmpty(authToken)) {
			String password = accountManager.getPassword(account);
			if (!TextUtils.isEmpty(password)) {
				try {
					authToken = userRequester.fetchAuthenticationToken(account.name, password);
				}
				catch (InvalidCredentialsException e) {
					//fall through, this will prompt the user for new credentials.
				}
			}
		}
		
		// If we have an authorization token, return it
		if (!TextUtils.isEmpty(authToken)) {
			Bundle res = new Bundle();
			
			res.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
			res.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
			res.putString(AccountManager.KEY_AUTHTOKEN, authToken);
			
			return res;
		}
		
		//If we get here, then we were unable to obtain an authorization token
		// either there were no stored credentials or the stored credentials are no longer valid
		// prompt the user for new credentials
		
		Intent intent = new Intent(context, AccountActivity.class);
		intent.putExtra(AccountActivity.ARG_ACCOUNT_TYPE, account.type);
		intent.putExtra(AccountActivity.ARG_ACCOUNT_NAME, account.name);
		intent.putExtra(AccountActivity.ARG_AUTH_TYPE, authTokenType);
		intent.putExtra(AccountActivity.ARG_IS_ADDING_NEW_ACCOUNT, false);
		intent.putExtra(AccountActivity.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
		
		Bundle res = new Bundle();
		res.putParcelable(AccountManager.KEY_INTENT, intent);		
		return res;
	}

	@Override
	public String getAuthTokenLabel(String authTokenType) {
		return "";
	}

	@Override
	public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options)
									throws NetworkErrorException {
		return null;
	}

	@Override
	public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException {
		return null;
	}

}
