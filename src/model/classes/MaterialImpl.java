package model.classes;


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
	private final String rawMaterial;
	private final String processedMaterial;
	
	/**
	 * Il costruttore servirà per prendere in input il nome del materiale, al fine di 
	 * poter creare un materiale completo.
	 * 
	 * @param name
	 */
	public MaterialImpl(final String rawMaterial, final String processedMaterial) {
		this.rawMaterial 			= rawMaterial;
		this.processedMaterial 		= processedMaterial;
	}
	
	/*
	 * Consente di avere il riferimento al materiale grezzo
	 * 
	 * @return il materiale grezzo
	 */
	@Override
	public String getRawMaterial() {
		return this.rawMaterial;
	}
	
	/*
	 * Consente di avere il riferimento al materiale lavorato
	 * 
	 * @return il materiale lavorato
	 */
	@Override
	public String getProcessedMaterial() {
		return this.processedMaterial;
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
		return (this.processedMaterial.equals(other.rawMaterial) ||
				this.rawMaterial.equals(other.processedMaterial));
	}
}