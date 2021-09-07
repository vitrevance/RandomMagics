package randommagics.customs;

public interface ILifeContainer {
	
	public int getCapacity();
	
	public int getEssence();
	
	/**
	 * Called when trying to fill life essence to container.
	 * Return: amount of essence that can be accepted (0 if no essence accepted, "amount" if all).
	 */
	public int canAccept(int amount);
	
	/**
	 * Called when trying to drain life essence from container.
	 * Return: amount of essence actually drained.
	 */
	public int drain(int amount);
}