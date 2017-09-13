package sample;

import java.util.Collection;
import java.util.Map;

public class SampleTest {

	public static void main(final String[] args) {
		final SampleProcessor processor = new SampleProcessor();
		// Give your local path here:
		final String fileLocation = "C:\\DATA\\eclipse\\forecast_workspace\\sample\\src\\sample\\sample_file.txt";
		final Map<String, Collection<?>> processedDetails = processor
				.processDetails(fileLocation);
		System.out.println("OUPUT:");
		System.out.println(processedDetails);

		System.out.println("Top 2 contributors:");
		processor.printTopTwoContributorNamesBasedOnType("CD", fileLocation);

		//System.out.println(SampleProcessor.TYPE_ID_MODE_PATTERN.matcher("AB-123001-M").matches());
		//System.out.println(SampleProcessor.NAME_PATTERN.matcher("Dalt Anto").matches());
		//System.out.println(SampleProcessor.AMOUNT_PATTERN.matcher("12345.25").matches());
	}

}
