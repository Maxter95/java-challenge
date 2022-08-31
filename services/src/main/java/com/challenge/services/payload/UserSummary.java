package com.challenge.services.payload;


public class UserSummary {
    private Long id;
    private String username;
    private String name;
    private String email;
    private String prenom;


    public UserSummary(Long id, String username, String name) {
        this.id = id;
        this.username = username;
        this.name = name;
    }
    
    

    public UserSummary(Long id, String username, String name, String email) {
		super();
		this.id = id;
		this.username = username;
		this.name = name;
		this.email = email;
	}

    


	public UserSummary(Long id, String username, String name, String email, String prenom) {
		super();
		this.id = id;
		this.username = username;
		this.name = name;
		this.email = email;
		this.prenom = prenom;
	}



	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}


	public String getPrenom() {
		return prenom;
	}



	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
}
