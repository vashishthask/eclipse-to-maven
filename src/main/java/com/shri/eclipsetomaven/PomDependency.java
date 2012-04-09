package com.shri.eclipsetomaven;

public class PomDependency {
	private final String groupId;
	private final String artifactId;
	private final String jarVersion;
	
	
	public PomDependency(String groupId, String artifactId, String jarVersion) {
		super();
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.jarVersion = jarVersion;
	}


	public String getGroupId() {
		return groupId;
	}


	public String getArtifactId() {
		return artifactId;
	}


	public String getJarVersion() {
		return jarVersion;
	}
}
