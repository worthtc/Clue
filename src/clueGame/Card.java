package clueGame;

public class Card{
	private String name;
	public enum CardType{ROOM,WEAPON,PERSON};
	private CardType type;
	
	public Card(String name){
		this.name = name;
	}
	
	public Card(String name, CardType type){
		this.name = name;
		this.type = type;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CardType getType() {
		return type;
	}

	public void setType(CardType type) {
		this.type = type;
	}
	
	public boolean equals(Card c){
		if(c.getName().equals(name) && c.getType() == type) return true;
		return false;
	}

	@Override
	public String toString() {
		return "Card [name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	
	
	
}