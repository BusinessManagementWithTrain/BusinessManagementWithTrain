package model.classes;

import java.util.Objects;

import model.interfaces.Material;

/**
 * Classe destinata all'implementazione del materiale, oggetto utilizzato 
 * principalmente per indicare la produzione delle varie aziende, così da 
 * semplificare la richiesta e l'invio dello stesso
 * 
 * @author Grasso Emanuele
 */

public class MaterialImpl implements Material {

	/*
	 * Come indicato dalla documentazione, ogni materiale conterrà le informazioni relative
	 * al nome, due materiali verranno considerati uguali quando avranno lo stesso nome
	 */
	private final String name; 
	
	/**
	 * Il costruttore servirà per prendere in input il nome del materiale, al fine di 
	 * poter creare un materiale completo.
	 * 
	 * @param name
	 */
	public MaterialImpl(final String name) {
		this.name = name;
	}
	
	/*
	 * Consente di avere il riferimento al nome del materiale
	 * 
	 * @return il nome del materiale
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/*
	 * Come indicato nella documentazione, due materiali verranno considerati uguali
	 * quando avranno lo stesso nome
	 * 
	 * @param obj
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MaterialImpl other = (MaterialImpl) obj;
		return Objects.equals(this.name, other.name);
	}
}
