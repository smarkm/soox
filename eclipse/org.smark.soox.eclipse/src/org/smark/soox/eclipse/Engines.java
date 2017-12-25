package org.smark.soox.eclipse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

public class Engines {
		public static Map<String, String> engines = new HashMap<>();
		
		static {
			loadEnginesFile();
		}
		public static String getEngine(String key){
			String engine = engines.get(key);
			if (engine==null) {
				engine = engines.get("g");
			}
			if (engine==null) {
				return "https://www.google.com/#q=";
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
			ResourceBundle bundle = ResourceBundle.getBundle("engines");
			try {
				File cfg = new File(buildEngineConfigPath());
				if (!cfg.getParentFile().exists()) {
					cfg.getParentFile().mkdirs();
				}
				FileWriter writer = new FileWriter(cfg);
				String lineSeparator = System.getProperty("line.separator", "\n");
				for(String key:bundle.keySet()){
					writer.write(key+"="+bundle.getString(key)+lineSeparator);
				};
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return true;
		}
		
		private static String buildEngineConfigPath(){
			StringBuilder path = new StringBuilder(System.getProperty("user.home"))
					.append(File.separator+"soox_engines.properties");
			return path.toString();
		}

		public static void deleteEngine(SearchEngine engine) {
			engines.remove(engine.suffix);
		}
		
		public static void updateEngine(SearchEngine newEngine) {
			addEngine(newEngine);
		}

		public static void addEngine(SearchEngine newEngine) {
			engines.put(newEngine.suffix, newEngine.engine);
		}
}
