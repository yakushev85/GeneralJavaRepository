package chartbuilderfx.oleksandr.iakushev.com.calculatorfx.indexparser.functions;

public abstract class MathFunction {
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public abstract double eval(double arg);
}
