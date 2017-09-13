package sample;

public class SampleException extends Exception {

	private static final long serialVersionUID = 1L;

	public SampleException(final String errorMessage) {
		super(errorMessage);
		System.out.println(errorMessage);
	}

}
