/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.redhat.playground.service;

import it.redhat.playground.JDG;
import it.redhat.playground.domain.SimpleValue;
import it.redhat.playground.domain.Value;
import org.infinispan.Cache;

public class JDGService {

    private Cache<Long,Value> cache;

    public JDGService(Cache cache) {
        setCache(cache);
    }


    String address() {
        return "Address of this cluster node";
    }

    String get(Long key) {
        return cache.get(key).toString();
    }

    String hashtags() {
        return "List all registered hashtags feeding the grid";
    }

    String info() {
        return "Information on cache.";
    }

    String key() {
        return "Get a key which is affine to this cluster node";
    }

    String loadtest() {
        return "Load example values in the grid";
    }

    String local() {
        return "List all local valuesFromKeys";
    }

    String locate(Long id) {
        return JDG.locatePrimary(cache,id).toString();
    }

    String primary() {
        return "List all local valuesFromKeys for which this node is primary.";
    }

    String put(Long key, String value) {
        return cache.put(key, new SimpleValue(value)).toString();
    }


    public Cache getCache() {
        return cache;
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }
}

