/*
 *    ALBasicClassificationPerformanceEvaluator.java
 *    Copyright (C) 2016 Otto von Guericke University, Magdeburg, Germany
 *    @author Daniel Kottke (daniel.kottke@ovgu.de)
 *
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program. If not, see <http://www.gnu.org/licenses/>.
 *    
 */
package moa.evaluation;

import java.util.Arrays;

import moa.core.Example;
import moa.core.Measurement;
import weka.core.Instance;

/**
 * Active Learning Wrapper for BasicClassificationPerformanceEvaluator.
 *
 * @author Daniel Kottke (daniel.kottke@ovgu.de)
 * @version $Revision: 1 $
 */
public class ALBasicClassificationPerformanceEvaluator extends BasicClassificationPerformanceEvaluator implements ALEvaluator<Example<Instance>>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private int seenInstances = 0;

    private int acquiredInstances = 0;

   /**
     * Receives the information if a label has been acquired and increases counters.
     *
     * @param trainInst the instance that was previously considered
     * @param labelAcquired bool type which indicates if trainInst 
     *        was acquired by the active learner
     */
	@Override
	public void doLabelAcqReport(Example<Instance> trainInst, int labelAcquired) {
		this.seenInstances++;
		this.acquiredInstances += labelAcquired;
	}

   /**
     * Returns absolute number of acquired labels so far.
     */
	public int getAbsNumOfAcqInst(){
		return acquiredInstances;
	}

   /**
     * Returns relative number of acquired labels so far.
     */
	public float getRelNumOfAcqInst(){
		return ((float) acquiredInstances) / (float) seenInstances;
	}

	
	@Override
    public Measurement[] getPerformanceMeasurements() {
		Measurement[] measurements = super.getPerformanceMeasurements(); 
		measurements  = Arrays.copyOf(measurements, measurements.length + 2);
		measurements[measurements.length - 2] = new Measurement("Abs Number of Label Acquisitions", getAbsNumOfAcqInst());
		measurements[measurements.length - 1] = new Measurement("Rel Number of Label Acquisitions", getRelNumOfAcqInst());
		return measurements;
    }


}
