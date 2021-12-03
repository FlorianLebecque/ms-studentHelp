package be.ecam.ms_studenthelp.Object;

import be.ecam.ms_studenthelp.Interfaces.IReaction;

public class Reaction implements IReaction {

	private final String postId;
	private final String authorId;
	private int value;

	public Reaction(String postId, String authorId, int value) {
		this.postId = postId;
		this.authorId = authorId;
		setValue(value);
	}

	public void setValue(int value) {
		if (value > 0) this.value = 1;
		if (value < 0) this.value = -1;
	}

	public String getPostId() { return postId; }
	public String getAuthorId() { return authorId; }
	public int getValue() { return value; }

	public String toString() {
		return String.format("%d from %s on %s", value, authorId, postId);
	}
	public int toInt() {
		return value;
	}
}

