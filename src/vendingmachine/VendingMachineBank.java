package vendingmachine;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import model.Note;

public class VendingMachineBank {

	private LinkedHashMap<Note, Integer> noteList;

	public LinkedHashMap<Note, Integer> getNoteList() {
		return noteList;
	}

	public void setNoteList(LinkedHashMap<Note, Integer> noteList) {
		this.noteList = noteList;
	}

	public VendingMachineBank() {
		noteList = new LinkedHashMap<>();
		for (Note note : Note.values()) {
			noteList.put(note, 2);
		}
	}

	public boolean validateNote(int userNote) {
		for (Map.Entry<Note, Integer> entry : noteList.entrySet()) {
			if (userNote == entry.getKey().getCost()) {
				entry.setValue(entry.getValue() + 1);
				return true;
			}
		}
		return false;
	}

	public HashMap<Note, Integer> refund(int userRemaining) {
		HashMap<Note, Integer> noteListBeforeRefund = new HashMap<Note, Integer>();
		noteListBeforeRefund.putAll(noteList);

		HashMap<Note, Integer> refund = new HashMap<Note, Integer>();
		for (HashMap.Entry<Note, Integer> note : noteList.entrySet()) {

			int quantity = userRemaining / note.getKey().getCost();
			if (quantity <= note.getValue() && quantity > 0) {
				note.setValue(note.getValue() - quantity);
				refund.put(note.getKey(), quantity);
				userRemaining -= quantity * note.getKey().getCost();
			}

		}
		// cannot refund
		if (userRemaining > 0) {
			noteList.clear();
			noteList.putAll(noteListBeforeRefund);
			return null;
		}
		System.out.println("Refund:");
		for (Map.Entry<Note, Integer> note : refund.entrySet()) {
			System.out.println(note.getKey().getCost() + ": " + note.getValue() + " note(s)");
		}
		return refund;
	}

}
