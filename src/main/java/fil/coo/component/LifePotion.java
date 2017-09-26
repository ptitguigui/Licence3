package fil.coo.component;

import fil.coo.character.GameCharacter;

public class LifePotion implements Item {

	int bonusHp = 10;
	
	public void use(GameCharacter g) {
		g.changeHp(bonusHp);
		System.out.println("You heal "+ bonusHp +" hp");
	}
	
	public String toString() {
		return "life potion";
	}

}
