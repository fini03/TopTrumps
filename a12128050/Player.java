package a12128050;

import java.util.ArrayDeque;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

public class Player implements Comparable<Player> {
	private String name;
	private Queue<VehicleCard> deck = new ArrayDeque<VehicleCard>();
	
	public Player(final String name) {
		if(name == null || name.isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public int getScore() {
		return deck.stream().mapToInt(d -> d.totalBonus()).sum();
	}
	public void addCards(final Collection<VehicleCard> cards) {
		if(cards == null || cards.contains(null)) {
			throw new IllegalArgumentException();
		}
		/*
		 * for(var c : cards) addCard(c);
		 */
		deck.addAll(cards);
	}
	public void addCard(final VehicleCard card) {
		if(card == null) {
			throw new IllegalArgumentException();
		}
		deck.add(card);
	}
	public void clearDeck() {
		deck.clear();
	}
	public List<VehicleCard> getDeck() {
		return new ArrayList<VehicleCard>(deck);
	}
	protected VehicleCard peekNextCard() {
		return deck.peek();
	}
	public VehicleCard playNextCard() {
		return deck.poll();
	}
	@Override
	public int compareTo(final Player other) {
		return name.compareToIgnoreCase(other.name);
	}
	@Override
	public int hashCode() {
		return Objects.hash(name.toLowerCase());
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		return Objects.equals(name.toLowerCase(), other.name.toLowerCase()) && Objects.equals(this.getScore(), other.getScore());
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(name);
		builder.append("(");
		builder.append(getScore());
		builder.append("):");
		for(var d : deck) {
			builder.append("\n");
			builder.append(d);
		}
		return builder.toString();
	}
	public boolean challengePlayer(Player p) {
		if(p == null || p.equals(this)) {
			throw new IllegalArgumentException();
		}
		Queue<VehicleCard> thisPlayersDeck = new ArrayDeque<VehicleCard>();
		Queue<VehicleCard> pPlayersDeck = new ArrayDeque<VehicleCard>();
		Queue<VehicleCard> WinnersDeck = new ArrayDeque<VehicleCard>();
		Boolean draw = true;
		while(draw) {
			if(deck.isEmpty() || p.getDeck().isEmpty()) {
				addCards(thisPlayersDeck);
				p.addCards(pPlayersDeck);
				return false;
			}
			VehicleCard thisPlayersCard = playNextCard();
			VehicleCard pPlayersCard = p.playNextCard();
			thisPlayersDeck.add(thisPlayersCard);
			pPlayersDeck.add(pPlayersCard);
			WinnersDeck.add(thisPlayersCard);
			WinnersDeck.add(pPlayersCard);
			if(thisPlayersCard.compareTo(pPlayersCard) > 0) {
      	addCards(WinnersDeck);
      	return true;
      }
      else if(thisPlayersCard.compareTo(pPlayersCard) < 0) {
      	p.addCards(WinnersDeck);
        return false;
      }
		}
		return false;
	}
	public static Comparator<Player> compareByScore() {
		return (p1, p2) -> Integer.compare(p1.getScore(), p2.getScore());
		//return (p1, p2) -> {return p1.getScore() - p2.getScore();};
	}
	public static Comparator<Player> compareByDeckSize() {
		return (p1, p2) -> Integer.compare(p1.getDeck().size(), p2.getDeck().size());
		//return (p1, p2) -> {return p1.getDeck().size() - p2.getDeck().size();};
	}
}
