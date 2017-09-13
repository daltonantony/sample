package sample;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

/** Assignment: Java 6 based file processor */
public class SampleProcessor {

	private static final Pattern TYPE_ID_MODE_PATTERN = Pattern.compile("[A-Z]{2}-[0-9]{6}-[A-Z]{1}");
	private static final Pattern NAME_PATTERN = Pattern.compile("[A-Za-z ]+");
	private static final Pattern AMOUNT_PATTERN = Pattern.compile("[0-9]+[.]{1}[0-9]{2}");
	private static final String DATE_FORMAT = "yyyy-MM-dd";

	public Map<String, Collection<?>> processDetails(final String fileLocation) {
		final List<SampleDetailsVO> voList = getSampleDetailsVOList(fileLocation);
		final Map<String, Collection<?>> mapOfDetails = getMapOfDetails(voList);
		return mapOfDetails;
	}

	public void printTopTwoContributorNamesBasedOnType(final String type, final String fileLocation) {
		final Map<String, Collection<?>> mapOfDetails = processDetails(fileLocation);
		@SuppressWarnings("unchecked")
		final List<SampleDetailsVO> voListForType = (List<SampleDetailsVO>) mapOfDetails.get(type);
		if (voListForType == null || voListForType.isEmpty()) {
			System.out.println("No vaid data available.");
			return;
		}

		Collections.sort(voListForType, new SampleAmountComparator());
		Collections.reverse(voListForType);

		System.out.println("The top 2 contributors for type [" + type + "] are:");
		int count = 0;
		for (final SampleDetailsVO vo : voListForType) {
			if (count > 1) {
				break;
			}
			System.out.println(vo.getName());
			count++;
		}
	}

	private List<SampleDetailsVO> getSampleDetailsVOList(final String fileLocation) {
		final List<SampleDetailsVO> voList = new ArrayList<SampleDetailsVO>();

		try {
			final List<String> fileContentLines = getFileContents(fileLocation);

			for (final String line : fileContentLines) {
				final String[] details = line.split(",");
				final SampleDetailsVO vo = getSampleDetailsVO(details);
				voList.add(vo);
			}
		} catch (final SampleException e) {
			e.printStackTrace();
		}
		return voList;
	}

	private Map<String, Collection<?>> getMapOfDetails(final List<SampleDetailsVO> voList) {
		final Set<SampleDetailsVO> duplicateIdItems = new HashSet<SampleDetailsVO>();
		final Set<SampleDetailsVO> uniqueIdItems = new TreeSet<SampleDetailsVO>(new SampleIdComparator());
		for (final SampleDetailsVO vo : voList) {
			if (!uniqueIdItems.add(vo)) {
				duplicateIdItems.add(vo);
			}
		}

		// in case not even a single instance of duplicate items should be added in unique list
		doAdditionalFiltering(duplicateIdItems, uniqueIdItems);

		voList.removeAll(duplicateIdItems);
		final List<SampleDetailsVO> listOfABItems = new ArrayList<SampleDetailsVO>();
		final List<SampleDetailsVO> listOfCDItems = new ArrayList<SampleDetailsVO>();
		for (final SampleDetailsVO vo : voList) {
			if ("AB".equals(vo.getType())) {
				listOfABItems.add(vo);
			} else if ("CD".equals(vo.getType())) {
				listOfCDItems.add(vo);
			}
		}

		final Map<String, Collection<?>> mapOfDetails = new HashMap<String, Collection<?>>();
		mapOfDetails.put("AB", listOfABItems);
		mapOfDetails.put("CD", listOfCDItems);
		mapOfDetails.put("XY", duplicateIdItems);
		return mapOfDetails;
	}

	private void doAdditionalFiltering(final Set<SampleDetailsVO> duplicateIdItems,
			final Set<SampleDetailsVO> uniqueIdItems) {
		final Set<SampleDetailsVO> additionalDuplicateItems = new HashSet<SampleDetailsVO>();
		for (final SampleDetailsVO dupVO : duplicateIdItems) {
			for (final SampleDetailsVO uniVO : uniqueIdItems) {
				if (uniVO.getId().equals(dupVO.getId())) {
					additionalDuplicateItems.add(uniVO);
				}
			}
		}
		uniqueIdItems.removeAll(additionalDuplicateItems);
		duplicateIdItems.addAll(additionalDuplicateItems);
	}

	private List<String> getFileContents(final String fileLocation) {
		final List<String> fileContentLines = new ArrayList<String>();
		BufferedReader br = null;
		FileReader fr = null;

		try {
			fr = new FileReader(fileLocation);
			br = new BufferedReader(fr);
			String currentLine;
			System.out.println("Reading contents from: " + fileLocation);

			while ((currentLine = br.readLine()) != null) {
				System.out.println(currentLine);
				fileContentLines.add(currentLine);
			}
			System.out.println("Completed reading file contents.");

		} catch (final IOException e) {
			System.out.println("Exception while either instantiating file reader or reading a file.");
			e.printStackTrace();
		} finally {
			try {
				if (fr != null) {
					fr.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (final IOException e) {
				System.out.println("Exception while closing file reader.");
				e.printStackTrace();
			}
		}

		return fileContentLines;
	}

	private void validate(final String[] details) throws SampleException {
		// alphanumeric part (sample valid value: AB-123001-M)
		if (!TYPE_ID_MODE_PATTERN.matcher(details[0]).matches()) {
			throw new SampleException("'Type - Id - Mode' format is invalid: " + details[0]);
		}

		// name (sample valid value: John Smith)
		if (!NAME_PATTERN.matcher(details[1]).matches()) {
			throw new SampleException("'Name' format is invalid: " + details[1]);
		}

		// dates (sample valid value: 23-08-2017)
		try {
			getDate(details[2]);
			getDate(details[3]);
		} catch (final ParseException e) {
			throw new SampleException("'Date' format is invalid: " + details[2] + " or " + details[3]);
		}

		// amount (sample valid value: 3500.00)
		if (!AMOUNT_PATTERN.matcher(details[4]).matches()) {
			throw new SampleException("'Amount' format is invalid: " + details[4]);
		}
	}

	private SampleDetailsVO getSampleDetailsVO(final String[] details) throws SampleException {
		validate(details);

		final SampleDetailsVO vo = new SampleDetailsVO();
		vo.setType(details[0].substring(0, 2));
		vo.setId(details[0].substring(3, 9));
		vo.setMode(details[0].substring(10, 11));
		vo.setName(details[1]);

		try {
			vo.setPaymentDate(getDate(details[2]));
			vo.setDueDate(getDate(details[3]));
		} catch (final ParseException e) {
			// won't occur, since date is already validated
		}

		vo.setAmount(new BigDecimal(details[4]));
		vo.setFine(calculateFine(vo));

		return vo;
	}

	private Date getDate(final String dateString) throws ParseException {
		final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		return dateFormat.parse(dateString);
	}

	private BigDecimal calculateFine(final SampleDetailsVO vo) {
		BigDecimal fine = BigDecimal.ZERO;
		if (vo.getPaymentDate().after(vo.getDueDate())) {
			fine = vo.getAmount().multiply(BigDecimal.valueOf(5)).divide(BigDecimal.valueOf(100), 2,
					RoundingMode.HALF_UP);
			vo.setFine(fine);
		}
		return fine;
	}

}
