package a12128050;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class FoilVehicleCard extends VehicleCard {
	private Set<Category> specials;
	public FoilVehicleCard(final String name, final Map<Category, Double> categories, final Set<Category> specials) {
		super(name, categories);
		if(specials == null || specials.size() > 3 || specials.isEmpty()) {
			throw new IllegalArgumentException();
		}
		for(Category c : specials) {
			if(c == null) {
				throw new IllegalArgumentException();
			}
		}
		this.specials = new HashSet<Category>(specials);
	}
	//getter to return a shallow copy
	public Set<Category> getSpecials() {
		return new HashSet<Category>(specials);
	}
	@Override
	public int totalBonus() {
		int sum = 0;
		sum = super.totalBonus();
		for(var s : specials) {
			sum += Math.abs(s.bonus(this.getCategories().get(s)));
		}
		return sum;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("- ");
		builder.append(super.getName());
		builder.append("(");
		builder.append(totalBonus());
		builder.append(") -> {");
		Boolean first = true;
		for(Entry<Category, Double> c : super.getCategories().entrySet()) {
			if(first) {
				builder.append(starOutput(c.getKey(), c.getValue()));
				first = false;
			}
			else {
				builder.append(", ");
				builder.append(starOutput(c.getKey(), c.getValue()));
			}
		}
		builder.append("}");
		return builder.toString();
	}
	private String starOutput(Category key, Double value) {
		StringBuilder builder = new StringBuilder();
		if(specials.contains(key)) {
			builder.append("*");
			builder.append(key.toString());
			builder.append("*=");
			builder.append(value);
		} 
		else {
			builder.append(key.toString());
			builder.append("=");
			builder.append(value);
		}
		return builder.toString();
	}
}
