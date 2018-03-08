package server.core.util.protocol;

public interface ServerOutputProtocol {

	/**
	 * Means that your chosen name is not accepted
	 * <p>
	 * NAME BAD
	 */
	void nameBad();

	/**
	 * Means that your name is accepted
	 * <p>
	 * NAME OK
	 */
	void nameOk();


}
