/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.redhat.playground.console.commands;

import it.redhat.playground.console.TextUI;
import it.redhat.playground.console.support.IllegalParametersException;
import it.redhat.playground.distexec.Rotate;
import it.redhat.playground.domain.Value;
import org.infinispan.Cache;
import org.infinispan.distexec.DefaultExecutorService;
import org.infinispan.distexec.DistributedExecutorService;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class RotateConsoleCommand implements ConsoleCommand {

    private static final String COMMAND_NAME = "rotate";
    private final Cache<Long, Value> cache;

    public RotateConsoleCommand(Cache<Long, Value> cache) {
        this.cache = cache;
    }

    @Override
    public String command() {
        return COMMAND_NAME;
    }

    @Override
    public boolean execute(TextUI console, Iterator<String> args) throws IllegalParametersException {
        try {
            Integer offset = Integer.parseInt(args.next());

            long before = System.currentTimeMillis();

            DistributedExecutorService des = new DefaultExecutorService(cache);
            List<Future> results = des.submitEverywhere(new Rotate(offset));

            console.println("Rotated all strings of " + offset + " characters");
            for (Future result : results) {
                try {
                    console.println(result.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            long after = System.currentTimeMillis();
            console.println("Total time: " + (after - before));
        } catch (NumberFormatException e) {
            throw new IllegalParametersException("Expected usage: rotate <offset>\nValue for offset has to be a number. Example:\n rotate 10");
        } catch (NoSuchElementException e) {
            throw new IllegalParametersException("Expected usage: rotate <offset>");
        }
        return true;
    }

    @Override
    public void usage(TextUI console) {
        console.println(COMMAND_NAME + " <offset>");
        console.println("\t\tRotate every string in the grid by offset.");
    }
}
