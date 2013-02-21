package energyoptimizer;

public class Size {
	private long B = 0;

	public Size(int B, int KB, int MB, int GB, int TB) {
		addB(B);
		addKB(KB);
		addMB(MB);
		addGB(GB);
		addTB(TB);
	}

	public void addB(int B) {
		this.B += B;
	}

	public void addKB(int KB) {
		addB(KB * 1024);
	}

	public void addMB(int MB) {
		addKB(MB * 1024);
	}

	public void addGB(int GB) {
		addMB(GB * 1024);
	}

	public void addTB(int TB) {
		addGB(TB * 1024);
	}

	public long getBites() {
		return B;
	}

	public String toString() {
		return B + "B";
	}
}