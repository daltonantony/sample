package sample;

import java.util.Comparator;

public class SampleIdComparator implements Comparator<SampleDetailsVO> {

	@Override
	public int compare(final SampleDetailsVO o1, final SampleDetailsVO o2) {
		return o1.getId().compareTo(o2.getId());
	}

}
