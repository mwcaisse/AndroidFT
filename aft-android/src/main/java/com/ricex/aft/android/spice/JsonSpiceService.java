package com.ricex.aft.android.spice;

import java.util.List;

import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.Application;

import com.octo.android.robospice.SpringAndroidSpiceService;
import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.octo.android.robospice.persistence.springandroid.json.jackson.JacksonObjectPersisterFactory;

/** JSON Spice service 
 * 
 * @author Mitchell Caisse
 *
 */

public class JsonSpiceService extends SpringAndroidSpiceService {

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public CacheManager createCacheManager(Application application) throws CacheCreationException {
		CacheManager cacheManager = new CacheManager();
		JacksonObjectPersisterFactory persisterFactory = new JacksonObjectPersisterFactory(application);
		cacheManager.addPersister(persisterFactory);		
		return cacheManager;
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public RestTemplate createRestTemplate() {
		RestTemplate template = new RestTemplate();
		
		//add message converters to the template
	    MappingJacksonHttpMessageConverter jsonConverter = new MappingJacksonHttpMessageConverter();
	    FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
	    StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
	    final List< HttpMessageConverter< ? >> listHttpMessageConverters = template.getMessageConverters();

	    listHttpMessageConverters.add( jsonConverter );
	    listHttpMessageConverters.add( formHttpMessageConverter );
	    listHttpMessageConverters.add( stringHttpMessageConverter );
	    template.setMessageConverters( listHttpMessageConverters );
	    
	    return template;
	}
}
