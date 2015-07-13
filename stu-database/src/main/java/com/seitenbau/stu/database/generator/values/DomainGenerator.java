package com.seitenbau.stu.database.generator.values;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;

import com.seitenbau.stu.database.generator.hints.DomainDataHint;
import com.seitenbau.stu.database.generator.hints.Hint;
import com.seitenbau.stu.database.generator.values.valuetypes.Value;

public class DomainGenerator extends ValueGenerator {

	private DomainData ConstraintsData;
	private ArrayList<DomainDataHint> valueList;

	public DomainData getConstraintsData() {
		return this.ConstraintsData;
	}

	public void setConstraintsData(DomainData constraintsData) {
		this.ConstraintsData = constraintsData;
	}

	public DomainGenerator(String string) {
		setKey(string);
	}

	public DomainGenerator() {
	}

	@Override
	public Result nextValue(Integer seed) {
		Random rand = new Random(seed);

		// Init Random
		rand.nextInt();
		rand.nextInt();
		rand.nextInt();

		handleHints();

		Result result = new Result(null, false, false);

		if (valueList.size() > 0) {

			boolean flag = true;
			int counter = 0;
			do {
				counter++;
				int i = rand.nextInt(valueList.size());
				DomainDataHint value = valueList.get(i);
				result.setValue(value.getValue());
				result.setGenerated(true);
				result.setFinal(true);

				boolean internflag = true;
				// Check notAllowedValues
				for (Value<?> v : notAllowedValues) {
					try {
						if (v.compareTo(result.getValue()) == 0) {
							internflag = false;
							break;
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				if (internflag)
					flag = false;
			} while (flag && counter < valueList.size() * 2);

			if (counter == valueList.size() * 2)
				return null;

			return result;
		}

		return result;
	}

	@Override
	public void handleHints() {
		super.handleHints();

		ArrayList<DomainDataHint> al = ConstraintsData.data
				.get(getKey());
		valueList = new ArrayList<DomainDataHint>();

		if (al == null)
			log.error("DomainData with " + getKey()
					+ "+ not found in the dictionary!");

		for (DomainDataHint entry : al) {
			if (!notAllowedValues.contains(entry.getValue()))
				valueList.add(entry);
		}

		for (Hint hint : getHints()) {
			if (DomainDataHint.class.isInstance(hint)) {
				DomainDataHint dsdh = ((DomainDataHint) hint);

				String key = dsdh.getKey();
				Value<?> value = dsdh.getValue();

				if (key.compareTo(key) == 0 && value != null) {
					Iterator<Entry<String, ArrayList<DomainDataHint>>> it = ConstraintsData.data
							.entrySet().iterator();
					while (it.hasNext()) {
						Entry<String, ArrayList<DomainDataHint>> pairs = it
								.next();
						if (pairs.getKey() == key) {

							ArrayList<DomainDataHint> intList = new ArrayList<DomainDataHint>();
							for (DomainDataHint c : al) {
								if (c.notAppliesTo(dsdh))
									intList.add(c);
							}

							for (DomainDataHint c : intList) {
								valueList.remove(c);
							}
						}
					}

				}

			}
		}
	}

	public static class Factory implements ValueGeneratorFactory {

		@Override
		public ValueGenerator createGenerator() {
			return new DomainGenerator();
		}
	}

	@Override
	public Integer getMaxIndex() {
		return ConstraintsData.data.get(getKey()).size();
	}

	@Override
	public void clearHints() {
		super.clearHints();
		valueList.clear();
		notAllowedValues.clear();
	}
}
