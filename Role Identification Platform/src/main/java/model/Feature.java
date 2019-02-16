/*
 * The MIT License
 *
 * Copyright 2016 Claudio Souza.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author Claudio Davi
 */
public class Feature {

    private List<String> features = new ArrayList<>();
    private List<String> lastAdded = new ArrayList<>();
    private int id;

    /**
     * Gets the issue descriptions and filter it to the feature format. The
     * description is broken into lines and checked if it matches our grammar
     * with @ at the beginning. After that, we break it into text and value and
     * add the text into our feature vector.
     *
     * @param desc
     * @return list of features
     */
    public List<String> filterFeature(String desc) {
        List<String> feat = new ArrayList<>();
        String[] descFeatures;
        String lastInserted = "";
        if (!desc.isEmpty()) {
            desc = desc.toLowerCase(Locale.getDefault());
            descFeatures = desc.split("\\r?\\n");

            for (int i = 0; i < descFeatures.length; i++) {

                if (!descFeatures[i].trim().startsWith("@")) {
                    descFeatures[i] = "";
                } else {
                    String aux[] = descFeatures[i].split(":");

                    descFeatures[i] = aux[0].trim();
                }
            }

            for (String descFeature : descFeatures) {
                if (!descFeature.isEmpty() && !feat.contains(descFeature)) {

                    getFeatures().remove(descFeature);
                    feat.remove(descFeature);
                    feat.add(descFeature);

                }
            }
        }
        java.util.Collections.sort(feat);
        lastAdded = feat;
        return feat;
    }

    /**
     * Used to get the last features added. Deprecated since we found another
     * way to use the feature data
     *
     * @deprecated
     * @return
     */
    public List<String> getLastAdded() {
        return lastAdded;
    }

    /**
     * Gets the list of features inside the Feature Object and sorts it.
     *
     * @return a sorted list of features.
     */
    public List<String> getFeatures() {
        java.util.Collections.sort(features);
        return features;
    }

    /**
     * Only use this for filtered, ready to go features
     *
     * @param feat Feature list of Strings
     */
    public void setFeatures(List<String> feat) {
        /* processed features */
        List<String> procFeat = new ArrayList<>();

        feat.stream().forEach((s) -> {
            s = s.toLowerCase(Locale.getDefault());
            procFeat.add(s);
        });

        for (int i = 0; i < procFeat.size(); i++) {
            if (!procFeat.get(i).startsWith("@") || procFeat.get(i).contains(":")) {
                return;
            } else if (i == procFeat.size() - 1) {
                java.util.Collections.sort(procFeat);
                this.features = procFeat;
            }

        }
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

}
