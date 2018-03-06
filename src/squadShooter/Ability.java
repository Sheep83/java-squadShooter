package squadShooter;

public class Ability {
	
	private String name;
	private int damage;
	
	public Ability(String name, int damage) {
		this.setName(name);
		this.setDamage(damage);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	
	
	

}
