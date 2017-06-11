package com.sizatn.springdemo.module.dwr.controller;

import java.util.Collection;

import org.directwebremoting.Browser;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;

public class DwrController {

	public void test(final String message) {
		Runnable run = new Runnable() {
			
			ScriptBuffer scriptBuffer = new ScriptBuffer(); 
			
			@Override
			public void run() {
				scriptBuffer.appendCall("showMessage", message);
				Collection<ScriptSession> sessions = Browser.getTargetSessions();
				for (ScriptSession scriptSession : sessions) {
					scriptSession.addScript(scriptBuffer);
				}
			}
		};
		
		Browser.withAllSessions(run);
	}

}
