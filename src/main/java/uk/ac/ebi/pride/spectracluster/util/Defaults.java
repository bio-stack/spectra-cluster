package uk.ac.ebi.pride.spectracluster.util;

import uk.ac.ebi.pride.spectracluster.consensus.ConcensusSpectrumBuilderFactory;
import uk.ac.ebi.pride.spectracluster.consensus.ConsensusSpectrum;
import uk.ac.ebi.pride.spectracluster.consensus.IConsensusSpectrumBuilder;
import uk.ac.ebi.pride.spectracluster.engine.EngineFactories;
import uk.ac.ebi.pride.spectracluster.engine.IClusteringEngine;
import uk.ac.ebi.pride.spectracluster.normalizer.IIntensityNormalizer;
import uk.ac.ebi.pride.spectracluster.normalizer.TotalIntensityNormalizer;
import uk.ac.ebi.pride.spectracluster.quality.IQualityScorer;
import uk.ac.ebi.pride.spectracluster.quality.SignalToNoiseChecker;
import uk.ac.ebi.pride.spectracluster.similarity.CombinedFisherIntensityTest;
import uk.ac.ebi.pride.spectracluster.similarity.FrankEtAlDotProduct;
import uk.ac.ebi.pride.spectracluster.similarity.ISimilarityChecker;
import uk.ac.ebi.pride.spectracluster.spectrum.IPeak;
import uk.ac.ebi.pride.spectracluster.util.comparator.ClusterComparator;
import uk.ac.ebi.pride.spectracluster.util.function.IFunction;
import uk.ac.ebi.pride.spectracluster.util.function.peak.BinnedHighestNPeakFunction;
import uk.ac.ebi.pride.spectracluster.util.function.peak.FractionTICPeakFunction;
import uk.ac.ebi.pride.spectracluster.util.function.peak.NullPeakFunction;

import java.util.List;

/**
 * uk.ac.ebi.pride.spectracluster.util.Defaults
 *
 * @author Steve Lewis
 * @date 20/05/13
 */
public class Defaults {

    public static final int DEFAULT_NUMBER_RECLUSTERING_PASSES = 2;

    public static final int DEFAULT_NUMBER_COMPARED_PEAKS = 15;

    public static final float DEFAULT_FRAGMENT_ION_TOLERANCE = 0.5F;

    /**
     * This default precursor tolerance is used by the incremental
     * clustering engines as window size
     */
    public static final float DEFAULT_PRECURSOR_ION_TOLERANCE = 2.0F;

    /**
     * Defines the similarity threshold above which spectra are added
     * to a cluster.
     */
    public static final double DEFAULT_SIMILARITY_THRESHOLD = 0.99;
    //public static final double DEFAULT_SIMILARITY_THRESHOLD = 48;

    /**
     * Defines the similarity threshold below which spectra are removed
     * from a cluster.
     * This is not used in greedy clustering
     */
    public static final double DEFAULT_RETAIN_THRESHOLD = 0.6; // not used in greedy clustering
    //public static final double DEFAULT_RETAIN_THRESHOLD = 45;

    public static final int DEFAULT_LARGE_BINNING_REGION = 1000;

    private static double similarityThreshold = DEFAULT_SIMILARITY_THRESHOLD;

    private static int largeBinningRegion = DEFAULT_LARGE_BINNING_REGION;

    private static int numberComparedPeaks = DEFAULT_NUMBER_COMPARED_PEAKS;

    private static float fragmentIonTolerance = DEFAULT_FRAGMENT_ION_TOLERANCE;

    private static double retainThreshold = DEFAULT_RETAIN_THRESHOLD;

    private static int numberReclusteringPasses = DEFAULT_NUMBER_RECLUSTERING_PASSES;

    private static final int DEFAULT_MAJOR_PEAKS = 5;

    private static int majorPeakCount = DEFAULT_MAJOR_PEAKS;

    private static float defaultPrecursorIonTolerance = DEFAULT_PRECURSOR_ION_TOLERANCE;

    public static int getMajorPeakCount() {
        return majorPeakCount;
    }

    public static void setMajorPeakCount(int majorPeakCount) {
        Defaults.majorPeakCount = majorPeakCount;
    }

    public static double getSimilarityThreshold() {
        return similarityThreshold;
    }

    public static int getLargeBinningRegion() {
        return largeBinningRegion;
    }

    public static int getNumberComparedPeaks() {
        return numberComparedPeaks;
    }

    public static float getFragmentIonTolerance() {
        return fragmentIonTolerance;
    }

    public static double getRetainThreshold() {
        return retainThreshold;
    }

    public static void setSimilarityThreshold(double similarityThreshold) {
        Defaults.similarityThreshold = similarityThreshold;
    }

    public static void setLargeBinningRegion(int largeBinningRegion) {
        Defaults.largeBinningRegion = largeBinningRegion;
    }

    public static void setNumberComparedPeaks(int numberComparedPeaks) {
        Defaults.numberComparedPeaks = numberComparedPeaks;
    }

    public static void setFragmentIonTolerance(float fragmentIonTolerance) {
        Defaults.fragmentIonTolerance = fragmentIonTolerance;
    }

    public static float getDefaultPrecursorIonTolerance() {
        return defaultPrecursorIonTolerance;
    }

    public static void setDefaultPrecursorIonTolerance(float defaultPrecursorIonTolerance) {
        Defaults.defaultPrecursorIonTolerance = defaultPrecursorIonTolerance;
    }

    /**
     * The retain threshold defines the similarity threshold below which
     * spectra are removed from a cluster.
     * @param retainThreshold
     */
    public static void setRetainThreshold(double retainThreshold) {
        Defaults.retainThreshold = retainThreshold;
    }

    public static int getNumberReclusteringPasses() {
        return numberReclusteringPasses;
    }

    public static void setNumberReclusteringPasses(final int pNumberReclusteringPasses) {
        numberReclusteringPasses = pNumberReclusteringPasses;
    }

    private static IConsensusSpectrumBuilder defaultConsensusSpectrumBuilder = null;

    /**
      * filter sees that we dont pass more then MaximialPeakFilter.DEFAULT_MAX_PEAKS peaks (100)
      */
   //  public static IPeakFilter defaultPeakFilter = IPeakFilter.NULL_FILTER; //   Take out for testing onlyu
     // public static IPeakFilter defaultPeakFilter = new MaximialPeakFilter(MaximialPeakFilter.DEFAULT_MAX_PEAKS); jg - this setting was active until 16-Dec-2014
    //private static IFunction<List<IPeak>, List<IPeak>> defaultPeakFilter = new BinnedHighestNPeakFunction(20, 100, 50); // keep 20 peaks per 100 m/z with a 50 m/z overlap
     // peak filtering is not needed in GreedyClustering
     private static IFunction<List<IPeak>, List<IPeak>> defaultPeakFilter = new NullPeakFunction();

     public static IFunction<List<IPeak>, List<IPeak>> getDefaultPeakFilter() {
         return defaultPeakFilter;
     }

     public static void setDefaultPeakFilter(IFunction<List<IPeak>, List<IPeak>> defaultPeakFilter) {
         Defaults.defaultPeakFilter = defaultPeakFilter;
     }

    /**
     * default filter to use before comparing two spectra
     */
    private static IFunction<List<IPeak>, List<IPeak>> defaultComparisonPeakFilter = new FractionTICPeakFunction(0.5F, 20);

    public static IFunction<List<IPeak>, List<IPeak>> getDefaultComparisonPeakFilter() {
        return defaultComparisonPeakFilter;
    }

    public static void setDefaultComparisonPeakFilter(IFunction<List<IPeak>, List<IPeak>> defaultComparisonPeakFilter) {
        Defaults.defaultComparisonPeakFilter = defaultComparisonPeakFilter;
    }

    /**
     * filter to use a consensus spectrum
     */
    private static ConcensusSpectrumBuilderFactory consensusFactory =  ConsensusSpectrum.FACTORY;

    public static ConcensusSpectrumBuilderFactory getConsensusFactory() {
        return consensusFactory;
    }

    public static void setConsensusFactory(ConcensusSpectrumBuilderFactory consensusFactory) {
        Defaults.consensusFactory = consensusFactory;
    }

    /**
     * this is the way to get a ConsensusSpectrumBuilder
     *
     * @return
     */
    public static IConsensusSpectrumBuilder getDefaultConsensusSpectrumBuilder() {
        return consensusFactory.getConsensusSpectrumBuilder();
    }


    //private static ISimilarityChecker defaultSimilarityChecker = new FrankEtAlDotProduct(getFragmentIonTolerance(), getNumberComparedPeaks());
    //private static ISimilarityChecker defaultSimilarityChecker = new FisherExactTest((float) getFragmentIonTolerance());
    private static ISimilarityChecker defaultSimilarityChecker = new CombinedFisherIntensityTest((float) getFragmentIonTolerance());

    public static ISimilarityChecker getDefaultSimilarityChecker() {
        return defaultSimilarityChecker;
    }

    public static void setDefaultSimilarityChecker(ISimilarityChecker defaultSimilarityChecker) {
        Defaults.defaultSimilarityChecker = defaultSimilarityChecker;
    }

    private static IQualityScorer defaultQualityScorer = new SignalToNoiseChecker();

    public static IQualityScorer getDefaultQualityScorer() {
        return defaultQualityScorer;
    }

    public static void setDefaultQualityScorer(IQualityScorer defaultQualityScorer) {
        Defaults.defaultQualityScorer = defaultQualityScorer;
    }

    private static ClusterComparator defaultSpectrumComparator = ClusterComparator.INSTANCE;

    public static ClusterComparator getDefaultSpectrumComparator() {
        return defaultSpectrumComparator;
    }

    public static void setDefaultSpectrumComparator(ClusterComparator dc) {
        defaultSpectrumComparator = dc;
    }

     public static IClusteringEngine getDefaultClusteringEngine() {
        ISimilarityChecker similarityChecker = getDefaultSimilarityChecker();
        final ClusterComparator spectrumComparator = getDefaultSpectrumComparator();
        final double st = getSimilarityThreshold();
        final double rt = getRetainThreshold();
        return EngineFactories.buildClusteringEngineFactory(similarityChecker, spectrumComparator, st, rt).buildInstance();

    }

    /**
     * Default intensity normalizer
     */
    private static IIntensityNormalizer defaultIntensityNormalizer = TotalIntensityNormalizer.DEFAULT;

    public static IIntensityNormalizer getDefaultIntensityNormalizer() {
        return defaultIntensityNormalizer;
    }

    public static void setDefaultIntensityNormalizer(IIntensityNormalizer defaultIntensityNormalizer) {
        Defaults.defaultIntensityNormalizer = defaultIntensityNormalizer;
    }
}
