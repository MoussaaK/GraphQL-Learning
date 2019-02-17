package org.konate.telecom3.tutographql;

public class Link {
    
	private final String url;
    private final String description;

    public Link(String url, String description) {
        this.url = url;
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }
    
    @Override
  	public String toString() {
  		return "Link [url=" + url + ", description=" + description + "]";
  	}
}

