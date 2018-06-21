package com.mulesoft.training;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.routing.filter.Filter;
import org.mule.api.transport.PropertyScope;

public class MuleFilter implements Filter, Initialisable
{
	protected transient final Logger logger = LoggerFactory.getLogger(MuleFilter.class);
	private String propertyName = "http.query.params";
	private PropertyScope propertyScope = PropertyScope.INBOUND;
	
	private String urlParamType;
	
	public MuleFilter()
	{
		super();
	}
	
	public void setUrlParamType(String value)
	{
		this.urlParamType = value;
	}
	
	/**
	 * This method is also called during deployment of this class.
	 * If the property is not set in the Mule XML, the InitialisationException is thrown.
	 */
	public void initialise() throws InitialisationException
	{
		if (this.urlParamType == null)
			throw new InitialisationException(new IllegalArgumentException("The property urlParamType is not configured for the custom filer " + MuleFilter.class.getName() + "."), this);
	}

	public boolean accept(MuleMessage message)
	{
		boolean result = false;
		log("Checking to accept or reject the received message...");
		log("Value urlParamType=\"" + this.urlParamType + "\"");

		Map<String, String> queryParams = message.getInboundProperty("http.query.params");
		String queryParamType = queryParams.get("type");
		log("The received message has inbound parameter type with value \"" + queryParamType + "\".");
		
		if (queryParamType.compareToIgnoreCase(this.urlParamType) == 0)
		{
			result = true;
			log("The received message is accepted.");
		}
		else
			log("The received message is rejected.");
				
		return result;
	}
	
	private void log(String message)
	{
		logger.info("Mule Filter: " + message);
	}
}
