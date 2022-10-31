package be.ecam.ms_studenthelp.Interfaces;

import be.ecam.ms_studenthelp.Object.Author;

public interface IReaction {
	public void setValue(int value);

	public IPost getPost();
	public Author getAuthor();
	public int getValue();

	public int toInt();
}


