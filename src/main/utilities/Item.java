package main.utilities;

public class Item {
	
	// ATTRIBUTES //
	
	protected String id;
	protected String name;
	
	// OVERRIDE METHOD 'toString' //
	
	@Override
	public String toString() {
		return name;
	}
}
