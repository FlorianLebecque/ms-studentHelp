package be.ecam.ms_studenthelp.Object;

import be.ecam.ms_studenthelp.Interfaces.IPost;
import be.ecam.ms_studenthelp.Interfaces.IReaction;

public class Reaction implements IReaction {

	private final IPost post;
	private final Author author;
	private int value;

	public Reaction(IPost post, Author author, int value) {
		this.post = post;
		this.author = author;
		setValue(value);
	}

	public IPost getPost() { return post; }
	public Author getAuthor() { return author; }
	public int getValue() { return value; }

	public void setValue(int value) {
		if (value > 0) this.value = 1;
		if (value < 0) this.value = -1;
	}

	public int toInt() {
		return value;
	}
}

