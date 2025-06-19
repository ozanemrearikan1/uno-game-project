package game;
/**
 * @author ozanemrearikan
 */
public class Card {

	private Couleur colour;
	private Valeur valour;

	// Four colour and another enumeration for Wild
	public enum Couleur{
		Red, Blue, Green, Yellow, Wild;
	}

	// (4 Zero, 8x9 at total 76 for numbers) + (3 action cards x 4 colors x 2 for each = 24) + (4 for each Wild = 8) = 108
	public enum Valeur{
		Zero, One, Two, Three, Four, Five, Six, Seven, Eight, Nine, DrawTwo, Skip, Reverse, Wild, WildFour;
	}
	
	/**
	 * Colour and value are important. Actions will be detected and points are in player class.
	 * @param colour
	 * @param valour
	 */
	public Card(Couleur colour, Valeur valour) {
		this.colour = colour;
		this.valour = valour;
	}
	
	
	
	@Override
	public String toString() {
		return colour + "-" + valour;
	}

	// After long time, I turned back to this class to put image paths.
	/**
	 * This method return the image of card.
	 * Since each card has its class, the method returns accordingly to card's fields/instance variables value and color.
	 * Since wild cards' color is set by players, they will not have algorithmic image direction finding.
	 * @return String imageDirection
	 */
	public String getImageDirection() {
		String direction = "cardimages/unocards/";
		if (this.valour.equals(Valeur.Wild)) {
			return direction + "wild_wild.png";
		}
		if (this.valour.equals(Valeur.WildFour)) {
			return direction + "wildfour_wild.png";
		}
		return direction + valour.toString().toLowerCase() + "_" + colour.toString().toLowerCase() + ".png";
	}

	// For save&load manipulations, there is a need for newer method:
	/**
	 * The method affiliated with this method takes card informations as string and put them into save file.
	 * This method uses this card string to convert them again playable cards in the loaded game!
	 * @param String cardString
	 * @return	Card card
	 */
	public static Card convertToCard(String cardString) {
		String[] parts = cardString.split("-");
		Couleur couleur = Couleur.valueOf(parts[0]);
		Valeur valeur = Valeur.valueOf(parts[1]);
		return new Card(couleur, valeur);
	}

	
	// Getters & Setters
	public Couleur getColour() {
		return colour;
	}

	public Valeur getValour() {
		return valour;
	}
	
	public void setColour(Couleur colour) {
		this.colour = colour;
	}

	public void setValour(Valeur valour) {
		this.valour = valour;
	}





}
