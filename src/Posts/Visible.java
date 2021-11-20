package Posts;

public interface Visible {
    /**
     * Set the status of published contents to true
     */
    void setVisible();

    /**
     * Set the status of published contents to false
     */
    void setInvisible();

    /**
     * Return the status of the visibility
     */
    boolean visibility();
}
