package com.svashishtha.eclipsetomaven.classpathentry;

public class ClasspathEntryAttributes {


	private String kind;
	private String path;
	private String exported;
	private String combinedAccessRules;

	public void setKind(String kind) {
		this.kind = kind;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setExported(String exported) {
		this.exported = exported;
	}

	public void setCombinedAccessRules(String combinedAccessRules) {
		this.combinedAccessRules = combinedAccessRules;
	}

	public String getKind() {
		return kind;
	}

	public String getPath() {
		return path;
	}

	public String getExported() {
		return exported;
	}

	public String getCombinedAccessRules() {
		return combinedAccessRules;
	}

}
