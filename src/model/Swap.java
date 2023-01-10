package model;

import java.util.Objects;

public class Swap {
	
	private int origin;
	private int target;

	public Swap(int origin, int target) {
		super();
		this.origin = origin;
		this.target = target;
	}

	public int getOrigin() {
		return origin;
	}

	public void setOrigin(int origin) {
		this.origin = origin;
	}

	public int getTarget() {
		return target;
	}

	public void setTarget(int target) {
		this.target = target;
	}

	@Override
	public int hashCode() {
		int max = origin > target ? origin : target;
		int min = origin < target ? origin : target;
		return Objects.hash(max, min);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Swap other = (Swap) obj;
		return origin == other.origin && target == other.target || origin == other.target && target == other.origin;
	}
}
