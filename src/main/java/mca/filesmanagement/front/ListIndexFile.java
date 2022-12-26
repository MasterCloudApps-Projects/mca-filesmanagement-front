package mca.filesmanagement.front;

import java.util.ArrayList;
import java.util.List;

public class ListIndexFile {

	private List<IndexFileResponse> lista = new ArrayList<>(0);
	
	public ListIndexFile() {
		super();
	}

	/**
	 * @return the lista
	 */
	public List<IndexFileResponse> getLista() {
		return lista;
	}

	/**
	 * @param lista the lista to set
	 */
	public void setLista(List<IndexFileResponse> lista) {
		this.lista = lista;
	}
}
