package fil.coo.character;

import fil.coo.game.Room;

public class Monster extends GameCharacter {

	public Monster(String nom, int hp, int strenght, int gold) {
		super(nom, hp, strenght, gold);
	}
	
	public String toString(){
		return this.nom + " the Monster";
	}

}
