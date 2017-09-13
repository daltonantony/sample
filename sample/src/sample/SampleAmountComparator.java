package sample;

import java.util.Comparator;

public class SampleAmountComparator implements Comparator<SampleDetailsVO> {

	@Override
	public int compare(final SampleDetailsVO o1, final SampleDetailsVO o2) {
		return o1.getAmount().compareTo(o2.getAmount());
	}

}
