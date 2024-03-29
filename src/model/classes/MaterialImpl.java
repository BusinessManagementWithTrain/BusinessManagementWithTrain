package model.classes;


import exceptions.EqualsMaterialsException;
import model.interfaces.Material;

/**
 * Classe destinata all'implementazione del materiale, oggetto utilizzato 
 * principalmente per indicare la produzione delle varie aziende, cos� da 
 * semplificare la richiesta e l'invio dello stesso
 * 
 * @author Grasso Emanuele
 */

public class MaterialImpl implements Material {

	/*
	 * Come indicato dalla documentazione, ogni materiale 
	 * contiene le informazioni relative al nome del grezzo e del lavorato
	 */
	private final String rawMaterial;
	private final String processedMaterial;
	
	/**
	 * Il costruttore servir� per prendere in input il nome del materiale, al fine di 
	 * poter creare un materiale completo.
	 * 
	 * @param name
	 * @throws EqualsMaterialsException 
	 */
	public MaterialImpl(final String rawMaterial, final String processedMaterial) throws EqualsMaterialsException {
		if(rawMaterial.equals(processedMaterial)) {
			throw new EqualsMaterialsException();
		}
		
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
}