package org.smark.soox.eclipse;

public class SearchEngine {
	String engine;
	String suffix;

	public SearchEngine(String engine, String suffix) {
		super();
		this.engine = engine;
		this.suffix = suffix;
	}

	public String getEngine() {
		return engine;
	}

	public void setEngine(String engine) {
		this.engine = engine;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

}
