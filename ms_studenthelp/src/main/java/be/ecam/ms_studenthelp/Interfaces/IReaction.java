package be.ecam.ms_studenthelp.Interfaces;

public interface IReaction {
	public void setValue(int value);

	public String getPostId();
	public String getAuthorId();
	public int getValue();

	public String toString();
	public int toInt();
}


