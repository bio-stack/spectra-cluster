package uk.ac.ebi.pride.spectracluster.engine;

import uk.ac.ebi.pride.spectracluster.cluster.ICluster;
import uk.ac.ebi.pride.spectracluster.similarity.ISimilarityChecker;

import java.util.Collection;

/**
 * This object does the clusters
 *
 * @author Johannes Griss
 * @author Steve Lewis
 * @author Rui Wang
 */
public interface IClusteringEngine {

    /**
     * Get the similarity check used
     *
     * @return an instance of similarity checker
     */
    public ISimilarityChecker getSimilarityChecker();


    /**
     * Get the similarity threshold used
     *
     * @return similarity threshold
     */
    public double getSimilarityThreshold();

    /**
     * Get clustered clusters sorted by MZ is useful
     *
     * @return !null list this will be sorted by mz a include clusters of all sizes
     */
    public Collection<ICluster> getClusters();

    /**
     * add some clusters
     */
    public void addClusters(ICluster... cluster);

    /**
     * clusters are merged in the internal collection
     *
     * @return true is  anything happened
     */
    public boolean processClusters();

    /**
     * total number of clusters including queued clustersToAdd
     *
     * @return Integer representing the number of clusters
     */
    public int size();
}
