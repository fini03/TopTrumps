package a12128050;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

public class VehicleCard implements Comparable<VehicleCard> {
	public enum Category {
		ECONOMY_MPG("Miles/Gallon"),
		CYLINDERS_CNT("Zylinder"),
		DISPLACEMENT_CCM("Hubraum[cc]"),
		POWER_HP("Leistung[hp]"),
		WEIGHT_LBS("Gewicht[lbs]") {@Override public boolean isInverted() {return true;}},
		ACCELERATION("Beschleunigung") {@Override public boolean isInverted() {return true;}},
		YEAR("Baujahr[19xx]");
		
		private final String categoryName;
		private Category(final String categoryName) {
			if(categoryName.equals(null) || categoryName.isEmpty()) {
				throw new IllegalArgumentException();
			}
			this.categoryName = categoryName;
		}
		public boolean isInverted() {
			return false;
		}
		public int bonus(final Double value) {
			if(this.isInverted()) {
				return -value.intValue();
			}
			return value.intValue();
		}
		@Override
		public String toString() {
			return categoryName;
		}
	}
	private String name;
	private Map<Category, Double> categories;
	public VehicleCard(final String name, final Map<Category, Double> categories) {
		//name null or empty
		if(name == null || name.isEmpty()) {
			throw new IllegalArgumentException();
		}
		//categories null or empty, categories doesn't contain all Category(enum) values
		if(categories == null || categories.isEmpty()) {
			throw new IllegalArgumentException();
		}
		for(var c : Category.values()) {
			if(!categories.containsKey(c)) {
				throw new IllegalArgumentException();
			}
		}
		//categories contains null or values less than 0
		for(var c : categories.values()) {
			if(c == null || c < 0) {
				throw new IllegalArgumentException();
			}
		}
		this.name = name;
		//shallow copy of categories argument before assignment to this.categories
		this.categories = new HashMap<Category, Double>(categories);
	}
	//getters for immutable class
	public String getName() {
		return name;
	}
	//getter to return a shallow copy
	public Map<Category, Double> getCategories() {
		return new HashMap<Category, Double>(categories);
	}
	public static Map<Category, Double> newMap(double economy, double cylinders, double displacement, double power, double weight, double acceleration, double year) {
		Map<Category, Double> newVehicleCard = new HashMap<Category, Double>();
		newVehicleCard.put(Category.ECONOMY_MPG, economy);
		newVehicleCard.put(Category.CYLINDERS_CNT, cylinders);
		newVehicleCard.put(Category.DISPLACEMENT_CCM, displacement);
		newVehicleCard.put(Category.POWER_HP, power);
		newVehicleCard.put(Category.WEIGHT_LBS, weight);
		newVehicleCard.put(Category.ACCELERATION, acceleration);
		newVehicleCard.put(Category.YEAR, year);
		return newVehicleCard;
	}
	//returns 1 if totalBonus > other.totalBonus, 0 if totalBonus == other.totalBonus, -1 if totalBonus < other.totalBonus
	@Override
	public int compareTo(final VehicleCard other) {
		return Integer.compare(totalBonus(), other.totalBonus());
		//return totalBonus() - other.totalBonus();
	}
	public int totalBonus() {
		int sum = 0;
		for(Entry<Category, Double> c : categories.entrySet()) {
			sum += c.getKey().bonus(c.getValue());
		}
		return sum;
	}
	@Override
	public int hashCode() {
		return Objects.hash(name, totalBonus());
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VehicleCard other = (VehicleCard) obj;
		return Objects.equals(name, other.name) && Objects.equals(totalBonus(), other.totalBonus());
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("- ");
		builder.append(name);
		builder.append("(");
		builder.append(totalBonus());
		builder.append(") -> ");
		builder.append(categories);
		return builder.toString();
	}
}
