package main;

//Modified version of geeksforgeeks.org implementation
public class TopStreamingArtists {
	private Artist first;
	
	public TopStreamingArtists() {
		first = null;
	}
	
	public boolean isEmpty() {
		return first == null;
	}
	
	public TopStreamingArtists insert(TopStreamingArtists list, String name) {
		Artist new_Artist = new Artist(name);
		new_Artist.next = null;

		// If the Linked List is empty, then make the new Artist as first
		if (list.isEmpty()) {
			list.first = new_Artist;
		} else {
			// Else traverse till the last Artist and insert the new_Artist there
			Artist last = list.first;
			while (last.next != null) {
				last = last.next;
			}

			// Insert the new_Artist at last Artist
			last.next = new_Artist;
		}
		
		return list;
	}

	public String printList(TopStreamingArtists list) {
		Artist currArtist = list.first;

		String s = "";
		// Traverse through the TopStreamingArtists
		while (currArtist != null) {
			// Print the name at current Artist
			s += (currArtist.name + "\n");

			// Go to next Artist
			currArtist = currArtist.next;
		}
		return s;
	}
}