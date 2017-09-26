package fil.coo.character;

public abstract class GameCharacter {

	protected String nom;
	private int hp;
	private int strenght;
	private int gold;
	
	public GameCharacter(String nom, int hp, int strenght, int gold) {
		this.nom = nom;
		this.hp = hp;
		this.strenght = strenght;
		this.gold = gold;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getStrenght() {
		return strenght;
	}

	public void setStrenght(int strenght) {
		this.strenght = strenght;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public boolean isAlive() {
		return hp > 0;
	}
	
	public void changeHp(int bonusHp) {
		this.hp += bonusHp;
	}
	
	public void changeStrenght(int bonusStrenght) {
		this.strenght += bonusStrenght;
	}
	
	public void changeGold(int bonusGold) {		
		this.gold += bonusGold;
	}
}
