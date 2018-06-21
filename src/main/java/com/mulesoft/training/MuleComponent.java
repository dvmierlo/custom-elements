package com.mulesoft.training;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mule.api.annotations.param.Payload;
import org.mule.api.lifecycle.*;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleException;
import org.mule.api.annotations.param.InboundHeaders;

public class MuleComponent implements Initialisable, Startable, Callable, Stoppable, Disposable
{
	private static Logger logger = LoggerFactory.getLogger(MuleComponent.class);
	private int count;
	
	public MuleComponent()
	{
		log("Default constructor");
		
		this.count = 1;
	}
	
	public Map<String, String> processMap(Map<String, String> input)
	{
		input.put("processed by", "processMap");
		return input;
	}

	public Map<String, String> processArray(List<String> input)
	{
		Map<String, String> output = new HashMap<String, String>();
		output.put("message", input.get(0));
		output.put("processedBy", "processArray");

		return output;
	}

	public Map<String, String> processString(String input)
	{
		Map<String, String> output = new HashMap<String, String>();
		output.put("message", input);
		output.put("processedBy", "processString");

		return output;
	}

	public Map<String, String> processAll(@Payload Object input, @InboundHeaders("http.method") String method)
	{
		Map<String, String> output = new HashMap<String, String>();
		output.put("message", input.toString());
		output.put("http.method", method);
		output.put("processedBy", "processAll");
		output.put("currentCOunt", Integer.toString(this.count));
		
		this.count++;
		
		return output;
	}

	public void initialise() throws InitialisationException
	{
		log("initialise");
	}

	public void start() throws MuleException
	{
		log("start");
	}

	// onCall does NOT get called when annotations are used. See method processAll.
	public Object onCall(MuleEventContext eventContext) throws Exception
	{
		log("onCall");
		return eventContext.getMessage();
	}

	public void stop() throws MuleException
	{
		log("stop");
	}

	public void dispose()
	{
		log("dispose");
	}
	
	private void log(String message)
	{
		logger.info("Mule Component Lifecyle: " + message);
	}
}
