/*
 * Copyright (c) 2013, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package org.glassfish.hk2.runlevel.tests.cancel;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

import org.glassfish.hk2.runlevel.RunLevel;

/**
 * @author jwells
 *
 */
@RunLevel(5)
public class DependsOnBlockingService {
    @Inject
    private BlockingService blockingService;
    
    private static boolean postConstructCalled = false;
    
    public static boolean getPostConstructCalled() {
        return postConstructCalled;
    }
    
    @PostConstruct
    public void postConstruct() {
        postConstructCalled = true;
    }
    
    public static void clear() {
        postConstructCalled = false;
    }

}
