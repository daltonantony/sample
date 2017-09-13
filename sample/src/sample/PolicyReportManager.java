package sample;

/**************************************************************************************************/
/*
 *
 * DO NOT ADD PACKAGE DECLARATION PLEASE NOTE: THIS SKELETON FILE MUST BE PLACED UNDER DEFAULT PACKAGE OF SRC FOLDER
 *
 * ALL THE CODING MUST BE DONE INSIDE THIS SINGLE .JAVA FILE ONLY DO NOT CREATE DEPENDENT CLASSES DO NOT MODIFY CODE
 * SKELETON DO NOT MODIFY: ACCESS SPECIFIERS, RETURN TYPES OR DATA TYPES, EXCEPTION CLAUSES, CLASS OR METHOD NAMES IN
 * THE SKELETON
 *
 *
 * YOU MUST CODE ON THE SAME PROJECT MAPPED IN EBOX VIEW THROUGHOUT THE ASSESSMENT CLICK ON 'Problem/Project Status'
 * BUTTON TO SEE THE PROJECT MAPPED IN EBOX
 *
 * YOU MUST CLICK ON 'SAVE PROJECT' BUTTON EVERY 10 MINUTES TO PERIODICALLY SAVE CTRL + S ALONE, WILL NOT BE SUFFICIENT
 * TO SAVE YOUR CODE IN EBOX CLICKING ON 'SAVE PROJECT' ENSURES NO LOSS OF CODE
 *
 * REACH OUT TO PROCTORS FOR ANY QUERIES
 *
 * Skeleton Version 2.0 Date Modified: 21-June-2016
 */
/*************************************************************************************************/

// imports here

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

/**
 * This class must be used to write the solution for the given requirement. No additional classes must be created
 *
 */
public class PolicyReportManager {

    public static final Pattern TYPE_ID_MODE_PATTERN = Pattern.compile("[A-Z]{2}/[A-Z]{1}[0-9]{4}-");
    public static final String DATE_PATTERN_MM_DD_YYYY = "MMddyyyy";
    public static final String DATE_PATTERN_MM_DD_YYYY_WITH_HYPHEN = "MM-dd-yyyy";
    public static final Map<String, Integer> finePercentMap = new HashMap<String, Integer>();

    static {
        finePercentMap.put("Q", 1);
        finePercentMap.put("H", 2);
        finePercentMap.put("Y", 5);
    }

    /**
     * Do not change the method signature
     *
     * @param filePath
     * @return Map
     * @throws ABCInsuranceCorpException
     */
    public Map processPolicyDetails(final String filePath) throws ABCInsuranceCorpException {

        final List<PolicyVO> policyDetails = getPolicyDetails(filePath);
        return getOutputMap(policyDetails);
    }

    /**
     * Do not change the method signature
     *
     * @param filePath
     * @param policyType
     * @return String[]
     * @throws ABCInsuranceCorpException
     */
    public String[] getTopFiveCustomers(final String filePath, final String policyType) throws ABCInsuranceCorpException {

        final Map<String, Object> map = processPolicyDetails(filePath);
        final List<PolicyVO> detailsForType = new ArrayList<PolicyVO>();
        detailsForType.addAll((Collection<? extends PolicyVO>) ((Map) map.get(policyType)).get("O"));
        detailsForType.addAll((Collection<? extends PolicyVO>) ((Map) map.get(policyType)).get("D"));

        if (!detailsForType.isEmpty()) {
            System.out.println("No values");
            return new String[1];
        }

        Collections.sort(detailsForType, new PolicyAmountComparator());
        Collections.reverse(detailsForType);

        System.out.println("Top 5 contributors:");

        int i = 0;
        final String[] names = new String[5];
        for (final PolicyVO vo : detailsForType) {
            if (i > 4) {
                break;
            }
            names[i] = vo.getCustomerName();
            i++;
        }

        return names;
    }

    private List<PolicyVO> getPolicyDetails(final String fileLocation) throws ABCInsuranceCorpException {
        final List<PolicyVO> policyDetails = new ArrayList<PolicyVO>();

        final List<String> fileContentLines = getFileContents(fileLocation);
        for (final String line : fileContentLines) {
            final String[] details = line.split(",");
            final PolicyVO vo = getPolicyVO(details);
            policyDetails.add(vo);
        }

        return policyDetails;
    }

    private Map<String, Object> getOutputMap(final List<PolicyVO> policyDetails) {
        final List<PolicyVO> duplicateDetails = new ArrayList<PolicyVO>();
        final Set<PolicyVO> uniqueDetails = new TreeSet<PolicyVO>(new PolicyNumberComparator());
        for (final PolicyVO vo : policyDetails) {
            if (!uniqueDetails.add(vo)) {
                duplicateDetails.add(vo);
            }
        }

        final Map<String, Object> outputMap = new HashMap<String, Object>();
        outputMap.put("MB", getInnerMap("MB", uniqueDetails));
        outputMap.put("HP", getInnerMap("HP", uniqueDetails));

        final Set<String> duplicatePolicyNumbers = new HashSet<String>();
        for (final PolicyVO vo : duplicateDetails) {
            duplicatePolicyNumbers.add(vo.getPolicyNumber());
        }
        outputMap.put("DUP", duplicatePolicyNumbers);

        return outputMap;
    }

    private Map<String, List<PolicyVO>> getInnerMap(final String type, final Set<PolicyVO> policyDetails) {
        final List<PolicyVO> delayedPolicyDetails = new ArrayList<PolicyVO>();
        final List<PolicyVO> onTimePolicyDetails = new ArrayList<PolicyVO>();

        for (final PolicyVO vo : policyDetails) {
            if (vo.getPolicyNumber().substring(0, 2).equals(type)) {
                addDelayedAndOnTimeItems(delayedPolicyDetails, onTimePolicyDetails, vo);
            }
        }

        final Map<String, List<PolicyVO>> innerMap = new HashMap<String, List<PolicyVO>>();
        innerMap.put("D", delayedPolicyDetails);
        innerMap.put("O", onTimePolicyDetails);

        return innerMap;
    }

    private void addDelayedAndOnTimeItems(final List<PolicyVO> delayedPolicyDetails,
                                          final List<PolicyVO> onTimePolicyDetails, final PolicyVO vo)
    {
        if (vo.getFine() == 0) {
            onTimePolicyDetails.add(vo);
        } else {
            delayedPolicyDetails.add(vo);
        }
    }

    private List<String> getFileContents(final String fileLocation) throws ABCInsuranceCorpException {
        final List<String> fileContentLines = new ArrayList<String>();
        BufferedReader br = null;
        FileReader fr = null;

        try {
            fr = new FileReader(fileLocation);
            br = new BufferedReader(fr);
            String currentLine;
            System.out.println("Reading from location: " + fileLocation);

            while ((currentLine = br.readLine()) != null) {
                System.out.println(currentLine);
                fileContentLines.add(currentLine);
            }
            System.out.println("Completed.");

        } catch (final IOException e) {
            throw new ABCInsuranceCorpException("Exception during file read.", e);
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (final IOException e) {
                throw new ABCInsuranceCorpException("Exception during file read.", e);
            }
        }

        return fileContentLines;
    }

    private PolicyVO getPolicyVO(final String[] details) throws ABCInsuranceCorpException {
        validate(details);

        final PolicyVO vo = new PolicyVO();
        vo.setPolicyNumber(details[0]);
        vo.setCustomerName(details[1]);
        vo.setSumAssurred(Integer.valueOf(details[2]));
        try {
            vo.setLastPremiumPaidDate(getDate(details[3], true));
            vo.setDueDate(getDate(details[4], true));
        } catch (final ParseException e) {
            throw new ABCInsuranceCorpException("Invalid date.");
        }
        vo.setFine(calculateFine(vo));

        return vo;
    }

    private float calculateFine(final PolicyVO vo) {
        if (vo.getLastPremiumPaidDate().after(vo.getDueDate())) {
            final String mode = vo.getPolicyNumber().substring(3, 4);
            return vo.getSumAssurred() * finePercentMap.get(mode) / 100;
        }
        return 0;
    }

    private void validate(final String[] details) throws ABCInsuranceCorpException {
        final String policyNumber = details[0];
        // alphanum part without date component (Eg: MB/Q1234-)
        final String alphaNumWithoutDate = policyNumber.substring(0, 9);
        if (!TYPE_ID_MODE_PATTERN.matcher(alphaNumWithoutDate).matches()) {
            throw new ABCInsuranceCorpException("Alphanum part of ID is not in valid format");
        }

        // policy start date component in the id (Eg: 12122012)
        final String dateComponentInId = policyNumber.substring(9);
        Date policyStartDate = null;
        try {
            policyStartDate = getDate(dateComponentInId, false);
        } catch (final ParseException e) {
            throw new ABCInsuranceCorpException("Policy Start Date is not in valid format", e);
        }

        // type validation
        final String type = policyNumber.substring(0, 2);
        if (!("MB".equals(type) || "HP".equals(type))) {
            throw new ABCInsuranceCorpException("Type is not valid.");
        }

        // mode validation
        final String mode = policyNumber.substring(3, 4);
        if (!("Q".equals(mode) || "H".equals(mode) || "Y".equals(mode))) {
            throw new ABCInsuranceCorpException("Mode is not valid.");
        }

        // policy start date validation
        Date lastPremiumPaymentDate = null;
        try {
            lastPremiumPaymentDate = getDate(details[3], true);
        } catch (final ParseException e) {
            throw new ABCInsuranceCorpException("Last premium payment date is not in valid format", e);
        }
        if (policyStartDate.after(lastPremiumPaymentDate)) {
            throw new ABCInsuranceCorpException("Policy start date is after Last premium payment date.");
        }

    }

    private Date getDate(final String dateString, final boolean hasHyphen) throws ParseException {
        if (hasHyphen) {
            final SimpleDateFormat dateFormatForPatternWithHyphen =
                    new SimpleDateFormat(DATE_PATTERN_MM_DD_YYYY_WITH_HYPHEN);
            return dateFormatForPatternWithHyphen.parse(dateString);
        } else {
            final SimpleDateFormat dateFormatForPatternWithoutHyphen = new SimpleDateFormat(DATE_PATTERN_MM_DD_YYYY);
            return dateFormatForPatternWithoutHyphen.parse(dateString);
        }
    }

    private class PolicyNumberComparator implements Comparator<PolicyVO> {

        @Override
        public int compare(final PolicyVO o1, final PolicyVO o2) {
            return o1.getPolicyNumber().compareTo(o2.getPolicyNumber());
        }

    }

    private class PolicyAmountComparator implements Comparator<PolicyVO> {

        @Override
        public int compare(final PolicyVO o1, final PolicyVO o2) {
            return BigDecimal.valueOf(o1.getSumAssurred()).compareTo(BigDecimal.valueOf(o2.getSumAssurred()));
        }

    }

}

/**
 * VO class supplied part of the Skeleton. Do not modify this class
 *
 */
class PolicyVO {

    private String policyNumber;
    private String customerName;
    private Date dueDate;
    private Date lastPremiumPaidDate;
    private float fine;
    private int sumAssurred;

    /**
     * @return the policyNumber
     */
    public String getPolicyNumber() {
        return policyNumber;
    }

    /**
     * @param policyNumber the policyNumber to set
     */
    public void setPolicyNumber(final String policyNumber) {
        this.policyNumber = policyNumber;
    }

    /**
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName the customerName to set
     */
    public void setCustomerName(final String customerName) {
        this.customerName = customerName;
    }

    /**
     * @return the dueDate
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * @param dueDate the dueDate to set
     */
    public void setDueDate(final Date dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * @return the lastPremiumPaidDate
     */
    public Date getLastPremiumPaidDate() {
        return lastPremiumPaidDate;
    }

    /**
     * @param lastPremiumPaidDate the lastPremiumPaidDate to set
     */
    public void setLastPremiumPaidDate(final Date lastPremiumPaidDate) {
        this.lastPremiumPaidDate = lastPremiumPaidDate;
    }

    /**
     * @return the fine
     */
    public float getFine() {
        return fine;
    }

    /**
     * @param fine the fine to set
     */
    public void setFine(final float fine) {
        this.fine = fine;
    }

    /**
     * @return the sumAssurred
     */
    public int getSumAssurred() {
        return sumAssurred;
    }

    /**
     * @param sumAssurred the sumAssurred to set
     */
    public void setSumAssurred(final int sumAssurred) {
        this.sumAssurred = sumAssurred;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("PolicyVO [policyNumber=");
        builder.append(policyNumber);
        builder.append(", customerName=");
        builder.append(customerName);
        builder.append(", dueDate=");
        builder.append(dueDate);
        builder.append(", lastPremiumPaidDate=");
        builder.append(lastPremiumPaidDate);
        builder.append(", fine=");
        builder.append(fine);
        builder.append(", sumAssurred=");
        builder.append(sumAssurred);
        builder.append("]");
        return builder.toString();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        boolean isEqual = false;
        final PolicyVO other = (PolicyVO) obj;
        if (this == obj) {
            isEqual = true;
        }
        if (this.policyNumber.equalsIgnoreCase(other.policyNumber)
                && this.lastPremiumPaidDate.equals(other.lastPremiumPaidDate) && this.dueDate.equals(other.dueDate)
                && this.fine == other.fine)
        {
            isEqual = true;
        }

        return isEqual;

    }
}

/**
 *
 * Exception class supplied part of the Skeleton. Do not modify this class
 *
 */

class ABCInsuranceCorpException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * @param message
     */
    public ABCInsuranceCorpException(final String message) {
        super(message);
    }

    /**
     * @param throwable
     */
    public ABCInsuranceCorpException(final Throwable throwable) {
        super(throwable);
    }

    /**
     * @param message
     * @param throwable
     */
    public ABCInsuranceCorpException(final String message, final Throwable throwable) {
        super(message, throwable);
    }
}
