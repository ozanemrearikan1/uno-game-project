package user;
/**
 * @author ozanemrearikan
 */
public class User {

	private String name;
	private String surname;
	private String username;
	private int age;
	private String email;
	private String password;
	
	

	public User(String name, String surname, String username, int age, String email, String password) {
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.age = age;
		this.email = email;
		this.password = password;
		
		
		
	}

	// Getters & Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

	
	

}
