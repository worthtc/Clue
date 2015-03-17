package clueGame;

public class Solution {
	private String person;
	private String weapon;
	private String room;
	
	public Solution(String person, String weapon, String room){
		this.person = person;
		this.weapon = weapon;
		this.room = room;
	}

	public String getPerson() {
		return person;
	}

	public String getWeapon() {
		return weapon;
	}

	public String getRoom() {
		return room;
	}
	
	public boolean equals(Solution s){
		if (person.equals(s.getPerson()) && weapon.equals(s.getWeapon()) && room.equals(s.getRoom())) return true;
		return false;
	}
}
