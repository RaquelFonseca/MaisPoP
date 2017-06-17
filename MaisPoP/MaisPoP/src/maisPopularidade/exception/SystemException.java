package maisPopularidade.exception;

public class SystemException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SystemException() {
		super("Erro!");

	}

	public SystemException(String string) {
		super(string);
	}


}
