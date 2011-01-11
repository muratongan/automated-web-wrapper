/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package automatedwebwrapper.WebCrawler.UtilityClasses;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.clusterers.EM;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.AddCluster;
import weka.filters.unsupervised.attribute.StringToNominal;

/**
 *
 * @author murat
 */
public class UrlClusterer {
    private Collection<String> urls;

    public UrlClusterer(Collection<String> urls) {
        this.urls = urls;
    }

    public List<List<String>> getClusters() {
        List<List<String>> clusters = new ArrayList<List<String>>();

        FastVector atts = new FastVector();
        atts.addElement(new Attribute("url", (FastVector) null));
        for (int i=0; i<10; i++) {
            atts.addElement(new Attribute("att"+Integer.toString(i), (FastVector) null));
        }

        Instances data = new Instances("Data", atts, 0);
        
        for (String url : urls) {
            Instance instance = new Instance(11);
            UrlTokenizer tokenizer = new UrlTokenizer(url);
            String path = tokenizer.getPath();
            instance.setValue(data.attribute("url"), path);
            String[] parts = path.split("/");
            for (int i=0; i<10; i++) {
                if (parts.length > i && parts[i] != null) {
                    instance.setValue(data.attribute("att"+Integer.toString(i)), parts[i]);
                } else {
                    instance.setValue(data.attribute("att"+Integer.toString(i)), "");
                }
            }
            data.add(instance);
        }
        
        try {
            ArffSaver saver = new ArffSaver();
            saver.setInstances(data);
            saver.setFile(new File("first.txt"));
            saver.writeBatch();

            StringToNominal stringToNominal = new StringToNominal();
            stringToNominal.setInputFormat(data);
            stringToNominal.setOptions(weka.core.Utils.splitOptions("-R 2-11"));
            data = Filter.useFilter(data, stringToNominal);

            saver = new ArffSaver();
            saver.setInstances(data);
            saver.setFile(new File("input.txt"));
            saver.writeBatch();

            AddCluster filter = new AddCluster();
            filter.setOptions(weka.core.Utils.splitOptions("-W weka.clusterers.EM -I 1"));
            filter.setInputFormat(data);
            data = Filter.useFilter(data, filter);

            saver = new ArffSaver();
            saver.setInstances(data);
            saver.setFile(new File("output.txt"));
            saver.writeBatch();

            for (int i=0; i<data.attribute(data.numAttributes()-1).numValues(); i++) {
                List<String> cluster = new LinkedList<String>();
                for (int j=0; j<data.numInstances(); j++) {
                    if (data.instance(j).stringValue(data.attribute(data.numAttributes()-1)).equals(data.attribute(data.numAttributes()-1).value(i))) {
                        cluster.add(data.instance(j).stringValue(data.attribute(0)));
                    }
                }
                clusters.add(cluster);
            }
        } catch (Exception ex) {
            Logger.getLogger(UrlClusterer.class.getName()).log(Level.SEVERE, null, ex);
        }

        return clusters;
    }
}
