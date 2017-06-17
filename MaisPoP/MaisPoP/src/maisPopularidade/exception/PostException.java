package maisPopularidade.exception;


public class PostException extends SystemException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PostException() {
		super("Post invalido!");

	}

	public PostException(String string) {
		super(string);
	}

}
