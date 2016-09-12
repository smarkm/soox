package org.smark.soox.eclipse.handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.naming.InitialContext;
import javax.security.auth.login.Configuration;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.smark.soox.eclipse.SooxInpurtDialog;

/**
 */
public class SooxSearchHandler extends AbstractHandler {
	static Set<String> searchHistory = new HashSet<>();
	public SooxSearchHandler() {
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		String tipText = "hello g";
		if (searchHistory.size()>0) {
			tipText = (String)searchHistory.toArray()[0];
		}
		SooxInpurtDialog dialog = new SooxInpurtDialog(window.getShell(), "soox", "seach easy use soox", tipText, null);
		int rs = dialog.open();
		if (rs==InputDialog.OK) {
			String value = dialog.getValue();
			String[] keys = value.trim().split(" ");
			StringBuilder validKeys = new StringBuilder("");
			String engin = "";
			if (keys.length>0) {
				engin = keys[keys.length-1];
				if (Engines.engines.containsKey(engin)) {
					validKeys.append(value.substring(0, value.length()-1));
				}else{
					validKeys.append(value);
				}
			}
			String command = "cmd /c start "+Engines.getEngine(engin)+validKeys.toString().trim().replaceAll(" ", "%20");
			System.out.println(command);
			try {
				Runtime.getRuntime().exec(command);
				searchHistory.add(validKeys.toString().trim());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static void main(String[]args) throws IOException{
		Runtime.getRuntime().exec("cmd /c start https://www.google.com/#q=eclipse%20s");
	}
	
	static class Engines {
		static Map<String, String> engines = new HashMap<>();
		
		static {
			loadEnginesFile();
		}
		public static String getEngine(String key){
			String engine = engines.get(key);
			if (engine==null) {
				engine = engines.get("g");
			}
			return engine;
		}
		
		public static  void loadEnginesFile(){
			try {
				
				File cfgFile = new File(buildEngineConfigPath());
				if (cfgFile.exists()) {
					Properties es = new Properties();
					es.load(new FileInputStream(cfgFile));
					for (Object key: es.keySet()) {
						engines.put((String)key, es.getProperty((String) key));
					}
				}else{
					if(initSooxConfiguration()){
						loadEnginesFile();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		private static boolean initSooxConfiguration(){ 
			System.out.println("");
			URL url = Engines.class.getClassLoader().getResource("/engines.properties");
			File orgineFile = new File(Engines.class.getResource("/engines.properties").getPath());
			System.out.println(orgineFile.getAbsolutePath());
			try {
				Files.copy(orgineFile.toPath(), new FileOutputStream(new File(buildEngineConfigPath())));
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
		
		private static String buildEngineConfigPath(){
			StringBuilder cfgFileName = new StringBuilder(System.getProperty("osgi.configuration.area"))
					.append(File.separator+"org.smark.soox.eclipse")
					.append(File.separator+"engines.properties");
			try {
				return new URL(cfgFileName.toString()).getPath();
			} catch (MalformedURLException e) {
				return System.getProperty("user.home")+File.separator+".soox";
			}
		}
	}
}
