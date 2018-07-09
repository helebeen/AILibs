package de.upb.crc901.automl.metamining;

import hasco.model.ComponentInstance;

/**
 * Used to score a given {@link ComponentInstance} based on meta feature of the
 * ComponentInstance and possibly also its application context.
 * 
 * @author Helena Graf
 *
 */
public interface IMetaMiner {

	/**
	 * Gives a score to the given {@link ComponentInstance} based on its meta
	 * features and possibly meta features of the application context as well. The
	 * score reflects an estimate of the quality of the (partial) solution the
	 * ComponentInstance represents.
	 * 
	 * @param componentInstance
	 *            The instance for which an estimate is to be made
	 * @return The estimated score
	 */
	public double score(ComponentInstance componentInstance);
}
