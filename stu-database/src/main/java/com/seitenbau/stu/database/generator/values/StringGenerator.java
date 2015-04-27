package com.seitenbau.stu.database.generator.values;

import java.util.Random;

import com.seitenbau.stu.database.generator.data.EntityBlueprint;

public class StringGenerator extends ValueGenerator {

	private Random random;

	@Override
	public void initialize(long seed) {
		random = new Random(seed);
		
		values = new String[]{ "\"Hund\"", "\"Katze\"", "\"Maus\"", "\"Alpha\"", "\"Beta\"", "\"Gamma\"", "\"Delta\"", "\"Lorem\"",
				"\"ipsum\"", "\"dolor\"", "\"sit\"", "\"amet\"" };
	}
	
	@Override
	public Result nextValue(){		
		return new Result(values[random.nextInt(values.length)], true, true);
	}

	@Override
	public Result nextValue(Integer index) {
		Random rand = new Random(index);		
		return new Result(values[rand.nextInt(values.length)], true, true);
	}

	public static class Factory implements ValueGeneratorFactory {

		@Override
		public ValueGenerator createGenerator() {
			return new StringGenerator();
		}

	}
	
	@Override
	public Integer getMaxIndex() {
		return values.length;
	}
}
