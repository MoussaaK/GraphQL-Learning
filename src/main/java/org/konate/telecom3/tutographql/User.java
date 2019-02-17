package org.konate.telecom3.tutographql;

public class User {
	private String login;
	private String name;
    private Repository repositories;
    
	public User() {
		super();
	}
	
	public User(String login, String name) {
		super();
		this.login = login;
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public Repository getRepositories() {
		return repositories;
	}

	public void setRepositories(Repository repositories) {
		this.repositories = repositories;
	}

	@Override
	public String toString() {
		return "User [login=" + login + ", name=" + name + ", repositories=" + repositories + "]";
	}

}
