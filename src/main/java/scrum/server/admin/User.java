package scrum.server.admin;

import ilarkesto.base.Crypt;

public class User extends GUser {

	private String name;
	private String realName;
	private String password;

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Override
	public boolean isAdmin() {
		return false;
	}

	@Override
	public boolean matchesPassword(String password) {
		return Crypt.cryptWebPassword(password).equals(this.password);
	}

	@Override
	public void setPassword(String value) {
		this.password = Crypt.cryptWebPassword(password);
		entityModified();
	}

	@Override
	public String getAutoLoginString() {
		return null;
	}

	public User(GUser template) {
		super(template);
	}

	public User() {
		super(null);
	}

}